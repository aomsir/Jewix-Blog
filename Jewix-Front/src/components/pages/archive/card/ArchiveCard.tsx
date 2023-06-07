import { ReactElement, ReactNode } from "react"
import { HTMLAttributes } from "react"
import css from "./ArchiveCard.module.scss"
interface ArchiveCardProps extends HTMLAttributes<HTMLDivElement> {
    label: string
    content: string
    icon: ReactNode
}
/**
 * 文章归档页面上方卡片
 */
export default function ArchiveCard({ className, label, content, icon, ...rest }: ArchiveCardProps): ReactElement {
    return (
        <div className={`${className ?? ""} ${css["archive-Card"]}`} {...rest}>
            <section className="flex flex-col justify-between">
                <div className="label">{label}</div>
                <div className="content">{content}</div>
            </section>
            <div className="icon-wrap">{icon}</div>
        </div>
    )
}
