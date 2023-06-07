export function message(props: { type: "success" | "error"; text: string }) {
    import("@/components/web/message/message").then(({ myMessage }) => {
        myMessage(props)
    })
}

message.success = (text: string) => message({ type: "success", text })
message.error = (text: string) => message({ type: "error", text })
