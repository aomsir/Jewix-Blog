import { CardLinkProps } from "@/components/bases/card-link/CardLink"
import ArchiveContent, { ArchiveContentProps } from "@/components/pages/archive/ArchiveContent"
import BlogrollContent from "@/components/pages/blogroll/BlogrollContent"
import MsgContent from "@/components/pages/msg/MsgContent"
import { CommentsProps } from "@/components/pages/msg/MsgContentComments"
import PageContent from "@/components/pages/page/PageContent"
import TimeContent from "@/components/pages/time/TimeContent"
import { CommentEnums, PageEnums } from "@/configs/enums"
import { fetchCommentsById_TypeTransformed } from "@/servers/api"
import { fetchPageInfoByUrl } from "@/servers/api/page"
import { halfStart } from "@/utils/array"
import { timeStampToTimes, timesToTime } from "@/utils/convert"
import { addKey } from "@/utils/transform"
import { GetServerSideProps } from "next"
import ErrorPage from "next/error"
import Head from "next/head"
import { ReactElement } from "react"
export type PageProps = ArchiveContentProps & {
    pageInfo: {
        targetId: number
        title: string
        description: string
        content: string
        avatar?: string
        nickname: string
        date: string
        readers: number
        status?: number
        type: PageEnums.Type
    }
    initialComments: CommentsProps["comments"]
    globalBlogroll: CardLinkProps[]
}
export default function Page(props: PageProps): ReactElement {
    const mapping = {
        [PageEnums.Type.通用模板]: PageContent,
        [PageEnums.Type.留言板]: MsgContent,
        [PageEnums.Type.友人帐]: BlogrollContent,
        [PageEnums.Type.文章归档]: ArchiveContent,
        [PageEnums.Type.时光机]: TimeContent,
    }

    const ContentComponent = mapping[props.pageInfo.type as keyof typeof mapping]
    if (!ContentComponent) {
        return <ErrorPage statusCode={404} />
    }

    return (
        <>
            <Head>
                <title>{props.pageInfo.title}</title>
                <meta name="description" content={props.pageInfo.description} />
            </Head>
            <main>
                <ContentComponent {...props} />
            </main>
        </>
    )
}
export const PageCommentPageSize = 10

export const getServerSideProps: GetServerSideProps = async context => {
    try {
        if (!context.params) {
            throw new Error("url没有url参数")
        }
        const url = context.params.url
        if (!url) {
            throw new Error("url没有url参数")
        }
        // 获取页面信息
        const pageInfo = addKey(await fetchPageInfoByUrl(url as string), s => ({
            nickname: s.userName,
            date: timesToTime(halfStart(timeStampToTimes(Date.parse(s.createTime)))),
            readers: s.views,
            status: s.status ?? 0,
            avatar: "/avatar.png",
        }))
        // 获取评论
        const result = await fetchCommentsById_TypeTransformed({
            current: 1,
            pageSize: PageCommentPageSize,
            targetId: pageInfo.targetId,
            type: getCommentTypeByPageType(pageInfo.type),
        })

        let ownProps

        if (pageInfo.type === PageEnums.Type.文章归档) {
            ownProps = {
                ...(await ArchiveContent.getProps()),
            }
        } else {
            ownProps = {
                initialComments: result.list,
            }
        }

        return {
            props: { pageInfo, ...ownProps },
        }
    } catch (error) {
        console.log("page页面", error)
        return {
            notFound: true,
        }
    }
}

function getCommentTypeByPageType(pageType: PageEnums.Type) {
    const mapping = {
        [PageEnums.Type.时光机]: CommentEnums.Type.时光机,
        [PageEnums.Type.友人帐]: CommentEnums.Type.友人帐,
        [PageEnums.Type.留言板]: CommentEnums.Type.留言板,
        [PageEnums.Type.通用模板]: CommentEnums.Type.通用,
    }
    return mapping[pageType as keyof typeof mapping] ?? CommentEnums.Type.文章
}
