import { ReactElement, useEffect, useRef } from "react"
import { HTMLAttributes } from "react"
import css from "./Image.module.scss"
type ImageProps = HTMLAttributes<HTMLDivElement> & {
    width?: number
    height?: number
    src: string
    alt?: string
    fallback?: string
}
export default function MyImage(props: ImageProps): ReactElement {
    const { className, src, alt, width, height, fallback, ...rest } = props

    return (
        <div className={`${className ?? ""} ${css.image}`} {...rest} style={{ width, height }}>
            <img src={src ?? fallback} alt={alt} onError={e => ((e.target as HTMLMediaElement).src = fallback ?? "")}></img>
        </div>
    )
}
