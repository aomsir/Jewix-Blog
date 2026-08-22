import { cleanup, render, screen, waitFor } from "@testing-library/react";
import { createMemoryRouter, RouterProvider } from "react-router-dom";
import { afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import { useAuthorization } from "@/app/access";
import {
  fetchAllCategories,
  fetchAllTags,
  fetchArticleByUUID,
} from "@/services/api";
import { ArticleEditAccessGate } from "./articleEditAccess";
import ArticleEdit from "./index";

vi.mock("@/app/access", () => ({
  useAuthorization: vi.fn(),
}));

vi.mock("@/components/bases/editor/Editor", () => ({
  default: () => <div>editor-stub</div>,
}));

vi.mock("@/utils/markdownIt", () => ({
  md: { render: () => "" },
}));

vi.mock("@/services/api", () => ({
  fetchAllTags: vi.fn(),
  fetchAllCategories: vi.fn(),
  fetchArticleByUUID: vi.fn(),
  insertArticle: vi.fn(),
  insertTag: vi.fn(),
  updateArticle: vi.fn(),
}));

const useAuthorizationMock = vi.mocked(useAuthorization);
const fetchAllTagsMock = vi.mocked(fetchAllTags);
const fetchAllCategoriesMock = vi.mocked(fetchAllCategories);
const fetchArticleByUUIDMock = vi.mocked(fetchArticleByUUID);

function deferred<T>() {
  let resolve!: (value: T) => void;
  const promise = new Promise<T>((res) => {
    resolve = res;
  });
  return { promise, resolve };
}

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

function flattenConsole(spy: ReturnType<typeof vi.spyOn>) {
  return spy.mock.calls
    .flat()
    .map((value: unknown) => String(value))
    .join("\n");
}

function renderGate(filter: (route: { name?: string }) => boolean) {
  const router = createMemoryRouter(
    [
      { path: "/article", element: <div>article-list</div> },
      {
        path: "/article/edit",
        element: (
          <ArticleEditAccessGate filter={filter}>
            <div>editor-form</div>
          </ArticleEditAccessGate>
        ),
      },
    ],
    { initialEntries: ["/article/edit"] },
  );
  render(<RouterProvider router={router} />);
  return router;
}

describe("ArticleEditAccessGate", () => {
  afterEach(() => {
    cleanup();
  });

  it("does not navigate during render, hides children, then replace-navigates to /article", async () => {
    const errorSpy = vi.spyOn(console, "error").mockImplementation(() => undefined);
    const warnSpy = vi.spyOn(console, "warn").mockImplementation(() => undefined);
    const filter = vi.fn((route: { name?: string }) => route.name !== "文章编辑");
    const router = renderGate(filter);
    expect(filter).toHaveBeenCalledWith({ name: "文章编辑" });
    expect(screen.queryByText("editor-form")).not.toBeInTheDocument();
    expect(`${flattenConsole(errorSpy)}\n${flattenConsole(warnSpy)}`).not.toMatch(
      /You should call navigate\(\) in a React\.useEffect/i,
    );
    await waitFor(() => expect(screen.getByText("article-list")).toBeInTheDocument());
    expect(router.state.location.pathname).toBe("/article");
    errorSpy.mockRestore();
    warnSpy.mockRestore();
  });

  it("renders children and stays on /article/edit when allowed", () => {
    const router = renderGate((route) => route.name === "文章编辑");
    expect(screen.getByText("editor-form")).toBeInTheDocument();
    expect(router.state.location.pathname).toBe("/article/edit");
    expect(screen.queryByText("article-list")).not.toBeInTheDocument();
  });
});

describe("ArticleEdit page skips data loading when denied", () => {
  beforeEach(() => {
    fetchAllTagsMock.mockReset();
    fetchAllCategoriesMock.mockReset();
    fetchArticleByUUIDMock.mockReset();
    useAuthorizationMock.mockReset();
    const tags = deferred<unknown[]>();
    const categories = deferred<{ result: unknown[] }>();
    const article = deferred<{ result: unknown }>();
    fetchAllTagsMock.mockReturnValue(tags.promise as never);
    fetchAllCategoriesMock.mockReturnValue(categories.promise as never);
    fetchArticleByUUIDMock.mockReturnValue(article.promise as never);
  });

  afterEach(() => {
    cleanup();
  });

  it("does not mount the form or call tag/category/article fetches", async () => {
    useAuthorizationMock.mockReturnValue({
      normalRouteFilter: (route: { name?: string }) => route.name !== "文章编辑",
      operationFilter: () => false,
    });
    const router = createMemoryRouter(
      [
        { path: "/article", element: <div>article-list</div> },
        { path: "/article/edit", element: <ArticleEdit /> },
      ],
      { initialEntries: ["/article/edit?uuid=denied-id"] },
    );
    render(<RouterProvider router={router} />);
    expect(screen.queryByPlaceholderText("在这里输入文章标题")).not.toBeInTheDocument();
    expect(screen.queryByText("editor-stub")).not.toBeInTheDocument();
    await waitFor(() => expect(screen.getByText("article-list")).toBeInTheDocument());
    expect(fetchAllTagsMock).not.toHaveBeenCalled();
    expect(fetchAllCategoriesMock).not.toHaveBeenCalled();
    expect(fetchArticleByUUIDMock).not.toHaveBeenCalled();
    expect(router.state.location.pathname).toBe("/article");
  });

  it("does not continue tag/category/article updates after unmount", async () => {
    useAuthorizationMock.mockReturnValue({
      normalRouteFilter: (route: { name?: string }) => route.name === "文章编辑",
      operationFilter: () => false,
    });
    const tags = deferred<Array<{ id: number; tagName: string }>>();
    const categories = deferred<{ result: unknown[] }>();
    const article = deferred<{ result: { title: string } }>();
    fetchAllTagsMock.mockReturnValue(tags.promise as never);
    fetchAllCategoriesMock.mockReturnValue(categories.promise as never);
    fetchArticleByUUIDMock.mockReturnValue(article.promise as never);

    const errorSpy = vi.spyOn(console, "error").mockImplementation(() => undefined);
    const { unmount } = render(
      <RouterProvider
        router={createMemoryRouter(
          [
            { path: "/article", element: <div>article-list</div> },
            { path: "/article/edit", element: <ArticleEdit /> },
          ],
          { initialEntries: ["/article/edit?uuid=stale-id"] },
        )}
      />,
    );
    await waitFor(() => expect(fetchAllTagsMock).toHaveBeenCalledTimes(1));
    expect(screen.getByPlaceholderText("在这里输入文章标题")).toBeInTheDocument();

    unmount();
    tags.resolve([{ id: 1, tagName: "stale-tag" }]);
    await Promise.resolve();
    await Promise.resolve();
    categories.resolve({ result: [{ id: 2, categoryName: "stale-cat" }] });
    await Promise.resolve();
    await Promise.resolve();
    article.resolve({ result: { title: "stale-title" } });
    await Promise.resolve();
    await Promise.resolve();

    expect(fetchAllCategoriesMock).not.toHaveBeenCalled();
    expect(fetchArticleByUUIDMock).not.toHaveBeenCalled();
    expect(flattenConsole(errorSpy)).not.toMatch(/not wrapped in act|unmounted component/i);
    errorSpy.mockRestore();
  });
});
