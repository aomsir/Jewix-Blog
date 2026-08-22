import {
  ApiOutlined,
  AuditOutlined,
  BookOutlined,
  CommentOutlined,
  FileTextOutlined,
  FolderOpenOutlined,
  IdcardOutlined,
  LoginOutlined,
  MenuOutlined,
  PicCenterOutlined,
  SecurityScanOutlined,
  SettingOutlined,
  TagOutlined,
  TeamOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { lazy, type ComponentType, type LazyExoticComponent, type ReactNode } from "react";
import { useMatches } from "react-router-dom";

export function useRouteMeta() {
  const matches = useMatches();
  for (let i = matches.length - 1; i >= 0; i -= 1) {
    const handle = matches[i]?.handle as { name?: string } | undefined;
    if (handle?.name) {
      return { name: handle.name };
    }
  }
  return { name: "" };
}

export type AdminRouteMeta = {
  path: string;
  name: string;
  icon?: ReactNode;
  hidden?: boolean;
  layout: boolean;
  access?: "normalRouteFilter";
  component?: LazyExoticComponent<ComponentType>;
  redirect?: string;
  children?: AdminRouteMeta[];
};

export function flattenRoutePaths(routes: AdminRouteMeta[]): string[] {
  return routes.flatMap((route) => [route.path, ...flattenRoutePaths(route.children ?? [])]);
}

export function buildMenuRoutes(
  routes: AdminRouteMeta[],
  access: { normalRouteFilter: (route: { name: string }) => boolean },
): AdminRouteMeta[] {
  return routes
    .filter((route) => route.layout && !route.hidden && !route.redirect && route.path !== "*")
    .filter((route) => !route.access || access.normalRouteFilter({ name: route.name }))
    .map((route) => ({
      ...route,
      children: route.children
        ? buildMenuRoutes(
            route.children.map((child) => ({ ...child, layout: true })),
            access,
          )
        : undefined,
    }))
    .filter((route) => !route.children || route.children.length > 0 || Boolean(route.component));
}

export const routeMeta: AdminRouteMeta[] = [
  {
    path: "/article",
    name: "文章管理",
    icon: <BookOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/article")),
  },
  {
    path: "/article/edit",
    name: "文章编辑",
    layout: false,
    hidden: true,
    component: lazy(() => import("@/pages/article/edit")),
  },
  {
    path: "/comment",
    name: "评论管理",
    icon: <CommentOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/comment")),
  },
  {
    path: "/page",
    name: "页面管理",
    icon: <BookOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/page")),
  },
  {
    path: "/blogroll",
    name: "友链管理",
    icon: <TeamOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/blogroll")),
  },
  {
    path: "/category",
    name: "分类管理",
    icon: <PicCenterOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/category")),
  },
  {
    path: "/tag",
    name: "标签管理",
    icon: <TagOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/tag")),
  },
  {
    path: "/photo",
    name: "相册管理",
    icon: <FolderOpenOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/photo")),
  },
  {
    path: "/user",
    name: "用户管理",
    icon: <UserOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/user")),
  },
  {
    path: "/account",
    name: "",
    layout: true,
    hidden: true,
    children: [
      {
        path: "/account/settings",
        name: "profileSettings",
        layout: true,
        component: lazy(() => import("@/pages/account/settings")),
      },
    ],
  },
  {
    path: "/auth",
    name: "权限管理",
    icon: <SecurityScanOutlined />,
    layout: true,
    access: "normalRouteFilter",
    children: [
      {
        path: "/auth/menu",
        name: "菜单管理",
        icon: <MenuOutlined />,
        layout: true,
        component: lazy(() => import("@/pages/auth/menu")),
      },
      {
        path: "/auth/resource",
        name: "资源管理",
        icon: <ApiOutlined />,
        layout: true,
        access: "normalRouteFilter",
        component: lazy(() => import("@/pages/auth/resource")),
      },
      {
        path: "/auth/role",
        name: "角色管理",
        icon: <IdcardOutlined />,
        layout: true,
        access: "normalRouteFilter",
        component: lazy(() => import("@/pages/auth/role")),
      },
    ],
  },
  {
    path: "/log",
    name: "日志管理",
    icon: <FileTextOutlined />,
    layout: true,
    access: "normalRouteFilter",
    children: [
      {
        path: "/log/operation",
        name: "操作日志",
        icon: <AuditOutlined />,
        layout: true,
        access: "normalRouteFilter",
        component: lazy(() => import("@/pages/log/operation")),
      },
      {
        path: "/log/login",
        name: "登录日志",
        icon: <LoginOutlined />,
        layout: true,
        access: "normalRouteFilter",
        component: lazy(() => import("@/pages/log/login")),
      },
    ],
  },
  {
    path: "/setting",
    name: "网站配置",
    icon: <SettingOutlined />,
    layout: true,
    access: "normalRouteFilter",
    component: lazy(() => import("@/pages/setting")),
  },
  {
    path: "/login",
    name: "login",
    layout: false,
    component: lazy(() => import("@/pages/login")),
  },
  {
    path: "/",
    name: "",
    layout: true,
    redirect: "/article",
  },
  {
    path: "*",
    name: "",
    layout: false,
    component: lazy(() => import("@/pages/404")),
  },
];
