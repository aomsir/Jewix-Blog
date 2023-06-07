import CardImage from "@/components/bases/card-image/Card"
import BaseLoading from "@/components/bases/loading/BaseLoading"
import { BREAK_POINT_COLUMNS } from "@/configs/others"
import { categoryArticlePageSize, CategoryProps } from "@/pages/category/[id]"
import { fetchArticlesByCategoryIdTransformed } from "@/servers/api"
import Link from "next/link"
import { useRouter } from "next/router"
import { ReactElement, useEffect, useState } from "react"
import { HTMLAttributes } from "react"
import InfiniteScroll from "react-infinite-scroller"
import Masonry from "react-masonry-css"
import css from "./CategoryContent.module.scss"
interface CategoryContentProps extends HTMLAttributes<HTMLDivElement> {
    initialBlogs: CategoryProps["initialBlogs"]
    categoryName: string
}
export default function CategoryContent({ className, categoryName, initialBlogs, ...rest }: CategoryContentProps): ReactElement {
    const [blogs, setBlogs] = useState(initialBlogs)
    const { query } = useRouter()

    // 当文章详情改变时候，更新文章列表
    useEffect(() => {
        setBlogs(initialBlogs)
    }, [query])

    const [hasMore, setHasMore] = useState(true)
    let isLoaded = false
    const fetchItems = async () => {
        try {
            if (!isLoaded) {
                const result = await fetchArticlesByCategoryIdTransformed({
                    current: Math.ceil(blogs.length / categoryArticlePageSize) + 1,
                    pageSize: categoryArticlePageSize,
                    categoryId: parseInt(query.id as string),
                })
                setBlogs(prev => [...prev, ...result.list])
                if (result.list.length < categoryArticlePageSize) {
                    setHasMore(false)
                }
            }
        } catch (error) {
            setHasMore(false)
        }
    }

    return (
        <div className={`${className ?? ""} ${css["category-content"]}`} {...rest}>
            <h1>分类</h1>
            <div className="breadcrumb">
                <Link href="/">首页</Link>
                <span>/</span>
                {categoryName}
            </div>
            <InfiniteScroll loadMore={fetchItems} hasMore={hasMore} initialLoad={false} loader={<BaseLoading key={0} />}>
                <Masonry className="grid" breakpointCols={BREAK_POINT_COLUMNS} columnClassName="col">
                    <my-no-data has-data={!!blogs.length}>
                        {blogs.map((item, index) => (
                            <CardImage {...item} key={index} />
                        ))}
                    </my-no-data>
                </Masonry>
            </InfiniteScroll>
        </div>
    )
}
