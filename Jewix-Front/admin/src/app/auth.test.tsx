import { act, cleanup, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import { fetchCurrentUserInfo } from "@/services/api/user";
import { AuthProvider, useAuth } from "./auth";

vi.mock("@/services/api/user", () => ({
  fetchCurrentUserInfo: vi.fn(),
}));

const fetchCurrentUserInfoMock = vi.mocked(fetchCurrentUserInfo);

const sampleUser = {
  menuListPageDTO: [],
  user: { id: 7, username: "admin" },
} as never;

const otherUser = {
  menuListPageDTO: [],
  user: { id: 99, username: "other" },
} as never;

function deferred<T>() {
  let resolve!: (value: T) => void;
  let reject!: (reason?: unknown) => void;
  const promise = new Promise<T>((res, rej) => {
    resolve = res;
    reject = rej;
  });
  return { promise, resolve, reject };
}

function AuthProbe() {
  const { currentUser, loading, refreshUser, setCurrentUser, clearUser } = useAuth();
  return (
    <div>
      <div data-testid="loading">{String(loading)}</div>
      <div data-testid="user-id">{currentUser?.user?.id ?? "none"}</div>
      <button type="button" onClick={() => void refreshUser()}>
        refresh
      </button>
      <button type="button" onClick={() => setCurrentUser(sampleUser)}>
        set
      </button>
      <button type="button" onClick={() => setCurrentUser(otherUser)}>
        set-other
      </button>
      <button type="button" onClick={() => clearUser()}>clear</button>
    </div>
  );
}

describe("AuthProvider", () => {
  const originalPath = window.location.pathname;

  beforeEach(() => {
    fetchCurrentUserInfoMock.mockReset();
    window.history.replaceState({}, "", "/article");
  });

  afterEach(() => {
    cleanup();
    window.history.replaceState({}, "", originalPath);
  });

  it("skips refreshUser initialization on the login page", async () => {
    window.history.replaceState({}, "", "/login");
    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(screen.getByTestId("loading")).toHaveTextContent("false"));
    expect(fetchCurrentUserInfoMock).not.toHaveBeenCalled();
    expect(screen.getByTestId("user-id")).toHaveTextContent("none");
  });

  it("calls refreshUser with skipErrorHandler on non-login startup", async () => {
    fetchCurrentUserInfoMock.mockResolvedValue({
      code: 200,
      msg: "ok",
      status: sampleUser,
    });
    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(screen.getByTestId("loading")).toHaveTextContent("false"));
    expect(fetchCurrentUserInfoMock).toHaveBeenCalledWith({ skipErrorHandler: true });
  });

  it("hydrates currentUser from a successful refresh", async () => {
    fetchCurrentUserInfoMock.mockResolvedValue({
      code: 200,
      msg: "ok",
      status: sampleUser,
    });
    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(screen.getByTestId("user-id")).toHaveTextContent("7"));
    expect(screen.getByTestId("loading")).toHaveTextContent("false");
  });

  it("setCurrentUser writes currentUser", async () => {
    window.history.replaceState({}, "", "/login");
    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(screen.getByTestId("loading")).toHaveTextContent("false"));
    await userEvent.click(screen.getByRole("button", { name: "set" }));
    expect(screen.getByTestId("user-id")).toHaveTextContent("7");
  });

  it("clearUser clears currentUser", async () => {
    fetchCurrentUserInfoMock.mockResolvedValue({
      code: 200,
      msg: "ok",
      status: sampleUser,
    });
    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(screen.getByTestId("user-id")).toHaveTextContent("7"));
    await userEvent.click(screen.getByRole("button", { name: "clear" }));
    expect(screen.getByTestId("user-id")).toHaveTextContent("none");
  });

  it("handles refresh rejection without unhandled rejection and ends cleared", async () => {
    const unhandled: unknown[] = [];
    const onUnhandled = (reason: unknown) => {
      unhandled.push(reason);
    };
    process.on("unhandledRejection", onUnhandled);
    try {
      fetchCurrentUserInfoMock.mockRejectedValue(new Error("network"));
      let refreshUserRef: (() => Promise<unknown>) | undefined;
      function CaptureRefresh() {
        refreshUserRef = useAuth().refreshUser;
        return <AuthProbe />;
      }
      render(
        <AuthProvider>
          <CaptureRefresh />
        </AuthProvider>,
      );

      await waitFor(() => expect(screen.getByTestId("loading")).toHaveTextContent("false"));
      expect(screen.getByTestId("user-id")).toHaveTextContent("none");
      await expect(refreshUserRef?.()).resolves.toBeUndefined();
      await Promise.resolve();
      await Promise.resolve();
      expect(unhandled).toEqual([]);
    } finally {
      process.off("unhandledRejection", onUnhandled);
    }
  });

  it("does not restore currentUser from a stale refresh after clearUser", async () => {
    const pending = deferred<{ code: number; msg: string; status: typeof sampleUser }>();
    fetchCurrentUserInfoMock.mockReturnValueOnce(pending.promise as never);

    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(fetchCurrentUserInfoMock).toHaveBeenCalledTimes(1));

    await userEvent.click(screen.getByRole("button", { name: "clear" }));
    expect(screen.getByTestId("user-id")).toHaveTextContent("none");

    pending.resolve({ code: 200, msg: "ok", status: sampleUser });
    await waitFor(() => expect(screen.getByTestId("loading")).toHaveTextContent("false"));
    expect(screen.getByTestId("user-id")).toHaveTextContent("none");
  });

  it("does not overwrite currentUser from a stale refresh after setCurrentUser", async () => {
    const pending = deferred<{ code: number; msg: string; status: typeof sampleUser }>();
    fetchCurrentUserInfoMock.mockReturnValueOnce(pending.promise as never);

    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(fetchCurrentUserInfoMock).toHaveBeenCalledTimes(1));

    await userEvent.click(screen.getByRole("button", { name: "set-other" }));
    expect(screen.getByTestId("user-id")).toHaveTextContent("99");

    pending.resolve({ code: 200, msg: "ok", status: sampleUser });
    await waitFor(() => expect(screen.getByTestId("loading")).toHaveTextContent("false"));
    expect(screen.getByTestId("user-id")).toHaveTextContent("99");
  });

  it("keeps loading true until the latest concurrent refresh finishes", async () => {
    type Resp = { code: number; msg: string; status: typeof sampleUser };
    const first = deferred<Resp>();
    const second = deferred<Resp>();
    fetchCurrentUserInfoMock
      .mockReturnValueOnce(first.promise as never)
      .mockReturnValueOnce(second.promise as never);

    render(
      <AuthProvider>
        <AuthProbe />
      </AuthProvider>,
    );
    await waitFor(() => expect(fetchCurrentUserInfoMock).toHaveBeenCalledTimes(1));
    expect(screen.getByTestId("loading")).toHaveTextContent("true");

    await userEvent.click(screen.getByRole("button", { name: "refresh" }));
    await waitFor(() => expect(fetchCurrentUserInfoMock).toHaveBeenCalledTimes(2));
    expect(screen.getByTestId("loading")).toHaveTextContent("true");

    await act(async () => {
      first.resolve({ code: 200, msg: "ok", status: sampleUser });
    });
    expect(screen.getByTestId("loading")).toHaveTextContent("true");
    expect(screen.getByTestId("user-id")).toHaveTextContent("none");

    await act(async () => {
      second.resolve({ code: 200, msg: "ok", status: otherUser });
    });
    await waitFor(() => expect(screen.getByTestId("loading")).toHaveTextContent("false"));
    expect(screen.getByTestId("user-id")).toHaveTextContent("99");
  });
});
