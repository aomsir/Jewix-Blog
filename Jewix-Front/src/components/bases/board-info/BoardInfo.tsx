import { ReactElement, ReactNode } from "react"
import Image from "next/image"
import { HTMLAttributes } from "react"
import css from "./BoardInfo.module.scss"
import Divider from "../divider/Divider"
import { PageEnums } from "@/configs/enums"
export interface BoardInfoProps extends HTMLAttributes<HTMLDivElement> {
    title: string
    info?: {
        avatar?: string
        nickname?: string
        date?: string
        readers?: number
        status?: number
        totalCommentCount?: number
    }
    children: ReactNode
}
export default function BoardInfo({ className, title, info, children, ...rest }: BoardInfoProps): ReactElement {
    const { avatar, nickname, date, readers, status, totalCommentCount } = info ?? {}

    return (
        <div className={`${className ?? ""} ${css["board-info"]}`} {...rest}>
            <h1>{title}</h1>
            {!!info && (
                <div className="info">
                    <div className="left">
                        {/* 头像 */}
                        <div className="img">
                            <Image src={avatar!} alt={"avatar"} width={60} height={60} />
                        </div>
                        {/* 用户信息、评论信息 */}
                        <section className="flex flex-col  justify-between">
                            <div className="name">{nickname}</div>
                            <div className="desc">
                                <span>{date}</span>
                                <Divider className="my-divider" />
                                <span>{totalCommentCount}评论</span>
                                <Divider className="my-divider" />
                                <span>{readers}阅读</span>
                                <Divider className="my-divider" />
                                <span>{PageEnums.Status[status!]}</span>
                            </div>
                        </section>
                    </div>
                    <div className="right"></div>
                </div>
            )}
            {children}
        </div>
    )
}
