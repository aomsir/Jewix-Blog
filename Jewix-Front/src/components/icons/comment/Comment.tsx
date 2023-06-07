import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface EditProps extends HTMLAttributes<HTMLDivElement> {}
export default function Comment({ className, ...rest }: EditProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="42.504" height="38.961" viewBox="0 0 42.504 38.961">
                <path
                    id="评论小"
                    d="M19.989,81.11l-6.554-6.57H3.545A3.54,3.54,0,0,1,0,70.994L.018,46.21a3.534,3.534,0,0,1,3.546-3.544h35.4A3.539,3.539,0,0,1,42.5,46.212L42.486,71a3.543,3.543,0,0,1-3.544,3.542h-9.87l-6.581,6.57A1.769,1.769,0,0,1,19.989,81.11ZM38.946,70.994l.016-24.783s-35.4-.005-35.4,0S3.544,71,3.547,71H14.168a1.766,1.766,0,0,1,1.252.519l5.825,5.837,5.844-5.839A1.769,1.769,0,0,1,28.341,71h10.6ZM11.511,62.148a2.656,2.656,0,1,1,2.656-2.656A2.656,2.656,0,0,1,11.511,62.148Zm9.74,0a2.656,2.656,0,1,1,2.656-2.656A2.656,2.656,0,0,1,21.252,62.148Zm9.74,0a2.656,2.656,0,1,1,2.656-2.656A2.656,2.656,0,0,1,30.992,62.148Z"
                    transform="translate(0 -42.667)"
                    fill="#c1c1c1"
                />
            </svg>
        </i>
    )
}
