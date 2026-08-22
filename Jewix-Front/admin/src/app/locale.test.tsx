import { cleanup, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { afterEach, describe, expect, it } from "vitest";
import { FormattedMessage } from "react-intl";
import { SelectLang } from "@/components/bases/RightContent";
import { LocaleIntlProvider, LocaleProvider, useLocale } from "./locale";

Object.defineProperty(window, "matchMedia", {
  writable: true,
  value: (query: string) => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: () => undefined,
    removeListener: () => undefined,
    addEventListener: () => undefined,
    removeEventListener: () => undefined,
    dispatchEvent: () => false,
  }),
});

function LocaleProbe() {
  const { locale } = useLocale();
  return (
    <div>
      <div data-testid="locale">{locale}</div>
      <FormattedMessage id="navBar.lang" />
      <SelectLang />
    </div>
  );
}

describe("SelectLang locale switching", () => {
  afterEach(() => {
    cleanup();
  });

  it("clicking English switches locale state and IntlProvider messages", async () => {
    render(
      <LocaleProvider>
        <LocaleIntlProvider>
          <LocaleProbe />
        </LocaleIntlProvider>
      </LocaleProvider>,
    );
    expect(screen.getByTestId("locale")).toHaveTextContent("zh-CN");
    expect(screen.getByText("语言")).toBeInTheDocument();
    await userEvent.click(screen.getByRole("img", { name: "global" }));
    await userEvent.click(await screen.findByText("English"));
    await waitFor(() => expect(screen.getByTestId("locale")).toHaveTextContent("en-US"));
    expect(screen.getByText("Languages")).toBeInTheDocument();
    expect(screen.queryByText("语言")).not.toBeInTheDocument();
  });
});
