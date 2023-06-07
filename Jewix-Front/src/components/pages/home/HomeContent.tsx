import CardImage from "@/components/bases/card-image/Card"
import BaseLoading from "@/components/bases/loading/BaseLoading"
import { BREAK_POINT_COLUMNS } from "@/configs/others"
import { ArticlePageSize, HomeProps } from "@/pages"
import { fetchArticles } from "@/servers/api/article"
import { createArticle } from "@/servers/models/article"
import { useRouter } from "next/router"
import { ReactElement, useEffect, useState } from "react"
import InfiniteScroll from "react-infinite-scroller"
import Masonry from "react-masonry-css"

export default function HomeContent({ initialBlogs }: { initialBlogs: HomeProps["initialBlogs"] }): ReactElement {
    const [blogs, setBlogs] = useState(initialBlogs)
    const [hasMore, setHasMore] = useState(true)
    const { query } = useRouter()
    let isLoaded = false
    // 当页面查询字符串改变时，重新设置blogs的状态
    useEffect(() => {
        setBlogs(initialBlogs)
        setHasMore(true)
    }, [query])

    const fetchItems = async () => {
        if (!isLoaded) {
            try {
                const { data } = await fetchArticles({
                    current: Math.ceil(blogs.length / ArticlePageSize) + 1,
                    pageSize: ArticlePageSize,
                    title: query.title as string,
                })
                setBlogs(prev => {
                    return [...prev, ...data.result!.list.map(item => createArticle.toHomeArticle(item))]
                })
                // 如果返回的数据小于pageSize，说明没有更多数据了
                if (data.result!.list.length < ArticlePageSize) {
                    setHasMore(false)
                }
            } catch (error) {
                setHasMore(false)
            }
        }
    }
    if (!blogs.length) {
        return <my-no-data />
    }

    return (
        <InfiniteScroll loadMore={fetchItems} hasMore={hasMore} loader={<BaseLoading key={0} />} initialLoad={true}>
            <Masonry className="grid" breakpointCols={BREAK_POINT_COLUMNS} columnClassName="col">
                {blogs.map(item => (
                    <CardImage {...item} key={item.id} />
                ))}
            </Masonry>
        </InfiniteScroll>
    )
}
