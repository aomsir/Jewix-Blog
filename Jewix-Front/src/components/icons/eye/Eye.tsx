import { ReactElement } from "react"
interface Props {
    [key: string]: any
}
export default function Eye({ ...rest }: Props): ReactElement {
    return (
        <i {...rest}>
            <svg xmlns="http://www.w3.org/2000/svg" width="19.402" height="13.296" viewBox="0 0 19.402 13.296">
                <path
                    id="chakan"
                    d="M111.978,220.431a1.819,1.819,0,1,1,1.939-1.815A1.88,1.88,0,0,1,111.978,220.431Zm0-4.833a3.245,3.245,0,0,0-2.991,1.873,2.89,2.89,0,0,0,.707,3.3,3.4,3.4,0,0,0,3.531.649,3.033,3.033,0,0,0,1.989-2.807,3.138,3.138,0,0,0-3.236-3.029Zm8.387,3.018a9.2,9.2,0,0,1-16.762.079v-.159a9.219,9.219,0,0,1,16.75,0v.079Zm1.309,0a.775.775,0,0,0,0-.216,1.062,1.062,0,0,0,0-.2v-.079a10.635,10.635,0,0,0-19.259-.057.541.541,0,0,0,0,.113.4.4,0,0,0,0,.147v.216a.509.509,0,0,0,0,.113v.147a.859.859,0,0,0,0,.136h-.133a.562.562,0,0,0,0,.113v.079a10.634,10.634,0,0,0,19.247.057.615.615,0,0,0,0-.136.478.478,0,0,0,0-.159A1.2,1.2,0,0,0,121.674,218.616Z"
                    transform="translate(-102.279 -211.968)"
                    fill="#696969"
                />
            </svg>
        </i>
    )
}
