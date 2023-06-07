import { request } from '@umijs/max';
import { API } from '../ant-design-pro/typings';

export const fetchSetting = (params: API.FetchSettingParams) =>
  request('admin/configs', { params });
