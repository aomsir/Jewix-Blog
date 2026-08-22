import { ComponentProps } from "react"
import InfiniteScroll from "react-infinite-scroll-component"
import { effectiveHasMore } from "./scrollLoadArm"
import { useScrollLoadArm } from "./useScrollLoadArm"

type LibraryProps = ComponentProps<typeof InfiniteScroll>

export type GatedInfiniteScrollProps = LibraryProps & {
    initialLoad: boolean
}

export default function GatedInfiniteScroll({
    initialLoad,
    hasMore,
    scrollableTarget,
    ...rest
}: GatedInfiniteScrollProps) {
    const armed = useScrollLoadArm(initialLoad, scrollableTarget)

    return (
        <InfiniteScroll
            {...rest}
            scrollableTarget={scrollableTarget}
            hasMore={effectiveHasMore(hasMore, armed)}
        />
    )
}
