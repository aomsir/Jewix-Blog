import axios, { type AxiosAdapter, type AxiosRequestConfig } from "axios";
import { message } from "antd";
import { LocalToken } from "@/utils/token";
import { ApiError, UnauthorizedError } from "./errors";
import { notifyUnauthorized } from "./unauthorized";

declare module "axios" {
  interface AxiosRequestConfig {
    skipErrorHandler?: boolean;
  }
}

export type HttpRequestConfig = AxiosRequestConfig;

export function createHttpClient(adapter?: AxiosAdapter) {
  const client = axios.create({ baseURL: "/api/", adapter });

  client.interceptors.request.use((config) => {
    const token = LocalToken.get();
    if (token) config.headers.token = token;
    return config;
  });

  client.interceptors.response.use(
    (response) => {
      const { code, msg } = response.data ?? {};
      const skip = Boolean((response.config as HttpRequestConfig).skipErrorHandler);
      if (code === 401 || msg === "登录状态失效,请重新登录") {
        LocalToken.remove();
        if (!skip) {
          const path = window.location.pathname.replace(/^\/admin/, "") || "/";
          const redirect = path === "/login" ? "/login" : `/login?redirect=${encodeURIComponent(path)}`;
          notifyUnauthorized(redirect);
          message.error(msg ?? "登录状态失效,请重新登录");
        }
        throw new UnauthorizedError(msg);
      }
      if (typeof code === "number" && code >= 400) {
        if (!skip) message.error(msg ?? "Request failed");
        throw new ApiError(code, msg ?? "Request failed");
      }
      return response;
    },
    (error) => {
      const skip = Boolean((error.config as HttpRequestConfig | undefined)?.skipErrorHandler);
      if (!skip) {
        if (error.response) message.error(`Response status:${error.message}`);
        else if (error.request) message.error("None response! Please retry.");
        else message.error("发送请求时出了点问题，请重试。");
      }
      return Promise.reject(error);
    },
  );

  return client;
}

export const http = createHttpClient();
