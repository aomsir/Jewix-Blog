import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface ArrowRightProps extends HTMLAttributes<HTMLDivElement> {}
export default function ArrowRight({ className, ...rest }: ArrowRightProps): ReactElement {
    return (
        <i className={`${className}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 8.565 12.45">
                <path
                    d="M6353.792,5786.2l5.619,4.864-5.619,4.766"
                    transform="translate(-6352.382 -5784.788)"
                    fill="none"
                    stroke="#a8a8a8"
                    strokeLinecap="round"
                    strokeWidth="2"
                />
            </svg>
        </i>
    )
}
