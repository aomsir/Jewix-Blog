import FormComment, { CommentFormData } from "@/components/bases/form-comment/FormComment"
import { ReactElement } from "react"
import { HTMLAttributes } from "react"
import BoardInfo, { BoardInfoProps } from "@/components/bases/board-info/BoardInfo"
export interface MsgBoardProps extends HTMLAttributes<HTMLDivElement> {
    info: BoardInfoProps["info"]
    onConfirm: (formData: CommentFormData) => void
}
/**
 * 留言板
 */
export default function MsgBoard({ className, info, onConfirm, ...rest }: MsgBoardProps): ReactElement {
    return (
        <div className={`${className}`} {...rest}>
            <BoardInfo title="留言板" info={info}>
                {/* 提交回复表单 */}
                <div className="form-wrap">
                    <h2>发表留言</h2>
                    <FormComment onConfirm={onConfirm} />
                </div>
            </BoardInfo>
        </div>
    )
}
