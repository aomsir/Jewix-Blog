import Footer from "@/components/bases/Footer";
// import { login } from '@/services/ant-design-pro/api';
import { API } from "@/services/ant-design-pro/typings";
import { fetchCurrentUserInfo, login } from "@/services/api/user";
import { LocalToken } from "@/utils/token";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { LoginForm, ProFormText } from "@ant-design/pro-components";
import { useEmotionCss } from "@ant-design/use-emotion-css";
import { FormattedMessage, Helmet, history, SelectLang, useIntl, useModel } from "@umijs/max";
import { Alert, message } from "antd";
import React, { useState } from "react";
import { flushSync } from "react-dom";
import Settings from "../../../config/defaultSettings";
import css from "./index.less";

const Lang = () => {
  const langClassName = useEmotionCss(({ token }) => {
    return {
      width: 42,
      height: 42,
      lineHeight: "42px",
      position: "fixed",
      right: 16,
      borderRadius: token.borderRadius,
      ":hover": {
        backgroundColor: token.colorBgTextHover,
      },
    };
  });
  return (
    <div className={langClassName} data-lang>
      {SelectLang && <SelectLang />}
    </div>
  );
};

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};

const Login: React.FC = () => {
  console.log("login page");
  // 登录状态
  const [userLoginState, setUserLoginState] = useState<API.ResponseStructure>();
  // 获取初始登录状态
  const { initialState, setInitialState, refresh } = useModel("@@initialState");
  // 国际化
  const intl = useIntl();
  // 登录
  const handleSubmit = async (values: API.LoginParams) => {
    const data = await login(values);
    LocalToken.set(data.token);

    // 如果成功
    if (data.msg === "success") {
      message.success(
        intl.formatMessage({
          id: "pages.login.success",
          defaultMessage: "登录成功！",
        }),
      );
      // 等待initialState刷新，不然initialState为空值
      await refresh();
      const userInfo =
        (await initialState?.fetchUserInfo?.()) ??
        (
          await fetchCurrentUserInfo({
            skipErrorHandler: true,
          })
        ).status;
      if (userInfo) {
        // flushSync 用于同步更新 state
        flushSync(() => {
          setInitialState((s) => ({
            ...s,
            currentUser: userInfo,
          }));
        });
      }
      // 获取到之前的页面
      const urlParams = new URL(window.location.href).searchParams;
      // 跳转到之前的页面
      history.push(urlParams.get("redirect") || "/");
    } else {
      // 如果失败去设置用户错误信息
      setUserLoginState(data);
    }
  };
  // 返回
  return (
    <div className={css.login}>
      {/* 网页标题 */}
      <Helmet>
        <title>
          {intl.formatMessage({
            id: "menu.login",
            defaultMessage: "登录页",
          })}
          - {Settings.title}
        </title>
      </Helmet>
      {/* 内容 */}
      <div
        style={{
          flex: "1",
          padding: "32px 0",
        }}
      >
        <LoginForm
          contentStyle={{
            minWidth: 280,
            maxWidth: "75vw",
          }}
          logo={<img alt="logo" src="/admin/logo.svg" />}
          title="Jewix博客"
          subTitle="后台管理系统"
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.LoginParams);
          }}
        >
          {(userLoginState?.code ?? 200) >= 400 && (
            <LoginMessage content={userLoginState?.msg ?? "未知错误"} />
          )}
          <ProFormText
            name="username"
            fieldProps={{
              size: "large",
              prefix: <UserOutlined />,
            }}
            placeholder="用户名"
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id="pages.login.username.required"
                    defaultMessage="请输入用户名!"
                  />
                ),
              },
            ]}
          />
          <ProFormText.Password
            name="password"
            fieldProps={{
              size: "large",
              prefix: <LockOutlined />,
            }}
            placeholder="密码"
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id="pages.login.password.required"
                    defaultMessage="请输入密码！"
                  />
                ),
              },
            ]}
          />
        </LoginForm>
      </div>
      {/* 页脚 */}
      <Footer />
      {/* 右上角语言 */}
      <Lang />
    </div>
  );
};

export default Login;
