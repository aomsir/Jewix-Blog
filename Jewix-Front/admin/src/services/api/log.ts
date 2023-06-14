import { deleteFactory, fetchFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

export const fetchLogLogin = fetchFactory<API.FetchLogLoginResponse>("/admin/logs/login");
export const fetchLogOperation = fetchFactory<API.FetchLogOperationResponse>("/admin/logs/operate");

export const deleteLogLogins = deleteFactory("/admin/logs/login");
export const deleteLogOperation = deleteFactory("/admin/logs/login");
