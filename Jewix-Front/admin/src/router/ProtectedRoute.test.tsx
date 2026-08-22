import { cleanup, render, screen } from "@testing-library/react";
import {
  createMemoryRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";
import { afterEach, describe, expect, it, vi } from "vitest";

vi.mock("@/services/api/user", () => ({
  fetchCurrentUserInfo: vi.fn(),
}));

import { AccessContext } from "@/app/access";
import { AuthContext } from "@/app/auth";
import { ProtectedRoute } from "./ProtectedRoute";

const authValue = {
  currentUser: { user: { id: 1 }, menuListPageDTO: [] } as never,
  loading: false,
  refreshUser: async () => undefined,
  setCurrentUser: vi.fn(),
  clearUser: vi.fn(),
};

type AccessStub = {
  normalRouteFilter: (route: { name?: string }) => boolean;
  operationFilter: (route?: { name?: string }, operation?: string) => boolean;
};

const allowAll: AccessStub = {
  normalRouteFilter: () => true,
  operationFilter: () => true,
};

const denyAll: AccessStub = {
  normalRouteFilter: () => false,
  operationFilter: () => false,
};

const UNAUTHENTICATED = Symbol("unauthenticated");

function accessFor(allowedNames: string[]): AccessStub {
  return {
    normalRouteFilter: (route: { name?: string }) => allowedNames.includes(route.name ?? ""),
    operationFilter: () => true,
  };
}

function renderTree(
  initialPath: string,
  access: AccessStub = allowAll,
  currentUser: unknown = authValue.currentUser,
) {
  const resolvedUser = currentUser === UNAUTHENTICATED ? undefined : currentUser;
  const router = createMemoryRouter(
    createRoutesFromElements(
      <>
        <Route path="/login" element={<div>login</div>} />
        <Route element={<ProtectedRoute />}>
          {/* /article is the denial redirect target; leave it unguarded in this fixture */}
          <Route path="/article" element={<div>article</div>} />
          <Route path="/article/edit" handle={{ name: "文章编辑" }} element={<div>article-edit</div>} />
          <Route
            path="/user"
            handle={{ name: "用户管理", access: "normalRouteFilter" }}
            element={<div>user</div>}
          />
          <Route
            path="/comment"
            handle={{ name: "评论管理", access: "normalRouteFilter" }}
            element={<div>comment</div>}
          />
          <Route path="/auth" handle={{ name: "权限管理", access: "normalRouteFilter" }}>
            <Route path="menu" handle={{ name: "菜单管理" }} element={<div>auth-menu</div>} />
            <Route
              path="resource"
              handle={{ name: "资源管理", access: "normalRouteFilter" }}
              element={<div>auth-resource</div>}
            />
          </Route>
          <Route path="/log" handle={{ name: "日志管理", access: "normalRouteFilter" }}>
            <Route
              path="operation"
              handle={{ name: "操作日志", access: "normalRouteFilter" }}
              element={<div>log-operation</div>}
            />
          </Route>
          <Route path="*" element={<div>not-found</div>} />
        </Route>
      </>,
    ),
    { initialEntries: [initialPath] },
  );

  const view = render(
    <AuthContext.Provider value={{ ...authValue, currentUser: resolvedUser as never }}>
      <AccessContext.Provider value={access as never}>
        <RouterProvider router={router} />
      </AccessContext.Provider>
    </AuthContext.Provider>,
  );
  return { ...view, router };
}

describe("ProtectedRoute", () => {
  afterEach(() => {
    cleanup();
  });

  it("redirects unauthenticated users and preserves the destination", () => {
    const { router } = renderTree("/article", allowAll, UNAUTHENTICATED);
    expect(screen.getByText("login")).toBeInTheDocument();
    expect(router.state.location.pathname).toBe("/login");
    expect(router.state.location.search).toBe("?redirect=%2Farticle");
  });

  it("encodes pathname and search into the login redirect", () => {
    const { router } = renderTree("/article?tab=draft", allowAll, UNAUTHENTICATED);
    expect(screen.getByText("login")).toBeInTheDocument();
    expect(router.state.location.pathname).toBe("/login");
    expect(router.state.location.search).toBe(
      `?redirect=${encodeURIComponent("/article?tab=draft")}`,
    );
  });

  it("blocks leaf routes when their own normalRouteFilter fails", () => {
    renderTree("/user", denyAll);
    expect(screen.getByText("article")).toBeInTheDocument();
    expect(screen.queryByText("user")).not.toBeInTheDocument();
  });

  it("allows leaf /user when normalRouteFilter passes", () => {
    renderTree("/user", accessFor(["用户管理"]));
    expect(screen.getByText("user")).toBeInTheDocument();
  });

  it("denies ordinary leaf /comment without 评论管理", () => {
    renderTree("/comment", accessFor(["文章管理"]));
    expect(screen.getByText("article")).toBeInTheDocument();
    expect(screen.queryByText("comment")).not.toBeInTheDocument();
  });

  it("allows ordinary leaf /comment with 评论管理", () => {
    renderTree("/comment", accessFor(["评论管理"]));
    expect(screen.getByText("comment")).toBeInTheDocument();
  });

  it("denies /auth/menu when parent 权限管理 is missing even if leaf has no access handle", () => {
    renderTree("/auth/menu", accessFor(["菜单管理"]));
    expect(screen.getByText("article")).toBeInTheDocument();
    expect(screen.queryByText("auth-menu")).not.toBeInTheDocument();
  });

  it("allows /auth/menu when parent 权限管理 passes", () => {
    renderTree("/auth/menu", accessFor(["权限管理", "菜单管理"]));
    expect(screen.getByText("auth-menu")).toBeInTheDocument();
  });

  it("allows authenticated /article/edit without route-level access handle", () => {
    renderTree("/article/edit", denyAll);
    expect(screen.getByText("article-edit")).toBeInTheDocument();
  });

  it("keeps authenticated wildcard * inside the protected branch", () => {
    renderTree("/missing", allowAll);
    expect(screen.getByText("not-found")).toBeInTheDocument();
  });
});
