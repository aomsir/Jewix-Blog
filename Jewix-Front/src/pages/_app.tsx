import "swiper/css"
import "swiper/css/pagination"
// import "tailwindcss/tailwind.css"
import "@/styles/index.scss"
import "github-markdown-css"
import "prismjs/themes/prism-okaidia.min.css"
import App, { AppContext } from "next/app"
import DefaultLayout, { MainLayoutProps } from "@/layouts/default/Layout"
import type { AppProps } from "next/app"
import ColorThemeProvider, { THEME } from "@/components/providers/colorTheme/ColorTheme"
import Head from "next/head"
import { useEffect, useRef, useState } from "react"
import Cookies from "js-cookie"
import { LinksProps } from "@/layouts/default/header/links/links"
import { MenuProps } from "@/layouts/default/header/menu/Menu"
import { fetchAllGlobalBlogroll } from "@/servers/api/blogroll"
import { convertKey, convertKeyOfDeepArray } from "@/utils/convert"
import { fetchAllCategories } from "@/servers/api/category"
import { addKeyOfTree } from "@/utils/transform"
import { fetchSiteInfoTransformed } from "@/servers/api/common"
import { AsideProps } from "@/layouts/default/aside/Aside"
import { fetchRecommendedArticles } from "@/servers/api"
import siteConfig from "~/site.config"
import { fetchAllPagesTransformed } from "@/servers/api/page"
import { useRouter } from "next/router"
import { LocalToken } from "@/utils/token"
import { fetchCurrentUser } from "@/servers/api/user"
import webSiteConfig from "~/site.config"
export type _AppProps = AppProps & {
    theme: THEME
    isMobile: boolean
    layoutProps: {
        links: LinksProps["links"]
        menu: MenuProps["menuData"]
        siteInfo: AsideProps["siteInfo"]
        recommendedArticles: AsideProps["recommendedArticles"]
        hiddenModules: MainLayoutProps["hiddenModules"]
    }
}

export default function MyApp(props: _AppProps) {
    const { Component, pageProps, theme, isMobile, layoutProps } = props
    /* 提供响应式 */
    const [isPhone, setIsPhone] = useState(isMobile)
    useEffect(() => {
        window.onresize = () => {
            if (window.innerWidth <= 500) {
                setIsPhone(true)
            } else {
                setIsPhone(false)
            }
        }
        // @ts-ignore
        window.onresize()

        // 注册web组件
        import("@/components/web").then(({ defineWebComponents }) => {
            defineWebComponents()
        })

        // 是否登录过期来清除token
        const token = LocalToken.get()
        if (token) {
            fetchCurrentUser(undefined, undefined, { headers: { token } })
                .then(({ data }) => {
                    if (data.code === 500) {
                        LocalToken.remove()
                    }
                })
                .catch(() => {
                    LocalToken.remove()
                })
        }
    }, [])

    const setLocalTheme = (theme: THEME) => {
        Cookies.set("colorTheme", theme)
    }

    // 404 500页面不使用布局
    if (Component.name === "Error") {
        return <Component {...pageProps} />
    }
    // 隐藏模块
    const hiddenModules = [] as string[]
    const router = useRouter()
    // 如果是文章详情页或者是url页，隐藏banner
    router.pathname.includes("url") && hiddenModules.push("banner")
    router.pathname.includes("detail") && hiddenModules.push("banner")

    return (
        <>
            <Head>
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <meta name="keywords" content={webSiteConfig.keywords.join(",")} />
            </Head>
            <ColorThemeProvider initialTheme={theme} onChange={setLocalTheme}>
                <DefaultLayout isPhone={isPhone} {...layoutProps} hiddenModules={hiddenModules}>
                    <Component {...pageProps} categories={layoutProps.menu[0]} globalBlogroll={layoutProps.menu[2].children} />
                </DefaultLayout>
            </ColorThemeProvider>
        </>
    )
}
/**
 * 获取app的初始props
 */
MyApp.getInitialProps = async (appContext: AppContext) => {
    const { req } = appContext.ctx
    // 从cookie获取主题颜色
    const cookies = req?.headers.cookie
    const theme = cookies
        ?.split(";")
        .find(item => item.includes("colorTheme"))
        ?.split("=")[1]
    // 隐藏模块
    const hiddenModules = [] as string[]

    try {
        // 获取app的初始props
        const appProps = await App.getInitialProps!(appContext)
        // 判断是否是移动设备
        const isMobile = _isMobile(req?.headers["user-agent"] ?? "")
        //  获取友链
        const blogroll = (await fetchAllGlobalBlogroll()).map(item => ({
            ...convertKey(item, {
                link: "url",
                photo: "avatar",
                description: "desc",
            } as const),
            target: "_blank",
            label: item.title,
        }))
        console.log(3);
        // 获取分类
        const categories = (await fetchAllCategories()).data.result!
        console.log(4);
        const categoriesConverted = convertKeyOfDeepArray(categories, { categoryName: "label", sonList: "children" } as const, "children")
        console.log(5);
        const categoriesAdded = categoriesConverted.map(item => addKeyOfTree(item, item => ({ url: "/category/" + item.id }), "children"))
        console.log(6);
        // 获取站点信息
        const siteInfo = await fetchSiteInfoTransformed(undefined)
        console.log(7);
        // 获取推荐文章
        const recommendedArticles = (await fetchRecommendedArticles()).data.result!
        console.log(8);
        // 获取页面
        const pages = await fetchAllPagesTransformed(undefined)
        console.log(9);

        return {
            ...appProps,
            theme: theme ?? THEME.LIGHT,
            isMobile,
            layoutProps: {
                links: siteConfig.links,
                menu: [
                    {
                        id: "999",
                        label: "分类",
                        children: categoriesAdded,
                    },
                    {
                        id: "1000",
                        label: "页面",
                        children: pages,
                    },
                    { id: "1001", label: "友链", children: blogroll },
                ],
                siteInfo,
                recommendedArticles,
                hiddenModules,
            },
        }
    } catch (error) {
        console.log(error)

        // return {

        // }
    }
}
/*
 *  判断是否是移动设备
 */
function _isMobile(userAgent: string) {
    return /Mobile|Mobi/i.test(userAgent)
}
