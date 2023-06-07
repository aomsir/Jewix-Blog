import { useRouter } from "next/router"
import { ReactElement, useEffect, useRef } from "react"
import { HTMLAttributes } from "react"
import { highlightAllUnder } from "prismjs"
import css from "./DetailArticleContent.module.scss"
import { md } from "@/utils/markdownIt"
interface DetailArticleContentProps extends HTMLAttributes<HTMLDivElement> {
    content: string
    onMounted: (contentEl: HTMLElement) => void
}
export default function DetailArticleContent({ className, content, onMounted, ...rest }: DetailArticleContentProps): ReactElement {
    const contentRef = useRef<HTMLDivElement>(null)
    const router = useRouter()
    useEffect(() => {
        // 高亮
        highlightAllUnder(contentRef.current!)
        // 获取pre标签
        const preTags = contentRef.current!.querySelectorAll("pre")
        // 添加data-language属性到pre标签
        preTags.forEach(pre => {
            pre.setAttribute("data-language", pre.querySelector("code")?.className.replace("language-", "") ?? "")
        })

        onMounted(contentRef.current!)
    }, [router.query.id])

    return (
        <div
            className={`${className ?? ""} markdown-body  ${css["detail-article__content"]}`}
            dangerouslySetInnerHTML={{ __html: md.render(content) }}
            ref={contentRef}
            {...rest}
        ></div>
    )
}
