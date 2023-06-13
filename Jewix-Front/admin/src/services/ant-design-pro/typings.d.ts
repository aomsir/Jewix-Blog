// @ts-ignore
/* eslint-disable */

import { ArticleEnums, BlogrollEnums, PageEnums, PhotoEnums, UserEnums } from "@/config/enums";
// 与后端约定的响应数据格式
declare namespace API {
  type ResponseStructure<Result = any> = {
    code: number;
    result?: Result;
    msg: string;
    token?: string;
    status?: API.FetchUserDetailResponse;
  };
  type PaginationParams = {
    current?: number;
    pageSize?: number;
  };
  type PaginationResponse<List = any[]> = {
    list: List;
    pageIndex: number;
    pageSize: number;
    totalCount: number;
    totalPage: number;
  };
  type DeleteParams = {
    ids: (string | number)[];
  };
  type FetchArticleResponse = {
    content: string;
    cover: string;
    createTime: string;
    id: number;
    isDelete: number;
    isTop: number;
    originUrl: string;
    status: number;
    title: string;
    type: number;
    updateTime: string;
    uuid: string;
    views: number;
  };
  type FetchArticleDetailResponse = API.FetchArticleResponse & {
    categories: [null];
    commentCount: 0;
    lastUuid: "4e5dcbe2-5240-45fa-900c-d59ae3d0169b";
    nextUuid: null;
    tags: [null];
    userName: null;
  };
  type UpdateArticleBody = Omit<InsertArticleBody, "createTime" | "updateTime">;
  type InsertArticleBody = {
    categoryIds: number[];
    content: string;
    cover: string;
    description: string;
    isTop: ArticleEnums.IsTop;
    originUrl?: string;
    status: ArticleEnums.Status;
    tagIds: number[];
    title: string;
    type: ArticleEnums.Type;
  };
  type FetchTagResponse = {
    id: number;
    createTime: string;
    // status: number;
    tagName: string;
    updateTime: string;
  };
  type InsertTagBody = {
    tagName: string;
  };
  type UpdateTagBody = {
    id: number;
    tagName: string;
  };
  type CategoryResponse = {};
  type FetchUserParams = API.PaginationParams & {
    status: UserEnums.Status;
    email?: string;
  };
  type FetchUserResponse = {
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    authorities?: unknown;
    createTime: string;
    credentialsNonExpired: boolean;
    description: string;
    email: string;
    enabled: boolean;
    id: number;
    nickname: string;
    password?: string;
    roles: unknown[];
    salt?: unknown;
    status: number;
    updateTime: string;
    username: string;
    uuid: string;
    webSite: string;
  };
  type InsertUserParams = {
    email: string;
    nickname: string;
    password: string;
    description: string;
    webSite: string;
  };
  type UpdateUserParams = Omit<InsertUserParams, "password"> & {
    password?: string;
    id: number;
    uuid: string;
  };
  type UpdateUserStatusParams = {
    uuid: string;
    status: UserEnums.Status;
  };
  type FetchUserDetailResponse = {
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    authorities: null;
    createTime: string;
    credentialsNonExpired: boolean;
    description: string;
    email: string;
    enabled: boolean;
    id: number;
    nickname: string;
    password: string;
    roles: unknown[];
    salt: string;
    status: number;
    updateTime: string;
    username: string;
    uuid: string;
    webSite: string;
  };
  type UpdateUserDetailBody = {
    id: number;
    uuid: string;
    description?: string;
    email?: string;
    nickname?: string;
    password?: string;
    webSite?: string;
  };
  type FetchCategoryResponse = {
    categoryName: string;
    createTime: string;
    id: number;
    parentId: number;
    status: null;
    updateTime: string;
    sonList?: FetchCategoryResponse[];
  };
  type InsertCategoryParams = {
    categoryName: string;
    parentId: number;
  };
  type FetchCommentParams = {
    author?: string;
    content?: string;
    email?: string;
    status?: number;
  };
  type FetchCommentResponse = {
    agent: string;
    author: string;
    content: string;
    createTime: string;
    email: string;
    id: number;
    ip: string;
    location: string;
    parentId: number;
    permId: number;
    status: number;
    targetId: number;
    type: number;
    updateTime: string;
    url: string;
  };
  type InsertCommentParams = {
    author: string;
    content: string;
    email: string;
    parentId: number;
    permId: number;
    targetId: number;
    type: number;
    url?: string;
  };
  type UpdateCommentParams = {
    author: string;
    content: string;
    email: string;
    id: number;
    status: number;
    url: string;
  };
  type DeleteCommentParams = {
    ids: (number | string)[];
  };
  type UpdateCommentStatusParams = Pick<API.UpdateCommentParams, "id", "status">;
  type FetchBlogrollParams = API.PaginationParams & {
    location: BlogrollEnums.Location;
  };
  type FetchBlogrollResponse = {
    createTime: string;
    description: string;
    id: number;
    link: string;
    location: BlogrollEnums.Location;
    photo: string;
    status: null;
    title: string;
    updateTime: string;
  };
  type UpdateBlogrollBody = {
    description: string;
    id: number;
    link: string;
    location: number;
    photo: string;
    title: string;
  };
  type InsertBlogrollBody = {
    description: string;
    link: string;
    location: number;
    photo: string;
    title: string;
  };
  type FetchPhotosParams = API.PaginationParams & {
    type: PhotoEnums.type;
  };
  type FetchPhotosResponse = {
    createTime: string;
    fileName: string;
    id: number;
    location: string;
    type: number;
    url: string;
  };
  type DeletePhotoParams = {
    fileName: string;
    type: PhotoEnums.type;
  };
  type FetchSettingParams = {
    type: number;
  };
  type CurrentUser = {
    name?: string;
    avatar?: string;
    userid?: string;
    email?: string;
    signature?: string;
    title?: string;
    group?: string;
    tags?: { key?: string; label?: string }[];
    notifyCount?: number;
    unreadCount?: number;
    country?: string;
    access?: string;
    geographic?: {
      province?: { label?: string; key?: string };
      city?: { label?: string; key?: string };
    };
    address?: string;
    phone?: string;
  };
  export type FetchPagesResponse = {
    content: string;
    createTime: string;
    description: string;
    id: number;
    status: null;
    title: string;
    type: PageEnums.Type;
    updateTime: string;
    userId: number;
    userName: string;
    uuid: string;
    views: number;
    url: string;
  };
  export type FetchPageResponse = {
    content: string;
    createTime: string;
    description: string;
    id: number;
    omit: null;
    status: null;
    title: string;
    type: PageEnums.Type;
    updateTime: string;
    userId: number;
    userName: string;
    uuid: string;
    views: number;
  };
  export type InsertPageBody = {
    title: string;
    omit: string;
    content: string;
    description: string;
    type: PageEnums;
  };
  type UpdatePageBody = {
    id: number;
    uuid: string;
    title: string;
    content: string;
    description: string;
    type: number;
    views: number;
  };
  type DeletePageParams = {
    uuid: string;
  };
  type FetchMenuResponse = {
    componentPath: string;
    createTime: string;
    iconName: string;
    id: number;
    name: string;
    parentId: number;
    path: string;
    sonList: API.FetchMenuResponse[];
    type: number;
    updateTime: string;
  };
  type FetchResourceResponse = {
    createTime: string;
    id: 2;
    label: string;
    method: string;
    name: string;
    parentId: number;
    route: string;
  };
  type FetchRolesResponse = {
    createTime: string;
    id: number;
    roleLabel: string;
    roleName: string;
    updateTime: string;
  };
  type InsertRoleBody = {
    roleName: string;
    labelRole: string;
  };
  type UpdateRoleBody = {
    id: number;
  } & API.InsertRoleBody;
  type DeleteRolesParams = {
    roleIds: (number | string)[];
  };

  type LoginParams = {
    username: string;
    password: string;
    // autoLogin?: boolean;
    // type?: string;
  };
  type RuleListItem = {
    key?: number;
    disabled?: boolean;
    href?: string;
    avatar?: string;
    name?: string;
    owner?: string;
    desc?: string;
    callNo?: number;
    status?: number;
    updatedAt?: string;
    createdAt?: string;
    progress?: number;
  };
  type RuleList = {
    data?: RuleListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };
  type FakeCaptcha = {
    code?: number;
    status?: string;
  };
  type ErrorResponse = {
    /** 业务约定的错误码 */
    errorCode: string;
    /** 业务上的错误信息 */
    errorMessage?: string;
    /** 业务上的请求是否成功 */
    success?: boolean;
  };
  type NoticeIconList = {
    data?: NoticeIconItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };
  type NoticeIconItemType = "notification" | "message" | "event";
  type NoticeIconItem = {
    id?: string;
    extra?: string;
    key?: string;
    read?: boolean;
    avatar?: string;
    title?: string;
    status?: string;
    datetime?: string;
    description?: string;
    type?: NoticeIconItemType;
  };
}
