import DetailArticle, { DetailArticleProps } from "@/components/pages/detail/DetailArticle"
import DetailComments from "@/components/pages/detail/DetailComments"
import { CommentsProps } from "@/components/pages/msg/MsgContentComments"
import { fetchCommentsById_TypeTransformed } from "@/servers/api"
import { fetchArticleById } from "@/servers/api/article"
import { createArticleTagCategoryUser } from "@/servers/models/composites/article-tag-category-user"
import { CommentEnums } from "@/configs/enums"
import { GetServerSideProps } from "next"
import Head from "next/head"
import Link from "next/link"
import { MouseEvent, ReactElement } from "react"
import css from "./index.module.scss"
import { message } from "@/components/bases/message/Message"
export type DetailProps = DetailArticleProps & {
    comments: CommentsProps["comments"]
}
/**
 * page
 * @returns 文章详情
 */
export default function Detail(props: DetailProps): ReactElement {
    function preCheck(e: MouseEvent<HTMLAnchorElement>, uuid: string): void {
        if (!uuid) {
            e.preventDefault()
            message.error("没有了文章了😥")
        }
    }

    return (
        <>
            <Head>
                <title>{props.article.title}</title>
                <meta name="description" content="文章详情" />
            </Head>
            <main className={css.detail}>
                <DetailArticle {...props} />
                <div className="pre-next">
                    <Link href={`/detail/${props.article.pre}`} onClick={e => preCheck(e, props.article.pre)}>
                        上一篇
                    </Link>
                    <Link href={`/detail/${props.article.next}`} onClick={e => preCheck(e, props.article.next)}>
                        下一篇
                    </Link>
                </div>
                <p className="how-many-comments">{props.article.commentTotal}条评论</p>
                <DetailComments comments={props.comments} article={props.article} />
            </main>
        </>
    )
}

export const getServerSideProps: GetServerSideProps = async context => {
    //@ts-ignore
    const { id } = context.params
    try {
        // 获取文章
        const article = createArticleTagCategoryUser.toDetailArticle(
            (
                await fetchArticleById(undefined, [id], {
                    headers: context.req.headers,
                })
            ).data.result!
        )
        // 获取评论
        const result = await fetchCommentsById_TypeTransformed({ current: 1, pageSize: 15, type: CommentEnums.Type.文章, targetId: article.id })
        // 更新评论数
        // article.commentTotal = result?.totalCount ?? 0
        return {
            props: {
                article,
                comments: result.list,
            },
        }
    } catch (error) {
        console.log(error)

        return {
            notFound: true,
        }
    }
}
