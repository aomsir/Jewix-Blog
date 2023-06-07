import { HTMLAttributes, ReactElement } from "react"
import css from "./Divider.module.scss"

interface Props extends HTMLAttributes<HTMLDivElement> {}
export default function Divider({ className, ...rest }: Props): ReactElement {
    return <div className={`${className} ${css.divider}`} {...rest}></div>
}
