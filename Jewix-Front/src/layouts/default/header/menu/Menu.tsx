import ArrowDown from "@/components/icons/arrowDown/ArrowDown"
import { MouseEvent, ReactElement, useEffect, useRef } from "react"
import { HTMLAttributes } from "react"
import css from "./Menu.module.scss"
import MenuItemLabel from "./MenuItemLabel"
export interface MenuProps extends HTMLAttributes<HTMLDivElement> {
    menuData: { id: number; label: string; url?: string; total?: number; children?: MenuProps["menuData"]; target?: string }[]
    activeIndexes?: number[]
}
export default function Menu({ className, menuData, activeIndexes, ...rest }: MenuProps): ReactElement {
    // 打开子菜单
    const openSub = (menuItemEl: HTMLElement) => {
        const subMenuEl = menuItemEl.firstElementChild?.nextElementSibling as HTMLDivElement
        // 打开菜单过程隐藏滚动条
        // HACK 可以设置类名，解耦合
        subMenuEl.style.overflowY = ""
        if (!subMenuEl) {
            return
        }
        // 关闭同级的被展开的子菜单
        Array.from(menuItemEl.parentElement?.children ?? []).forEach(menuItemEl => {
            // 将展开的关闭掉
            if (!menuItemEl.classList.contains("subMenuHidden")) {
                closeSub(menuItemEl as HTMLDivElement)
            }
        })
        /* 还原子菜单高度 */
        const subMenuItemEls = Array.from(subMenuEl.children)
        subMenuEl.style.height = subMenuItemEls.reduce((pre, subMenuItemEl) => pre + subMenuItemEl.getBoundingClientRect().height, 0) + "px"
        /* 去除菜单项的 subMenuHidden 类名 */
        menuItemEl.classList.remove("subMenuHidden")
        /* 打开菜单过后，将高度设置成auto，显示滚动条 */
        setTimeout(() => {
            subMenuEl.style.height = ""
            subMenuEl.style.overflowY = "auto"
        }, 200)
    }
    // 关闭子菜单
    const closeSub = (menuItemEl: HTMLElement) => {
        const subMenuEl = menuItemEl.firstElementChild?.nextElementSibling as HTMLDivElement
        if (!subMenuEl) {
            return
        }
        // 关闭菜单隐藏滚动条
        subMenuEl.style.overflowY = ""
        /* 将高度从具体值设置成0 */
        // 设置具体高度值，可形成动画
        subMenuEl.style.height = subMenuEl.getBoundingClientRect().height + "px"
        // 渲染后再进行设置0
        setTimeout(() => {
            subMenuEl.style.height = "0"
        }, 0)
        // 添加 subMenuHidden 类名到菜单项
        menuItemEl.classList.add("subMenuHidden")
        // 关闭子子...菜单
        const subMenuItemEls = Array.from(subMenuEl.children)
        if (subMenuItemEls) {
            subMenuItemEls.forEach(subMenuItemEl => {
                closeSub(subMenuItemEl as HTMLElement)
            })
        }
    }

    const toggle = (e: MouseEvent) => {
        let labelEl = e.target as HTMLElement
        // 如果是a元素就不进行开关了
        if (labelEl.tagName === "A") {
            return
        }
        // 若是点击的svg直接往上找到label元素
        while (labelEl.tagName !== "DIV") {
            if (labelEl.parentElement) {
                labelEl = labelEl.parentElement
            }
        }
        const menuItemEl = labelEl.parentElement! as HTMLElement

        if (menuItemEl.classList.contains("subMenuHidden")) {
            // 打开子菜单
            openSub(menuItemEl)
        } else {
            // 关闭子菜单
            closeSub(menuItemEl)
        }
    }

    const isSelected = (i: number, i1?: number, i2?: number) => {
        const indexes = [i, i1, i2]
        while (indexes[indexes.length - 1] === undefined) {
            indexes.pop()
        }
        return activeIndexes?.join() === indexes.join() ? "selected" : ""
    }

    const menuWrapRef = useRef<HTMLDivElement>(null)
    useEffect(() => {
        // 点击外部隐藏菜单
        document.addEventListener("click", e => {
            if (!menuWrapRef.current?.contains(e.target as HTMLElement)) {
                const menuEl = menuWrapRef.current?.firstElementChild
                Array.from(menuEl?.children ?? []).forEach(menuItemEl => {
                    closeSub(menuItemEl as HTMLElement)
                })
            }
        })
    })

    return (
        <div className={`${className} ${css["my-menu-wrap"]}`} {...rest} ref={menuWrapRef}>
            <ul className="my-menu">
                {menuData.map((item, index) => (
                    /* 一级菜单项 */
                    <li key={item.id} className={`subMenuHidden ${isSelected(index)}`}>
                        {/* 特定状况，非通用 */}
                        {!!item.children?.length && <MenuItemLabel href={item.url} label={item.label} children={item.children} total={item.total} onClick={toggle} target={item.target} />} 
                        {item.children && (
                            <ul style={{ "--depth": 1 } as any}>
                                {item.children.map((item1, index1) => (
                                    /* 二级菜单项 */
                                    <li key={item1.id} className={`subMenuHidden ${isSelected(index, index1)}`}>
                                        <MenuItemLabel
                                            href={item1.url}
                                            label={item1.label}
                                            children={item1.children}
                                            total={item1.total}
                                            onClick={toggle}
                                            target={item1.target}
                                        />
                                        {item1.children && (
                                            <ul style={{ "--depth": 2 } as any}>
                                                {item1.children.map((item2, index2) => (
                                                    /*  三级菜单项  */
                                                    <li key={item2.id} className={`subMenuHidden ${isSelected(index, index1, index2)}`}>
                                                        <MenuItemLabel
                                                            href={item2.url}
                                                            label={item2.label}
                                                            total={item2.total}
                                                            children={item2.children}
                                                            onClick={toggle}
                                                            target={item2.target}
                                                        />
                                                        {item2.children && <ul></ul>}
                                                    </li>
                                                ))}
                                            </ul>
                                        )}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    )
}
