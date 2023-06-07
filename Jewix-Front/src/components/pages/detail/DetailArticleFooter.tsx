import Money from "@/components/icons/money/Money"
import { Modal } from "@/components/web/modal/modal"
import { DetailProps } from "@/pages/detail/[id]"
import { ReactElement, useEffect, useRef } from "react"
import { HTMLAttributes } from "react"
import webSiteConfig from "~/site.config"
import css from "./DetailArticleFooter.module.scss"
interface DetailArticleFooterProps extends HTMLAttributes<HTMLDivElement> {
    modificationTime: DetailProps["article"]["modificationTime"]
}
export default function DetailArticleFooter({ className, modificationTime, ...rest }: DetailArticleFooterProps): ReactElement {
    const modalRef = useRef<Modal>(null)

    useEffect(() => {
        setTimeout(() => {
            // console.log(modalRef.current.show())
        }, 1000)
    })

    return (
        <div className={`${className ?? ""} ${css["detail-article__footer"]}`} {...rest}>
            <div className="info flex justify-between">
                <p>
                    最后修改：{modificationTime[0]}年{modificationTime[1]}月{modificationTime[2]}日 {modificationTime[3]}:{modificationTime[4]}
                </p>
                <p>允许规范转载</p>
            </div>
            <div className="bottom">
                <button
                    onClick={() => {
                        modalRef.current?.show?.()
                    }}
                >
                    <Money />
                    赞赏
                </button>
                <p>如果觉得我的文章对你有用，请随意赞赏</p>
                <my-modal ref={modalRef}>
                    <div className="pay">
                        <div>
                            <img src={webSiteConfig.reward.alipay} alt="支付宝" />
                            <span>支付宝</span>
                        </div>
                        <div>
                            <img src={webSiteConfig.reward.wechat} alt="微信" />
                            <span>微信</span>
                        </div>
                    </div>
                </my-modal>
            </div>
        </div>
    )
}
