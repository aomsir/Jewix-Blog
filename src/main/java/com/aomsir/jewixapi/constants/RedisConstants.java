package com.aomsir.jewixapi.constants;

public interface RedisConstants {
    public static final String USER_TOKEN_KEY = "user:token:";
    public static final String USER_INFO_KEY = "user:info:";

    public static final Long USER_EXPIRED = 7L;
    public static final String ARTICLE_DETAIL_KEY = "article:detail:";
    public static final Long ARTICLE_DETAIL_EXPIRE = 1L;
    public static final String ARTICLE_VIEW_IP_KEY = "article:views:ip:";
    public static final String ARTICLE_VIEW_INFO_KEY = "article:view:info:";
    public static final String ARTICLE_RANDOM_KEY = "article:random";

    public static final Long ARTICLE_RANDOM_EXPIRE = 1L;
    public static final String ARTICLE_FRONT_LIST_KEY = "article:list";
    public static final Long ARTICLE_FRONT_LIST_EXPIRE = 1L;
    public static final String CATEGORY_FRONT_LIST_KEY = "category:list";
    public static final Long CATEGORY_FRONT_LIST_EXPIRE = 1L;
    public static final String CATEGORY_SON_KEY = "category:son:";
    public static final Long CATEGORY_SON_EXPIRE = 1L;

    public static final String CATEGORY_LIST_KEY = "category:list";
    public static final Long CATEGORY_LIST_EXPIRE = 1L;
}
