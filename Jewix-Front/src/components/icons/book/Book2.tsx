import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface Book2Props extends HTMLAttributes<HTMLDivElement> {}
export default function Book2({ className, ...rest }: Book2Props): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 10.74 9.308">
                <g transform="translate(-32 -96)">
                    <path
                        d="M480.358,168.592a.358.358,0,0,1-.358-.358v-7.876a.358.358,0,1,1,.716,0v7.876A.358.358,0,0,1,480.358,168.592Z"
                        transform="translate(-442.988 -63.284)"
                        fill="#a8a8a8"
                    />
                    <path
                        d="M41.634,105.308H33.105A1.106,1.106,0,0,1,32,104.2v-7.1A1.106,1.106,0,0,1,33.105,96h2.769a1.855,1.855,0,0,1,1.5.76,1.855,1.855,0,0,1,1.5-.76h2.769a1.106,1.106,0,0,1,1.105,1.105v7.1A1.106,1.106,0,0,1,41.634,105.308Zm-8.529-8.592a.39.39,0,0,0-.389.389v7.1a.39.39,0,0,0,.389.389h8.528a.39.39,0,0,0,.389-.389v-7.1a.39.39,0,0,0-.389-.389H38.865a1.139,1.139,0,0,0-1.138,1.138.358.358,0,1,1-.716,0,1.139,1.139,0,0,0-1.138-1.138Z"
                        fill="#a8a8a8"
                    />
                </g>
            </svg>
        </i>
    )
}
