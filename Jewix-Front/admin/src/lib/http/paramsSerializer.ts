export function serializeParams(params: Record<string, unknown>): string {
  return Object.keys(params)
    .filter((key) => params[key] !== null && params[key] !== undefined)
    .map((key) => {
      const encodedKey = encodeURIComponent(key);
      const value = params[key];
      if (Array.isArray(value)) {
        return value
          .map((item) => `${encodedKey}=${encodeURIComponent(String(item))}`)
          .join("&");
      }
      return `${encodedKey}=${encodeURIComponent(String(value))}`;
    })
    .join("&");
}
