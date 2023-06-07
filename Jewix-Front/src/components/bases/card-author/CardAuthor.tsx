import Image from "next/image"
import { ReactElement } from "react"
import css from "./CardAuthor.module.scss"

interface Props {
    image: string
    name: string
    desc: string
    contents: string[][]
    className: string
    [key: string]: any
}
export default function CardAuthor({ image, name, desc, contents, className, ...rest }: Props): ReactElement {
    return (
        <div className={`${css["card-author"]} ${className}`}>
            <section className="header">
                <div className="img">
                    <Image src={image} alt="头像" width={322} height={234} />
                </div>
                <p className="name">{name}</p>
                <p className="desc">{desc}</p>
            </section>
            <section className="content">
                {contents.map(([key, val]) => (
                    <p key={key}>
                        <span className="key">{key}</span>
                        <span className="val">{val}</span>
                    </p>
                ))}
            </section>
        </div>
    )
}
