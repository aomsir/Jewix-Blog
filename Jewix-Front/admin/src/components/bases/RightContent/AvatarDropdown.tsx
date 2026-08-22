import { logout } from "@/services/api";
import { LocalToken } from "@/utils";
import { LogoutOutlined, SettingOutlined } from "@ant-design/icons";
import { useEmotionCss } from "@ant-design/use-emotion-css";
import { Spin } from "antd";
import { stringify } from "querystring";
import type { MenuInfo } from "rc-menu/lib/interface";
import React, { useCallback } from "react";
import { flushSync } from "react-dom";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/app/auth";
import HeaderDropdown from "../HeaderDropdown";

export type GlobalHeaderRightProps = {
  menu?: boolean;
  children?: React.ReactNode;
};

export const AvatarName = () => {
  const { currentUser } = useAuth();
  return <span className="anticon">{currentUser?.user.nickname}</span>;
};

export const AvatarDropdown: React.FC<GlobalHeaderRightProps> = ({ menu, children }) => {
  const navigate = useNavigate();
  const { currentUser, clearUser, loading } = useAuth();

  const loginOut = async () => {
    try {
      await logout();
      const { search, pathname } = window.location;
      const urlParams = new URL(window.location.href).searchParams;
      const redirect = urlParams.get("redirect");
      LocalToken.remove();
      if (window.location.pathname !== "/admin/login" && !redirect) {
        navigate(
          {
            pathname: "/login",
            search: stringify({
              redirect: pathname.replace("/admin", "") + search,
            }),
          },
          { replace: true },
        );
      }
    } catch (error) {}
  };

  const actionClassName = useEmotionCss(({ token }) => {
    return {
      display: "flex",
      height: "48px",
      marginLeft: "auto",
      overflow: "hidden",
      alignItems: "center",
      padding: "0 8px",
      cursor: "pointer",
      borderRadius: token.borderRadius,
      "&:hover": {
        backgroundColor: token.colorBgTextHover,
      },
    };
  });

  const onMenuClick = useCallback(
    async (event: MenuInfo) => {
      const { key } = event;
      if (key === "logout") {
        flushSync(() => {
          clearUser();
        });
        await loginOut();
        return;
      }
      navigate(`/account/${key}`);
    },
    [clearUser, navigate],
  );

  const loadingNode = (
    <span className={actionClassName}>
      <Spin
        size="small"
        style={{
          marginLeft: 8,
          marginRight: 8,
        }}
      />
    </span>
  );

  if (loading) {
    return loadingNode;
  }

  if (!currentUser || !currentUser.user.nickname) {
    return loadingNode;
  }

  const menuItems = [
    ...(menu
      ? [
          {
            key: "settings",
            icon: <SettingOutlined />,
            label: "个人设置",
          },
          {
            type: "divider" as const,
          },
        ]
      : []),
    {
      key: "logout",
      icon: <LogoutOutlined />,
      label: "退出登录",
    },
  ];

  return (
    <HeaderDropdown
      menu={{
        selectedKeys: [],
        onClick: onMenuClick,
        items: menuItems,
      }}
    >
      {children}
    </HeaderDropdown>
  );
};
