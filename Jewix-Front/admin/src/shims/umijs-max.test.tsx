import { cleanup, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { createMemoryRouter, RouterProvider } from "react-router-dom";
import { afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import { AccessProvider } from "@/app/access";
import { AuthProvider } from "@/app/auth";
import NoFoundPage from "@/pages/404";
import type { API } from "@/services/ant-design-pro/typings";
import { fetchCurrentUserInfo } from "@/services/api/user";
import { useAccess, useModel } from "@umijs/max";

vi.mock("@/services/api/user", () => ({
  fetchCurrentUserInfo: vi.fn(),
}));

const fetchCurrentUserInfoMock = vi.mocked(fetchCurrentUserInfo);

function menu(
  name: string,
  type: number,
  sonList: API.FetchMenuResponse[] = [],
): API.FetchMenuResponse {
  return {
    componentPath: "",
    createTime: "",
    iconName: "",
    id: name.length,
    name,
    parentId: 0,
    path: "",
    sonList,
    type,
    updateTime: "",
  };
}

const sampleUser = {
  menuListPageDTO: [
    menu("文章管理", 0, [menu("新增", 1), menu("删除", 1)]),
    menu("评论管理", 0),
  ],
  user: { id: 11, nickname: "admin" },
} as API.FetchUserDetailResponse;

function ShimProbe() {
  const { initialState, setInitialState } = useModel("@@initialState");
  const access = useAccess();
  return (
    <div>
      <div data-testid="user-id">{initialState?.currentUser?.user?.id ?? "none"}</div>
      <div data-testid="article-route">
        {String(access.normalRouteFilter({ name: "文章管理" }))}
      </div>
      <div data-testid="auth-route">{String(access.normalRouteFilter({ name: "权限管理" }))}</div>
      <div data-testid="article-create">
        {String(Boolean(access.operationFilter({ name: "文章管理" }, "新增")))}
      </div>
      <div data-testid="article-update">
        {String(Boolean(access.operationFilter({ name: "文章管理" }, "更新")))}
      </div>
      <button
        type="button"
        onClick={() =>
          setInitialState((state: { currentUser?: API.FetchUserDetailResponse } | undefined) => ({
            ...state,
            currentUser: sampleUser,
          }))
        }
      >
        set-user
      </button>
      <button
        type="button"
        onClick={() =>
          setInitialState((state: { currentUser?: API.FetchUserDetailResponse } | undefined) => ({
            ...state,
            currentUser: undefined,
          }))
        }
      >
        clear-user
      </button>
    </div>
  );
}

function renderShim() {
  return render(
    <AuthProvider>
      <AccessProvider>
        <ShimProbe />
      </AccessProvider>
    </AuthProvider>,
  );
}

describe("umijs-max shim useModel/useAccess", () => {
  const originalPath = window.location.pathname;

  beforeEach(() => {
    fetchCurrentUserInfoMock.mockReset();
    window.history.replaceState({}, "", "/login");
  });

  afterEach(() => {
    cleanup();
    window.history.replaceState({}, "", originalPath);
  });

  it("starts with empty currentUser and real access denials", async () => {
    renderShim();
    await waitFor(() => expect(screen.getByTestId("user-id")).toHaveTextContent("none"));
    expect(screen.getByTestId("article-route")).toHaveTextContent("false");
    expect(screen.getByTestId("auth-route")).toHaveTextContent("false");
    expect(screen.getByTestId("article-create")).toHaveTextContent("false");
  });

  it("setInitialState writes currentUser that the probe can read back", async () => {
    renderShim();
    await waitFor(() => expect(screen.getByTestId("user-id")).toHaveTextContent("none"));
    await userEvent.click(screen.getByRole("button", { name: "set-user" }));
    expect(screen.getByTestId("user-id")).toHaveTextContent("11");
  });

  it("clear/undefined currentUser removes the user", async () => {
    renderShim();
    await waitFor(() => expect(screen.getByTestId("user-id")).toHaveTextContent("none"));
    await userEvent.click(screen.getByRole("button", { name: "set-user" }));
    expect(screen.getByTestId("user-id")).toHaveTextContent("11");
    await userEvent.click(screen.getByRole("button", { name: "clear-user" }));
    expect(screen.getByTestId("user-id")).toHaveTextContent("none");
  });

  it("normalRouteFilter and operationFilter follow createAccess, not always-true", async () => {
    renderShim();
    await waitFor(() => expect(screen.getByTestId("user-id")).toHaveTextContent("none"));
    await userEvent.click(screen.getByRole("button", { name: "set-user" }));
    expect(screen.getByTestId("article-route")).toHaveTextContent("true");
    expect(screen.getByTestId("auth-route")).toHaveTextContent("false");
    expect(screen.getByTestId("article-create")).toHaveTextContent("true");
    expect(screen.getByTestId("article-update")).toHaveTextContent("false");
  });
});

describe("404 Back Home uses React Router navigate", () => {
  afterEach(() => {
    cleanup();
  });

  it("navigates to / without writing window.history as the router source of truth", async () => {
    const router = createMemoryRouter(
      [
        { path: "/", element: <div>home</div> },
        { path: "/missing", element: <NoFoundPage /> },
      ],
      { initialEntries: ["/missing"] },
    );
    render(<RouterProvider router={router} />);
    expect(screen.getByText("404")).toBeInTheDocument();
    await userEvent.click(screen.getByRole("button", { name: /back home/i }));
    expect(screen.getByText("home")).toBeInTheDocument();
    expect(router.state.location.pathname).toBe("/");
  });
});
