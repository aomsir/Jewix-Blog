type Type = React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement>

declare namespace JSX {
    interface IntrinsicElements {
        "my-message": Type
        // 添加更多自定义元素时，继续在此处添加...
        "my-no-data": Type & { text?: string; hasData?: boolean }
        "my-tabs": Type & { initialActiveIndex?: number }
        "my-tab": Type
        "my-overlay": Type
        "my-modal": Type
    }
}
