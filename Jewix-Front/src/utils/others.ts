/**
 * 注册激活函数与抑制函数，新的激活函数被调用后旧的抑制函数连带被调用
 * 可用于控制类似menu的li的单个active类名
 * @returns
 */
export function singleActive(initialKey?: string) {
    let currentKey = initialKey
    let _onChange = (index: string) => {}

    const actives = new Map<string, { activate?: () => void; inActivate?: () => void }>()

    function register(key: string, activate?: () => void, inActivate?: () => void) {
        actives.set(key, { activate, inActivate })
    }

    function activate(key: string, type: "api" | "user") {
        // console.log(currentKey, key)
        if (currentKey === undefined) {
            _activateByKey(key)
            if (type !== "api") _onChange(key)
            currentKey = key
        } else {
            if (currentKey !== key) {
                _inActivateByKey(currentKey)
                _activateByKey(key)
                if (type !== "api") _onChange(key)
                currentKey = key
            }
        }
    }

    function onChange(f: (index: string) => void) {
        _onChange = f
    }

    function _inActivateByKey(currentKey: string) {
        const currentKeys = [...currentKey.split("-"), ""]
        currentKeys.reduce((pre, cur) => {
            actives.get(pre)?.inActivate?.()
            return `${pre}-${cur}`
        })
    }

    function _activateByKey(activeKey: string) {
        const activeKeys = [...activeKey.split("-"), ""]
        activeKeys.reduce((pre, cur) => {
            actives.get(pre)?.activate?.()
            return `${pre}-${cur}`
        })
    }

    return { register, activate, onChange }
}
