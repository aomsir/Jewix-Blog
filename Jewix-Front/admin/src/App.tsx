import { App as AntdApp } from "antd";
import { Helmet, HelmetProvider } from "react-helmet-async";
import { RouterProvider } from "react-router-dom";
import { AccessProvider } from "@/app/access";
import { AuthProvider } from "@/app/auth";
import { LocaleIntlProvider, LocaleProvider } from "@/app/locale";
import defaultSettings from "@/config/defaultSettings";
import { router } from "@/router/routes";

export default function App() {
  return (
    <HelmetProvider>
      <LocaleProvider>
        <LocaleIntlProvider>
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
        </LocaleIntlProvider>
      </LocaleProvider>
    </HelmetProvider>
  );
}
