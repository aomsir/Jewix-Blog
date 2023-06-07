import { API } from '@/services/ant-design-pro/typings';
import { request } from '@umijs/max';
/**
 * 将分页函数转换为获取全部数据的异步请求函数
 * @param fetchFunc 分页fetch函数
 * @returns 获取全部数据的异步请求函数
 */
export function fetchAllFactory<
  Response extends unknown,
  P extends undefined | Record<string, any>,
>(
  fetchFunc: (
    params: API.PaginationParams & P,
    options?: Record<any, any>,
  ) => Promise<API.ResponseStructure<API.PaginationResponse<Response[]>>>,
) {
  return async (rest?: P, options?: Record<any, any>) => {
    const list = [] as unknown as Response[] | [];
    const size = 30;
    let page = 1;

    while (true) {
      const { result } = await fetchFunc(
        { current: page++, pageSize: size, ...(rest as P) },
        options,
      );

      if (!result?.list.length) {
        break;
      }
      // @ts-ignore
      list.push(...result.list);
    }

    return list;
  };
}
/**
 * 通用fetch函数工厂，生成fetch数据的函数
 * @param url 远程路径
 * @returns fetch数据的函数
 */
export function fetchFactory<R, P = unknown>(url: string) {
  return (
    { current: page, pageSize: length, ...rest }: P & API.PaginationParams,
    options?: Record<any, any>,
  ) => {
    return request<API.ResponseStructure<API.PaginationResponse<R[]>>>(url, {
      params: {
        page,
        length,
        ...rest,
      },
      ...(options || {}),
    });
  };
}
export function fetchRowFactory<R, P = undefined>(url: string) {
  return (params?: P, paths?: string[], options?: Record<any, any>) => {
      return request<API.ResponseStructure<R>>(url + (paths?.length ? "/" + paths.join("/") : ""), {
          params,
          ...(options || {}),
      })
  }
}
/**
 * 通用write函数工厂，生成发送post,put请求的函数
 * @param url 远程路径
 * @returns 发送post,put请求的函数
 */
export function writeFactory<P>(url: string, method: 'post' | 'put') {
  return (data: P, options?: Record<string, any>) => {
    return request<API.ResponseStructure>(url, {
      method,
      data,
      ...(options ?? {}),
    });
  };
}

export const insertFactory = <P>(url: string) => writeFactory<P>(url, 'post');
export const updateFactory = <P>(url: string) => writeFactory<P>(url, 'put');

export function deleteFactory<P = API.DeleteParams>(url: string) {
  return ({ ...rest }: P, options?: Record<string, any>) => {
    return request<API.ResponseStructure>(url, {
      method: 'delete',
      params: {
        ...rest,
      },
      // 重写paramsSerializer，使得delete请求能够处理数组
      paramsSerializer: (params) =>
        Object.keys(params)
          .map((key) => {
            const value = params[key];
            if (Array.isArray(value)) {
              return value.map((v) => `${key}=${v}`).join('&');
            }
            return `${key}=${value}`;
          })
          .join('&'),
      ...(options ?? {}),
    });
  };
}
