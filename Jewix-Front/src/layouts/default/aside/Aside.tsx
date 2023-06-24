import CardAuthor from "@/components/bases/card-author/CardAuthor"
import CardCarousel, { CardCarouselProps } from "@/components/bases/card-carousel/CardCarousel"
import { SiteInfo } from "@/servers/api/common"
import { HTMLAttributes, ReactElement } from "react"
export type AsideProps = HTMLAttributes<HTMLDivElement> & {
    siteInfo:SiteInfo      
    recommendedArticles: CardCarouselProps["items"]
}
export default function Aside(props: AsideProps): ReactElement {
    const { siteInfo, recommendedArticles, ...rest } = props

    return (
        <div {...rest}>
            {/* @ts-ignore */}
            <CardAuthor className="card-author" {...siteInfo} />
            <CardCarousel items={recommendedArticles} />
        </div>
    )
}
