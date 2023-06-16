import HomeContent from "@/components/pages/home/HomeContent"
import { fetchArticles } from "@/servers/api/article"
import { createArticle } from "@/servers/models/article"
import { GetServerSideProps } from "next"
import Head from "next/head"
import webSiteConfig from "~/site.config"
import css from "./index.module.scss"

export interface HomeProps {
    initialBlogs: { id: string; image: string; title: string; desc: string; viewers: number; isTop: boolean }[]
}
/**
 * page
 * @returns 首页
 */
export default function Home({ initialBlogs }: HomeProps) {
    return (
        <div className={css["home-page"]}>
            <Head>
                <title>{webSiteConfig.title}</title>
                <meta name="description" content={webSiteConfig.description} />
            </Head>
            <main className={css.main}>
                <HomeContent initialBlogs={initialBlogs} />
            </main>
        </div>
    )
}
export const ArticlePageSize = 10
export const getServerSideProps: GetServerSideProps = async context => {
    const { query } = context
    try {
        // 初始化页面数据
        const initialBlogs = (
            await fetchArticles(
                { current: 1, pageSize: ArticlePageSize, title: query.title as string },
                {
                    headers: context.req.headers,
                }
            )
        ).data.result!.list.map(item => createArticle.toHomeArticle(item))
        
        return {
            props: {
                initialBlogs,
            },
        }
    } catch (error) {
        console.log(error)

        return {
            notFound: true,
        }
    }
}
