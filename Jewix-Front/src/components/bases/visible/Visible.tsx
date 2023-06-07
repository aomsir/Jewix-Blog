import { MouseEvent, ReactElement, ReactNode, useEffect, useRef } from "react"
import { HTMLAttributes } from "react"
import css from "./Visible.module.scss"
interface VisibleProps extends HTMLAttributes<HTMLDivElement> {
    // 显示内容
    content: ReactNode
}
/**
 * 设置可见与不可见的容器，根据高度变化
 */
export default function Visible({ className, children, content, ...rest }: VisibleProps): ReactElement {
    const visibleRef = useRef(null)
    const toggle = (e: MouseEvent<HTMLElement>) => {
        const btn = e.target as HTMLElement
        let toggleEl = btn.parentElement as HTMLElement | null
        while (toggleEl && !toggleEl.hasAttribute("data-stop")) {
            toggleEl = toggleEl.parentElement
        }
        toggleEl = toggleEl!.nextElementSibling as HTMLElement | null
        if (toggleEl) {
            if (isHidden(toggleEl)) {
                setVisible(toggleEl)
            } else {
                setHidden(toggleEl)
            }
        }
    }
    // 首次隐藏
    useEffect(() => {
        if (visibleRef.current) {
            setHidden(visibleRef.current)
        }
    }, [])

    return (
        <div className={`${className ?? ""} ${css.visible}`} {...rest}>
            <span data-stop onClick={toggle}>
                {children}
            </span>
            <div ref={visibleRef} className="visible-container hidden" style={{ display: "block" }}>
                {content}
            </div>
        </div>
    )
}

const isHidden = (el: HTMLElement) => el.classList.contains("hidden")
const setVisible = (el: HTMLElement) => {
    el.classList.remove("hidden")
    el.style.height = el.firstElementChild?.getBoundingClientRect().height + "px"
    setTimeout(() => {
        el.style.overflow = ""
    }, 200)
}
const setHidden = (el: HTMLElement) => {
    el.classList.add("hidden")
    el.style.height = "0px"
    el.style.overflow = "hidden"
}
