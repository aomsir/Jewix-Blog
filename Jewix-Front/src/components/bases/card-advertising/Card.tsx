import { ReactElement } from "react"
interface Props {
    [key: string]: any
}
export default function CardAdvertising({ ...rest }: Props): ReactElement {
    return <div {...rest}>card1</div>
}
