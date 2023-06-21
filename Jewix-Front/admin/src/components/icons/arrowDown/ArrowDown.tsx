import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface ArrowDownProps extends HTMLAttributes<HTMLDivElement> {}
export default function ArrowDown({ className, ...rest }: ArrowDownProps): ReactElement {
    return (
        <i className={`${className}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="8.726" height="4.5" viewBox="0 0 8.726 4.5">
                <path
                    id="路径_42"
                    data-name="路径 42"
                    d="M1409.7,2548.837l3.607,3.136,3.709-3.136"
                    transform="translate(-1408.997 -2548.132)"
                    fill="none"
                    stroke="#fff"
                    strokeLinecap="round"
                    strokeWidth="1"
                />
            </svg>
        </i>
    )
}
