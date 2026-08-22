import { http, type HttpRequestConfig } from "@/lib/http/client";
import { serializeParams } from "@/lib/http/paramsSerializer";

export type RequestConfig = Record<string, unknown>;
export type RunTimeLayoutConfig = (...args: unknown[]) => Record<string, unknown>;

type UmiRequestOptions = {
  method?: string;
  params?: unknown;
  data?: unknown;
  paramsSerializer?: (params: Record<string, unknown>) => string;
  skipErrorHandler?: boolean;
};

export async function request<T = unknown>(url: string, options: UmiRequestOptions = {}): Promise<T> {
  const method = (options.method ?? "GET").toLowerCase();
  const response = await http.request<T>({
    url,
    method,
    params: options.params,
    data: options.data,
    skipErrorHandler: options.skipErrorHandler,
    paramsSerializer: options.paramsSerializer
      ? (params) => options.paramsSerializer!(params as Record<string, unknown>)
      : (params) => serializeParams(params as Record<string, unknown>),
  } as HttpRequestConfig);
  return response.data;
}
