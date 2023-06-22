import { fetchRowFactory, updateFactory } from "@/utils/request";
import { API } from "./typings";

export const fetchWebSiteSetting = fetchRowFactory<API.FetchWebSiteSetting>("/admin/configs");
export const insertWebSiteSetting = updateFactory("/admin/configs");
export const updateWebSiteSetting = updateFactory("/admin/configs");
