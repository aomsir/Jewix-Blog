import CategoryContent from "@/components/pages/category/CategoryContent"
import { fetchArticlesByCategoryIdTransformed } from "@/servers/api"
import { findFromTree } from "@/utils/get"
import { GetServerSideProps } from "next"
import Head from "next/head"
import { ReactElement } from "react"
import { HomeProps } from ".."
import { _AppProps } from "../_app"
import { useRouter } from "next/router"

export interface CategoryProps {
    initialBlogs: HomeProps["initialBlogs"]
    categories: _AppProps["layoutProps"]["menu"][0]
}
/**
 * page
 * @returns 文章分类
 */
export default function Category({ initialBlogs, categories, ...rest }: CategoryProps): ReactElement {
    const router = useRouter()
    // @ts-ignore
    const categoryName = findFromTree(categories, node => node.id === parseInt(router.query.id), "children")?.label ?? "未知分类"

    return (
        <div {...rest}>
            <Head>
                <title>文章分类</title>
                <meta name="description" content="文章分类" />
            </Head>
            <CategoryContent initialBlogs={initialBlogs} categoryName={categoryName} />
        </div>
    )
}
export const categoryArticlePageSize = 10
export const getServerSideProps: GetServerSideProps = async context => {
    const id = context.params?.id

    if (id === undefined || id instanceof Array) {
        return {
            notFound: true,
        }
    }

    try {
        const result = await fetchArticlesByCategoryIdTransformed({ current: 1, pageSize: categoryArticlePageSize, categoryId: parseInt(id) })

        return {
            props: {
                initialBlogs: result.list,
            },
        }
    } catch (error) {
        console.log(error)

        return {
            notFound: true,
        }
    }
}
