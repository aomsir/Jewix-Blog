import { ReactElement } from "react"
import { HTMLAttributes } from "react"
import css from "./Loading.module.scss"
interface LoadingProps extends HTMLAttributes<HTMLDivElement> {}
export default function Loading({ className, ...rest }: LoadingProps): ReactElement {
    return (
        <div className={`${className ?? ""} ${css.Loading}`} {...rest}>
            <section></section>
            <section></section>
        </div>
    )
}
