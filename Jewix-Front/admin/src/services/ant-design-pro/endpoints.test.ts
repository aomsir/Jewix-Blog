import { beforeEach, describe, expect, it, vi } from "vitest";
import { http } from "@/lib/http/client";
import { addRule, currentUser, getNotices, outLogin, removeRule, rule, updateRule } from "./api";
import { getFakeCaptcha } from "./login";

vi.mock("@/lib/http/client", () => ({
  http: {
    request: vi.fn(),
  },
}));

const requestMock = vi.mocked(http.request);

describe("ant-design-pro endpoints omit /api/ prefix", () => {
  beforeEach(() => {
    requestMock.mockReset();
    requestMock.mockResolvedValue({ data: {} } as never);
  });

  it("currentUser/rule/notices/outLogin use relative urls", async () => {
    await currentUser();
    await outLogin();
    await getNotices();
    await rule({ current: 1, pageSize: 10 });
    await updateRule();
    await addRule();
    await removeRule();
    const urls = requestMock.mock.calls.map((call) => call[0]?.url);
    expect(urls).toEqual([
      "currentUser",
      "login/outLogin",
      "notices",
      "rule",
      "rule",
      "rule",
      "rule",
    ]);
    expect(urls.every((url) => typeof url === "string" && !url.startsWith("/api/"))).toBe(true);
  });

  it("captcha uses a relative url", async () => {
    await getFakeCaptcha({ phone: "13800000000" });
    expect(requestMock).toHaveBeenCalledWith(
      expect.objectContaining({ url: "login/captcha" }),
    );
  });
});
