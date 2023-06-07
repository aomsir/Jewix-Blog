import { MyHTMLElement } from "../index.util"
import { Overlay } from "../overlay/overlay"

export class Modal extends MyHTMLElement {
    #overlay = new Overlay()
    #modalEl = this.#createModalEl()

    constructor() {
        super()
        this.attachShadow({ mode: "open" })
        // 插入子组件
        this.parentElement?.appendChild(this.#overlay)
        // 插槽作为子组件孩子
        this.shadowRoot?.appendChild(this.#modalEl)
        this.insertSlot(this.shadowRoot)
        this.insertStyle(`
            :host {
                position: fixed;
                top: 50%;
                left: 50%;
                translate: -50% -50%;
                display: none;
                min-width: 300px;
                min-height: 150px;
                background-color: #fff;
                z-index: 1000;
                padding: 1.5rem;
                border-radius: 1rem;
                box-shadow: 0 25px 50px -12px #00000040;;
            }

            :host(.visible) {
                animation: modalShow .2s cubic-bezier(.4,0,.2,1) forwards;
            }

            :host(.hidden) {
                animation: modalHide .2s cubic-bezier(.4,0,.2,1) forwards;
            }

            @keyframes modalShow {
                from {
                    transform: scale(.8);
                    opacity: 0;
                }
                to {
                    transform: scale(1);
                    opacity: 1;
                }
            }

            @keyframes modalHide {
                from {
                    transform: scale(1);
                    opacity: 1;
                }
                to {
                    transform: scale(.8);
                    opacity: 0;
                }
            }
        `)
    }

    show() {
        this.#overlay.show()

        this.style.display = "block"
        this.classList.add("visible")
        this.classList.remove("hidden")

        requestAnimationFrame(() => {
            document.onmouseup = e => {
                const target = e.target as HTMLElement
                console.log(this, target, e.currentTarget)

                if (!this.contains(target)) {
                    this.hide()
                    document.onmouseup = null
                }
            }
        })
    }

    hide() {
        this.#overlay.hide()
        this.classList.remove("visible")
        this.classList.add("hidden")

        this.onanimationend = () => {
            this.style.display = "none"
            this.onanimationend = null
        }
    }

    #createModalEl() {
        const modal = document.createElement("div")
        modal.classList.add("modal")
        return modal
    }
}
