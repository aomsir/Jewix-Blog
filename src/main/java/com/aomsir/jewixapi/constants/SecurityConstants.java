package com.aomsir.jewixapi.constants;

import org.springframework.security.authentication.DisabledException;

public interface SecurityConstants {
    public static final String REQUEST_METHOD_IS_NOT_ALLOWED = "请求方式不受支持";

    public static final String EMAIL_REGEX_ERROR = "邮箱格式错误";

    public static final String BAD_CREDENTIALS_EXCEPTION = "凭证无效,拒绝访问";
    public static final String CREDENTIALS_EXPIRED_EXCEPTION = "凭证过期,拒绝访问";
    public static final String DISABLED_EXCEPTION = "账户被禁用,拒绝访问";

    public static final String LOCKED_EXCEPTION = "账户被锁,拒绝访问";
    public static final String PROVIDER_NOT_FOUND_EXCEPTION = "找不到提供程序，拒绝访问";
    public static final String USERNAME_NOT_FOUND_EXCEPTION = "用户不存在,拒绝访问";
    public static final String LOGIN_FAILED = "登录失败";
    public static final String PERMISSION_DENIED = "权限不足,拒绝访问";
    public static final String PLEASE_LOGIN_FIRST = "请先登录";
    public static final String LOGOUT_SUCCESS = "注销成功";

}
