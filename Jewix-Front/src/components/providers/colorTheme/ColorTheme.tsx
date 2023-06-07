import { createContext, PropsWithChildren, ReactElement, useEffect, useState } from "react"
interface Props {
    initialTheme: THEME
    onChange?: (theme: THEME) => void
}

export enum THEME {
    LIGHT = "light",
    DARK = "dark",
}

export const ColorThemeContext = createContext({
    theme: THEME.DARK,
    setTheme: (theme: THEME) => {},
})

export default function ColorThemeProvider({ initialTheme, onChange, children }: PropsWithChildren<Props>): ReactElement {
    const [theme, setTheme] = useState(initialTheme)

    useEffect(() => {
        // 根据主题颜色的改变改变html标签data-theme属性值
        document.documentElement.setAttribute("data-theme", theme.toString())
    }, [theme])

    const value = {
        theme,
        setTheme: (theme: THEME) => {
            setTheme(theme)
            onChange?.(theme)
        },
    }

    return <ColorThemeContext.Provider value={value}>{children}</ColorThemeContext.Provider>
}
