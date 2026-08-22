import { describe, expect, it } from "vitest";
import { getArticleEditFallback } from "./articleEditAccess";

describe("getArticleEditFallback", () => {
  it("sends denied editors to /article, not /exception/403", () => {
    const fallback = getArticleEditFallback(() => false);
    expect(fallback).toEqual({ to: "/article", replace: true });
    expect(fallback?.to).not.toBe("/exception/403");
  });

  it("does not navigate when 文章编辑 is allowed", () => {
    expect(getArticleEditFallback(() => true)).toBeNull();
  });

  it("asks normalRouteFilter for 文章编辑 without remapping", () => {
    const names: string[] = [];
    getArticleEditFallback((route) => {
      names.push(route.name ?? "");
      return true;
    });
    expect(names).toEqual(["文章编辑"]);
  });
});
