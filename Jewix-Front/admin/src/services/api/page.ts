import { deleteFactory, fetchFactory, insertFactory, updateFactory } from '@/utils';
import { API } from '../ant-design-pro/typings';

export const fetchPages = fetchFactory<API.FetchPagesResponse>('pages');
export const insertPage = insertFactory<API.InsertPageBody>('admin/pages');
export const updatePage = updateFactory<API.UpdatePageBody>('admin/pages');
export const deletePage = deleteFactory<API.DeletePageParams>('admin/pages');
// export const fetchPageByUUID = fetchRowFactory<API.FetchPageResponse>('pages');
