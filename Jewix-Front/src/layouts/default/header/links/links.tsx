import Link from "next/link"
import { ReactElement } from "react"
import { HTMLAttributes } from "react"
export interface LinksProps extends HTMLAttributes<HTMLDivElement> {
    links: { label: string; url: string }[]
}
// 导航
export default function Links({ className, links, ...rest }: LinksProps): ReactElement {
    return (
        <div className={`${className ?? ""}`} {...rest}>
            {links.map(({ label, url }, i) => (
                <Link key={i} href={url} target={url === "/" ? "_self" : "_blank"}>
                    {label}
                </Link>
            ))}
        </div>
    )
}
