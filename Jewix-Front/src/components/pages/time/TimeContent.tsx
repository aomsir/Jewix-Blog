import FormComment from "@/components/bases/form-comment/FormComment"
import MyInfiniteScroll, { ActionRefType } from "@/components/bases/myInfiniteScroll/MyInfiniteScroll"
import { CommentEnums } from "@/configs/enums"
import { PageCommentPageSize, PageProps } from "@/pages/[url]"
import { fetchCommentsById_TypeTransformed, insertRootCommentWithTransforming } from "@/servers/api"
import { LocalToken } from "@/utils/token"
import { ReactElement, useEffect, useRef, useState } from "react"
import { HTMLAttributes } from "react"
import TimeCard from "./card/TimeCard"
import css from "./TimeContent.module.scss"
export type TimeContentProps = HTMLAttributes<HTMLDivElement> & {
    initialComments: PageProps["initialComments"]
    pageInfo: PageProps["pageInfo"]
}
export default function TimeContent({ className, initialComments, pageInfo, ...rest }: TimeContentProps): ReactElement {
    const [hasToken, setHasToken] = useState(false)
    const fetchItems = async (current: number) => {
        const result = await fetchCommentsById_TypeTransformed({
            type: CommentEnums.Type.时光机,
            targetId: pageInfo.targetId,
            current,
            pageSize: PageCommentPageSize,
        })

        return result.list
    }

    const actionRef = useRef<ActionRefType>()

    useEffect(() => {
        setHasToken(!!LocalToken.get())
    })

    return (
        <div className={`${className ?? ""} ${css["time-content"]}`} {...rest}>
            <h1>时光机</h1>
            {hasToken && (
                <FormComment
                    className="formComment"
                    onConfirm={async formData => {
                        await insertRootCommentWithTransforming({ ...formData, type: CommentEnums.Type.时光机, targetId: pageInfo.targetId })
                        import("@/components/web/message/message").then(({ myMessage }) => {
                            myMessage({
                                type: "success",
                                text: "评论成功",
                            })
                        })
                        actionRef.current?.reload()
                    }}
                />
            )}
            <MyInfiniteScroll
                actionRef={actionRef}
                request={fetchItems}
                /* @ts-ignore */
                initialList={initialComments}
                pageSize={PageCommentPageSize}
                noDataText="暂无数据"
                contentRender={list => list.map(item => <TimeCard key={item.id} card={item} />)}
            />
        </div>
    )
}
