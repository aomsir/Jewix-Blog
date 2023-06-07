import { PropsWithChildren, ReactElement } from "react"
import { HTMLAttributes } from "react"
import css from "./Space.module.scss"
interface SpaceProps extends HTMLAttributes<HTMLDivElement> {}
export default function Space({ className, children, ...rest }: PropsWithChildren<SpaceProps>): ReactElement {
    return (
        <div className={`${className ?? ""} ${css.space}`} {...rest}>
            {children}
        </div>
    )
}
