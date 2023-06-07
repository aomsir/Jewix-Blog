import CardComment from "@/components/bases/card-comment/CardComment"
import FormComment from "@/components/bases/form-comment/FormComment"
import { DetailProps } from "@/pages/detail/[id]"
import { fetchCommentsById_TypeTransformed, insertComment } from "@/servers/api"
import { CommentEnums } from "@/configs/enums"
import { ReactElement, useState } from "react"
import { HTMLAttributes } from "react"
import { DetailArticleProps } from "./DetailArticle"
import css from "./DetailComment.module.scss"
import { transformCommentToBackend } from "@/servers/transformers"
import InfiniteScroll from "react-infinite-scroller"
import BaseLoading from "@/components/bases/loading/BaseLoading"

interface DetailCommentProps extends HTMLAttributes<HTMLDivElement> {
    comments: DetailProps["comments"]
    article: DetailArticleProps["article"]
}
export default function DetailComment({ className, comments: initialComments, article, ...rest }: DetailCommentProps): ReactElement {
    const [comments, setComments] = useState(initialComments)
    const [hasMore, setHasMore] = useState(true)
    let isLoaded = false
    const size = 15

    const fetchItems = async () => {
        try {
            if (!isLoaded) {
                const result = await fetchCommentsById_TypeTransformed({
                    current: Math.ceil(comments.length / size) + 1,
                    pageSize: size,
                    type: CommentEnums.Type.文章,
                    targetId: article.id,
                })
                setComments(pre => [...pre, ...result.list])
                if (result.list.length < size) {
                    setHasMore(false)
                }
            }
        } catch (error) {
            setHasMore(false)
        }
    }
    return (
        <div className={`${className ?? ""} ${css["detail-comment"]}`} {...rest}>
            <div className="comments">
                <InfiniteScroll loadMore={fetchItems} hasMore={hasMore} loader={<BaseLoading key={0} />} initialLoad={false}>
                    {comments.map(comment => (
                        <CardComment
                            className="comment"
                            key={comment.id}
                            comment={comment}
                            // 发布多级评论
                            onConfirm={async formData => {
                                const commentAdded = transformCommentToBackend({
                                    ...formData,
                                    type: CommentEnums.Type.文章,
                                    targetId: article.id,
                                })

                                await insertComment(commentAdded)
                                import("@/components/web/message/message").then(({ myMessage }) => {
                                    myMessage({
                                        type: "success",
                                        text: "评论成功",
                                    })
                                })
                            }}
                        />
                    ))}
                </InfiniteScroll>
            </div>
            <h3>发表评论</h3>
            <FormComment
                className="comment-form"
                // 发布评论
                onConfirm={async formData => {
                    try {
                        const commentTransformed = transformCommentToBackend({
                            ...formData,
                            parentId: 0,
                            permId: 0,
                            type: CommentEnums.Type.文章,
                            targetId: article.id,
                        })
                        await insertComment(commentTransformed)
                        // 插入评论
                        setComments(prev => [...prev])
                        import("@/components/web/message/message").then(({ myMessage }) => {
                            myMessage({
                                type: "success",
                                text: "评论成功",
                            })
                        })
                        await fetchItems()
                    } catch (error) {}
                }}
            />
        </div>
    )
}
