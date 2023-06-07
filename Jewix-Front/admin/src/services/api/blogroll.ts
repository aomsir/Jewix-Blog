import { deleteFactory, fetchFactory, insertFactory, updateFactory } from '@/utils/request';
import { API } from '../ant-design-pro/typings';

export const fetchBlogroll = fetchFactory<API.FetchBlogrollResponse, API.FetchBlogrollParams>(
  'friend-links/page',
);
export const insertBlogroll = insertFactory<API.InsertBlogrollBody>('/admin/friend-links');
export const updateBlogroll = updateFactory<API.UpdateBlogrollBody>('/admin/friend-links');

export const deleteBlogroll = deleteFactory<API.DeleteParams>('/admin/friend-links');
