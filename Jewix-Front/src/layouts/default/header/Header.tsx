import Divider from "@/components/bases/divider/Divider"
import Search from "@/components/bases/search/Search"
import Moon from "@/components/icons/moon/Moon"
import Sun from "@/components/icons/sun/Sun"
import { ColorThemeContext, THEME } from "@/components/providers/colorTheme/ColorTheme"
import Link from "next/link"
import { useRouter } from "next/router"
import { HTMLAttributes, ReactElement, useContext, useEffect, useRef } from "react"
import webSiteConfig from "~/site.config"
import Links, { LinksProps } from "./links/links"
import Menu, { MenuProps } from "./menu/Menu"
type Header = {
    links: LinksProps["links"]
    menuData: MenuProps["menuData"]
} & HTMLAttributes<HTMLDivElement>

export default function Header({ links, menuData, ...rest }: Header): ReactElement {
    const { theme, setTheme } = useContext(ColorThemeContext)

    const isLight = theme === THEME.LIGHT

    const headerRef = useRef(null)
    const headerInnerRef = useRef(null)
    useEffect(() => {
        StaySameHeight(headerInnerRef.current!, headerRef.current!)
    })
    // 获取路由
    const router = useRouter()

    return (
        <header {...rest} ref={headerRef}>
            <div className="header-inner" ref={headerInnerRef}>
                <div className="logo">
                    <Link href="/">
                        <img src={isLight ? webSiteConfig.logo.light : webSiteConfig.logo.dark} alt="logo" />
                    </Link>
                </div>
                <nav className="nav">
                    {/* 链接 */}
                    <Links className="links" links={links} />
                    <Divider className="my-divider" />
                    {/*  菜单 */}
                    <Menu menuData={menuData} activeIndexes={[0, 1]} />
                    <Divider className="my-divider" />
                    {/* 操作（主题|搜索） */}
                    <div className="operations">
                        {isLight ? <Moon onClick={() => setTheme(THEME.DARK)} /> : <Sun onClick={() => setTheme(THEME.LIGHT)} />}
                        <Search
                            className="search"
                            onConfirm={value => {
                                console.log(value)

                                if (value) {
                                    router.push(`/?title=${value}`)
                                } else {
                                    router.push("/")
                                }
                            }}
                        />
                    </div>
                </nav>
            </div>
        </header>
    )
}
/**
 * 元素与目标元素保持同样高度
 * @param target 目标元素
 * @param source 跟随元素
 */
function StaySameHeight(target: HTMLElement, source: HTMLElement) {
    new ResizeObserver(entries => {
        source.style.height = entries[0].borderBoxSize[0].blockSize + "px"
    }).observe(target)
}
