import { deleteFactory, fetchFactory } from '@/utils/request';
import { API } from '@/services/ant-design-pro/typings';
import { PhotoEnums } from '@/config/enums';
import { BASE_URL } from '/config/proxy';

export const fetchPhotos = fetchFactory<API.FetchPhotosResponse, API.FetchPhotosParams>(
  'admin/photos',
);
export const deletePhoto = deleteFactory<API.DeletePhotoParams>('admin/photos');
// 获取上传图片的地址
export const getInsertImageUrl = (type = PhotoEnums.type.又拍云) =>
  `${BASE_URL}/api/admin/photos?type=${type}`;
export const getFetchImageUrl = (res: API.FetchPhotosResponse) =>
  res.url ?? `${BASE_URL}/api/photos/download?name=${res.fileName}`;
