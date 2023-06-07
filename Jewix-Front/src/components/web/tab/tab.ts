export class Tab extends HTMLElement {
    constructor() {
        super()
        this.attachShadow({ mode: "open" })
        this.#insertStyle()
        this.#insertSlot()
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
                padding: 5px 15px;
                border-bottom: 2px solid #d9d9d9;
            }

            :host(.active) {
                animation: tabActive 0.2s ease-in-out forwards;
            }

            @keyframes tabActive {
                0% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(1.05);
                }
                100% {
                    transform: scale(1);
                    border-bottom-color: #1f2937;
                }
            }
        `
        this.shadowRoot?.append(styleEl)
    }
}

export default Tab
