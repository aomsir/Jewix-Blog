import { CommentFormData } from "@/components/bases/form-comment/FormComment"
import { CommentEnums } from "@/configs/enums"
import { convertKey, convertKeyOfTree, timeStampToTimes } from "@/utils/convert"
import { addKey, addKeyOfTree } from "@/utils/transform"
import { API } from "../api/typings"

export function transformComment(comment: API.FetchCommentsResponse) {
    const commentConverted = convertKeyOfTree(
        comment,
        {
            location: "address",
            author: "user",
            childList: "replies",
            blogger: "isBlogger",
        } as const,
        "replies" as const
    )
    let parentNickName = ""
    const peer = {} as any

    const commentAdded = addKeyOfTree(
        commentConverted,
        s => {
            // 获取操作系统和浏览器
            const [, os, browser] = s.agent.match(/"os":"(.*?)","browser":"(.*?)"}/) ?? []
            let forNickname

            // 没有回复的时候，就是在最后一层中
            if (!s.replies?.length) {
                // 添加至同层回复列表
                peer[s.id.toString()] = s.user
                // 查看当前回复的parentId是否在同层回复列表中
                forNickname = peer[s.parentId] ?? parentNickName
            }
            if (s.parentId === 0) {
                parentNickName = s.user
            }
            // 更新楼主名字
            return {
                os: os?.split(" ") ?? [],
                browser: browser?.split(" ") ?? [],
                date: timeStampToTimes(Date.parse(s.createTime)),
                for: forNickname ?? null,
            }
        },
        "replies"
    )

    return commentAdded
}

export function transformCommentToBackend(comment: CommentFormData & { parentId: number; permId: number; type: CommentEnums.Type; targetId: number }) {
    const commentConverted = convertKey(comment, {
        nickName: "author",
        website: "url",
    } as const)

    return commentConverted
}
