import BoardInfo from "@/components/bases/board-info/BoardInfo"
import CardComment from "@/components/bases/card-comment/CardComment"
import FormComment from "@/components/bases/form-comment/FormComment"
import { message } from "@/components/bases/message/Message"
import MyInfiniteScroll, { ActionRefType } from "@/components/bases/myInfiniteScroll/MyInfiniteScroll"
import { CommentEnums } from "@/configs/enums"
import { PageCommentPageSize, PageProps } from "@/pages/[url]"
import { fetchCommentsById_TypeTransformed, insertCommentWithTransforming, insertRootCommentWithTransforming } from "@/servers/api"
import { useRouter } from "next/router"
import { ReactElement, useEffect, useRef, useState } from "react"
import { HTMLAttributes } from "react"
import css from "./PageContent.module.scss"
type PageContentProps = HTMLAttributes<HTMLDivElement> & {
    pageInfo: PageProps["pageInfo"]
    initialComments: PageProps["initialComments"]
}
export default function PageContent(props: PageContentProps): ReactElement {
    const { pageInfo, initialComments, ...rest } = props
    const actionRef = useRef<ActionRefType>()
    const { query } = useRouter()

    const fetchItems = async (current: number) => {
        const result = await fetchCommentsById_TypeTransformed({
            current,
            pageSize: PageCommentPageSize,
            type: CommentEnums.Type.通用,
            targetId: pageInfo.targetId,
        })

        return result.list
    }

    // 当页面query更改时，重新加载当前页面
    useEffect(() => {
        actionRef.current?.reload()
    }, [query])

    return (
        <div className={`${rest.className ?? ""} ${css.PageContent}`} {...rest}>
            <BoardInfo title={pageInfo.title}>
                <div dangerouslySetInnerHTML={{ __html: pageInfo.content }}></div>
            </BoardInfo>
            <MyInfiniteScroll
                request={fetchItems}
                noDataText="暂无评论"
                // @ts-ignore
                initialList={initialComments}
                pageSize={PageCommentPageSize}
                contentRender={list => (
                    <>
                        <h2 className="totalMessageCount">{list.length}条评论</h2>
                        {list.map(item => (
                            <CardComment
                                key={item.id}
                                comment={item}
                                onConfirm={async formData => {
                                    await insertCommentWithTransforming({ ...formData, targetId: pageInfo.targetId, type: CommentEnums.Type.通用 })
                                    actionRef.current?.reload()
                                    message.success("评论成功")
                                }}
                            />
                        ))}
                    </>
                )}
                actionRef={actionRef}
            />
            <FormComment
                className="comment-form"
                onConfirm={async formData => {
                    await insertRootCommentWithTransforming({ ...formData, targetId: pageInfo.targetId, type: CommentEnums.Type.通用 })
                    actionRef.current?.reload()
                    message.success("评论成功")
                }}
            />
        </div>
    )
}
