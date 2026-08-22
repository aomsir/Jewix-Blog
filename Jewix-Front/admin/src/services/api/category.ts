import { http } from "@/lib/http/client";
import { API } from "@/services/ant-design-pro/typings";
import { deleteFactory, fetchAllFactory, fetchFactory, insertFactory } from "@/utils/request";

export const fetchSonCategories = fetchFactory<API.FetchCategoryResponse, { parentId: number }>(
  "categories/son",
);
export const fetchAllSonCategories = fetchAllFactory(fetchSonCategories);
export const fetchParentCategories = fetchFactory<API.FetchCategoryResponse>("categories/parent");
export const fetchAllParentCategories = fetchAllFactory(fetchParentCategories);
// 分页获取顶级+子级分类
export async function fetchCategories(
  { current, pageSize }: API.PaginationParams,
  options?: Record<any, any>,
) {
  // 顶级分类
  const res = await fetchParentCategories({ current, pageSize }, options);
  // 子级分类
  if (res.result) {
    for (let i = 0; i < res.result.list.length; i++) {
      const category = res.result.list[i];
      const list = await fetchAllSonCategories({ parentId: category.id });
      if (list.length) {
        category.sonList = list;
      }
    }
  }

  return res;
}
export const insertCategory = insertFactory<API.InsertCategoryParams>("admin/categories");

export async function fetchAllCategories(options?: Record<any, any>) {
  return http
    .request<API.ResponseStructure<API.PaginationResponse<API.FetchCategoryResponse[]>>>({
      url: "categories",
      ...(options || {}),
    })
    .then((response) => response.data);
}

export const deleteCategories = deleteFactory("admin/categories");
