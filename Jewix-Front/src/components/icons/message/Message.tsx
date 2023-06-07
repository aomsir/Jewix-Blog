import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface MessageProps extends HTMLAttributes<HTMLDivElement> {}
export default function Message({ className, ...rest }: MessageProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 11.546 11.546">
                <g transform="translate(-64.67 -64.67)">
                    <path
                        d="M365.687,490.117m-.527,0a.527.527,0,1,0,.527-.527A.527.527,0,0,0,365.16,490.117Z"
                        transform="translate(-296.612 -419.436)"
                        fill="#848484"
                    />
                    <path
                        d="M577.757,490.117m-.527,0a.527.527,0,1,0,.527-.527A.527.527,0,0,0,577.23,490.117Z"
                        transform="translate(-505.945 -419.436)"
                        fill="#848484"
                    />
                    <path
                        d="M70.443,76.216a5.785,5.785,0,0,1-2.618-.627H65.039V72.472a5.773,5.773,0,1,1,5.4,3.744Zm-4.454-1.578h2.072l.105.057a4.825,4.825,0,1,0-2.211-2.485l.033.084v2.345Z"
                        fill="#848484"
                    />
                </g>
            </svg>
        </i>
    )
}
