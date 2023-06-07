import { ReactElement, useState } from "react"
import InfiniteScroll from "react-infinite-scroller"
import { HTMLAttributes } from "react"
import BaseLoading from "../loading/BaseLoading"
type InfiniteScrollProps<List> = HTMLAttributes<HTMLDivElement> & {
    request: (current: number) => Promise<List>
    initialList: List
    pageSize: number
    noDataText?: string
    contentRender: (list: List) => ReactElement | ReactElement[] | string
    actionRef?: React.MutableRefObject<ActionRefType | undefined>
}
export type ActionRefType = {
    reload: () => void
}

export const MyInfiniteScroll = <List extends unknown[]>(props: InfiniteScrollProps<List>) => {
    const { request, noDataText, initialList, pageSize, children, contentRender, actionRef, ...rest } = props
    const [list, setList] = useState(props.initialList)
    const [hasMore, setHasMore] = useState(true)
    const [lastPageList, setLastPageList] = useState<List>(props.initialList)

    let isLoaded = false

    const fetchItems = async () => {
        // 每次set后，才能再次加载，也就是一次渲染只能加载一次
        if (!isLoaded) {
            try {
                isLoaded = true

                const _list = await request(Math.floor(list.length / pageSize) + 1)

                const isDeletedListInSamePage = JSON.stringify(lastPageList).includes(JSON.stringify(_list))
                const isSameListInSamePage = lastPageList.length === _list.length
                const isAddedListInSamePage = _list.length > lastPageList.length

                // console.log(isDeletedListInSamePage, isSameListInSamePage, isAddedListInSamePage)
                // 如果是同一页，覆盖数据
                if (isDeletedListInSamePage || isSameListInSamePage || isAddedListInSamePage) {
                    setList(pre => [...pre.slice(0, lastPageList.length ? -lastPageList.length : undefined), ..._list] as any)
                } else {
                    // 如果是下一页，追加数据
                    setList(pre => [...pre, ..._list] as any)
                }
                // 如果获取的数据小于pageSize，说明没有更多数据了
                if (_list.length < pageSize) {
                    setHasMore(false)
                }
                // 记录上一页的长度，用于清空同一页的旧数据
                setLastPageList(_list)
            } catch (error) {
                setHasMore(false)
                console.error(error)
            }
        }
    }

    // actionRef
    if (actionRef) {
        actionRef.current = {
            reload: fetchItems,
        }
    }

    return (
        <InfiniteScroll loadMore={fetchItems} hasMore={hasMore} initialLoad={false} loader={<BaseLoading key={0} />}>
            <my-no-data has-data={!!list.length} text={noDataText}>
                {contentRender(list)}
            </my-no-data>
        </InfiniteScroll>
    )
}

export default MyInfiniteScroll
