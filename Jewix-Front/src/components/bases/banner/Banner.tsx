import Image from "next/image"
import css from "./Banner.module.scss"

interface Props {
    image: string
    title: string
    desc: string
    className?: string
    [key: string]: any
}

/**
 * component
 * @returns 各个页面的banner
 */
export default function ({ image, title, desc, className, ...rest }: Props) {
    return (
        <div className={css.banner + " " + className} {...rest}>
            <div className="overlay">
                <h1>{title}</h1>
                <section className="desc">{desc}</section>
            </div>
            <Image src={image} alt="banner" width={1920} height={500} />
        </div>
    )
}
