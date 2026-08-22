import { Navigate, Outlet, useLocation, useMatches } from "react-router-dom";
import { useAuthorization } from "@/app/access";
import { useAuth } from "@/app/auth";

type RouteHandle = {
  name?: string;
  access?: "normalRouteFilter";
};

export function ProtectedRoute() {
  const { currentUser, loading } = useAuth();
  const access = useAuthorization();
  const location = useLocation();
  const matches = useMatches();
  if (loading) return null;
  if (!currentUser) {
    const redirect = encodeURIComponent(location.pathname + location.search);
    return <Navigate to={`/login?redirect=${redirect}`} replace />;
  }
  const denied = matches.some((match) => {
    const handle = match.handle as RouteHandle | undefined;
    return (
      handle?.access === "normalRouteFilter" &&
      !access.normalRouteFilter({ name: handle.name })
    );
  });
  if (denied) {
    return <Navigate to="/article" replace />;
  }
  return <Outlet />;
}
