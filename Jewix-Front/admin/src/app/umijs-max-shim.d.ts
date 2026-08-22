declare module "@umijs/max" {
  export function request<T = any>(
    url: string,
    options?: {
      method?: string;
      params?: any;
      data?: any;
      paramsSerializer?: (params: any) => string;
      skipErrorHandler?: boolean;
      [key: string]: any;
    },
  ): Promise<T>;
}
