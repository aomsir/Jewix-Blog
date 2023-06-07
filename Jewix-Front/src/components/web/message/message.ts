import { getSvg } from "./icons"

export type MessageProps = {
    type: "success" | "error" | "info"
    text: string
    duration?: number
}

class MyMessage extends HTMLElement {
    #duration: number
    constructor(props: { duration: number }) {
        super()
        this.attachShadow({ mode: "open" })
        this.#duration = props.duration
    }
    connectedCallback() {
        this.createContainerEl()
    }
    create(props: MessageProps) {
        const { type, text, duration = 3000 } = props
        const wrapper = document.createElement("div")
        wrapper.classList.add("message", type)
        // 添加图标
        wrapper.innerHTML = getSvg(type)
        // 添加文本
        const textEl = document.createElement("span")
        textEl.textContent = text
        wrapper.append(textEl)
        /* 添加过度动画 */
        // 生成占位元素
        const placeholder = document.createElement("div")
        placeholder.style.height = "0px"
        placeholder.style.transition = `height ${this.#duration}ms ease-in-out`
        // 添加状态类名
        wrapper.classList.add("enter-from")
        // 重置高度，生成过度动画
        requestAnimationFrame(() => {
            placeholder.style.height = wrapper.getBoundingClientRect().height + parseFloat(window.getComputedStyle(wrapper).marginBottom) + "px"
            placeholder.style.width = wrapper.getBoundingClientRect().width + "px"
            // 添加状态类名
            wrapper.classList.remove("enter-from")
            wrapper.classList.add("enter-to")
        })
        // 过度动画结束后
        placeholder.addEventListener("transitionend", () => {
            // 删除占位元素
            placeholder.remove()
            // 添加状态类名
            wrapper.classList.remove("enter-to")
        })

        setTimeout(() => {
            // 卸载类名状态
            wrapper.classList.add("leave-from")
            requestAnimationFrame(() => {
                wrapper.classList.remove("leave-from")
                wrapper.classList.add("leave-to")
            })
            setTimeout(() => {
                this.shadowRoot?.removeChild(wrapper)
            }, this.#duration)
        }, duration)

        this.shadowRoot?.prepend(placeholder, wrapper)
    }
    // 创建容器元素
    createContainerEl() {
        const style = document.createElement("style")
        style.textContent = `
                :host {
                    position: fixed;
                    z-index: 999;
                    top: 20px;
                    left: 50%;
                    transform: translateX(-50%);
                    display: flex;
                    flex-direction: column;
                }

                .message {
                    color: rgb(31,41,55);
                    margin-bottom: 10px;
                    display: flex;
                    gap: 10px;
                    align-items: center;
                    padding: 15px;
                    border-radius: 5px;
                    background-color: hsl(0 0% 94.902% / 0.8);
                    box-shadow: 0 10px 15px -3px rgb(0 0 0 / .1), 0 4px 6px -4px rgb(0 0 0 / .1);
                    transition: all .3s ease-in-out;
                }
                .message.enter-from {
                    opacity: 0;
                    transform: translateY(-10px);
                }
                .message.enter-to {
                    position: absolute;
                }
                .message.leave-to {
                    opacity: 0;
                    transform: translateX(15px);
                }
                .message.success svg path {
                    stroke: rgb(54, 211, 153);
                }
                .message.error svg path {
                    stroke: rgb(248, 114, 114);
                }
            `
        this.shadowRoot?.appendChild(style)
    }
}

let messageEl: MyMessage
// 创建 message
export function myMessage(props: MessageProps) {
    if (!messageEl) {
        messageEl = new MyMessage({ duration: 300 })
        // 挂载到 body 上
        document.body.appendChild(messageEl)
    }

    messageEl.create(props)

    console.log("create message")
}

export default MyMessage
