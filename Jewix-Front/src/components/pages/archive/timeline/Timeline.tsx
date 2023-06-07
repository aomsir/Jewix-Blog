import Link from "next/link"
import { Fragment, ReactElement } from "react"
import { HTMLAttributes } from "react"
import { ArchiveContentProps } from "../ArchiveContent"
import css from "./Timeline.module.scss"
interface TimeLineProps extends HTMLAttributes<HTMLDivElement> {
    histories: ArchiveContentProps["histories"]
}
/**
 * 文章归档时间线
 */
export default function Timeline({ className, histories, ...rest }: TimeLineProps): ReactElement {
    let yearPre = ""
    let monthPre = ""

    return (
        <div className={`${className ?? ""} ${css.timeline}`} {...rest}>
            {histories.map(({ title, url, date: { year, month, day } }, i) => {
                return (
                    <Fragment key={i}>
                        {/* 年节点 */}
                        {yearPre !== year && (yearPre = year) && <div className="year">{year}年</div>}
                        {/* 月节点 */}
                        {monthPre !== month && (monthPre = month) && <div className="month">{month}月</div>}
                        {/* 普通节点 */}
                        <div className="node">
                            <p className="title">
                                <Link href={url ?? "#"}>{title}</Link>
                            </p>
                            <div className="date">{`${month}-${day}`}</div>
                        </div>
                    </Fragment>
                )
            })}
        </div>
    )
}
