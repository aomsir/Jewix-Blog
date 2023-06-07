import MenuToggle, { MenuToggleProps, MenuToggleRef } from "@/components/bases/menu-toggle/MenuToggle"
import Search from "@/components/bases/search/Search"
import Moon from "@/components/icons/moon/Moon"
import Sun from "@/components/icons/sun/Sun"
import { ColorThemeContext, THEME } from "@/components/providers/colorTheme/ColorTheme"
import Link from "next/link"
import router from "next/router"
import { forwardRef, ReactElement, Ref, useContext, useImperativeHandle, useRef } from "react"
import { HTMLAttributes } from "react"
import css from "./Header.module.scss"
export interface SmallHeaderProps extends HTMLAttributes<HTMLDivElement> {
    onToggle: MenuToggleProps["onToggle"]
}
export default forwardRef(({ className, onToggle, ...rest }: SmallHeaderProps, ref: Ref<MenuToggleRef>): ReactElement => {
    const { theme, setTheme } = useContext(ColorThemeContext)
    const isLight = theme === THEME.LIGHT

    const menuToggleRef = useRef<MenuToggleRef>(null)
    useImperativeHandle(ref, () => ({
        activityToggle: menuToggleRef.current?.activityToggle ?? ((force: boolean) => {}),
    }))

    return (
        <header className={`${className ?? ""} ${css["header"]}`} {...rest}>
            <MenuToggle ref={menuToggleRef} className="menu-icon" onToggle={onToggle} />
            <div className="logo">
                <Link href="/">
                    <img src={isLight ? "/logo-light.png" : "/logo-dark.png"} alt="logo" />
                </Link>
            </div>
            <div className="operations">
                <div className="search-warp">
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
                <div className="theme">{isLight ? <Moon onClick={() => setTheme(THEME.DARK)} /> : <Sun onClick={() => setTheme(THEME.LIGHT)} />}</div>
            </div>
        </header>
    )
})
