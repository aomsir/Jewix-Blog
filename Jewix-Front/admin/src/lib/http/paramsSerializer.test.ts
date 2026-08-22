import { describe, expect, it } from "vitest";
import { serializeParams } from "./paramsSerializer";

describe("serializeParams", () => {
  it("repeats array keys as ids=1&ids=2", () => {
    expect(serializeParams({ ids: [1, 2] })).toBe("ids=1&ids=2");
  });

  it("keeps scalar keys", () => {
    expect(serializeParams({ ids: [9], status: 1 })).toBe("ids=9&status=1");
  });

  it("omits null and undefined keys", () => {
    expect(serializeParams({ ids: [1], q: null, foo: undefined, status: 1 })).toBe(
      "ids=1&status=1",
    );
  });

  it("encodes both keys and values", () => {
    expect(serializeParams({ q: "a&b=c", "x y": "hello world" })).toBe(
      "q=a%26b%3Dc&x%20y=hello%20world",
    );
  });

  it("encodes keys with special characters", () => {
    expect(serializeParams({ "a&b": "1", "x y": "z" })).toBe("a%26b=1&x%20y=z");
  });

  it("encodes array item values", () => {
    expect(serializeParams({ tags: ["a&b", "c d"] })).toBe("tags=a%26b&tags=c%20d");
  });
});
