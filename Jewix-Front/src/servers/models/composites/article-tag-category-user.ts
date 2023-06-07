import { DetailArticleNull } from "@/components/pages/detail/DetailArticle"
import { timeStampToTimes } from "@/utils/convert"
import { convert } from ".."
import { Article, createArticle } from "../article"

export type ArticleTagCategoryUser = Article & {
    commentCount?: number
    categories: string[]
    tags: string[]
    userName?: string
}
/*
 * 创建文章种类标签用户名称复合模型，携带的参数则会覆盖默认值
 */
export const createArticleTagCategoryUser = (articleTagCategoryUser: Partial<ArticleTagCategoryUser>) => ({
    ...createArticle(),
    commentCount: 0,
    categories: [],
    tags: [],
    userName: "",
    ...articleTagCategoryUser,
})

createArticleTagCategoryUser.toDetailArticle = (articleTagCategoryUser: ArticleTagCategoryUser) => {
    const _articleTagCategoryUser = createArticleTagCategoryUser(articleTagCategoryUser)

    return convert(_articleTagCategoryUser, DetailArticleNull(), {
        publishTime: s => timeStampToTimes(Date.parse(s.createTime)),
        modificationTime: s => timeStampToTimes(Date.parse(s.updateTime)),
        viewers: "views",
        commentTotal: "commentCount",
        nickname: "userName",
        pre: "lastUuid",
        next: "nextUuid",
    })
}
