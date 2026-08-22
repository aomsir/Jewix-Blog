import { useAuth } from "@/app/auth";
import Footer from "@/components/bases/Footer";
import { SelectLang } from "@/components/bases/RightContent";
import Settings from "@/config/defaultSettings";
import { API } from "@/services/ant-design-pro/typings";
import { ApiError } from "@/lib/http/errors";
import { fetchCurrentUserInfo, login } from "@/services/api/user";
import { LocalToken } from "@/utils/token";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { LoginForm, ProFormText } from "@ant-design/pro-components";
import { useEmotionCss } from "@ant-design/use-emotion-css";
import { Alert, message } from "antd";
import React, { useState } from "react";
import { flushSync } from "react-dom";
import { Helmet } from "react-helmet-async";
import { FormattedMessage, useIntl } from "react-intl";
import { useNavigate, useSearchParams } from "react-router-dom";
import css from "./index.module.scss";

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
  const [userLoginState, setUserLoginState] = useState<API.ResponseStructure>();
  const { setCurrentUser } = useAuth();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const intl = useIntl();
  const handleSubmit = async (values: API.LoginParams) => {
    let data: API.ResponseStructure;
    try {
      data = await login(values);
    } catch (error) {
      if (error instanceof ApiError) {
        setUserLoginState({ code: error.code, msg: error.message });
      }
      return;
    }

    if (data.msg === "success") {
      LocalToken.set(data.token);
      message.success(
        intl.formatMessage({
          id: "pages.login.success",
          defaultMessage: "登录成功！",
        }),
      );
      try {
        const userInfo = (
          await fetchCurrentUserInfo({
            skipErrorHandler: true,
          })
        ).status;
        if (userInfo) {
          flushSync(() => {
            setCurrentUser(userInfo);
          });
        }
        navigate(searchParams.get("redirect") || "/");
      } catch (error) {
        LocalToken.remove();
        const fallback = intl.formatMessage({
          id: "pages.login.failure",
          defaultMessage: "登录失败，请重试！",
        });
        const text =
          error instanceof Error && error.message.trim() ? error.message : fallback;
        message.error(text);
      }
    } else {
      setUserLoginState(data);
    }
  };
  return (
    <div className={css.login}>
      <Helmet>
        <title>
          {intl.formatMessage({
            id: "menu.login",
            defaultMessage: "登录页",
          })}
          - {Settings.title}
        </title>
      </Helmet>
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
      <Footer />
      <Lang />
    </div>
  );
};

export default Login;
