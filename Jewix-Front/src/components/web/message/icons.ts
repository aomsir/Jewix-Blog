import { MessageProps } from "./message"

const errorSvg = `
<svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="none" viewBox="0 0 24 24">
    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
</svg>
`

const successSvg = `
<svg width="24px" height="24px" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
</svg>
`

export function getSvg(type: MessageProps["type"]) {
    switch (type) {
        case "error":
            return errorSvg
        case "success":
            return successSvg
        default:
            return ""
    }
}
