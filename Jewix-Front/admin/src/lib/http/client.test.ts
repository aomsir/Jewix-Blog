import type { AxiosAdapter, InternalAxiosRequestConfig } from "axios";
import { message } from "antd";
import { afterEach, describe, expect, it, vi } from "vitest";
import { LocalToken } from "@/utils/token";
import { createHttpClient } from "./client";
import { ApiError, UnauthorizedError } from "./errors";
import { setUnauthorizedHandler } from "./unauthorized";

const ok = (config: InternalAxiosRequestConfig, data: unknown) => ({
  data,
  status: 200,
  statusText: "OK",
  headers: {},
  config,
});

type AdapterError = Error & {
  config?: InternalAxiosRequestConfig;
  response?: unknown;
  request?: unknown;
};

const rejectWith = (
  factory: (config: InternalAxiosRequestConfig) => AdapterError,
): AxiosAdapter => {
  return async (config) => {
    throw factory(config);
  };
};

describe("createHttpClient", () => {
  const originalPath = window.location.pathname;

  afterEach(() => {
    vi.restoreAllMocks();
    setUnauthorizedHandler(undefined);
    window.history.replaceState({}, "", originalPath);
  });

  it("injects the local token", async () => {
    vi.spyOn(LocalToken, "get").mockReturnValue("token-value");
    const adapter: AxiosAdapter = async (config) => ok(config, { code: 200, status: true });
    const response = await createHttpClient(adapter).get("user/current");
    expect(response.config.headers?.token).toBe("token-value");
  });

  it("shows message.error and throws ApiError for business failures", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 400, msg: "bad request" });
    await expect(createHttpClient(adapter).get("failing")).rejects.toEqual(
      new ApiError(400, "bad request"),
    );
    expect(errorSpy).toHaveBeenCalledWith("bad request");
  });

  it("skips message.error when skipErrorHandler is true", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 400, msg: "hidden" });
    await expect(
      createHttpClient(adapter).get("failing", { skipErrorHandler: true }),
    ).rejects.toBeInstanceOf(ApiError);
    expect(errorSpy).not.toHaveBeenCalled();
  });

  it("clears token, redirects once, and throws UnauthorizedError on 401", async () => {
    vi.spyOn(LocalToken, "remove");
    const redirect = vi.fn();
    setUnauthorizedHandler(redirect);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 401, msg: "登录状态失效,请重新登录" });
    await expect(createHttpClient(adapter).get("private")).rejects.toBeInstanceOf(
      UnauthorizedError,
    );
    expect(LocalToken.remove).toHaveBeenCalledOnce();
    expect(redirect).toHaveBeenCalledOnce();
  });

  it("redirects 401 from /admin/article to /login?redirect=%2Farticle", async () => {
    window.history.pushState({}, "", "/admin/article");
    vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const redirect = vi.fn();
    setUnauthorizedHandler(redirect);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 401, msg: "登录状态失效,请重新登录" });
    await expect(createHttpClient(adapter).get("private")).rejects.toBeInstanceOf(
      UnauthorizedError,
    );
    expect(redirect).toHaveBeenCalledWith("/login?redirect=%2Farticle");
  });

  it("redirects 401 from /admin/login to /login", async () => {
    window.history.pushState({}, "", "/admin/login");
    vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const redirect = vi.fn();
    setUnauthorizedHandler(redirect);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 401, msg: "登录状态失效,请重新登录" });
    await expect(createHttpClient(adapter).get("private")).rejects.toBeInstanceOf(
      UnauthorizedError,
    );
    expect(redirect).toHaveBeenCalledWith("/login");
  });

  it("login dual-channel: login uses toast path; current-user uses skipErrorHandler", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter: AxiosAdapter = async (config) => {
      if (String(config.url).includes("login")) {
        return ok(config, { code: 400, msg: "bad credentials" });
      }
      return ok(config, { code: 401, msg: "登录状态失效,请重新登录" });
    };
    const client = createHttpClient(adapter);
    await expect(client.post("login", {})).rejects.toBeInstanceOf(ApiError);
    expect(errorSpy).toHaveBeenCalledWith("bad credentials");
    errorSpy.mockClear();
    await expect(
      client.get("users/current", { skipErrorHandler: true }),
    ).rejects.toBeInstanceOf(UnauthorizedError);
    expect(errorSpy).not.toHaveBeenCalled();
  });

  it("toasts Response status when adapter error has response", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter = rejectWith((config) => {
      const error: AdapterError = new Error("Request failed with status code 500");
      error.config = config;
      error.response = {
        data: {},
        status: 500,
        statusText: "Internal Server Error",
        headers: {},
        config,
      };
      return error;
    });
    await expect(createHttpClient(adapter).get("boom")).rejects.toMatchObject({
      response: expect.anything(),
    });
    expect(errorSpy).toHaveBeenCalledWith(
      "Response status:Request failed with status code 500",
    );
  });

  it("toasts None response when adapter error has request only", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter = rejectWith((config) => {
      const error: AdapterError = new Error("Network Error");
      error.config = config;
      error.request = {};
      return error;
    });
    await expect(createHttpClient(adapter).get("hang")).rejects.toMatchObject({
      request: expect.anything(),
    });
    expect(errorSpy).toHaveBeenCalledWith("None response! Please retry.");
  });

  it("toasts setup failure when adapter error has neither request nor response", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter = rejectWith((config) => {
      const error: AdapterError = new Error("setup failed");
      error.config = config;
      return error;
    });
    await expect(createHttpClient(adapter).get("broken")).rejects.toBeTruthy();
    expect(errorSpy).toHaveBeenCalledWith("发送请求时出了点问题，请重试。");
  });

  it("skips network-error toast when skipErrorHandler is true", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter = rejectWith((config) => {
      const error: AdapterError = new Error("Request failed with status code 500");
      error.config = config;
      error.response = {
        data: {},
        status: 500,
        statusText: "Internal Server Error",
        headers: {},
        config,
      };
      return error;
    });
    await expect(
      createHttpClient(adapter).get("boom", { skipErrorHandler: true }),
    ).rejects.toMatchObject({
      config: expect.objectContaining({ skipErrorHandler: true }),
      response: expect.anything(),
    });
    expect(errorSpy).not.toHaveBeenCalled();
  });
});
