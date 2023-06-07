import gravatar from "gravatar"

export function getAvatarUrlByEmail(email: string) {
    const url = gravatar.url(email, { size: "46", protocol: "http", d: "monsterid" })

    return url.replace("www.gravatar.com", "gravatar.loli.net")
}
