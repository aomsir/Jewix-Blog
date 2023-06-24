import { convertKey } from "@/utils/convert"
import { fetchRowFactory, fetchTransformerFactory } from "@/utils/request"
import { addKey } from "@/utils/transform"
import { API } from "./typings"

export const fetchSiteInfo = fetchRowFactory<API.FetchSiteInfoResponse>("commons/webInfo")
export const fetchSiteInfoTransformed = fetchTransformerFactory(fetchSiteInfo, result => {
    return addKey(
        convertKey(result, {
            nickname: "name",
            description: "desc",
        } as const),
        source => ({
            image: "/avatar.png",
            contents: [
                ["文章数目", source.articleCount.toString()],
                ["评论数目", source.commentCount.toString()],
                ["运行天数", source.runTime?.toString()],
                ["最后活动", `${source.lastActive}天前`],
            ],
            links: Object.keys(source.socialInfo).map(key => ({
                label: key,
                url: source.socialInfo[key],
            }))
        })
    )
})

type PromiseType<T extends Promise<unknown>> = T extends Promise<infer U> ? U: never
export type SiteInfo = PromiseType<ReturnType<typeof fetchSiteInfoTransformed>>

export const fetchArchiveInfo = fetchRowFactory<API.FetchArchiveInfoResponse>("commons/articles/archiveInfo")
export const fetchArchiveInfoTransformed = fetchTransformerFactory(fetchArchiveInfo, result =>
    convertKey(result, {
        tagCount: "totalTagCount",
        categoryCount: "totalCategoryCount",
        articleCount: "totalArticleCount",
        commentCount: "totalCommentCount",
    } as const)
)

