package com.aomsir.jewixapi.constant;

/**
 * @Author: Aomsir
 * @Date: 2023/6/15
 * @Description: Redis键常量信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface RedisConstants {
    String USER_TOKEN_KEY = "user:token:";
    String USER_INFO_KEY = "user:info:";

    Long USER_EXPIRED = 7L;
    String ARTICLE_DETAIL_KEY = "article:detail:";
    Long ARTICLE_DETAIL_EXPIRE = 1L;
    String ARTICLE_VIEW_IP_KEY = "article:view:ip:";
    String ARTICLE_VIEW_INFO_KEY = "article:view:info:";
    String ARTICLE_RANDOM_KEY = "article:random";

    Long ARTICLE_RANDOM_EXPIRE = 1L;
    String ARTICLE_FRONT_LIST_KEY = "article:list";
    Long ARTICLE_FRONT_LIST_EXPIRE = 1L;
    String CATEGORY_FRONT_LIST_KEY = "category:front:list";
    Long CATEGORY_FRONT_LIST_EXPIRE = 1L;
    String CATEGORY_SON_KEY = "category:son:";
    Long CATEGORY_SON_EXPIRE = 1L;

    String CATEGORY_LIST_KEY = "category:list";
    Long CATEGORY_LIST_EXPIRE = 1L;

    String FRIEND_LINK_LIST_KEY = "friend-link:list";
    Long FRIEND_LINK_LIST_EXPIRE = 30L;
    String PAGE_LIST_KEY = "page:list";
    Long PAGE_LIST_EXPIRE = 30L;
    String WEB_CONFIG_KEY = "web:config";
    Long WEB_CONFIG_EXPIRE = 1L;
}
