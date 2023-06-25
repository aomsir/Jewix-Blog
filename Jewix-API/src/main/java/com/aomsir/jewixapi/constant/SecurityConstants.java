package com.aomsir.jewixapi.constant;


/**
 * @Author: Aomsir
 * @Date: 2023/6/15
 * @Description: 权限业务常量信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface SecurityConstants {
    String REQUEST_METHOD_IS_NOT_ALLOWED = "请求方式不受支持";

    String EMAIL_REGEX_ERROR = "邮箱格式错误";

    String BAD_CREDENTIALS_EXCEPTION = "凭证无效,拒绝访问";
    String CREDENTIALS_EXPIRED_EXCEPTION = "凭证过期,拒绝访问";
    String DISABLED_EXCEPTION = "账户被禁用,拒绝访问";

    String LOCKED_EXCEPTION = "账户被锁,拒绝访问";
    String PROVIDER_NOT_FOUND_EXCEPTION = "找不到提供程序，拒绝访问";
    String USERNAME_NOT_FOUND_EXCEPTION = "用户不存在,拒绝访问";
    String LOGIN_FAILED = "登录失败";
    String PERMISSION_DENIED = "权限不足,拒绝访问";
    String PLEASE_LOGIN_FIRST = "请先登录";
    String LOGOUT_SUCCESS = "注销成功";

}
