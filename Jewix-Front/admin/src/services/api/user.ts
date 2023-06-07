import { UserEnums } from '@/config/enums';
import { deleteFactory, updateFactory } from '@/utils';
import { request } from '@umijs/max';
import { API } from '../ant-design-pro/typings';

/** 登录接口 POST /login */
export function login(body: API.LoginParams, options?: Record<string, any>) {
  return request<API.ResponseStructure>('login', {
    method: 'POST',
    data: body,
    ...(options || {}),
  });
}
export const logout = () => request('logout');
/**
 * 获取当前用户信息
 * @param options
 * @returns
 */
export function fetchCurrentUserInfo(options?: Record<string, any>) {
  return request<API.ResponseStructure<API.FetchUserDetailResponse>>('users/current');
}
export const updateCurrentUserInfo = updateFactory<API.UpdateUserDetailBody>('admin/users/my');
// 获取用户列表
export function fetchUsers({ current: page, pageSize: length, ...rest }: API.FetchUserParams) {
  return request<API.ResponseStructure<API.PaginationResponse<API.FetchUserResponse[]>>>(
    'admin/users',
    {
      params: {
        page,
        length,
        ...rest,
      },
    },
  );
}

export function insertUser(data: API.InsertUserParams, options?: Record<string, any>) {
  return request<API.ResponseStructure>('admin/users', {
    method: 'post',
    data,
    ...(options ?? {}),
  });
}

export function updateUser(data: API.UpdateUserParams, options?: Record<string, any>) {
  return request<API.ResponseStructure>('admin/users', {
    method: 'put',
    data,
    ...(options ?? {}),
  });
}

// export function deleteUser(id: number, options?: Record<string, any>) {
//   return request<API.ResponseStructure>(`admin/users/${id}`, {
//     method: 'delete',
//     ...(options ?? {}),
//   });
// }
export const deleteUsers = (params: API.DeleteParams, status: UserEnums.Status) => {
  if (status === UserEnums.Status.正常) {
    return deleteFactory<API.DeleteParams>('admin/users/archive')(params);
  } else if (status === UserEnums.Status.删除) {
    return deleteFactory<API.DeleteParams>('admin/users/physics')(params);
  }
};

export const updateUserStatus = updateFactory<API.UpdateUserStatusParams>('admin/users/status');
