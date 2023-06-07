import { PageEnums } from "@/configs/enums"
import { fetchRowFactory, fetchTransformerFactory } from "@/utils/request"
import { PromiseType } from "@/utils/type"
import { API } from "./typings"

export const fetchAllPages = fetchRowFactory<API.FetchPagesResponse[]>("pages")
export const fetchAllPagesTransformed = fetchTransformerFactory(fetchAllPages, pages =>
    pages.map(page => ({
        ...page,
        url: "/" + page.omit,
        label: page.title,
        targetId: page.id,
    }))
)
export type FetchAllPagesTransformedReturn = PromiseType<ReturnType<typeof fetchAllPagesTransformed>>

export function getUrlByType(type: PageEnums.Type) {
    switch (type) {
        case PageEnums.Type.时光机:
            return "/time"
        case PageEnums.Type.文章归档:
            return "/archive"
        case PageEnums.Type.友人帐:
            return "/blogroll"
        default:
            return undefined
    }
}

export async function fetchPageIdByType(type: PageEnums.Type) {
    // 获取页面
    const pages = await fetchAllPagesTransformed(undefined)
    const url = getUrlByType(type)
    if (!url) {
        throw new Error("获取页面url失败")
    }
    // 获取时光机页面id
    const id = getIdByUrlInPages(pages, url)
    if (!id) {
        throw new Error("获取时光机页面id失败")
    }
    return id
}

export async function fetchPageInfoByUrl(url: string) {
    // 获取页面
    const pages = await fetchAllPagesTransformed(undefined)

    // 根据url获取页面
    const page = pages.find(item => {
        return item.url.includes(url.trim())
    })

    if (!page) {
        throw new Error("获取页面信息失败")
    }
    return page
}

const getIdByUrlInPages = (pages: FetchAllPagesTransformedReturn, url: string) => pages.find(item => item.url === url)?.id
