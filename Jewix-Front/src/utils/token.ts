export class LocalToken {
    static tokenKey = "token"

    static set(token?: string) {
        if (token) {
            localStorage.setItem(LocalToken.tokenKey, token)
        }
    }
    static get() {
        return localStorage.getItem(LocalToken.tokenKey)
    }
    static remove() {
        localStorage.removeItem(LocalToken.tokenKey)
    }
}
