import ArrowDown from "@/components/icons/arrowDown/ArrowDown"
import Link from "next/link"
import { HTMLAttributes, ReactElement } from "react"
interface MenuLinkProps extends HTMLAttributes<HTMLDivElement> {
    href?: string
    label: string
    total?: number
    children?: any[]
    target?: string
}
/**
 * 菜单项
 */
export default function MenuItemLabel({ href, label, total, children, target, ...rest }: MenuLinkProps): ReactElement {
    // 有href属性就返回a表情包裹的label，并且显示文章数量
    const MyLink = (className?: string, total?: number) =>
        href ? (
            <Link className={className ?? ""} href={href} target={target ?? "_self"}>
                <span>{label}</span>
                {total && <span className="total">{total}</span>}
            </Link>
        ) : (
            <span>{label}</span>
        )
    // 没有孩子就拆掉被包裹的div
    if (children?.length) {
        return (
            <div className="label" {...rest}>
                {MyLink()}
                {<ArrowDown />}
            </div>
        )
    } else {
        return MyLink("label", total)
    }
}
