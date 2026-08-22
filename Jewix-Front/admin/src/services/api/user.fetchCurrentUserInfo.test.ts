import { beforeEach, describe, expect, it, vi } from "vitest";
import { http } from "@/lib/http/client";
import { fetchCurrentUserInfo } from "./user";

vi.mock("@/lib/http/client", () => ({
  http: {
    request: vi.fn(),
  },
}));

vi.mock("@/utils", () => ({
  deleteFactory: vi.fn(),
  updateFactory: vi.fn(),
}));

const requestMock = vi.mocked(http.request);

describe("fetchCurrentUserInfo", () => {
  beforeEach(() => {
    requestMock.mockReset();
    requestMock.mockResolvedValue({ data: { code: 200, msg: "ok" } } as never);
  });

  it("forwards options into the http request config", async () => {
    await fetchCurrentUserInfo({ skipErrorHandler: true });
    expect(requestMock).toHaveBeenCalledWith(
      expect.objectContaining({ url: "users/current", skipErrorHandler: true }),
    );
  });

  it("forces GET even when options try to override method", async () => {
    await fetchCurrentUserInfo({ method: "POST", skipErrorHandler: true });
    expect(requestMock).toHaveBeenCalledWith(
      expect.objectContaining({ url: "users/current", method: "get", skipErrorHandler: true }),
    );
  });
});
