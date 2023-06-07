import { fetchRowFactory } from "@/utils/request"
import { API } from "~/admin/src/services/ant-design-pro/typings"

export const fetchCurrentUser = fetchRowFactory<API.FetchUserDetailResponse>("users/current")
