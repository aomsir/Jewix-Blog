import { HomeProps } from "@/pages"
import { BaseEntity, createBaseModel } from "."
// 后端文章模型
export interface Article extends BaseEntity {
    id: number
    uuid: string
    title: string
    cover: string
    content: string
    type: number
    originUrl: string
    views: number
    isTop: number
    isDelete: number
    description?: string
}
// HACK 后期可能会去掉非article的属性
/**
 * 创建文章模型，携带的参数则会覆盖默认值
 */
export const createArticle = <T extends Partial<Article>>(article?: T) =>
    ({
        id: 0,
        uuid: "",
        title: "",
        cover: "",
        content: "",
        type: 0,
        originUrl: "",
        views: 0,
        isTop: 0,
        isDelete: 0,
        ...createBaseModel(),
        ...article,
    } as T & Article)
// 模型转换
createArticle.toHomeArticle = (article: Partial<Article>): HomeProps["initialBlogs"][0] => {
    const _article = createArticle(article)

    return {
        id: _article.uuid,
        image: _article.cover,
        title: _article.title,
        desc: _article.description ?? _article.content,
        viewers: _article.views,
        isTop: !!_article.isTop,
    }
}
