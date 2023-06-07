import { forwardRef, MouseEvent, ReactElement, Ref, useImperativeHandle, useRef } from "react"
import { HTMLAttributes } from "react"
import css from "./MenuToggle.module.scss"
export interface MenuToggleProps extends HTMLAttributes<HTMLDivElement> {
    onToggle?: (active: boolean) => void
}
export type MenuToggleRef = {
    activityToggle: (force: boolean) => void
}
/**
 * 移动端菜单按钮
 */
export default forwardRef(({ className, onToggle, ...rest }: MenuToggleProps, ref: Ref<MenuToggleRef>): ReactElement => {
    const menuToggleRef = useRef<HTMLDivElement>(null)

    function click(e: MouseEvent<HTMLElement>) {
        const classList = e.currentTarget.classList
        classList.toggle("active")
        onToggle?.(classList.contains("active"))
    }

    useImperativeHandle(ref, () => ({
        activityToggle(force) {
            menuToggleRef.current?.classList.toggle("active", force)
            onToggle?.(force)
        },
    }))

    return (
        <div ref={menuToggleRef} className={`${className ?? ""} ${css["menu-toggle"]}`} {...rest} onClick={click}>
            <div className="menu-toggle__inner">
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    )
})
