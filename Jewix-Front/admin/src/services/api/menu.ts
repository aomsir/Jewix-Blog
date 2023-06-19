import { fetchAllFactory, fetchFactory, fetchRowFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

export const fetchMenuItems = fetchFactory<API.FetchMenuResponse>("/admin/menus");
export const fetchAllMenuItems = fetchAllFactory(fetchMenuItems);

export const fetchMenuByRoleId = fetchRowFactory<API.FetchRoleMenuResponse, { id: number }>(
    "/admin/roles/menu",
);
