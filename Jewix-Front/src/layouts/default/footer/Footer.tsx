import { ReactElement } from "react"
import webSiteConfig from "~/site.config"
import css from "./Footer.module.scss"

export default function Footer({ className, ...rest }: { [key: string]: any }): ReactElement {
    return (
        <footer className={`${css.footer} ${className}`} {...rest}>
            <p>
                <span>本网站由</span>
                <img src="/layer1.png" />
                <img src="/layer2.png" style={{ height: "13px" }} />
                <span>提供CDN加速/云储存服务</span>
            </p>
            <p>
                <img src={webSiteConfig.record.ICP.icon} />{" "}
                <a href={webSiteConfig.record.ICP.url} target="_blank">
                    {webSiteConfig.record.ICP.province}ICP备{webSiteConfig.record.ICP.number}
                </a>
            </p>
            <p>
                <img src={webSiteConfig.record.公网安.icon} />
                <a href={webSiteConfig.record.公网安.url} target="_blank">
                    {webSiteConfig.record.公网安.province}公网安备{webSiteConfig.record.公网安.number}
                </a>
            </p>
        </footer>
    )
}
