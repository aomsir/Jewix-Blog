import MyImage from "@/components/ui/img/Image"
import { HTMLAttributes, ReactElement } from "react"
import css from "./CardLink.module.scss"

export interface CardLinkProps extends HTMLAttributes<HTMLElement> {
    title: string
    desc: string
    avatar: string
    url: string
}
export default function CardLink({ className, title, desc, avatar, url, ...rest }: CardLinkProps): ReactElement {
    return (
        <a {...rest} href={url} className={`${css.card} ${className ?? ""}`}>
            <p className="title">{title}</p>
            <p className="desc">{desc}</p>
            <MyImage className="avatar" src={avatar} alt="头像" width={60} height={60} fallback="/notFound.png"></MyImage>
        </a>
    )
}
