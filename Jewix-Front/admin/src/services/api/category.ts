// import { fetchAll } from '@/utils/request';
import { API } from '@/services/ant-design-pro/typings';
import { deleteFactory, fetchAllFactory, fetchFactory, insertFactory } from '@/utils/request';
import { request } from '@umijs/max';

// export function fetchCategories(params: API.PaginationParams, options?: Record<any, any>) {
//   return request<API.ResponseStructure<API.PaginationResult<API.CategoryResponse[]>>>(
//     'categories',
//     {
//       params: {
//         page: params.current,
//         length: params.pageSize,
//       },
//       ...(options || {}),
//     },
//   );
// }
export const fetchSonCategories = fetchFactory<API.FetchCategoryResponse, { parentId: number }>(
  'categories/son',
);
export const fetchAllSonCategories = fetchAllFactory(fetchSonCategories);
export const fetchParentCategories = fetchFactory<API.FetchCategoryResponse>('categories/parent');
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
export const insertCategory = insertFactory<API.InsertCategoryParams>('admin/categories');
// export const UpdateFactory = <P>(url: string) => writeFactory<P>(url, 'put');

export async function fetchAllCategories(options?: Record<any, any>) {
  return request<API.ResponseStructure<API.PaginationResponse<API.FetchCategoryResponse[]>>>(
    'categories',
    {
      ...(options || {}),
    },
  );
}
// export const fetchAllCategories = fetchAllFactory(fetchCategories);

export const deleteCategories = deleteFactory('admin/categories');
