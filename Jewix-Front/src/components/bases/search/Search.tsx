import { FormEvent, MouseEvent as ReactMouseEvent, ReactElement, useRef } from "react"
import { HTMLAttributes } from "react"
import css from "./Search.module.scss"
import IconSearch from "@/components/icons/search/Search"

interface SearchProps extends HTMLAttributes<HTMLFormElement> {
    onConfirm?: (value: string) => void
}
export default function Search({ className, onConfirm, ...rest }: SearchProps): ReactElement {
    const formEl = useRef<HTMLFormElement>(null)
    // 控制input显示与隐藏
    const showSearchForm = (e: ReactMouseEvent<HTMLElement>) => {
        const iconEl = e.currentTarget as HTMLElement
        iconEl.classList.add("hidden")
        ;(iconEl.nextElementSibling?.firstElementChild as HTMLInputElement)?.focus()
        // 如果是显示的，就隐藏起来
        setTimeout(() => {
            document.onmousedown = (e: MouseEvent) => {
                if (!formEl.current?.contains(e.target as Node)) {
                    iconEl.classList.remove("hidden")
                    document.onmousedown = null
                }
            }
        }, 0)
    }
    const submit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        onConfirm?.((e.target as HTMLFormElement).search.value)
    }

    return (
        <form ref={formEl} className={`${className ?? ""} ${css.search}`} {...rest} onSubmit={submit}>
            <IconSearch onClick={showSearchForm} />
            <div className="input">
                <input type="text" name="search" />
                <button type="submit">
                    <IconSearch />
                </button>
            </div>
        </form>
    )
}
