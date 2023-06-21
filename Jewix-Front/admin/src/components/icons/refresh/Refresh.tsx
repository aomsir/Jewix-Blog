import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface RefreshProps extends HTMLAttributes<HTMLDivElement> {}
export default function Refresh({ className, ...rest }: RefreshProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="15.191" height="13.56" viewBox="0 0 15.191 13.56">
                <path
                    id="刷新"
                    d="M30.4,85.356a5.272,5.272,0,0,0,5.78,6.594.753.753,0,1,1,.2,1.493,6.779,6.779,0,0,1-7.432-8.476l-.9-.241a.2.2,0,0,1-.1-.328l2.5-2.816a.2.2,0,0,1,.347.092l.754,3.689a.2.2,0,0,1-.248.234Zm10.184,2.731a5.272,5.272,0,0,0-5.78-6.594.753.753,0,1,1-.2-1.493,6.779,6.779,0,0,1,7.432,8.476l.9.241a.2.2,0,0,1,.1.328l-2.5,2.816a.2.2,0,0,1-.347-.092l-.754-3.689a.2.2,0,0,1,.248-.234Z"
                    transform="translate(-27.895 -79.941)"
                    fill="#fff"
                />
            </svg>
        </i>
    )
}
