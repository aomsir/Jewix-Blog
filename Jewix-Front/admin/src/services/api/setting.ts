import { fetchRowFactory, updateFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

export const fetchWebSiteSetting = fetchRowFactory<API.FetchWebSiteSetting>("/admin/configs");
export const insertWebSiteSetting = updateFactory("/admin/configs");
export const updateWebSiteSetting = updateFactory("/admin/configs");
