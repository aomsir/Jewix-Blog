import CardLink, { CardLinkProps } from "@/components/bases/card-link/CardLink"
import { BlogrollEnums } from "@/configs/enums"
import { ReactElement, useEffect, useRef, useState } from "react"
import { HTMLAttributes } from "react"
import css from "./BlogrollTabbar.module.scss"
export type BlogrollTabbarProps = HTMLAttributes<HTMLDivElement> & {
    blogroll: {
        location: BlogrollEnums.Location
        list: CardLinkProps[]
    }[]
    content: string
}

export default function BlogrollTabbar(props: BlogrollTabbarProps): ReactElement {
    const { blogroll, content, ...rest } = props
    const tabsRef = useRef<HTMLElement>(null)
    const [activeIndex, setActiveIndex] = useState(0)

    useEffect(() => {
        tabsRef.current?.addEventListener("change", (e: any) => {
            setActiveIndex(e.detail.index)
        })
    }, [])

    const blogrollTabs = blogroll.map((item, i) => ({
        title: BlogrollEnums.Location[item.location] + "链接",
        content: item.list.map((card, i) => <CardLink className="card" {...card} key={i} />),
    }))

    const tabs = [
        {
            title: "申请友链",
            content: <div dangerouslySetInnerHTML={{ __html: content }}></div>,
        },
        ...blogrollTabs,
    ]

    return (
        <div className={`${rest.className ?? ""} ${css.blogrollTabbar}`} {...rest}>
            <my-tabs className="my-tabs" ref={tabsRef}>
                {tabs.map((card, i) => (
                    <my-tab className="my-tab">{card.title}</my-tab>
                ))}
            </my-tabs>
            <div className="blogroll-info-board-content">{tabs[activeIndex].content}</div>
        </div>
    )
}
