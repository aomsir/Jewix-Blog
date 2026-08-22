type WebComponentProps = React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement>

declare namespace React {
    namespace JSX {
        interface IntrinsicElements {
            "my-message": WebComponentProps
            "my-no-data": WebComponentProps & { text?: string; "has-data"?: boolean | string; hasData?: boolean }
            "my-tabs": WebComponentProps & { initialActiveIndex?: number }
            "my-tab": WebComponentProps
            "my-overlay": WebComponentProps
            "my-modal": WebComponentProps
        }
    }
}
