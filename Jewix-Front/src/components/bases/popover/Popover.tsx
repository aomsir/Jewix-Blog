import { ReactElement } from "react"
import { HTMLAttributes } from "react"
import css from "./Popover.module.scss"
interface PopoverProps extends HTMLAttributes<HTMLDivElement> {}
export default function Popover({ className, children, ...rest }: PopoverProps): ReactElement {
    return (
        <div className={`${className ?? ""} ${css.popover}`} {...rest}>
            {children}
        </div>
    )
}
