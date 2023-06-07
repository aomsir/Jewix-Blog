import { BaseEntity, createBaseModel } from "."

export interface Tag extends BaseEntity {
    id: number
    tagName: string
}

export const createTag = (tag: Partial<Tag>) => ({
    id: 0,
    tagName: "",
    ...createBaseModel(),
    ...tag,
})
