import { ReactElement } from "react"

interface Props {
    [key: string]: any
}
export default function Moon({ ...rest }: Props): ReactElement {
    return (
        <i {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="16.21" height="18.931" viewBox="0 0 16.21 18.931">
                <path
                    id="黑夜模式"
                    d="M197.591,141.329a7.707,7.707,0,0,1,2.567-5.744.8.8,0,0,0-.292-1.388,9.618,9.618,0,0,0-4.552-.01,9.468,9.468,0,1,0,8.779,16.062.8.8,0,0,0-.417-1.344A7.764,7.764,0,0,1,197.591,141.329Z"
                    transform="translate(-188.12 -133.92)"
                />
            </svg>
        </i>
    )
}
