import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface EditProps extends HTMLAttributes<HTMLDivElement> {}
export default function Edit({ className, ...rest }: EditProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 44.175 38.961">
                <g transform="translate(-50.5 -114.2)">
                    <path
                        id="路径_72"
                        data-name="路径 72"
                        d="M51.8,151.22a1.941,1.941,0,0,0,1.941,1.941H88.7a1.941,1.941,0,0,0,0-3.882H53.741A1.941,1.941,0,0,0,51.8,151.22Zm0-35.079a1.941,1.941,0,0,0,1.941,1.941H85.373a1.941,1.941,0,0,0,0-3.882H53.741A1.941,1.941,0,0,0,51.8,116.141ZM67.761,143.7a2.369,2.369,0,0,0,3.347,0l24.114-24.114a2.367,2.367,0,1,0-3.347-3.347L67.761,140.345A2.366,2.366,0,0,0,67.761,143.7Z"
                        transform="translate(-1.238)"
                        fill="#c1c1c1"
                    />
                    <path
                        id="路径_73"
                        data-name="路径 73"
                        d="M87.52,154.334a1.941,1.941,0,0,0,1.941-1.941v-17.22a1.941,1.941,0,1,0-3.882,0V152.4A1.937,1.937,0,0,0,87.52,154.334Zm-35.079,0a1.941,1.941,0,0,0,1.941-1.941V117.441a1.941,1.941,0,1,0-3.882,0V152.4A1.937,1.937,0,0,0,52.441,154.334Z"
                        transform="translate(0 -1.238)"
                        fill="#c1c1c1"
                    />
                </g>
            </svg>
        </i>
    )
}
