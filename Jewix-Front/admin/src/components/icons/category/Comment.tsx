import { ReactElement } from "react"
import { HTMLAttributes } from "react"
interface EditProps extends HTMLAttributes<HTMLDivElement> {}
export default function Category({ className, ...rest }: EditProps): ReactElement {
    return (
        <i className={`${className ?? ""}`} {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="44.071" height="33.517" viewBox="0 0 44.071 33.517">
                <g id="分类" transform="translate(-63.922 -178.107)">
                    <path
                        id="路径_74"
                        data-name="路径 74"
                        d="M71,180.915a2.807,2.807,0,1,0,.822-1.986A2.841,2.841,0,0,0,71,180.915Zm0,0"
                        transform="translate(-6.724)"
                        fill="#c1c1c1"
                    />
                    <path
                        id="路径_75"
                        data-name="路径 75"
                        d="M63.922,457.989A2.81,2.81,0,1,0,64.745,456a2.845,2.845,0,0,0-.823,1.987Zm0,0"
                        transform="translate(0 -263.16)"
                        fill="#c1c1c1"
                    />
                    <path
                        id="路径_76"
                        data-name="路径 76"
                        d="M63.922,736.53a2.81,2.81,0,1,0,.823-1.987,2.845,2.845,0,0,0-.823,1.987Zm0,0"
                        transform="translate(0 -527.716)"
                        fill="#c1c1c1"
                    />
                    <path
                        id="路径_77"
                        data-name="路径 77"
                        d="M314.942,180.786a2.572,2.572,0,0,1-2.458,2.677H283.34a2.577,2.577,0,0,1-2.461-2.677h0a2.575,2.575,0,0,1,2.461-2.677h29.14a2.574,2.574,0,0,1,2.461,2.677Zm0,0"
                        transform="translate(-206.948 -0.001)"
                        fill="#c1c1c1"
                    />
                    <path
                        id="路径_78"
                        data-name="路径 78"
                        d="M314.942,457.973a2.574,2.574,0,0,1-2.458,2.677H283.34a2.577,2.577,0,0,1-2.461-2.677h0a2.573,2.573,0,0,1,2.461-2.677h29.14a2.571,2.571,0,0,1,2.461,2.677Zm0,0"
                        transform="translate(-206.948 -263.271)"
                        fill="#c1c1c1"
                    />
                    <path
                        id="路径_79"
                        data-name="路径 79"
                        d="M314.942,736.4a2.575,2.575,0,0,1-2.458,2.68H283.34a2.577,2.577,0,0,1-2.461-2.68h0a2.574,2.574,0,0,1,2.461-2.674h29.14a2.574,2.574,0,0,1,2.461,2.674Zm0,0"
                        transform="translate(-206.948 -527.717)"
                        fill="#c1c1c1"
                    />
                </g>
            </svg>
        </i>
    )
}
