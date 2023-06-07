import Eye from "@/components/icons/eye/Eye"
import MyImage from "@/components/ui/img/Image"
import Image from "next/image"
import Link from "next/link"
import { ReactElement } from "react"
import css from "./Card.module.scss"

interface Props {
    id: string
    image: string
    title: string
    desc: string
    viewers: number
    [key: string]: any
    isTop: boolean
}

export default function CardImage({ image, title, desc, viewers, id, isTop, ...rest }: Props): ReactElement {
    return (
        <div className={"card" + " " + css.card} {...rest}>
            {isTop && <span className="overhead">顶置</span>}
            <section className="image">
                <Link href={`/detail/${id}`}>
                    {/* <MyImage className="imageWrapper" src={image} notFound="/notFound.png" /> */}
                    <Image
                        src={image ?? "/notFound.png"}
                        width={300}
                        loader={({ src }) => src ?? "/notFound.png"}
                        height={150}
                        onError={e => ((e.target as HTMLImageElement).srcset = "/notFound.png")}
                        alt={""}
                    />
                </Link>
            </section>
            <section className="content">
                <Link href={`/detail/${id}`}>
                    <p className="title">{title}</p>
                </Link>
                <div className="desc">{desc}</div>
            </section>
            <section className="footer">
                <div className="viewers">
                    <Eye />
                    {viewers}
                </div>
            </section>
        </div>
    )
}

export const isNormalizedNextImageUrl = (url: string) => {
    return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("/")
}
