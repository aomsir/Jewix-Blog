import { cleanup, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { message } from "antd";
import { HelmetProvider } from "react-helmet-async";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import { AuthContext, type AuthState } from "@/app/auth";
import { LocaleIntlProvider, LocaleProvider } from "@/app/locale";
import { ApiError } from "@/lib/http/errors";
import { LocalToken } from "@/utils/token";
import Login from "./index";

const login = vi.fn();
const fetchCurrentUserInfo = vi.fn();

vi.mock("@/services/api/user", () => ({
  login: (...args: unknown[]) => login(...args),
  fetchCurrentUserInfo: (...args: unknown[]) => fetchCurrentUserInfo(...args),
}));

vi.mock("@/utils/token", () => ({
  LocalToken: {
    set: vi.fn(),
    get: vi.fn(),
    remove: vi.fn(),
  },
}));

Object.defineProperty(window, "matchMedia", {
  writable: true,
  value: vi.fn().mockImplementation((query: string) => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: vi.fn(),
    removeListener: vi.fn(),
    addEventListener: vi.fn(),
    removeEventListener: vi.fn(),
    dispatchEvent: vi.fn(),
  })),
});

function renderLogin(auth: Partial<AuthState> = {}) {
  const setCurrentUser = vi.fn();
  const value: AuthState = {
    currentUser: undefined,
    loading: false,
    refreshUser: vi.fn(async () => undefined),
    setCurrentUser,
    clearUser: vi.fn(),
    ...auth,
  };
  render(
    <HelmetProvider>
      <LocaleProvider>
        <LocaleIntlProvider>
          <AuthContext.Provider value={value}>
            <MemoryRouter initialEntries={["/login?redirect=%2Farticle"]}>
              <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/article" element={<div>article-home</div>} />
              </Routes>
            </MemoryRouter>
          </AuthContext.Provider>
        </LocaleIntlProvider>
      </LocaleProvider>
    </HelmetProvider>,
  );
  return { setCurrentUser };
}

async function fillAndSubmit(username: string, password: string) {
  await userEvent.type(screen.getByPlaceholderText("用户名"), username);
  await userEvent.type(screen.getByPlaceholderText("密码"), password);
  await userEvent.click(screen.getByRole("button", { name: /登\s*录|login/i }));
}

describe("Login page", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  afterEach(() => {
    cleanup();
  });

  it("writes currentUser and navigates to redirect on success", async () => {
    const userInfo = { user: { id: 1, nickname: "aom" }, menuListPageDTO: [] };
    login.mockResolvedValue({ msg: "success", token: "t-1" });
    fetchCurrentUserInfo.mockResolvedValue({ status: userInfo });
    const { setCurrentUser } = renderLogin();
    await fillAndSubmit("aom", "secret");
    await waitFor(() => expect(setCurrentUser).toHaveBeenCalledWith(userInfo));
    expect(LocalToken.set).toHaveBeenCalledWith("t-1");
    expect(await screen.findByText("article-home")).toBeInTheDocument();
  });

  it("shows login Alert and stays on page when login throws ApiError", async () => {
    const unhandled: unknown[] = [];
    const onUnhandled = (event: PromiseRejectionEvent) => {
      unhandled.push(event.reason);
    };
    window.addEventListener("unhandledrejection", onUnhandled);
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    login.mockRejectedValue(new ApiError(500, "bad credentials"));
    const { setCurrentUser } = renderLogin();
    await fillAndSubmit("aom", "bad");
    expect(await screen.findByRole("alert")).toHaveTextContent("bad credentials");
    expect(LocalToken.set).not.toHaveBeenCalled();
    expect(fetchCurrentUserInfo).not.toHaveBeenCalled();
    expect(setCurrentUser).not.toHaveBeenCalled();
    expect(screen.queryByText("article-home")).not.toBeInTheDocument();
    expect(errorSpy).not.toHaveBeenCalled();
    expect(unhandled).toEqual([]);
    window.removeEventListener("unhandledrejection", onUnhandled);
  });

  it("network login errors stay on page without a second inline LoginMessage", async () => {
    // Interceptor toast for real network failures is covered by src/lib/http/client.test.ts.
    const unhandled: unknown[] = [];
    const onUnhandled = (event: PromiseRejectionEvent) => {
      unhandled.push(event.reason);
    };
    window.addEventListener("unhandledrejection", onUnhandled);
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    login.mockRejectedValue(new Error("Network Error"));
    const { setCurrentUser } = renderLogin();
    await fillAndSubmit("aom", "bad");
    await waitFor(() => expect(login).toHaveBeenCalled());
    expect(screen.queryByRole("alert")).not.toBeInTheDocument();
    expect(LocalToken.set).not.toHaveBeenCalled();
    expect(fetchCurrentUserInfo).not.toHaveBeenCalled();
    expect(setCurrentUser).not.toHaveBeenCalled();
    expect(screen.queryByText("article-home")).not.toBeInTheDocument();
    expect(errorSpy).not.toHaveBeenCalled();
    expect(unhandled).toEqual([]);
    window.removeEventListener("unhandledrejection", onUnhandled);
  });

  it("does not persist token when login returns a non-success payload", async () => {
    login.mockResolvedValue({ msg: "账号或密码错误", token: "t-skip", code: 400 });
    const { setCurrentUser } = renderLogin();
    await fillAndSubmit("aom", "bad");
    expect(await screen.findByRole("alert")).toHaveTextContent("账号或密码错误");
    expect(LocalToken.set).not.toHaveBeenCalled();
    expect(fetchCurrentUserInfo).not.toHaveBeenCalled();
    expect(setCurrentUser).not.toHaveBeenCalled();
    expect(screen.queryByText("article-home")).not.toBeInTheDocument();
  });

  it("failure dual-channel: login error toasts; post-login current-user uses skipErrorHandler", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    login.mockRejectedValue(Object.assign(new Error("bad credentials"), { name: "ApiError" }));
    renderLogin();
    await fillAndSubmit("aom", "bad");
    await waitFor(() => expect(login).toHaveBeenCalled());
    expect(fetchCurrentUserInfo).not.toHaveBeenCalled();
    errorSpy.mockClear();
    login.mockResolvedValue({ msg: "success", token: "t-2" });
    fetchCurrentUserInfo.mockImplementation(async (options?: { skipErrorHandler?: boolean }) => {
      expect(options?.skipErrorHandler).toBe(true);
      throw Object.assign(new Error("expired"), { name: "UnauthorizedError" });
    });
    await userEvent.click(screen.getByRole("button", { name: /登\s*录|login/i }));
    await waitFor(() => expect(fetchCurrentUserInfo).toHaveBeenCalled());
    expect(fetchCurrentUserInfo.mock.calls.at(-1)?.[0]).toMatchObject({ skipErrorHandler: true });
  });

  it("clears token, toasts, and stays unauthenticated when current-user fetch fails after login", async () => {
    const unhandled: unknown[] = [];
    const onUnhandled = (event: PromiseRejectionEvent) => {
      unhandled.push(event.reason);
    };
    window.addEventListener("unhandledrejection", onUnhandled);
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    login.mockResolvedValue({ msg: "success", token: "t-3" });
    fetchCurrentUserInfo.mockRejectedValue(
      Object.assign(new Error("expired"), { name: "UnauthorizedError" }),
    );
    const { setCurrentUser } = renderLogin();
    await fillAndSubmit("aom", "secret");
    await waitFor(() => expect(LocalToken.remove).toHaveBeenCalled());
    expect(errorSpy).toHaveBeenCalledWith("expired");
    expect(setCurrentUser).not.toHaveBeenCalled();
    expect(screen.queryByText("article-home")).not.toBeInTheDocument();
    expect(screen.getByRole("button", { name: /登\s*录|login/i })).toBeInTheDocument();
    expect(unhandled).toEqual([]);
    window.removeEventListener("unhandledrejection", onUnhandled);
  });
});
