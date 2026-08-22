import { ProLayout, SettingDrawer } from "@ant-design/pro-components";
import { useState } from "react";
import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { useAuthorization } from "@/app/access";
import { useAuth } from "@/app/auth";
import Footer from "@/components/bases/Footer";
import { AvatarDropdown, AvatarName } from "@/components/bases/RightContent/AvatarDropdown";
import defaultSettings from "@/config/defaultSettings";
import { buildMenuRoutes, routeMeta } from "@/router/routeMeta";
import { getAvatarUrlByEmail } from "@/utils/avatar";

const layoutBgImgList = [
  {
    src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr",
    left: 85,
    bottom: 100,
    height: "303px",
  },
  {
    src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr",
    bottom: -68,
    right: -45,
    height: "303px",
  },
  {
    src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr",
    bottom: 0,
    left: 0,
    width: "331px",
  },
];

export default function AdminLayout() {
  const location = useLocation();
  const navigate = useNavigate();
  const access = useAuthorization();
  const { currentUser } = useAuth();
  const [settings, setSettings] = useState(defaultSettings);
  const menuRoutes = buildMenuRoutes(routeMeta, access);
  return (
    <ProLayout
      {...settings}
      location={location}
      route={{ routes: menuRoutes }}
      {...({ layoutBgImgList } as object)}
      menuHeaderRender={undefined}
      avatarProps={{
        src:
          getAvatarUrlByEmail(currentUser?.user.email ?? "") ??
          "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png",
        title: <AvatarName />,
        render: (_, avatarChildren) => <AvatarDropdown menu>{avatarChildren}</AvatarDropdown>,
      }}
      menuItemRender={(item, dom) =>
        item.path ? (
          <Link to={item.path} style={{ display: "flex", gap: "10px" }}>
            {item.pro_layout_parentKeys && item.pro_layout_parentKeys.length > 0 && item.icon}
            {dom}
          </Link>
        ) : (
          dom
        )
      }
      onMenuHeaderClick={() => navigate("/article")}
      footerRender={() => <Footer />}
    >
      <Outlet />
      <SettingDrawer
        disableUrlParams
        enableDarkTheme
        settings={settings as never}
        onSettingChange={(next) => setSettings(next as typeof defaultSettings)}
      />
    </ProLayout>
  );
}
