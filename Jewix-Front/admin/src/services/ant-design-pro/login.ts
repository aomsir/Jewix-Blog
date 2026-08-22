// @ts-ignore
/* eslint-disable */
import { http, type HttpRequestConfig } from '@/lib/http/client';
import type { API } from './typings';

function request<T>(url: string, options: HttpRequestConfig = {}) {
  return http.request<T>({ url, ...options }).then((response) => response.data);
}

/** 发送验证码 POST /api/login/captcha */
export async function getFakeCaptcha(
  params: {
    // query
    /** 手机号 */
    phone?: string;
  },
  options?: { [key: string]: any },
) {
  return request<API.FakeCaptcha>('login/captcha', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
