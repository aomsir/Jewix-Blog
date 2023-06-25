/* eslint-disable @typescript-eslint/no-namespace */
export namespace ArticleEnums {
    export enum Type {
        "原创" = 1,
        "转载",
    }

    export enum Status {
        "公开",
        "私密",
    }

    export enum IsTop {
        "否",
        "是",
    }

    export enum IsDelete {
        "否",
        "是",
    }
}

export namespace PhotoEnums {
    export enum type {
        "本地",
        "又拍云",
        "阿里云",
        "腾讯云",
    }
}

export namespace UserEnums {
    export enum Status {
        "未验证",
        "正常",
        "禁用",
        "删除",
    }
}

export namespace CommentEnums {
    export enum Status {
        "待审核",
        "开放",
        "垃圾",
    }
    export enum Type {
        "文章" = 1,
        "时光机" = 21,
        "友人帐" = 23,
        "留言板",
        "通用",
    }
}

export namespace BlogrollEnums {
    export enum Location {
        "首页" = 1,
        "内页",
        "失效",
    }
}

export namespace PageEnums {
    export enum Type {
        "时光机" = 1,
        "文章归档",
        "友人帐",
        "留言板",
        "通用模板",
    }
}
