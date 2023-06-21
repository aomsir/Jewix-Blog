import { ReactElement } from "react"

interface Props {
    color?: string
    [key: string]: any
}
export default function Search({ color = "#222", ...rest }: Props): ReactElement {
    return (
        <i {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="16.111" height="17.584" viewBox="0 0 16.111 17.584">
                <path
                    id="搜索_1_"
                    data-name="搜索 (1)"
                    d="M72.409,79.965a7.556,7.556,0,1,1,7.556-7.556A7.578,7.578,0,0,1,72.409,79.965Zm0-1.259a6.3,6.3,0,1,0-6.3-6.3A6.315,6.315,0,0,0,72.409,78.705Zm4.659,1.007a.608.608,0,0,1,.063-.881.669.669,0,0,1,.881.063l1.2,1.448a.608.608,0,0,1-.063.881.669.669,0,0,1-.881-.063Z"
                    transform="translate(-64.353 -64.353)"
                    fill={color}
                    stroke={color}
                    strokeWidth="1"
                />
            </svg>
        </i>
    )
}
