import { act, cleanup, render, screen } from "@testing-library/react";
import {
  Children,
  isValidElement,
  Suspense,
  type ReactElement,
  type ReactNode,
} from "react";
import { MemoryRouter, Navigate, Route, Routes } from "react-router-dom";
import { afterEach, describe, expect, it, vi } from "vitest";
import { AccessContext } from "@/app/access";
import { AuthContext } from "@/app/auth";
import { UnauthorizedBridge } from "@/app/UnauthorizedBridge";
import App from "@/App";
import defaultSettings from "@/config/defaultSettings";
import { notifyUnauthorized, setUnauthorizedHandler } from "@/lib/http/unauthorized";
import AdminLayout from "@/layouts/AdminLayout";
import { buildMenuRoutes, flattenRoutePaths, routeMeta, type AdminRouteMeta } from "./routeMeta";
import { router } from "./routes";

vi.mock("@/services/api/user", () => ({
  fetchCurrentUserInfo: vi.fn(),
}));

const capturedLayoutProps: Record<string, unknown>[] = [];

vi.mock("@ant-design/pro-components", async (importOriginal) => {
  const actual = await importOriginal<typeof import("@ant-design/pro-components")>();
  return {
    ...actual,
    ProLayout: (props: Record<string, unknown>) => {
      capturedLayoutProps.push(props);
      return <div data-testid="pro-layout">{props.children as ReactNode}</div>;
    },
    SettingDrawer: (props: Record<string, unknown>) => (
      <div
        data-testid="setting-drawer"
        data-disable-url-params={String(props.disableUrlParams)}
        data-enable-dark-theme={String(props.enableDarkTheme)}
        data-title={(props.settings as { title?: string } | undefined)?.title}
      />
    ),
  };
});

const authValue = {
  currentUser: {
    user: { id: 1, email: "a@b.c", nickname: "admin" },
    menuListPageDTO: [],
  } as never,
  loading: false,
  refreshUser: async () => undefined,
  setCurrentUser: vi.fn(),
  clearUser: vi.fn(),
};

const allowAll = {
  normalRouteFilter: () => true,
  operationFilter: () => true,
};

type RouteLike = {
  path?: string;
  handle?: { name?: string; access?: string };
  element?: ReactNode;
  children?: RouteLike[];
};

function snapshot(routes: AdminRouteMeta[]): Array<{
  path: string;
  name: string;
  access?: "normalRouteFilter";
  layout: boolean;
  hidden?: boolean;
  redirect?: string;
  children?: ReturnType<typeof snapshot>;
}> {
  return routes.map((route) => ({
    path: route.path,
    name: route.name,
    access: route.access,
    layout: route.layout,
    hidden: route.hidden,
    redirect: route.redirect,
    children: route.children ? snapshot(route.children) : undefined,
  }));
}

function elementName(node: ReactNode): string | undefined {
  if (!isValidElement(node)) return undefined;
  const type = node.type as { displayName?: string; name?: string };
  return type.displayName ?? type.name;
}

function findByElementName(routes: RouteLike[] | undefined, name: string): RouteLike | undefined {
  for (const route of routes ?? []) {
    if (elementName(route.element) === name) return route;
    const nested = findByElementName(route.children, name);
    if (nested) return nested;
  }
}

function findByPath(routes: RouteLike[] | undefined, path: string): RouteLike | undefined {
  for (const route of routes ?? []) {
    if (route.path === path) return route;
    const nested = findByPath(route.children, path);
    if (nested) return nested;
  }
}

function childSegments(route: RouteLike | undefined): string[] {
  return (route?.children ?? [])
    .map((child) => child.path)
    .filter((path): path is string => Boolean(path))
    .map((path) => path.split("/").filter(Boolean).at(-1) ?? path);
}

function isLazyElement(node: ReactNode): boolean {
  if (!isValidElement(node)) return false;
  if (node.type !== Suspense) return false;
  const page = (node.props as { children?: ReactNode }).children;
  if (!isValidElement(page)) return false;
  const type = page.type as { $$typeof?: symbol };
  return type.$$typeof === Symbol.for("react.lazy");
}

function typeNames(node: ReactNode): string[] {
  if (!isValidElement(node)) return [];
  const kids = (node.props as { children?: ReactNode }).children;
  return [elementName(node) ?? "Anonymous", ...Children.toArray(kids).flatMap(typeNames)];
}

describe("routeMeta", () => {
  it("preserves every admin path including nested groups", () => {
    expect(flattenRoutePaths(routeMeta)).toEqual([
      "/article",
      "/article/edit",
      "/comment",
      "/page",
      "/blogroll",
      "/category",
      "/tag",
      "/photo",
      "/user",
      "/account",
      "/account/settings",
      "/auth",
      "/auth/menu",
      "/auth/resource",
      "/auth/role",
      "/log",
      "/log/operation",
      "/log/login",
      "/setting",
      "/login",
      "/",
      "*",
    ]);
  });

  it("keeps name/access/layout/hidden/redirect for the full children tree", () => {
    expect(snapshot(routeMeta)).toEqual([
      { path: "/article", name: "文章管理", access: "normalRouteFilter", layout: true },
      { path: "/article/edit", name: "文章编辑", layout: false, hidden: true },
      { path: "/comment", name: "评论管理", access: "normalRouteFilter", layout: true },
      { path: "/page", name: "页面管理", access: "normalRouteFilter", layout: true },
      { path: "/blogroll", name: "友链管理", access: "normalRouteFilter", layout: true },
      { path: "/category", name: "分类管理", access: "normalRouteFilter", layout: true },
      { path: "/tag", name: "标签管理", access: "normalRouteFilter", layout: true },
      { path: "/photo", name: "相册管理", access: "normalRouteFilter", layout: true },
      { path: "/user", name: "用户管理", access: "normalRouteFilter", layout: true },
      {
        path: "/account",
        name: "",
        layout: true,
        hidden: true,
        children: [{ path: "/account/settings", name: "profileSettings", layout: true }],
      },
      {
        path: "/auth",
        name: "权限管理",
        access: "normalRouteFilter",
        layout: true,
        children: [
          { path: "/auth/menu", name: "菜单管理", layout: true },
          { path: "/auth/resource", name: "资源管理", access: "normalRouteFilter", layout: true },
          { path: "/auth/role", name: "角色管理", access: "normalRouteFilter", layout: true },
        ],
      },
      {
        path: "/log",
        name: "日志管理",
        access: "normalRouteFilter",
        layout: true,
        children: [
          { path: "/log/operation", name: "操作日志", access: "normalRouteFilter", layout: true },
          { path: "/log/login", name: "登录日志", access: "normalRouteFilter", layout: true },
        ],
      },
      { path: "/setting", name: "网站配置", access: "normalRouteFilter", layout: true },
      { path: "/login", name: "login", layout: false },
      { path: "/", name: "", layout: true, redirect: "/article" },
      { path: "*", name: "", layout: false },
    ]);
  });

  it("keeps /auth and /log parent access and nested children", () => {
    const auth = routeMeta.find((route) => route.path === "/auth");
    const log = routeMeta.find((route) => route.path === "/log");
    expect(auth?.access).toBe("normalRouteFilter");
    expect(auth?.name).toBe("权限管理");
    expect(auth?.children?.map((child) => [child.path, child.access])).toEqual([
      ["/auth/menu", undefined],
      ["/auth/resource", "normalRouteFilter"],
      ["/auth/role", "normalRouteFilter"],
    ]);
    expect(log?.children?.map((child) => child.path)).toEqual(["/log/operation", "/log/login"]);
  });

  it("builds nested ProLayout menus for /log when parent permission exists", () => {
    const menus = buildMenuRoutes(routeMeta, {
      normalRouteFilter: (route) =>
        ["日志管理", "操作日志", "登录日志", "文章管理"].includes(route.name),
    });
    const log = menus.find((route) => route.path === "/log");
    expect(log?.children?.map((child) => child.path)).toEqual(["/log/operation", "/log/login"]);
    expect(menus.some((route) => route.path === "/auth")).toBe(false);
    expect(menus.map((route) => route.path)).toEqual(["/article", "/log"]);
  });

  it("filters nested children by access and drops empty groups", () => {
    const menus = buildMenuRoutes(routeMeta, {
      normalRouteFilter: (route) => ["日志管理", "操作日志", "文章管理"].includes(route.name),
    });
    const log = menus.find((route) => route.path === "/log");
    expect(log?.children?.map((child) => child.path)).toEqual(["/log/operation"]);
    expect(menus.some((route) => route.path === "/account")).toBe(false);
    expect(menus.some((route) => route.path === "/login")).toBe(false);
  });
});

describe("router", () => {
  it("uses createBrowserRouter with basename /admin", () => {
    expect(router.basename).toBe("/admin");
  });

  it("wraps public login and protected layout/false routes for useMatches", () => {
    const bridge = router.routes[0];
    expect(elementName(bridge?.element)).toBe("UnauthorizedBridge");
    const login = findByPath(bridge?.children, "/login") ?? findByPath(bridge?.children, "login");
    const protectedRoute = findByElementName(bridge?.children, "ProtectedRoute");
    const layout = findByElementName(protectedRoute?.children, "AdminLayout");
    expect(login).toBeTruthy();
    expect(protectedRoute).toBeTruthy();
    expect(layout).toBeTruthy();
    expect(findByElementName(protectedRoute?.children, "UnauthorizedBridge")).toBeUndefined();
    expect(findByPath(protectedRoute?.children, "/login")).toBeUndefined();

    const article = findByPath(layout?.children, "/article");
    expect(article?.handle).toMatchObject({ name: "文章管理", access: "normalRouteFilter" });
    expect(isLazyElement(article?.element)).toBe(true);

    const auth = findByPath(layout?.children, "/auth");
    expect(auth?.handle).toMatchObject({ name: "权限管理", access: "normalRouteFilter" });
    expect(auth?.element).toBeUndefined();
    expect(childSegments(auth)).toEqual(["menu", "resource", "role"]);
    expect(findByPath(auth?.children, "menu")?.handle).toMatchObject({ name: "菜单管理" });
    expect(findByPath(auth?.children, "menu")?.handle?.access).toBeUndefined();

    const log = findByPath(layout?.children, "/log");
    expect(log?.handle).toMatchObject({ name: "日志管理", access: "normalRouteFilter" });
    expect(childSegments(log)).toEqual(["operation", "login"]);

    const account = findByPath(layout?.children, "/account");
    expect(account?.handle).toMatchObject({ name: "" });
    expect(childSegments(account)).toEqual(["settings"]);

    const redirect = findByPath(layout?.children, "/");
    expect(isValidElement(redirect?.element) && redirect?.element.type === Navigate).toBe(true);
    expect((redirect?.element as ReactElement).props.to).toBe("/article");
    expect((redirect?.element as ReactElement).props.replace).toBe(true);

    const edit = findByPath(protectedRoute?.children, "/article/edit");
    expect(edit?.handle).toMatchObject({ name: "文章编辑" });
    expect(edit?.handle?.access).toBeUndefined();
    expect(isLazyElement(edit?.element)).toBe(true);
    expect(findByPath(layout?.children, "/article/edit")).toBeUndefined();

    const wildcard = findByPath(protectedRoute?.children, "*");
    expect(isLazyElement(wildcard?.element)).toBe(true);
    expect(findByPath(layout?.children, "*")).toBeUndefined();
  });
});

describe("UnauthorizedBridge", () => {
  afterEach(() => {
    cleanup();
    setUnauthorizedHandler(undefined);
  });

  function renderBridge() {
    return render(
      <AuthContext.Provider value={authValue}>
        <MemoryRouter initialEntries={["/article"]}>
          <Routes>
            <Route element={<UnauthorizedBridge />}>
              <Route path="/article" element={<div>article</div>} />
              <Route path="/login" element={<div>login</div>} />
            </Route>
          </Routes>
        </MemoryRouter>
      </AuthContext.Provider>,
    );
  }

  it("registers a replace navigate handler and cleans it up", () => {
    const { unmount } = renderBridge();
    expect(screen.getByText("article")).toBeInTheDocument();
    act(() => notifyUnauthorized("/login"));
    expect(screen.getByText("login")).toBeInTheDocument();
    unmount();
    expect(() => notifyUnauthorized("/login")).not.toThrow();
  });

  it("clears the current user before navigating on 401", () => {
    renderBridge();
    authValue.clearUser.mockClear();
    act(() => notifyUnauthorized("/login"));
    expect(authValue.clearUser).toHaveBeenCalledTimes(1);
    expect(screen.getByText("login")).toBeInTheDocument();
  });
});

describe("AdminLayout", () => {
  afterEach(() => {
    cleanup();
    capturedLayoutProps.length = 0;
  });

  it("keeps ProLayout chrome, nested menus, avatar dropdown, SettingDrawer and Footer", () => {
    render(
      <AuthContext.Provider value={authValue}>
        <AccessContext.Provider value={allowAll as never}>
          <MemoryRouter initialEntries={["/article"]}>
            <AdminLayout />
          </MemoryRouter>
        </AccessContext.Provider>
      </AuthContext.Provider>,
    );

    const props = capturedLayoutProps.at(-1);
    expect(props).toBeTruthy();
    expect(props?.title).toBe(defaultSettings.title);
    expect(props?.menuHeaderRender).toBeUndefined();
    expect(props?.layoutBgImgList).toEqual([
      {
        src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr",
        left: 85,
        bottom: 100,
        height: "303px",
      },
      {
        src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr",
        bottom: -68,
        right: -45,
        height: "303px",
      },
      {
        src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr",
        bottom: 0,
        left: 0,
        width: "331px",
      },
    ]);
    const menus = (props?.route as { routes: AdminRouteMeta[] }).routes;
    const log = menus.find((route) => route.path === "/log");
    expect(log?.children?.map((child) => child.path)).toEqual(["/log/operation", "/log/login"]);
    expect(typeof props?.menuItemRender).toBe("function");
    expect(typeof props?.onMenuHeaderClick).toBe("function");
    expect(typeof props?.footerRender).toBe("function");
    expect(props?.avatarProps).toMatchObject({
      title: expect.anything(),
    });
    expect(typeof (props?.avatarProps as { render?: unknown }).render).toBe("function");
    expect(screen.getByTestId("setting-drawer")).toHaveAttribute(
      "data-title",
      defaultSettings.title,
    );
    expect(screen.getByTestId("setting-drawer")).toHaveAttribute("data-disable-url-params", "true");
    expect(screen.getByTestId("setting-drawer")).toHaveAttribute("data-enable-dark-theme", "true");
    const link = (props?.menuItemRender as (item: { path?: string }, dom: ReactNode) => ReactNode)(
      { path: "/article" },
      <span>文章管理</span>,
    );
    expect(isValidElement(link) && (link.props as { to?: string }).to).toBe("/article");
  });
});

describe("App", () => {
  it("wraps zh-CN providers around RouterProvider", () => {
    const tree = App();
    const names = typeNames(tree);
    expect(names).toEqual(expect.arrayContaining([
      "HelmetProvider",
      "LocaleProvider",
      "LocaleIntlProvider",
      "App",
      "Helmet",
      "AuthProvider",
      "AccessProvider",
    ]));
    expect(names.some((name) => name.startsWith("RouterProvider"))).toBe(true);
    expect(defaultSettings.title).toBe("Jewix博客系统后台管理");
  });
});
