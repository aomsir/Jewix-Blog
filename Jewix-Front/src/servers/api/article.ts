import { timeStampToTimes } from "@/utils/convert"
import { fetchFactory, fetchRowFactory, fetchTransformerFactory } from "@/utils/request"
import { createArticle } from "../models/article"
import { API } from "./typings"
/**
 *  获取文章列表
 * @param page 页码
 * @param size 每页数量
 */
export const fetchArticles = fetchFactory<API.FetchArticlesResponse, { title?: string }>("articles")
export const fetchArticleById = fetchRowFactory<API.FetchArticleDetailResponse>("articles")
export const fetchArticlesByCategoryId = fetchFactory<API.FetchArticlesResponse, { categoryId: number }>("categories/articles")
// 获取转换过后的文章列表
export const fetchArticlesByCategoryIdTransformed = fetchTransformerFactory(fetchArticlesByCategoryId, result => ({
    ...result,
    list: result.list.map(item => createArticle.toHomeArticle(item)),
}))
// 获取推荐文章
export const fetchRecommendedArticles = fetchRowFactory<API.FetchRecommendedArticlesResponse>("articles/random")
// 获取文章归档的文章列表
export const fetchArchivedArticles = fetchFactory<API.FetchArchivedArticlesResponse>("/articles/archive")
export const fetchArchivedArticlesTransformed = fetchTransformerFactory(fetchArchivedArticles, result => ({
    ...result,
    list: result.list.map(item => {
        const times = timeStampToTimes(Date.parse(item.createTime))
        return { ...item, date: { year: times[0], month: times[1], day: times[2], url: `detail/${item.uuid}` } }
    }),
}))
