import Android from "@/components/icons/android/Android";
import Chrome from "@/components/icons/chrome/Chrome";
import Edge from "@/components/icons/edge/Edge";
import Firefox from "@/components/icons/firefox/Firefox";
import Linux from "@/components/icons/linux/Linux";
import Mac from "@/components/icons/mac/Mac";
import QQ from "@/components/icons/qq/QQ";
import Safari from "@/components/icons/safari/Safari";
import Windows from "@/components/icons/windows/Windows";

// 浏览器图标
export const BROWSER_ICONS = {
    CHROME: Chrome,
    EDGE: Edge,
    FIREFOX: Firefox,
    SAFARI: Safari,
    QQ: QQ,
    DEFAULT: Edge,
};
// 系统图标
export const OS_ICONS = {
    WINDOWS: Windows,
    MAC: Mac,
    IOS: Mac,
    LINUX: Linux,
    ANDROID: Android,
    DEFAULT: Windows,
};
// 获取浏览器名称对应的图标组件
export function getIcon(name: string, type: "browser" | "os") {
    const strategy = {
        browser: BROWSER_ICONS,
        os: OS_ICONS,
    };
    const Icons = strategy[type];
    // 如果映射表中存在对应的组件，则返回该组件
    // eslint-disable-next-line guard-for-in
    for (const key in Icons) {
        if (name?.toUpperCase().includes(key)) {
            // @ts-ignore
            const Icon = Icons[key];
            return <Icon className="icon" />;
        }
    }
    // 否则返回一个默认的图标组件
    return <Icons.DEFAULT className="icon" />;
}
