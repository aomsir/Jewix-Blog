import BoardInfo from "@/components/bases/board-info/BoardInfo"
import CardComment from "@/components/bases/card-comment/CardComment"
import { CardLinkProps } from "@/components/bases/card-link/CardLink"
import FormComment from "@/components/bases/form-comment/FormComment"
import { message } from "@/components/bases/message/Message"
import MyInfiniteScroll, { ActionRefType } from "@/components/bases/myInfiniteScroll/MyInfiniteScroll"
import BlogrollTabbar, { BlogrollTabbarProps } from "@/components/pages/blogroll/BlogrollTab"
import { CommentsProps } from "@/components/pages/msg/MsgContentComments"
import { BlogrollEnums, CommentEnums } from "@/configs/enums"
import { PageCommentPageSize, PageProps } from "@/pages/[url]"
import { fetchAllInnerBlogroll, fetchCommentsById_TypeTransformed, insertCommentWithTransforming, insertRootCommentWithTransforming } from "@/servers/api"
import css from "./BlogrollContent.module.scss"
import { ReactElement, useEffect, useRef, useState } from "react"
import { convertKeyOfArray } from "@/utils/convert"
interface BlogrollContentProps {
    globalBlogroll: CardLinkProps[]
    initialComments: CommentsProps["comments"]
    pageInfo: PageProps["pageInfo"]
}
/**
 * page
 * @returns 友情链接
 */
export default function BlogrollContent({ pageInfo, initialComments, globalBlogroll }: BlogrollContentProps): ReactElement {
    const actionRef = useRef<ActionRefType>()
    const fetchItems = async (current: number) => {
        const result = await fetchCommentsById_TypeTransformed({
            current,
            pageSize: PageCommentPageSize,
            targetId: pageInfo.targetId,
            type: CommentEnums.Type.友人帐,
        })

        return result.list
    }

    const [blogroll, setBlogroll] = useState<BlogrollTabbarProps["blogroll"]>()

    useEffect(() => {
        ;(async () => {
            const innerBlogroll = await fetchAllInnerBlogroll()
            const innerBlogrollConverted = convertKeyOfArray(innerBlogroll, {
                description: "desc",
                photo: "avatar",
                link: "url",
            })

            setBlogroll([
                {
                    location: BlogrollEnums.Location.首页,
                    list: globalBlogroll,
                },
                { location: BlogrollEnums.Location.内页, list: innerBlogrollConverted as any },
            ])
        })()
    }, [])

    return (
        <div className={css.blogrollContent}>
            {/* 信息 */}
            <BoardInfo className="infoBoard" title="友情链接" info={{ ...pageInfo }}>
                {blogroll && <BlogrollTabbar blogroll={blogroll} content={pageInfo.content} />}
            </BoardInfo>
            {/* 历史评论 */}
            <MyInfiniteScroll
                actionRef={actionRef}
                request={fetchItems}
                /* @ts-ignore */
                initialList={initialComments}
                pageSize={PageCommentPageSize}
                noDataText="暂无数据"
                contentRender={list => (
                    <>
                        <h2 className="totalMessageCount">{list.length}条评论</h2>
                        {list.map(comment => (
                            <CardComment
                                key={comment.id}
                                comment={comment}
                                onConfirm={async formData => {
                                    await insertCommentWithTransforming({ ...formData, type: CommentEnums.Type.友人帐, targetId: pageInfo.targetId })
                                    actionRef.current?.reload()
                                    message.success("评论成功")
                                }}
                            />
                        ))}
                    </>
                )}
            />
            {/* 评论表单 */}
            <h2 className="publish">发表评论</h2>
            <FormComment
                className="publish-comment"
                onConfirm={async formData => {
                    await insertRootCommentWithTransforming({ ...formData, type: CommentEnums.Type.友人帐, targetId: pageInfo.targetId })
                    actionRef.current?.reload()
                    message.success("评论成功")
                }}
            />
        </div>
    )
}
