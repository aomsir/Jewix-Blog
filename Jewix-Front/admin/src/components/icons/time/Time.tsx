import { ReactElement } from "react"

interface Props {
    [key: string]: any
}
export default function Time({ ...rest }: Props): ReactElement {
    return (
        <i {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="12.002" height="12.002" viewBox="0 0 12.002 12.002">
                <path
                    d="M96.483,94.418a.45.45,0,1,1-.772-.463,5.1,5.1,0,1,0-1.756,1.756.45.45,0,1,1,.463.772,6,6,0,1,1,2.065-2.065Zm-7.717-1.152,2.25-1.874-.132-2.758a.45.45,0,0,1,.9,0l.132,2.945a.45.45,0,0,1-.132.318L89.4,93.9a.45.45,0,1,1-.636-.636Z"
                    transform="translate(-85.333 -85.333)"
                    fill="#848484"
                />
            </svg>
        </i>
    )
}
