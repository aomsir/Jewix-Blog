import { http } from "@/lib/http/client";
import { API } from "@/services/ant-design-pro/typings";
import { deleteFactory, updateFactory } from "@/utils";

export function fetchArticles(params: API.PaginationParams, options?: Record<any, any>) {
  return http
    .request<API.ResponseStructure<API.PaginationResponse<API.FetchArticleResponse[]>>>({
      url: "admin/articles",
      params: {
        page: params.current,
        length: params.pageSize,
      },
      ...(options || {}),
    })
    .then((response) => response.data);
}

export function insertArticle(article: API.InsertArticleBody, options?: Record<any, any>) {
  return http
    .request<API.ResponseStructure<API.FetchArticleResponse>>({
      url: "admin/articles",
      method: "POST",
      data: article,
      ...(options || {}),
    })
    .then((response) => response.data);
}

export const updateArticle = updateFactory<API.UpdateArticleBody>("admin/articles");
export const fetchArticleByUUID = (uuid: API.FetchArticleResponse["uuid"]) =>
  http
    .request<API.ResponseStructure<API.FetchArticleDetailResponse>>({
      url: `admin/articles/${uuid}`,
    })
    .then((response) => response.data);

export const softDeleteArticles = deleteFactory("admin/articles/archive");
// 硬删除
export const deleteArticles = deleteFactory("/admin/articles/physics");
