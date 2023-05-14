package com.aomsir.jewixapi.constants;

public interface RedisConstants {
    public static final String USER_TOKEN_KEY = "user:token:";
    public static final String USER_INFO_KEY = "user:info:";

    public static final Long USER_EXPIRED = 7L;
    public static final String ARTICLE_DETAIL_KEY = "article:detail:";
    public static final Long ARTICLE_DETAIL_EXPIRE = 1L;
    public static final String ARTICLE_VIEW_IP_KEY = "article:view:ip:";
    public static final String ARTICLE_VIEW_INFO_KEY = "article:view:info:";
    public static final String ARTICLE_RANDOM_KEY = "article:random";

    public static final Long ARTICLE_RANDOM_EXPIRE = 1L;
    public static final String ARTICLE_FRONT_LIST_KEY = "article:list";
    public static final Long ARTICLE_FRONT_LIST_EXPIRE = 1L;
    public static final String CATEGORY_FRONT_LIST_KEY = "category:front:list";
    public static final Long CATEGORY_FRONT_LIST_EXPIRE = 1L;
    public static final String CATEGORY_SON_KEY = "category:son:";
    public static final Long CATEGORY_SON_EXPIRE = 1L;

    public static final String CATEGORY_LIST_KEY = "category:list";
    public static final Long CATEGORY_LIST_EXPIRE = 1L;

    public static final String FRIEND_LINK_LIST_KEY = "friend-link:list";
    public static final Long FRIEND_LINK_LIST_EXPIRE = 30L;
    public static final String PAGE_LIST_KEY = "page:list";
    public static final Long PAGE_LIST_EXPIRE = 30L;
    public static final String WEB_CONFIG_KEY = "web:config";
    public static final Long WEB_CONFIG_EXPIRE = 365L;
}
