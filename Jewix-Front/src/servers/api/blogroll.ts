import { BlogrollEnums } from "@/configs/enums"
import { API } from "admin/src/services/ant-design-pro/typings"
import { fetchAllFactory, fetchFactory } from "@/utils/request"
// 获取导航栏友链
export const fetchGlobalBlogroll = fetchFactory<API.FetchBlogrollResponse>("friend-links/page", { location: BlogrollEnums.Location.首页 })
export const fetchAllGlobalBlogroll = fetchAllFactory(fetchGlobalBlogroll)
// 获取内页友链
export const fetchInnerBlogroll = fetchFactory<API.FetchBlogrollResponse>("friend-links/page", { location: BlogrollEnums.Location.内页 })
export const fetchAllInnerBlogroll = fetchAllFactory(fetchInnerBlogroll)