import { ReactElement, useRef, useState } from "react"
import { HTMLAttributes } from "react"
import MsgBoard from "./MsgContentBoard"
import css from "./MsgContent.module.scss"
import Comments, { CommentsProps } from "./MsgContentComments"
import { fetchCommentsById_TypeTransformed, insertCommentWithTransforming, insertRootCommentWithTransforming } from "@/servers/api"
import { CommentEnums } from "@/configs/enums"
import { ActionRefType } from "@/components/bases/myInfiniteScroll/MyInfiniteScroll"
import { message } from "@/components/bases/message/Message"
import { PageCommentPageSize, PageProps } from "@/pages/[url]"

interface MsgContentProps extends HTMLAttributes<HTMLDivElement> {
    initialComments: CommentsProps["comments"]
    pageInfo: PageProps["pageInfo"]
}
export default function MsgContent({ className, initialComments, pageInfo, ...rest }: MsgContentProps): ReactElement {
    // BUG 评论加载第二页后，数量翻倍
    const [commentsLength, setCommentsLength] = useState(initialComments.length)
    const actionRef = useRef<ActionRefType>()

    const fetchItems = async (current: number) => {
        const result = await fetchCommentsById_TypeTransformed({
            current,
            pageSize: PageCommentPageSize,
            type: CommentEnums.Type.留言板,
            targetId: pageInfo.targetId,
        })

        setCommentsLength(pre => pre + result.list.length)
        return result.list
    }

    return (
        <div className={`${className} ${css["msg-content"]}`} {...rest}>
            <MsgBoard
                className="msg-board card"
                info={{ ...pageInfo, totalCommentCount: commentsLength }}
                onConfirm={async formData => {
                    try {
                        await insertRootCommentWithTransforming({ ...formData, type: CommentEnums.Type.留言板, targetId: pageInfo.targetId })
                        actionRef.current?.reload()
                        message.success("留言成功")
                    } catch (error) {}
                }}
            />
            <Comments
                className="comments"
                fetchItems={fetchItems}
                comments={initialComments}
                actionRef={actionRef}
                onConfirm={async formData => {
                    await insertCommentWithTransforming({ ...formData, type: CommentEnums.Type.留言板, targetId: pageInfo.targetId })
                    actionRef.current?.reload()
                    message.success("评论成功")
                }}
            />
        </div>
    )
}
