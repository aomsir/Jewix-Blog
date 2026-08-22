import { http } from "@/lib/http/client";
import {
  deleteFactory,
  fetchAllFactory,
  fetchFactory,
  insertFactory,
  updateFactory,
} from "@/utils/request";
import { API } from "../ant-design-pro/typings";

export const fetchTags = fetchFactory<API.FetchTagResponse>("tags");
export const insertTag = insertFactory<API.InsertTagBody>("admin/tags");
export const updateTag = updateFactory<API.UpdateTagBody>("admin/tags");
export const deleteTags = deleteFactory<{ tagIds: number[] }>("admin/tags");
export const fetchTagById = (id: API.FetchTagResponse["id"]) =>
  http.request({ url: `/admin/tags/${id}` }).then((response) => response.data);

export const fetchAllTags = fetchAllFactory(fetchTags);
