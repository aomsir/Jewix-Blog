import Divider from "@/components/bases/divider/Divider"
import { MenuToggleRef } from "@/components/bases/menu-toggle/MenuToggle"
import { forwardRef, ReactElement, Ref, RefObject, useImperativeHandle, useRef } from "react"
import { HTMLAttributes } from "react"
import Links, { LinksProps } from "../header/links/links"
import Menu, { MenuProps } from "../header/menu/Menu"
import css from "./Cover.module.scss"
type CoverProps = HTMLAttributes<HTMLDivElement> & {
    menuToggleRef: RefObject<MenuToggleRef>
    menuData: MenuProps["menuData"]
    links: LinksProps["links"]
}
export type CoverRef = {
    expandedToggle: (expanded: boolean) => void
}
export default forwardRef(({ menuData, menuToggleRef, links, className, ...rest }: CoverProps, ref: Ref<CoverRef>): ReactElement => {
    const coverRef = useRef<HTMLDivElement>(null)

    useImperativeHandle(ref, () => ({
        expandedToggle(expanded) {
            coverRef.current?.classList.toggle("expanded", expanded)
            // 点击外部关闭cover
            if (expanded) {
                setTimeout(() => {
                    document.onclick = (e: MouseEvent) => {
                        if (!coverRef.current?.contains(e.target as HTMLElement)) {
                            menuToggleRef.current?.activityToggle?.(false)
                        }
                    }
                }, 0)
            } else {
                document.onclick = null
            }
        },
    }))

    return (
        <div ref={coverRef} className={`${className ?? ""} ${css.cover}`} {...rest}>
            <div className="title">个人</div>
            <Links className="links" links={links} />
            <div className="title">组成</div>
            <Menu className="menu" menuData={menuData} />
        </div>
    )
})
