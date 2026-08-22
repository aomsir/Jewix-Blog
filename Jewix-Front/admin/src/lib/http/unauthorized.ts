export type UnauthorizedHandler = (redirectPath: string) => void;

let unauthorizedHandler: UnauthorizedHandler | undefined;

export function setUnauthorizedHandler(handler: UnauthorizedHandler | undefined) {
  unauthorizedHandler = handler;
}

export function notifyUnauthorized(redirectPath: string) {
  unauthorizedHandler?.(redirectPath);
}
