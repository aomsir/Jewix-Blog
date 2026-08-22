import { App as AntdApp, ConfigProvider } from "antd";
import antdZhCN from "antd/locale/zh_CN";
import { Helmet, HelmetProvider } from "react-helmet-async";
import { IntlProvider } from "react-intl";
import { RouterProvider } from "react-router-dom";
import { AccessProvider } from "@/app/access";
import { AuthProvider } from "@/app/auth";
import defaultSettings from "@/config/defaultSettings";
import zhCNMessages from "@/locales/zh-CN";
import { router } from "@/router/routes";

const defaultLocale = "zh-CN";

export default function App() {
  return (
    <HelmetProvider>
      <IntlProvider locale={defaultLocale} defaultLocale={defaultLocale} messages={zhCNMessages}>
        <ConfigProvider locale={antdZhCN}>
          <AntdApp>
            <Helmet>
              <title>{defaultSettings.title}</title>
            </Helmet>
            <AuthProvider>
              <AccessProvider>
                <RouterProvider router={router} />
              </AccessProvider>
            </AuthProvider>
          </AntdApp>
        </ConfigProvider>
      </IntlProvider>
    </HelmetProvider>
  );
}
