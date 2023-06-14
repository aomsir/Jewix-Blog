/**
 * @name umi 的路由配置
 * @description 只支持 path,component,routes,redirect,wrappers,name,icon 的配置
 * @param path  path 只支持两种占位符配置，第一种是动态参数 :id 的形式，第二种是 * 通配符，通配符只能出现路由字符串的最后。
 * @param component 配置 location 和 path 匹配后用于渲染的 React 组件路径。可以是绝对路径，也可以是相对路径，如果是相对路径，会从 src/pages 开始找起。
 * @param routes 配置子路由，通常在需要为多个路径增加 layout 组件时使用。
 * @param redirect 配置路由跳转
 * @param wrappers 配置路由组件的包装组件，通过包装组件可以为当前的路由组件组合进更多的功能。 比如，可以用于路由级别的权限校验
 * @param name 配置路由的标题，默认读取国际化文件 menu.ts 中 menu.xxxx 的值，如配置 name 为 login，则读取 menu.ts 中 menu.login 的取值作为标题
 * @param icon 配置路由的图标，取值参考 https://ant.design/components/icon-cn， 注意去除风格后缀和大小写，如想要配置图标为 <StepBackwardOutlined /> 则取值应为 stepBackward 或 StepBackward，如想要配置图标为 <UserOutlined /> 则取值应为 user 或者 User
 * @doc https://umijs.org/docs/guides/routes
 */
export default [
  {
    path: "/article",
    name: "文章管理",
    icon: "BookOutlined",
    component: "./article",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },
  {
    path: "/comment",
    name: "评论管理",
    icon: "Comment",
    component: "./comment",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },
  {
    path: "/page",
    name: "页面管理",
    icon: "Book",
    component: "./page",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },
  {
    path: "/blogroll",
    name: "友链管理",
    icon: "TeamOutlined",
    component: "./blogroll",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },
  {
    path: "/category",
    name: "分类管理",
    icon: "PicCenterOutlined",
    component: "./category",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },
  {
    path: "/tag",
    name: "标签管理",
    icon: "Tag",
    component: "./tag",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },
  {
    path: "/photo",
    name: "相册管理",
    icon: "FolderOpenOutlined",
    component: "./photo",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },
  {
    path: "/user",
    name: "用户管理",
    icon: "User",
    component: "./user",
    access: "normalRouteFilter", // 会调用 src/access.ts 中返回的 normalRouteFilter 进行鉴权
  },

  {
    path: "/account",
    hideInMenu: true, // 将该路由项隐藏在菜单中
    name: "",
    routes: [
      {
        path: "/account/settings",
        name: "profileSettings",
        component: "./account/settings",
      },
    ],
  },
  {
    path: "/auth",
    name: "权限管理",
    icon: "SecurityScanOutlined",
    access: "normalRouteFilter",
    routes: [
      {
        path: "/auth/menu",
        name: "菜单管理",
        icon: "MenuOutlined",
        component: "./auth/menu",
      },
      {
        path: "/auth/resource",
        name: "资源管理",
        icon: "ApiOutlined",
        component: "./auth/resource",
        access: "normalRouteFilter",
      },
      {
        path: "/auth/role",
        name: "角色管理",
        icon: "IdcardOutlined",
        component: "./auth/role",
        access: "normalRouteFilter",
      },
    ],
  },
  {
    path: "/log",
    name: "日志管理",
    icon: "FileTextOutlined",
    access: "normalRouteFilter",
    routes: [
      {
        path: "/log/operation",
        name: "操作日志",
        icon: "AuditOutlined",
        component: "./log/operation",
        access: "normalRouteFilter",
      },
      {
        path: "/log/login",
        name: "登录日志",
        icon: "LoginOutlined",
        component: "./log/login",
        access: "normalRouteFilter",
      },
    ],
  },
  // {
  //   path: '/setting',
  //   name: 'setting',
  //   icon: 'setting',
  //   component: './setting',
  // },
  {
    path: "/login",
    name: "login",
    layout: false,
    component: "./login",
  },
  {
    path: "/",
    name: "",
    redirect: "/article",
  },
  {
    path: "*",
    name: "",
    layout: false,
    component: "./404",
  },
] as const;
