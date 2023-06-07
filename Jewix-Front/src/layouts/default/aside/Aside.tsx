import CardAuthor from "@/components/bases/card-author/CardAuthor"
import CardCarousel, { CardCarouselProps } from "@/components/bases/card-carousel/CardCarousel"
import { HTMLAttributes, ReactElement } from "react"
export type AsideProps = HTMLAttributes<HTMLDivElement> & {
    siteInfo: {
        image: string
        name: string
        desc: string
        contents: [string, string][]
    }
    recommendedArticles: CardCarouselProps["items"]
}
export default function Aside(props: AsideProps): ReactElement {
    const { siteInfo, recommendedArticles, ...rest } = props

    return (
        <div {...rest}>
            <CardAuthor className="card-author" {...siteInfo} />
            <CardCarousel items={recommendedArticles} />
        </div>
    )
}
