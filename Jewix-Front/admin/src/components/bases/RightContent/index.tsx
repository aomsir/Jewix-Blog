import { useLocale, type AppLocale } from "@/app/locale";
import { GlobalOutlined, QuestionCircleOutlined } from "@ant-design/icons";
import { Dropdown } from "antd";
import type { MenuProps } from "antd";
import React from "react";

export type SiderTheme = "light" | "dark";

const locales: { key: AppLocale; label: string }[] = [
  { key: "zh-CN", label: "简体中文" },
  { key: "en-US", label: "English" },
];

export type SelectLangProps = {
  style?: React.CSSProperties;
};

export const SelectLang = ({ style }: SelectLangProps) => {
  const { locale, setLocale } = useLocale();
  const items: MenuProps["items"] = locales.map((item) => ({
    key: item.key,
    label: item.label,
  }));
  return (
    <Dropdown
      menu={{
        items,
        selectedKeys: [locale],
        onClick: ({ key }) => {
          if (key === "zh-CN" || key === "en-US") {
            setLocale(key);
          }
        },
      }}
      placement="bottomRight"
    >
      <span style={{ padding: 4, cursor: "pointer", ...style }}>
        <GlobalOutlined />
      </span>
    </Dropdown>
  );
};

export const Question = () => {
  return (
    <div
      style={{ display: "flex", height: 26 }}
      onClick={() => {
        window.open("https://pro.ant.design/docs/getting-started");
      }}
    >
      <QuestionCircleOutlined />
    </div>
  );
};
