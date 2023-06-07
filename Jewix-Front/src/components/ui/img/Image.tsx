import { ReactElement, useEffect, useRef, useState } from "react"
import { HTMLAttributes } from "react"
import css from "./Image.module.scss"
type ImageProps = HTMLAttributes<HTMLDivElement> & {
    width?: number
    height?: number
    src: string
    alt?: string
    fallback?: string
    loader?: ReactElement
}
export default function MyImage(props: ImageProps): ReactElement {
    const { className, src, alt, width, height, fallback, loader, ...rest } = props
    const [loaded, setLoaded] = useState(false)
    const imgRef = useRef<HTMLImageElement>(null)

    useEffect(() => {
        if (imgRef.current) {
            imgRef.current.src = src
            setLoaded(false)
        }
    }, [src])

    return (
        <div className={`${className ?? ""} ${css.image}`} {...rest} style={{ width, height }}>
            <img
                ref={imgRef}
                alt={alt}
                onError={e => {
                    ;(e.target as HTMLImageElement).src = fallback ?? ""
                    setLoaded(true)
                }}
                onLoad={() => {
                    setLoaded(true)
                }}
            ></img>
            {loaded || <span className="loading">{loader}</span>}
        </div>
    )
}
