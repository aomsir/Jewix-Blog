/**
 * Pure initialLoad gate for react-infinite-scroll-component.
 * IO mounts only when effectiveHasMore is true; arming after first scroll
 * ensures a always-visible sentinel still fires once the user has scrolled.
 */

export function initialArmed(initialLoad: boolean): boolean {
    return initialLoad
}

export function shouldListenForScroll(armed: boolean): boolean {
    return !armed
}

export function armAfterScroll(_armed: boolean): true {
    return true
}

export function effectiveHasMore(hasMore: boolean, armed: boolean): boolean {
    return hasMore && armed
}

export function resolveScrollEventTarget(
    scrollableTarget: HTMLElement | string | null | undefined,
    doc: Document | null,
    win: Window | null
): EventTarget | null {
    if (!win) {
        return null
    }
    if (scrollableTarget == null || scrollableTarget === "") {
        return win
    }
    if (typeof scrollableTarget === "string") {
        return doc?.getElementById(scrollableTarget) ?? win
    }
    return scrollableTarget
}
