import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface PlayProps extends HTMLAttributes<HTMLDivElement> {}
export default function Play({ className, ...rest }: PlayProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 10.774 10.774">
                <g transform="translate(0.25 0.25)">
                    <path
                        d="M5.137,0a5.137,5.137,0,1,0,5.137,5.137A5.152,5.152,0,0,0,5.137,0Zm0,9.846A4.709,4.709,0,1,1,9.846,5.137,4.723,4.723,0,0,1,5.137,9.846Z"
                        fill="#a8a8a8"
                        stroke="#a8a8a8"
                        strokeWidth="0.5"
                    />
                    <path
                        d="M397.449,312.01l-1.712-1.134c-.578-.385-1.07-.128-1.07.578v2.569c0,.706.471.963,1.07.578l1.712-1.134A.828.828,0,0,0,397.449,312.01Z"
                        transform="translate(-390.707 -307.579)"
                        fill="#a8a8a8"
                    />
                </g>
            </svg>
        </i>
    )
}
