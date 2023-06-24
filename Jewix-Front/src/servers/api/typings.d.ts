import { ArticleEnums, CommentEnums, PageEnums } from "@/configs/enums"

export namespace API {
    type FetchWebSiteSetting = {
        config: string;
        createTime: string;
        id: number;
        status: number;
        updateTime: string;
    };
    export type ResponseStructure<Result = any> = {
        code: number
        result?: Result
        msg: string
        token?: string
        status?: API.FetchUserDetailResponse
    }
    export type PaginationParams = {
        current?: number
        pageSize?: number
    }
    export type PaginationResponse<List = any[]> = {
        list: List
        pageIndex: number
        pageSize: number
        totalCount: number
        totalPage: number
    }
    export type TimeResponse = {
        createTime: string
        updateTime: string
    }
    export type FetchArticlesResponse = {
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
        status: number
    }
    export type FetchArticleDetailResponse = API.FetchArticlesResponse &
        API.TimeResponse & {
            commentCount?: number
            categories: string[]
            tags: string[]
            userName?: string
        }
    export type FetchAllCategoriesResponse = {
        id: number
        categoryName: string
        sonList: API.FetchAllCategoriesResponse
    }
    export type InsertCommentBody = {
        author: string
        content: string
        email: string
        url: string
        parentId: number
        permId: number
        targetId: number
        type: CommentEnums.Type
    }
    export type FetchCommentsParams = {
        targetId: number
        type: CommentEnums.Type
    }
    export type FetchCommentsResponse = API.TimeResponse & {
        status: number
        id: number
        author: string
        content: string
        email: string
        url: string
        ip: string
        location: string
        agent: string
        type: CommentEnums.Type
        targetId: number
        parentId: number
        permId: number
        blogger: boolean
        childList: API.FetchCommentsResponse[]
    }
    export type FetchSiteInfoResponse = {
        nickname: string
        email: string
        description: string
        articleCount: number
        commentCount: number
        runTime: string
        lastActive: number
        keywords: string[]
        socialInfo: Record<string, string>
        title: string,
        webDescription: string,
        police: string
        icp: string
    }
    export type FetchRecommendedArticlesResponse = {
        id: number
        title: string
        cover: string
        uuid: string
    }
    export type FetchArchiveInfoResponse = { articleCount: number; commentCount: number; categoryCount: number; tagCount: number }
    export type FetchArchivedArticlesResponse = {
        title: string
        createTime: string
        uuid: string
    }
    export type FetchPagesResponse = {
        createTime: string
        updateTime: string
        status: null
        id: number
        uuid: string
        userId: number
        title: string
        content: string
        description: string
        type: PageEnums.Type
        views: number
        userName: string
        omit: string
    }
    export type InsertPageBody = {
        title: string
        content: string
        description: string
        type: PageEnums
    }
}
