import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface FontProps extends HTMLAttributes<HTMLDivElement> {}
export default function Font({ className, ...rest }: FontProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 9.413 9.413">
                <path
                    d="M64.1,66v3.2l.416-.014a2.6,2.6,0,0,1,1.614-2.1,5.592,5.592,0,0,1,1.562.012s-.014,6.587-.014,7.051-.625.664-.625.664l-.832.014-.013.584h5.2v-.57h-.95c-.43-.04-.689-.6-.689-.6l.012-7.212a4.831,4.831,0,0,1,1.589.054c.938.173,1.731,2.15,1.731,2.15h.416V66Z"
                    transform="translate(-64.1 -66)"
                    fill="#a8a8a8"
                />
            </svg>
        </i>
    )
}
