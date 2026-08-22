declare module "gravatar" {
  export function url(
    email: string,
    options?: { size?: string; protocol?: string; d?: string },
  ): string;
}
