import { API } from "@/services/ant-design-pro/typings";
import { deleteFactory, updateFactory } from "@/utils";
import { request } from "@umijs/max";

export function fetchArticles(params: API.PaginationParams, options?: Record<any, any>) {
    return request<API.ResponseStructure<API.PaginationResponse<API.FetchArticleResponse[]>>>(
        "admin/articles",
        {
            params: {
                page: params.current,
                length: params.pageSize,
            },
            ...(options || {}),
        },
    );
}

export function insertArticle(article: API.InsertArticleBody, options?: Record<any, any>) {
    return request<API.ResponseStructure<API.FetchArticleResponse>>("admin/articles", {
        method: "POST",
        data: article,
        ...(options || {}),
    });
}

export const updateArticle = updateFactory<API.UpdateArticleBody>("admin/articles");
export const fetchArticleByUUID = (uuid: API.FetchArticleResponse["uuid"]) =>
    request<API.ResponseStructure<API.FetchArticleDetailResponse>>(`articles/${uuid}`);

export const softDeleteArticles = deleteFactory("admin/articles/archive");
// 硬删除
export const deleteArticles = deleteFactory("/admin/articles/physics");
