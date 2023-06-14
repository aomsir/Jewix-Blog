import { API } from "./services/ant-design-pro/typings";
import routes from "/config/routes";
export const OPERATIONS = {
  CREATE: "新增",
  UPDATE: "更新",
  DELETE: "删除",
  UPDATE_STATUS: "更新状态",
};
/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};
  const isAdmin = true;
  const hasRoutes = [
    "文章管理",
    "友链管理",
    "分类管理",
    "评论管理",
    "页面管理",
    "相册管理",
    "标签管理",
    "用户管理",
    "权限管理",
    "菜单管理",
    "资源管理",
    "角色管理",
    "日志管理",
    "操作日志",
    "登录日志",
  ] as (typeof routes)[number]["name"][];
  const hasOperations = {
    文章管理: ["新增", "更新" /* , "删除" */],
    友链管理: ["新增", "更新", "删除"],
    分类管理: ["新增", "删除"],
    评论管理: ["更新", "删除" /* , "更新状态" */],
    页面管理: ["新增", "更新", "删除"],
    /* 相册管理: ["新增", "删除"], */
    标签管理: ["更新"],
    用户管理: ["新增", "更新", "删除" /* , "更新状态" */],
    权限管理: ["新增", "更新", "删除"],
    菜单管理: ["新增", "更新", "删除"],
    资源管理: ["新增", "更新", "删除"],
    角色管理: ["新增", "更新", "删除"],
  };
  return {
    canAdmin: currentUser && currentUser.access === "admin",
    // adminRouteFilter: () => isAdmin, // 只有管理员可访问
    normalRouteFilter: (route: any) => hasRoutes.includes(route.name), // initialState 中包含了的路由才有权限访问
    operationFilter: (route: any, operation: string) =>
      hasOperations[route.name as keyof typeof hasOperations]?.includes(operation), // initialState 中包含了的路由才有权限访问
  };
}
