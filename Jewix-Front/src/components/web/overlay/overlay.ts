import { MyHTMLElement } from "../index.util"

export class Overlay extends MyHTMLElement {
    constructor() {
        super()
        this.attachShadow({ mode: "open" })
        this.insertSlot(this.shadowRoot)
        this.insertStyle(`
            :host {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(29,38,43,.4);
                opacity: 0;
                z-index: 999;
                transition: opacity .2s cubic-bezier(.4,0,.2,1);
            }

            :host(.visible) {
                opacity: 1;
            }
        `)
    }

    show() {
        // this.style.visibility = "visible"
        this.style.display = "block"
        requestAnimationFrame(() => this.classList.add("visible"))
    }

    async hide() {
        this.classList.remove("visible")
        // 等待过渡动画完成
        this.ontransitionend = () => {
            // this.style.visibility = "hidden"
            this.style.display = "none"
            this.ontransitionend = null
        }
    }
}
