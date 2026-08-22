import { cleanup, render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { createMemoryRouter, RouterProvider } from "react-router-dom";
import { afterEach, describe, expect, it } from "vitest";
import NoFoundPage from "./404";

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
