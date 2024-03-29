import Banner from "@/components/bases/banner/Banner"
import { MenuToggleRef } from "@/components/bases/menu-toggle/MenuToggle"
import { SiteInfo } from "@/servers/api/common"
import { PropsWithChildren, ReactElement, useRef } from "react"
import { HTMLAttributes } from "react"
import { AsideProps } from "../aside/Aside"
import Footer from "../footer/Footer"
import { LinksProps } from "../header/links/links"
import { MenuProps } from "../header/menu/Menu"
import { MainLayoutProps } from "../Layout"
import Cover, { CoverRef } from "./Cover"
import Header, { SmallHeaderProps } from "./Header"
import css from "./Small.module.scss"
interface SmallProps extends HTMLAttributes<HTMLDivElement> {
    menuData: MenuProps["menuData"]
    links: LinksProps["links"]
    hiddenModules: MainLayoutProps["hiddenModules"]
    siteInfo: SiteInfo
}
export default function Small({ links, menuData, className, children, hiddenModules,siteInfo, ...rest }: PropsWithChildren<SmallProps>): ReactElement {
    const menuToggleRef = useRef<MenuToggleRef>(null)
    const coverRef = useRef<CoverRef>(null)
    // 控制cover显示与隐藏
    const toggle: SmallHeaderProps["onToggle"] = active => {
        // 隐藏滚动条
        document.documentElement.style.overflow = active ? "hidden" : ""
        coverRef.current?.expandedToggle(active)
    }
    return (
        <div className={`${className ?? ""} ${css["small"]}`} {...rest}>
            <Header onToggle={toggle} ref={menuToggleRef} />
            <Cover ref={coverRef} menuToggleRef={menuToggleRef} menuData={menuData} links={links} />
            {hiddenModules.includes("banner") || (
                <Banner className="banner" image="/banner.png" title={`Hi！I‘m ${siteInfo.name}`} desc={siteInfo.desc}/>
            )}
            <main>{children}</main>
            <Footer className="small-footer" siteInfo={siteInfo} />
        </div>
    )
}
