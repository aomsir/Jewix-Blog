import { Suspense } from "react";
import { createBrowserRouter, Navigate, type RouteObject } from "react-router-dom";
import { UnauthorizedBridge } from "@/app/UnauthorizedBridge";
import AdminLayout from "@/layouts/AdminLayout";
import { ProtectedRoute } from "./ProtectedRoute";
import { routeMeta, type AdminRouteMeta } from "./routeMeta";

function toRelativeChildPath(parentPath: string, childPath: string) {
  const prefix = parentPath.endsWith("/") ? parentPath : `${parentPath}/`;
  return childPath.startsWith(prefix) ? childPath.slice(prefix.length) : childPath.replace(/^\//, "");
}

function metaToRoute(route: AdminRouteMeta): RouteObject {
  const handle = { name: route.name, access: route.access };
  if (route.children?.length) {
    return {
      path: route.path,
      handle,
      children: route.children.map((child) => {
        const childRoute = metaToRoute(child);
        return { ...childRoute, path: toRelativeChildPath(route.path, child.path) };
      }),
    };
  }
  if (route.redirect) {
    return { path: route.path, handle, element: <Navigate to={route.redirect} replace /> };
  }
  const Page = route.component!;
  return {
    path: route.path,
    handle,
    element: (
      <Suspense fallback={null}>
        <Page />
      </Suspense>
    ),
  };
}

const layoutRoutes = routeMeta.filter(
  (route) => route.layout && route.path !== "/login" && route.path !== "*",
);
const layoutFalseProtected = routeMeta.filter(
  (route) => !route.layout && route.path !== "/login",
);
const loginRoute = routeMeta.find((route) => route.path === "/login")!;

const routeObjects: RouteObject[] = [
  {
    element: <UnauthorizedBridge />,
    children: [
      metaToRoute(loginRoute),
      {
        element: <ProtectedRoute />,
        children: [
          {
            element: <AdminLayout />,
            children: layoutRoutes.map(metaToRoute),
          },
          ...layoutFalseProtected.map(metaToRoute),
        ],
      },
    ],
  },
];

export const router = createBrowserRouter(routeObjects, { basename: "/admin" });
