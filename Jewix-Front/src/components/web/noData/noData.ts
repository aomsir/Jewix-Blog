if (typeof window === "undefined") {
    throw ""
}
export class NoData extends HTMLElement {
    constructor() {
        super()
        this.attachShadow({ mode: "open" })
    }

    static get observedAttributes() {
        return ["has-data", "text"]
    }

    attributeChangedCallback(attrName: string, oldVal: string, newVal: string) {
        Array.from(this.shadowRoot?.children ?? []).forEach(child => child.remove())
        this.#updateChildren()
    }

    #updateChildren() {
        const hasData = this.getAttribute("has-data") === "true"

        if (hasData) {
            this.#insertSlot()
        } else {
            this.#insertSpan()
        }
    }
    // 创建插槽
    #insertSlot() {
        const slotEl = document.createElement("slot")
        this.shadowRoot?.appendChild(slotEl)
    }
    // 创建样式
    #insertSpan() {
        const spanEl = document.createElement("span")
        spanEl.textContent = this.getAttribute("text") || "暂无数据"
        const styleEl = document.createElement("style")
        styleEl.textContent = `
            span {
                display: block;
                padding: 20px;
                text-align: center;
            }
        `
        this.shadowRoot?.append(spanEl, styleEl)
    }
}

export default NoData
