import CardComment from "@/components/bases/card-comment/CardComment"
import { CommentFormData } from "@/components/bases/form-comment/FormComment"
import MyInfiniteScroll, { ActionRefType } from "@/components/bases/myInfiniteScroll/MyInfiniteScroll"
import { PageCommentPageSize } from "@/pages/[url]"
import { fetchCommentsById_TypeTransformed } from "@/servers/api"
import { PromiseType } from "@/utils/type"
import { ReactElement } from "react"
import { HTMLAttributes } from "react"
export interface CommentsProps extends HTMLAttributes<HTMLDivElement> {
    comments: {
        id: number
        avatar?: string
        address: string
        browser: string[]
        os: string[]
        url: string
        user: string
        date: string[]
        content: string
        for?: string
        email: string
        replies?: CommentsProps["comments"]
        isBlogger?: boolean
    }[]
    onConfirm: (
        formData: CommentFormData & {
            parentId: any
            permId: number
        }
    ) => void
    fetchItems: (current: number) => Promise<PromiseType<ReturnType<typeof fetchCommentsById_TypeTransformed>>["list"]>
    actionRef: React.MutableRefObject<ActionRefType | undefined>
}
/**
 * 评论
 */
export default function Comments({ className, comments, onConfirm, actionRef, fetchItems, ...rest }: CommentsProps): ReactElement {
    return (
        <div className={`${className}`} {...rest}>
            <h2 className="totalMessageCount">{comments.length}条评论</h2>
            <MyInfiniteScroll
                /* @ts-ignore */
                initialList={comments}
                request={fetchItems}
                pageSize={PageCommentPageSize}
                actionRef={actionRef}
                contentRender={list => list.map(comment => <CardComment className="comment" comment={comment} key={comment.id} onConfirm={onConfirm} />)}
            />
        </div>
    )
}
