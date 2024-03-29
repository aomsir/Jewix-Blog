import { PropsWithChildren, ReactElement } from "react"
import Header from "./header/Header"
import Footer from "./footer/Footer"
import Aside, { AsideProps } from "./aside/Aside"
import css from "./Layout.module.scss"
import Banner from "@/components/bases/banner/Banner"
import Small from "./small/Small"
import { LinksProps } from "./header/links/links"
import { MenuProps } from "./header/menu/Menu"
import { API } from "@/servers/api/typings"
import { SiteInfo } from "@/servers/api/common"
export type MainLayoutProps = {
    isPhone: boolean
    links: LinksProps["links"]
    menu: MenuProps["menuData"]
    siteInfo: SiteInfo
    recommendedArticles: AsideProps["recommendedArticles"]
    hiddenModules: ["banner"] | string[]
}
/**
 * component
 * @returns 主要布局
 */
export default function DefaultLayout({
    children,
    links,
    menu,
    isPhone,
    siteInfo,
    hiddenModules,
    recommendedArticles,
}: PropsWithChildren<MainLayoutProps>): ReactElement {
    //手机视图布局
    if (isPhone) {
        return (
            <Small links={links} menuData={menu} hiddenModules={hiddenModules} siteInfo={siteInfo}>
                {children}
            </Small>
        )
    }
    //pad、pc、视图布局
    return (
        <div className={css.layout}>
            <Header className={css.header} menuData={menu} links={links} />
            {hiddenModules.includes("banner") || (
                <Banner className="banner" image="/banner.png" title={`Hi！I‘m ${siteInfo.name}`} desc={siteInfo.desc}/>
            )}
            <main className={css.main}>
                <div className={css.content}>{children}</div>
                <Aside className={css.aside} siteInfo={siteInfo} recommendedArticles={recommendedArticles} />
            </main>
            <Footer className={css.footer} siteInfo={siteInfo}/>
        </div>
    )
}
