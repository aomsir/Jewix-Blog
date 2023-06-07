import { CommentFormData } from "@/components/bases/form-comment/FormComment"
import { fetchFactory, fetchTransformerFactory } from "@/utils/request"
import { insertFactory } from "@/utils/request"
import { transformComment, transformCommentToBackend } from "../transformers"
import { API } from "./typings"

export const fetchCommentsById_Type = fetchFactory<API.FetchCommentsResponse, API.FetchCommentsParams>("comments")
export const insertComment = insertFactory<API.InsertCommentBody>("comments")

export const fetchCommentsById_TypeTransformed = fetchTransformerFactory(fetchCommentsById_Type, result => {
    return { ...result, list: result.list.map(comment => transformComment(comment)) }
})

export const insertCommentWithTransforming = async (comment: CommentFormData & Pick<API.InsertCommentBody, "parentId" | "permId" | "type" | "targetId">) => {
    await insertComment(transformCommentToBackend(comment))
}

export const insertRootCommentWithTransforming = async (comment: CommentFormData & Pick<API.InsertCommentBody, "type" | "targetId">) => {
    await insertCommentWithTransforming({
        ...comment,
        permId: 0,
        parentId: 0,
    })
}
