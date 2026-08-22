import { useEffect, type ReactNode } from "react";
import { useNavigate } from "react-router-dom";

type RouteNameFilter = (route: { name?: string }) => boolean;

export function getArticleEditFallback(normalRouteFilter: RouteNameFilter) {
  if (normalRouteFilter({ name: "文章编辑" })) {
    return null;
  }
  return { to: "/article", replace: true as const };
}

export function ArticleEditAccessGate({
  filter,
  children,
}: {
  filter: RouteNameFilter;
  children: ReactNode;
}) {
  const navigate = useNavigate();
  const fallback = getArticleEditFallback(filter);
  useEffect(() => {
    if (fallback) {
      navigate(fallback.to, { replace: fallback.replace });
    }
  }, [fallback, navigate]);
  if (fallback) {
    return null;
  }
  return children;
}
