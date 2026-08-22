import { UserEnums } from "@/config/enums";
import { http, type HttpRequestConfig } from "@/lib/http/client";
import { deleteFactory, updateFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

/** 登录接口 POST /login */
export function login(body: API.LoginParams, options?: Record<string, any>) {
  return http
    .request<API.ResponseStructure>({
      url: "login",
      method: "POST",
      data: body,
      ...(options || {}),
    })
    .then((response) => response.data);
}
export const logout = () => http.request({ url: "logout" }).then((response) => response.data);
/**
 * 获取当前用户信息
 * @param options
 * @returns
 */
export function fetchCurrentUserInfo(options?: HttpRequestConfig) {
  return http
    .request<API.ResponseStructure<API.FetchUserDetailResponse>>({
      url: "users/current",
      ...options,
      method: "get",
    })
    .then((response) => response.data);
}
export const updateCurrentUserInfo = updateFactory<API.UpdateUserDetailBody>("admin/users/my");
// 获取用户列表
export function fetchUsers({ current: page, pageSize: length, ...rest }: API.FetchUserParams) {
  return http
    .request<API.ResponseStructure<API.PaginationResponse<API.FetchUserResponse[]>>>({
      url: "admin/users",
      params: {
        page,
        length,
        ...rest,
      },
    })
    .then((response) => response.data);
}

export function insertUser(data: API.InsertUserParams, options?: Record<string, any>) {
  return http
    .request<API.ResponseStructure>({
      url: "admin/users",
      method: "post",
      data,
      ...(options ?? {}),
    })
    .then((response) => response.data);
}

export function updateUser(data: API.UpdateUserParams, options?: Record<string, any>) {
  return http
    .request<API.ResponseStructure>({
      url: "admin/users",
      method: "put",
      data,
      ...(options ?? {}),
    })
    .then((response) => response.data);
}

export const deleteUsers = (params: API.DeleteParams, status: UserEnums.Status) => {
  if (status === UserEnums.Status.正常) {
    return deleteFactory<API.DeleteParams>("admin/users/archive")(params);
  } else if (status === UserEnums.Status.删除) {
    return deleteFactory<API.DeleteParams>("admin/users/physics")(params);
  }
};

export const updateUserStatus = updateFactory<API.UpdateUserStatusParams>("admin/users/status");
