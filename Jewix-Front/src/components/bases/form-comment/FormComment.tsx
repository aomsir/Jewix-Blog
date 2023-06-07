import { emojis } from "@/assets/emojis"
import Smile from "@/components/icons/smile/Smile"
import { useInput } from "@/hooks"
import { ReactElement, useEffect, useRef, useState } from "react"
import { HTMLAttributes } from "react"
import Visible from "../visible/Visible"
import css from "./FormComment.module.scss"
import MyImage from "@/components/ui/img/Image"
import { getAvatarUrlByEmail } from "@/utils/avatar"
import Loading from "@/components/ui/loading/Loading"
interface CommentProps extends HTMLAttributes<HTMLDivElement> {
    onConfirm: (formData: CommentFormData) => void
}
export type CommentFormData = {
    content: string
    nickName: string
    email: string
    website: string
}
export default function FormComment({ className, onConfirm, ...rest }: CommentProps): ReactElement {
    const contentRef = useRef(null)
    // 用来记录文本域的焦点位置
    const [selectionStart, setSelectionStart] = useState(Infinity)
    useEffect(() => {
        // 第一次不执行
        if (selectionStart === Infinity) return
        // 在更新selectionStart状态后执行聚焦目标位置
        const contentEl = contentRef.current as HTMLTextAreaElement | null
        if (contentEl) {
            contentEl.setSelectionRange(selectionStart, selectionStart)
            contentEl.focus()
        }
    }, [selectionStart])
    let formComment
    if (typeof localStorage !== "undefined") {
        formComment = JSON.parse(localStorage.getItem("formComment") ?? "{}")
    }
    /* 表单 */
    const [[content, setContent], contentProps] = useInput("")
    const [[nickName, setNickname], nicknameProps] = useInput(formComment?.nickName)
    const [[email, setEmail], emailProps] = useInput(formComment?.email)
    const [[website, setWebSite], webSiteProps] = useInput(formComment?.website)
    // const [[, setAnonymous], anonymousProps] = useInput(true)
    /**
     * 添加表情到content表单控件中
     */
    const addEmoji = (emoji: (typeof emojis)["0"]) => {
        const contentEl = contentRef.current as HTMLTextAreaElement | null
        if (contentEl) {
            let content = contentProps.value
            // 更新content
            content = content.substring(0, contentEl.selectionStart) + emoji.character + content.substring(contentEl.selectionEnd)
            setContent(content)
            // 更新焦点位置
            setSelectionStart(contentEl.selectionStart + emoji.character.length)
        }
    }

    // 头像url
    const [avatarUrl, setAvatarUrl] = useState(getAvatarUrlByEmail(email))

    return (
        <div className={`${className ?? ""} ${css.comment}`} {...rest}>
            {/* 表单 */}
            <form
                className="form"
                onSubmit={e => {
                    e.preventDefault()
                    // 保存表单数据到本地
                    localStorage.setItem(
                        "formComment",
                        JSON.stringify({
                            nickName,
                            email,
                            website,
                        })
                    )
                    onConfirm({
                        content,
                        nickName,
                        email,
                        website,
                    })
                }}
            >
                <div className="form-item comment">
                    <div className="label">
                        <span>评论</span>
                        <span className="text-red-500">*</span>
                    </div>
                    <div className="content">
                        <textarea placeholder="说点什么把..." ref={contentRef} {...contentProps}></textarea>
                        {/* 表情 */}
                        <Visible
                            content={
                                <div className="emoji-list">
                                    {emojis.map((emoji, i) => (
                                        <span className="tooltip" onClick={() => addEmoji(emoji)} data-tip={emoji.name} key={i}>
                                            {emoji.character}
                                        </span>
                                    ))}
                                </div>
                            }
                        >
                            <button type="button" className="emoji-btn" onClick={() => (contentRef.current as HTMLTextAreaElement | null)?.focus()}>
                                <Smile />
                                表情
                            </button>
                        </Visible>
                    </div>
                </div>
                <section className="info flex gap-4">
                    <div className="form-item name">
                        <div className="label">
                            <span>名称</span>
                            <span className="text-red-500">*</span>
                        </div>
                        <input className="content" name="name" type="text" placeholder="姓名或昵称" {...nicknameProps}></input>
                    </div>
                    <div className="form-item email">
                        <div className="label">
                            <span>邮箱</span>
                            <span className="text-red-500">*</span>
                        </div>
                        <MyImage className="image" src={avatarUrl} width={24} height={24} alt="" loader={<Loading />} />
                        <input
                            className="content"
                            name="email"
                            type="email"
                            placeholder="邮箱（ 将保密 ）"
                            {...emailProps}
                            onChange={async e => {
                                emailProps.onChange(e)
                                // 生成头像url
                                setAvatarUrl(getAvatarUrlByEmail(e.currentTarget.value))
                            }}
                        ></input>
                    </div>
                    <div className="form-item address">
                        <div className="label">
                            <span>地址</span>
                        </div>
                        <input className="content" name="url" type="url" placeholder="网站或博客" {...webSiteProps}></input>
                    </div>
                </section>
                <div className="operations">
                    <button type="submit">发表评论</button>
                    {/* <div className="switch-wrap">
                        <span className="label">悄悄话</span>
                        <input
                            type="checkbox"
                            className="toggle"
                            checked={anonymousProps.value}
                            onChange={_ => {
                                setAnonymous(!anonymousProps.value)
                            }}
                        />
                    </div> */}
                </div>
            </form>
        </div>
    )
}
