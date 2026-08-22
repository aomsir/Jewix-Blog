export class ApiError extends Error {
  constructor(
    public readonly code: number,
    message: string,
  ) {
    super(message);
    this.name = "ApiError";
  }
}

export class UnauthorizedError extends ApiError {
  constructor(message = "登录状态失效,请重新登录") {
    super(401, message);
    this.name = "UnauthorizedError";
  }
}
