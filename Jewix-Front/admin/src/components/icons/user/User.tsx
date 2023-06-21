import { ReactElement } from "react"

interface Props {
    [key: string]: any
}
export default function User({ ...rest }: Props): ReactElement {
    return (
        <i {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 10 10">
                <path
                    d="M102.572,101.238a2.857,2.857,0,1,0-3.144,0A4.7,4.7,0,0,0,96,105.643a.357.357,0,0,0,.714,0,4.3,4.3,0,0,1,8.572,0,.357.357,0,1,0,.714,0,4.7,4.7,0,0,0-3.428-4.405Zm-3.715-2.381A2.143,2.143,0,1,1,101,101,2.146,2.146,0,0,1,98.857,98.857Z"
                    transform="translate(-96 -96)"
                    fill="#848484"
                />
            </svg>
        </i>
    )
}
