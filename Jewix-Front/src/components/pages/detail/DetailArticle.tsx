import ArrowRight from "@/components/icons/arrowRight/ArrowRight"
import Book2 from "@/components/icons/book/Book2"
import Edit2 from "@/components/icons/edit/Edit2"
import Eye from "@/components/icons/eye/Eye"
import Font from "@/components/icons/font/Font"
import Message from "@/components/icons/message/Message"
import Pen from "@/components/icons/pen/Pen"
import Play from "@/components/icons/play/Play"
import Time from "@/components/icons/time/Time"
import User from "@/components/icons/user/User"
import Link from "next/link"
import { ReactElement, useState } from "react"
import { HTMLAttributes } from "react"
import css from "./DetailArticle.module.scss"
import DetailArticleContent from "./DetailArticleContent"
import DetailArticleFooter from "./DetailArticleFooter"
export const DetailArticleNull = () => ({
    id: 0,
    title: "",
    nickname: "",
    publishTime: ["", "", "", "", "", ""],
    modificationTime: ["", "", "", "", "", ""],
    viewers: 0,
    words: 0,
    tags: [],
    tagIds: [],
    categories: [],
    categoryIds: [],
    content: "",
    pre: "",
    next: "",
    commentTotal: 0,
})
export interface DetailArticleProps extends HTMLAttributes<HTMLDivElement> {
    article: ReturnType<typeof DetailArticleNull>
}
export default function DetailArticle({
    article: { title, nickname, publishTime, modificationTime, viewers, words: initialWords, content, categories, categoryIds, commentTotal },
    className,
    ...rest
}: DetailArticleProps): ReactElement {
    const [words, setWords] = useState(initialWords)

    return (
        <article className={`${className ?? ""} ${css["detail-article"]}`} {...rest}>
            <header>
                <div className="top">
                    <div className="left">
                        <Link href="/">首页</Link>
                        <ArrowRight />
                        <h1>{title}</h1>
                    </div>
                    <div className="operations">
                        <Font />
                        <Play />
                        <Edit2 />
                        <Book2 />
                    </div>
                </div>
                <div className="bottom">
                    <span>
                        <User />
                        {nickname}
                    </span>
                    <span>
                        <Time />
                        {publishTime[0] + "年" + publishTime[1] + "月" + publishTime[2] + "日"}
                    </span>
                    <span>
                        <Eye />
                        {viewers}次浏览
                    </span>
                    <span>
                        <Message />
                        {commentTotal}条评论
                    </span>
                    <span>
                        <Pen />
                        {words}字数
                    </span>
                    <span>
                        #
                        {categories.map((item, i) => (
                            <Link key={i} href={`/category/${categoryIds[i]}`}>
                                {item}
                            </Link>
                        ))}
                    </span>
                </div>
            </header>
            <DetailArticleContent
                content={content}
                onMounted={contentEl => {
                    setWords(contentEl.textContent?.length!)
                }}
            />
            <DetailArticleFooter modificationTime={modificationTime} />
        </article>
    )
}
