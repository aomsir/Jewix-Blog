import { fetchFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

export const fetchMenuItems = fetchFactory<API.FetchMenuResponse>("/admin/menus");
