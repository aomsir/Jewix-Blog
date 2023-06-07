import Tab from "./tab"

export class Tabs extends HTMLElement {
    constructor() {
        super()
        this.attachShadow({ mode: "open" })
        this.#insertSlot()
        this.#insertStyle()
    }
    connectedCallback() {
        const initialActiveIndex = parseInt(this.getAttribute("initialActiveIndex") ?? "0")
        const changeEvent = new CustomEvent("change", {
            detail: {
                index: 0,
            },
        })
        Array.from(this.children).forEach((child, index) => {
            if (child.nodeName === "MY-TAB") {
                // 初始化激活元素
                if (index === initialActiveIndex) {
                    child.classList.add("active")
                }
                // 监听子元素事件，切换激活元素
                child.addEventListener("click", () => {
                    Array.from(this.children).forEach(child => {
                        if (child.nodeName === "MY-TAB") {
                            child.classList.remove("active")
                        }
                    })
                    child.classList.add("active")
                    // 触发自定义change事件
                    changeEvent.detail.index = index
                    this.dispatchEvent(changeEvent)
                })
            }
        })
    }
    // 创建插槽
    #insertSlot() {
        const slotEl = document.createElement("slot")
        this.shadowRoot?.appendChild(slotEl)
    }
    // 创建样式
    #insertStyle() {
        const styleEl = document.createElement("style")
        styleEl.textContent = `
            :host {
                display: flex;
                cursor: pointer;
            }
        `
        this.shadowRoot?.append(styleEl)
    }
}

export default Tabs
