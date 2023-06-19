import { fetchAllFactory, fetchFactory, fetchRowFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

export const fetchResources = fetchFactory<API.FetchResourceResponse>("/admin/resources");
export const fetchAllResources = fetchAllFactory(fetchResources);

export const fetchResourcesByRoleId = fetchRowFactory<
    API.FetchRoleResourcesResponse,
    { id: number }
>("/admin/roles/resource");
