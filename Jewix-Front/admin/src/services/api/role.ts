import { deleteFactory, fetchFactory, insertFactory, updateFactory } from "@/utils";
import { API } from "../ant-design-pro/typings";

export const fetchRoles = fetchFactory<API.FetchRolesResponse>("/admin/roles");
export const insertRole = insertFactory<API.InsertRoleBody>("/admin/roles");
export const updateRole = updateFactory<API.UpdateRoleBody>("/admin/roles");
export const deleteRoles = deleteFactory<API.DeleteRolesParams>("/admin/roles");

export const upsertRoleMenus = insertFactory<API.UpsertRoleMenusBody>("/admin/menus/doAssign");
export const upsertRoleResources = insertFactory<API.UpsertRoleResourcesBody>(
  "/admin/resources/doAssign",
);
