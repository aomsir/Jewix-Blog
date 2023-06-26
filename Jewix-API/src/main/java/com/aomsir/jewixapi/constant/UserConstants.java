package com.aomsir.jewixapi.constant;

/**
 * @Author: Aomsir
 * @Date: 2023/6/15
 * @Description: 用户业务常量信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface UserConstants {
    String USER_IS_NULL = "用户不存在";

    String USER_EMAIL_HAS_EXISTED = "邮箱已存在,请更换";
    String USER_NAME_HAS_EXISTED = "用户名已存在,请更换";
    String USER_DELETE_MYSELF_IS_NOT_ALLOWED = "不允许删除自己";
    String USER_IS_NOT_CURRENT = "非当前登录用户,不可操作";
    String ARTICLE_USER_IS_NULL = "作者不存在";

    String USER_CAN_NOT_DELETE_THEMSELVES = "不允许删除自己";

    String USER_CAN_NOT_UPDATE_STATUS_THEMSELVES = "不允许修改自己的状态";
}
