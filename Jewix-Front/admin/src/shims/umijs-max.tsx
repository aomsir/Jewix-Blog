import type { ReactNode } from "react";
import { createElement } from "react";
import { Helmet as HelmetAsync } from "react-helmet-async";
import { Link as RouterLink } from "react-router-dom";

export { useAccess, useModel } from "./umi-compat";
export { request, type RequestConfig, type RunTimeLayoutConfig } from "./umi-request";

const historyListeners = new Set<() => void>();

export const history = {
  push(to: string | { pathname?: string; search?: string }) {
    const path = typeof to === "string" ? to : `${to.pathname ?? ""}${to.search ?? ""}`;
    window.history.pushState({}, "", `/admin${path.startsWith("/") ? path : `/${path}`}`);
    historyListeners.forEach((listener) => listener());
  },
  replace(to: string | { pathname?: string; search?: string }) {
    const path = typeof to === "string" ? to : `${to.pathname ?? ""}${to.search ?? ""}`;
    window.history.replaceState({}, "", `/admin${path.startsWith("/") ? path : `/${path}`}`);
    historyListeners.forEach((listener) => listener());
  },
  back() {
    window.history.back();
  },
  listen(listener: () => void) {
    historyListeners.add(listener);
    return () => historyListeners.delete(listener);
  },
  get location() {
    return {
      pathname: window.location.pathname.replace(/^\/admin/, "") || "/",
      search: window.location.search,
      hash: window.location.hash,
    };
  },
};

export const Link = RouterLink;
export const Helmet = HelmetAsync;

export function useRouteProps() {
  return { name: "" };
}

export function useIntl() {
  return {
    formatMessage: ({ id, defaultMessage }: { id?: string; defaultMessage?: string }) =>
      defaultMessage ?? id ?? "",
    locale: "zh-CN",
  };
}

export function FormattedMessage({
  id,
  defaultMessage,
  children,
}: {
  id?: string;
  defaultMessage?: string;
  children?: (msg: string) => ReactNode;
}) {
  const message = defaultMessage ?? id ?? "";
  if (typeof children === "function") return <>{children(message)}</>;
  return <>{message}</>;
}

export function SelectLang(props: { style?: React.CSSProperties }) {
  return createElement("span", { "data-lang": true, style: props.style });
}
