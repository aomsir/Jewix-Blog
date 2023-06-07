import Button from "@/components/bases/button/Button"
import Book from "@/components/icons/book/Book"
import Category from "@/components/icons/category/Comment"
import Comment from "@/components/icons/comment/Comment"
import Edit from "@/components/icons/edit/Edit"
import Refresh from "@/components/icons/refresh/Refresh"
import { PageCommentPageSize } from "@/pages/[url]"
import { fetchArchivedArticlesTransformed } from "@/servers/api"
import { fetchArchiveInfoTransformed } from "@/servers/api/common"
import { ReactElement, useState } from "react"
import { HTMLAttributes } from "react"
import css from "./ArchiveContent.module.scss"
import ArchiveCard from "./card/ArchiveCard"
// @ts-ignore
import Timeline from "./timeline/Timeline"
export type ArchiveContentProps = HTMLAttributes<HTMLDivElement> & {
    totalArticleCount: number
    totalCommentCount: number
    totalCategoryCount: number
    totalTagCount: number
    histories: { title: string; date: { year: string; month: string; day: string }; url: string }[]
}
export default function ArchiveContent({
    className,
    totalArticleCount,
    totalCommentCount,
    totalCategoryCount,
    totalTagCount,
    histories,
    ...rest
}: ArchiveContentProps): ReactElement {
    const cards = [
        { label: "文章数", content: totalArticleCount?.toString(), icon: <Edit /> },
        { label: "评论数", content: totalCommentCount?.toString(), icon: <Comment /> },
        { label: "分类数", content: totalCategoryCount?.toString(), icon: <Category /> },
        { label: "标签数", content: totalTagCount?.toString(), icon: <Book /> },
    ]

    const [articles, setArticles] = useState(histories)
    const [hasMore, setHasMore] = useState(true)
    const fetchItems = async () => {
        if (hasMore) {
            const result = await fetchArchivedArticlesTransformed({ current: Math.ceil(articles.length / PageCommentPageSize) + 1, pageSize: 10 })
            // @ts-ignore
            setArticles(prev => [...prev, ...result.list])
            if (result.list.length < PageCommentPageSize) {
                setHasMore(false)
            }
        }
    }
    return (
        <div className={`${className ?? ""} ${css["archive-content"]}`} {...rest}>
            <h1>文章归档</h1>
            <div className="Cards">
                {cards.map((card, i) => (
                    <ArchiveCard {...card} key={i} />
                ))}
            </div>
            <Timeline histories={articles} />
            {hasMore ? (
                <button onClick={fetchItems}>
                    <Refresh />
                    加载更多
                </button>
            ) : (
                <my-no-data has-data={false} text="已经到底辣" />
            )}
        </div>
    )
}

ArchiveContent.getProps = async () => {
    const result = await fetchArchiveInfoTransformed(undefined)
    const resultArchivedArticles = await fetchArchivedArticlesTransformed({ current: 1, pageSize: PageCommentPageSize })

    return {
        ...result,
        histories: resultArchivedArticles.list,
    }
}
