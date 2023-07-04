import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { ArticleEnums } from "@/config/enums";
import { useSelection } from "@/hooks/props";
import { API } from "@/services/ant-design-pro/typings";
import {
    deleteArticles,
    fetchArticleByUUID,
    fetchArticles,
    softDeleteArticles,
} from "@/services/api/article";
import { ActionType, PageContainer, ProList, ProListMetas } from "@ant-design/pro-components";
import { history } from "@umijs/max";
import { Image, message, Space } from "antd";
import { HTMLAttributes, ReactElement, useRef } from "react";
import css from "./Article.module.scss";
import ProTableToolBar from "./components/ProTableToolBar";
import { tableConfig } from "/config/table";
type ArticleProps = HTMLAttributes<HTMLDivElement>;
// 文章列表页面
export default function Article({ className, ...rest }: ArticleProps): ReactElement {
    const [selectionState, selectionProps] = useSelection();
    const actionRef = useRef<ActionType>();

    metas.actions!.render = (dom, entity) => {
        return (
            <Space>
                {entity.isDelete === ArticleEnums.IsDelete.是 ? (
                    <HasOperation operation={OPERATIONS.DELETE}>
                        <PopConfirmDelete
                            onConfirm={async () => {
                                try {
                                    await deleteArticles({ ids: [entity.id] });
                                    message.success("删除成功");
                                    actionRef.current?.reload();
                                } catch (error) {}
                            }}
                        >
                            <a style={{ color: "#fc3a52" }}>物理删除</a>
                        </PopConfirmDelete>
                    </HasOperation>
                ) : (
                    <>
                        <HasOperation operation={OPERATIONS.UPDATE}>
                            <a
                                onClick={async () => {
                                    try {
                                        const data = await fetchArticleByUUID(entity.uuid);
                                        history.push(`/article/edit?uuid=${entity.uuid}`);
                                    } catch (error) {}
                                }}
                            >
                                编辑
                            </a>
                        </HasOperation>
                        <HasOperation operation={OPERATIONS.DELETE}>
                            <PopConfirmDelete
                                onConfirm={async () => {
                                    try {
                                        await softDeleteArticles({ ids: [entity.id] });
                                        message.success("删除成功");
                                        actionRef.current?.reload();
                                    } catch (error) {}
                                }}
                            >
                                <a style={{ color: "#fc3a52" }}>逻辑删除</a>
                            </PopConfirmDelete>
                        </HasOperation>
                    </>
                )}
            </Space>
        );
    };

    return (
        <PageContainer className={`${className ?? ""} ${css.article}`} {...rest}>
            <ProList<API.FetchArticleResponse, API.PaginationParams>
                headerTitle="文章列表"
                toolBarRender={() => [
                    <ProTableToolBar
                        key={1}
                        onInsertButtonClick={() => history.push(`/article/edit`)}
                    />,
                ]}
                metas={metas}
                request={fetchWidthNormalizedResponse(fetchArticles)}
                pagination={{
                    defaultPageSize: 10,
                    showSizeChanger: true,
                    pageSizeOptions: tableConfig.pageSizes,
                }}
                // 批量操作
                rowSelection={selectionProps}
                actionRef={actionRef}
                //批量删除
                tableAlertRender={({ selectedRowKeys, onCleanSelected }) => (
                    <Space size={24}>
                        <span>已选择 {selectedRowKeys.length} 项</span>
                        <HasOperation operation={OPERATIONS.DELETE}>
                            <PopConfirmDelete
                                onConfirm={async () => {
                                    try {
                                        await softDeleteArticles({ ids: selectedRowKeys });
                                        message.success("删除成功");
                                        onCleanSelected();
                                        actionRef.current?.reload();
                                    } catch (error) {}
                                }}
                            >
                                <a>批量删除</a>
                            </PopConfirmDelete>
                        </HasOperation>
                    </Space>
                )}
            />
        </PageContainer>
    );
}
/**
 * 将接口返回的数据格式化为ProList/ProTable需要的格式
 */
export function fetchWidthNormalizedResponse<P>(
    fetchFunc: (params: P) => Promise<API.ResponseStructure<API.PaginationResponse>>,
) {
    return async (params: P) => {
        const data = await fetchFunc(params);

        return {
            data: data.result?.list ?? (data.result as unknown as unknown[]),
            success: data.msg === "success",
            total: data.result?.totalCount,
        };
    };
}
const metas: ProListMetas<API.FetchArticleResponse> = {
    title: {
        dataIndex: "title",
    },
    avatar: {
        render(_, entity) {
            return (
                <Image
                    width={160}
                    height={90}
                    src={entity.cover}
                    alt={entity.title}
                    fallback="/admin/notFound.png"
                />
            );
        },
    },
    description: {
        dataIndex: "description",
    },
    actions: {},
};
