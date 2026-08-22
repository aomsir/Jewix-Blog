import { describe, expect, it } from "vitest";
import { render, screen } from "@testing-library/react";

describe("vitest foundation", () => {
  it("loads jest-dom matchers via setup", () => {
    render(<div>foundation</div>);
    expect(screen.getByText("foundation")).toBeInTheDocument();
  });
});
