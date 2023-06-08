import { API } from './services/ant-design-pro/typings';
import routes from '/config/routes';
/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};
  const isAdmin = true;
  const hasRoutes = [
    '文章管理',
    '友链管理',
    '分类管理',
    '评论管理',
    '页面管理',
    '相册管理',
    '标签管理',
    '用户管理',
    '权限管理',
  ] as (typeof routes)[number]['name'][];
  return {
    canAdmin: currentUser && currentUser.access === 'admin',
    // adminRouteFilter: () => isAdmin, // 只有管理员可访问
    normalRouteFilter: (route: any) => hasRoutes.includes(route.name), // initialState 中包含了的路由才有权限访问
  };
}
