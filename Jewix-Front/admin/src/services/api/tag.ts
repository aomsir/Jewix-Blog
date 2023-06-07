import { fetchAllFactory, fetchFactory, insertFactory, updateFactory } from '@/utils/request';
import { request } from '@umijs/max';
import { API } from '../ant-design-pro/typings';

export const fetchTags = fetchFactory<API.FetchTagResponse>('tags');
export const insertTag = insertFactory<API.InsertTagBody>('admin/tags');
export const updateTag = updateFactory<API.UpdateTagBody>('admin/tags');
export const fetchTagById = (id: API.FetchTagResponse['id']) => request(`/admin/tags/${id}`);

export const fetchAllTags = fetchAllFactory(fetchTags);
