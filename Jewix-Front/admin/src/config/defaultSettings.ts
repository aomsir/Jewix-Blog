import { ProLayoutProps } from "@ant-design/pro-components";

const Settings: ProLayoutProps & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: "light",
  colorPrimary: "#1890ff",
  layout: "mix",
  contentWidth: "Fluid",
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: "Jewix博客系统后台管理",
  pwa: false,
  logo: "/admin/favicon.ico",
  iconfontUrl: "",
  token: {},
};

export default Settings;
