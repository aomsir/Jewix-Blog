import { useEffect, useState } from "react"
import { armAfterScroll, initialArmed, resolveScrollEventTarget, shouldListenForScroll } from "./scrollLoadArm"

export function useScrollLoadArm(
    initialLoad: boolean,
    scrollableTarget?: HTMLElement | string | null
): boolean {
    const [armed, setArmed] = useState(() => initialArmed(initialLoad))

    useEffect(() => {
        if (!shouldListenForScroll(armed)) {
            return
        }
        const target = resolveScrollEventTarget(
            scrollableTarget,
            typeof document !== "undefined" ? document : null,
            typeof window !== "undefined" ? window : null
        )
        if (!target) {
            return
        }
        const onScroll = () => {
            setArmed(armAfterScroll(armed))
        }
        target.addEventListener("scroll", onScroll, { passive: true })
        return () => {
            target.removeEventListener("scroll", onScroll)
        }
    }, [armed, scrollableTarget])

    return armed
}
