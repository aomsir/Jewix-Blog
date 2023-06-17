import { fetchAllFactory, fetchFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

export const fetchResources = fetchFactory<API.FetchResourceResponse>("/admin/resources");
export const fetchAllResources = fetchAllFactory(fetchResources);
