import request from "@/utils/request"
import { API } from "./typings"

export const fetchAllCategories = () => request<API.ResponseStructure<API.FetchAllCategoriesResponse[]>>("categories")
