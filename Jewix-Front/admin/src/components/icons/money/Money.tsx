import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface MoneyProps extends HTMLAttributes<HTMLDivElement> {}
export default function Money({ className, ...rest }: MoneyProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20">
                <path
                    d="M9.5,9.5v2H6.667C6.3,11.5,6,11.724,6,12s.3.5.667.5H9.5v2.3c0,.387.224.7.5.7s.5-.313.5-.7V12.5h2.833c.369,0,.667-.224.667-.5s-.3-.5-.667-.5H10.5v-2h2.833C13.7,9.5,14,9.276,14,9s-.3-.5-.667-.5H10.707l2.647-2.646a.5.5,0,0,0-.708-.708L10,7.793,7.354,5.146a.5.5,0,0,0-.708.708L9.293,8.5H6.667C6.3,8.5,6,8.724,6,9s.3.5.667.5ZM10,20A10,10,0,1,1,20,10,10,10,0,0,1,10,20Z"
                    fill="#fff"
                />
            </svg>
        </i>
    )
}
