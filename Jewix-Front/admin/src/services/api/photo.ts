import { PhotoEnums } from "@/config/enums";
import { API } from "@/services/ant-design-pro/typings";
import { deleteFactory, fetchFactory } from "@/utils/request";

export const fetchPhotos = fetchFactory<API.FetchPhotosResponse, API.FetchPhotosParams>(
  "admin/photos",
);
export const deletePhoto = deleteFactory<API.DeletePhotoParams>("admin/photos");
export const getInsertImageUrl = (type = PhotoEnums.type.又拍云) =>
  `/api/admin/photos?type=${type}`;
export const getFetchImageUrl = (res: API.FetchPhotosResponse) =>
  res.url ?? `/api/photos/download?name=${res.fileName}`;
