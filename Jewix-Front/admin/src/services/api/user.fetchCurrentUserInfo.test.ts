import { beforeEach, describe, expect, it, vi } from "vitest";
import { request } from "@umijs/max";
import { fetchCurrentUserInfo } from "./user";

vi.mock("@umijs/max", () => ({
  request: vi.fn(),
}));

vi.mock("@/utils", () => ({
  deleteFactory: vi.fn(),
  updateFactory: vi.fn(),
}));

const requestMock = vi.mocked(request);

describe("fetchCurrentUserInfo", () => {
  beforeEach(() => {
    requestMock.mockReset();
    requestMock.mockResolvedValue({ code: 200, msg: "ok" });
  });

  it("forwards options into the Umi request config", async () => {
    await fetchCurrentUserInfo({ skipErrorHandler: true });
    expect(requestMock).toHaveBeenCalledWith(
      "users/current",
      expect.objectContaining({ skipErrorHandler: true }),
    );
  });

  it("forces GET even when options try to override method", async () => {
    await fetchCurrentUserInfo({ method: "POST", skipErrorHandler: true });
    expect(requestMock).toHaveBeenCalledWith(
      "users/current",
      expect.objectContaining({ method: "GET", skipErrorHandler: true }),
    );
  });
});
