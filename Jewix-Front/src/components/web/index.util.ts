export class MyHTMLElement extends HTMLElement {
    slotEl: HTMLSlotElement | null = null
    styleEl: HTMLStyleElement | null = null

    constructor() {
        super()
    }
    // 创建插槽
    insertSlot(container: HTMLElement | ShadowRoot | null) {
        this.slotEl = document.createElement("slot")
        container?.appendChild(this.slotEl)
    }
    // 创建样式
    insertStyle(cssText: string) {
        this.styleEl = document.createElement("style")
        this.styleEl.textContent = cssText
        this.shadowRoot?.append(this.styleEl)
    }
}
