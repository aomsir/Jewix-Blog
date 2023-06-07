import { CommentsProps } from "@/components/pages/msg/MsgContentComments"
import { MouseEvent, ReactElement, useEffect, useRef, useState } from "react"
import { HTMLAttributes } from "react"
import css from "./CardComment.module.scss"
import FormComment, { CommentFormData } from "../form-comment/FormComment"
import { getIcon } from "@/configs/icons"
import { getAvatarUrlByEmail } from "@/utils/avatar"
import MyImage from "@/components/ui/img/Image"
import Loader from "@/components/ui/loading/Loading"
import BloggerIcon from "@/components/icons/blogger/BloggerIcon"
interface CardCommentProps extends HTMLAttributes<HTMLDivElement> {
    comment: CommentsProps["comments"]["0"]
    onConfirm: (
        commentFormData: CommentFormData & {
            parentId: CommentsProps["comments"]["0"]["id"]
            permId: number
        }
    ) => void
}
export default function CardComment({ className, comment, onConfirm, ...rest }: CardCommentProps): ReactElement {
    // 关闭评论表单
    const closeFormComment = (formCommentEl: HTMLElement) => {
        formCommentEl.classList.add("hidden")
        formCommentEl.style.height = formCommentEl.getBoundingClientRect().height + "px"
        setTimeout(() => {
            formCommentEl.style.height = "0px"
        }, 0)
    }
    // 打开评论表单
    const openFormComment = (e: MouseEvent) => {
        // 按钮元素
        const buttonEl = e.target as HTMLElement
        // 评论表单元素
        const formCommentEl = buttonEl.parentElement?.lastElementChild as HTMLElement
        if (!formCommentEl) return
        // 显示
        if (formCommentEl.classList.contains("hidden")) {
            /* 显示 */
            formCommentEl.classList.remove("hidden")
            formCommentEl.style.height = formCommentEl.firstElementChild?.getBoundingClientRect().height + "px"
            /* 添加外部鼠标的点击关闭显示事件（同时也实现了点击其他回复按钮此处的表单就会隐藏，单显） */
            const clickCloseHandler = (e2: globalThis.MouseEvent) => {
                if (e2.target && !formCommentEl.contains(e2.target as HTMLElement)) {
                    closeFormComment(formCommentEl)
                    document.removeEventListener("click", clickCloseHandler)
                }
            }
            // 不要立即执行
            setTimeout(() => {
                document.addEventListener("click", clickCloseHandler)
            }, 0)
            /* 聚焦 */
            const formCommentTextAreaEl = formCommentEl.querySelector("textarea")
            setTimeout(() => {
                formCommentTextAreaEl?.focus()
            }, 50)
            // auto高度
            setTimeout(() => {
                formCommentEl.style.height = "auto"
            }, 200)
        }
    }
    // 头像url
    const avatarUrl = getAvatarUrlByEmail(comment.email)

    return (
        <div className={`${className ?? ""} ${css.comment} `} {...rest}>
            {/* 头像信息 */}
            <a className="avatar" href={comment.url || "javascript:"} target="_blank">
                <MyImage className="image" src={avatarUrl} alt="头像" width={40} height={40} fallback="/defaultAvatar.png" loader={<Loader />} />
                {comment.isBlogger && <BloggerIcon className="bloggerIcon" />}
            </a>{" "}
            <section className="flex flex-col  justify-between">
                <section className="flex items-center gap-1">
                    <div className="name">
                        <a href={comment.url || "javascript:"} target="_blank">
                            {comment.user}
                        </a>
                    </div>
                    <div className="tooltip" data-tip={comment.browser.join(" ")}>
                        {getIcon(comment.browser[0], "browser")}
                    </div>
                    <div className="tooltip" data-tip={comment.os.join(" ")}>
                        {getIcon(comment.os[0], "os")}
                    </div>
                </section>
                <div className="desc">
                    <span>{comment.date[0] + "年" + comment.date[1] + "月" + comment.date[2] + "日"}</span>
                    <span>IP属地:&nbsp;{comment.address}</span>
                </div>
            </section>
            {/* 评论内容 */}
            <section className="col-start-2 mt-1.5">
                <div className="text">
                    <span className="font-bold">{comment.for && "@" + comment.for}</span>&nbsp;{comment.content}
                </div>
                {/* 回复按钮 */}
                <button className="reply" onClick={openFormComment}>
                    回复
                </button>
                {/* 二级评论 */}
                {(comment.replies?.length &&
                    comment.replies.map(comment2 => (
                        <CardComment
                            className="sub-comment"
                            key={comment2.id}
                            comment={comment2}
                            onConfirm={formData => onConfirm({ ...formData, permId: comment.id })}
                        />
                    ))) ||
                    null}
                {/* 回复表单 */}
                <div className="visible-container hidden">
                    <FormComment
                        onConfirm={formData =>
                            onConfirm({
                                ...formData,
                                parentId: comment.id,
                                permId: comment.id,
                            })
                        }
                        className="form-comment"
                    />
                </div>
            </section>
        </div>
    )
}
