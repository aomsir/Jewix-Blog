import { SiteInfo } from "@/servers/api/common"
import { API } from "@/servers/api/typings"
import { HTMLAttributes, HTMLProps, ReactElement } from "react"
import webSiteConfig from "~/site.config"
import { AsideProps } from "../aside/Aside"
import css from "./Footer.module.scss"
type FooterProps =  HTMLAttributes<HTMLDivElement> &  {
    siteInfo: SiteInfo
}
export default function Footer({ siteInfo,className, ...rest }: FooterProps): ReactElement {
    return (
        <footer className={`${css.footer} ${className}`} >
            <p>
                <span>本网站由</span>
                <img src="/layer1.png" />
                <img src="/layer2.png" style={{ height: "13px" }} />
                <span>提供CDN加速/云储存服务</span>
            </p>
            <p>
                <img src={webSiteConfig.record.ICP.icon} />{" "}
                <a href={webSiteConfig.record.ICP.url} target="_blank">
                    {/* {webSiteConfig.record.ICP.province}ICP备{webSiteConfig.record.ICP.number} */}
                    {siteInfo.icp}
                </a>
            </p>
            {!!siteInfo.police && <p>
                <img src={webSiteConfig.record.公网安.icon} />
                <a href={webSiteConfig.record.公网安.url} target="_blank">
                    {/* {webSiteConfig.record.公网安.province}公网安备{webSiteConfig.record.公网安.number} */}
                    {siteInfo.police}
                </a>
            </p>}
        </footer>
    )
}
