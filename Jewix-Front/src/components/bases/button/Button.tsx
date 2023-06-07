import { ReactElement } from "react"

interface Props {
    [key: string]: any
}

export default function Button({ ...rest }: Props): ReactElement {
    return <button {...rest}></button>
}
