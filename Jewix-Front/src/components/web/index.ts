export * from "./message/message"
export * from "./noData/noData"
import MyMessage from "./message/message"
import NoData from "./noData/noData"
import Tabs from "./tab/tabs"
import Tab from "./tab/tab"
import { Overlay } from "./overlay/overlay"
import { Modal } from "./modal/modal"

function defineWebComponent(name: string, component: CustomElementConstructor) {
    if (!customElements.get(name)) {
        customElements.define(name, component)
    }
}

export function defineWebComponents() {
    defineWebComponent("my-message", MyMessage)
    defineWebComponent("my-no-data", NoData)
    defineWebComponent("my-tabs", Tabs)
    defineWebComponent("my-tab", Tab)
    defineWebComponent("my-overlay", Overlay)
    defineWebComponent("my-modal", Modal)
}
