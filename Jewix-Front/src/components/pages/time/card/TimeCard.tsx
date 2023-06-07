import Image from "next/image"
import { ReactElement } from "react"
import { HTMLAttributes } from "react"
import { TimeContentProps } from "../TimeContent"
import css from "./TimeCard.module.scss"
interface TimeCardProps extends HTMLAttributes<HTMLDivElement> {
    card: TimeContentProps["initialComments"]["0"]
}
export default function TimeCard({ className, card, ...rest }: TimeCardProps): ReactElement {
    return (
        <div className={`${className ?? ""} ${css["time-card"]}`} {...rest}>
            <Image src={card.avatar ?? "/avatar.png"} alt={card.user} width={64} height={64} />
            <div className="info-wrap">
                <div className="nickname">{card.user}</div>
                <div className="info">
                    <div className="date">
                        <span>{card.date[1]}月</span>
                        <span>{card.date[2]}日</span>
                    </div>
                    <div className="form">
                        发自{card.browser?.join(" ")}&nbsp;&nbsp;{card.os?.join(" ")}
                    </div>
                </div>
            </div>
            {/* @ts-ignore */}
            <div className="content">{!card?.secret ? card.content : <div className="secret">这是一条悄悄话噢～</div>}</div>
        </div>
    )
}
