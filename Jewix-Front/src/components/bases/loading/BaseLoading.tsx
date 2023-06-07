import Loader from "@/components/ui/loading/Loading"
import Space from "@/components/ui/space/Space"
import { ReactElement } from "react"
import { HTMLAttributes } from "react"
import css from "./BaseLoading.module.scss"
interface LoaderProps extends HTMLAttributes<HTMLDivElement> {}
export default function BaseLoading({ className, ...rest }: LoaderProps): ReactElement {
    return (
        <div className={`${className ?? ""} ${css["base-loader"]}`} {...rest}>
            <Space className="loading">
                <Loader />
                <span>努力加载中...</span>
            </Space>
        </div>
    )
}
