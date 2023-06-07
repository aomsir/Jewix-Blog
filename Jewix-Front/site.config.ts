const webSiteConfig = {
    // 主页标题
    title: "Aomsir's Blog",
    // 主页描述
    description: "Aomsir's Blog",
    // 所有页面通用关键词
    keywords: ["Aomsir", "Aomsir's Blog"],
    // 页面顶部标签的图标
    favicon: "/favicon.ico",
    // 导航logo
    logo: {
        // 亮色主题图片
        light: "/logo-light.png",
        // 暗色主题图片
        dark: "/logo-dark.png",
    },
    // 全局导航链接
    links: [
        { label: "首页", url: "/" },
        { label: "微博", url: "https://weibo.com/u/6501838389" },
        {
            label: "哔哩哔哩",
            url: "https://space.bilibili.com/406964839?share_medium=android&share_source=copy_link&bbid=XYBFE25B85EF03122FDBE90426DEADD8124C5&ts=1583131487143",
        },
        { label: "GitHub", url: "https://github.com/aomsir" },
    ],
    // 赞赏码
    reward: {
        // 支付宝
        alipay: "/Alipay.png",
        // 微信
        wechat: "/wechatPay.png",
    },
    // 底部备案信息
    record: {
        ICP: {
            // 备案图标
            icon: "/layer3.png",
            // 备案省份简称
            province: "鄂",
            // 备案号
            number: "20001957号-1",
            // 备案链接
            url: "http://www.beian.miit.gov.cn/",
        },
        公网安: {
            // 备案图标
            icon: "/layer3.png",
            // 备案省份简称
            province: "鄂",
            // 公网安备案号
            number: "42052802000025号",
            // 公网安备案链接
            url: "https://www.beian.gov.cn/portal/index.do",
        },
    },
}

export default webSiteConfig
