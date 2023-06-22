import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { CommentEnums } from "@/config/enums";
import { useModelVisionModalForm } from "@/hooks/props";
import { API } from "@/services/ant-design-pro/typings";
import {
    deleteComment,
    fetchComments,
    insertComment,
    updateComment,
    updateCommentStatus,
} from "@/services/api";
import { halfStart } from "@/utils/array";
import { getAvatarUrlByEmail } from "@/utils/avatar";
import { timestampToTime } from "@/utils/convert";
import { getIcon } from "@/utils/icon";
import {
    ActionType,
    ModalForm,
    PageContainer,
    ProList,
    ProListMetas,
} from "@ant-design/pro-components";
import { Image, message, Space, Table } from "antd";
import { map } from "lodash";
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from "react";
import { fetchWidthNormalizedResponse } from "../article";
import EditCommentForm from "./components/EditCommentForm";
import { tableConfig } from "/config/table";
type CommentProps = HTMLAttributes<HTMLDivElement>;
// 评论列表
export default function Comment(props: CommentProps): ReactElement {
    const { ...rest } = props;
    const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
    const actionRef = useRef<ActionType>();
    const [initialValues, setInitialValues] = useState<API.UpdateCommentParams>();

    metas.content!.render = (_dom, entity, _index, _action, _schema) => {
        return (
            <>
                <p>
                    {timestampToTime(Date.parse(entity.createTime))}&nbsp;于&nbsp;
                    <span style={{ color: "rgb(134, 160, 175)", fontWeight: "bold" }}>
                        {CommentEnums.Type[entity.type]}
                    </span>
                </p>
                <p>{entity.content}</p>
                <Space>
                    {entity.status !== CommentEnums.Status.开放 ? (
                        <a
                            onClick={async () => {
                                try {
                                    await updateCommentStatus({
                                        id: entity.id,
                                        status: CommentEnums.Status.开放,
                                    });
                                    message.success("修改成功");
                                    actionRef.current?.reload();
                                } catch (error) {}
                            }}
                        >
                            通过
                        </a>
                    ) : (
                        <span>通过</span>
                    )}
                    {entity.status !== CommentEnums.Status.待审核 ? (
                        <a
                            onClick={async () => {
                                try {
                                    await updateCommentStatus({
                                        id: entity.id,
                                        status: CommentEnums.Status.待审核,
                                    });
                                    message.success("修改成功");
                                    actionRef.current?.reload();
                                } catch (error) {}
                            }}
                        >
                            待审核
                        </a>
                    ) : (
                        <span>待审核</span>
                    )}
                    {entity.status !== CommentEnums.Status.垃圾 ? (
                        <a
                            onClick={async () => {
                                try {
                                    await updateCommentStatus({
                                        id: entity.id,
                                        status: CommentEnums.Status.垃圾,
                                    });
                                    message.success("修改成功");
                                    actionRef.current?.reload();
                                } catch (error) {}
                            }}
                        >
                            垃圾
                        </a>
                    ) : (
                        <span>垃圾</span>
                    )}
                    <HasOperation operation={OPERATIONS.UPDATE}>
                        <a
                            onClick={() => {
                                modalVisionState.setOpen(true);
                                setInitialValues(entity);
                            }}
                            style={{ color: "green" }}
                        >
                            编辑
                        </a>
                    </HasOperation>
                    <HasOperation operation={OPERATIONS.DELETE}>
                        <PopConfirmDelete
                            onConfirm={async () => {
                                try {
                                    await deleteComment({ ids: [entity.id] });
                                    message.success("删除成功");
                                    actionRef.current?.reload();
                                } catch (error) {}
                            }}
                        >
                            <a style={{ color: "red" }}>删除</a>
                        </PopConfirmDelete>
                    </HasOperation>
                </Space>
            </>
        );
    };

    useEffect(() => {
        if (!modalVisionState.open) {
            setInitialValues(undefined);
        }
    }, [modalVisionState.open]);

    return (
        <PageContainer className={rest.className ?? ""} {...rest}>
            <ProList<API.FetchCommentResponse, API.PaginationResponse>
                headerTitle="评论列表"
                metas={metas}
                pagination={{
                    defaultPageSize: 10,
                    showSizeChanger: true,
                    pageSizeOptions: tableConfig.pageSizes,
                }}
                actionRef={actionRef}
                request={fetchWidthNormalizedResponse(fetchComments)}
                // 批量操作
                rowSelection={{
                    // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
                    // 注释该行则默认不显示下拉选项
                    selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
                }}
                // 批量删除
                tableAlertOptionRender={({ selectedRowKeys }) => {
                    return (
                        <HasOperation operation={OPERATIONS.DELETE}>
                            <PopConfirmDelete
                                onConfirm={async () => {
                                    try {
                                        await deleteComment({ ids: selectedRowKeys });
                                        actionRef.current?.reload();
                                    } catch (error) {}
                                }}
                            />
                        </HasOperation>
                    );
                }}
            />
            <ModalForm
                width={800}
                title="编辑评论"
                {...modalVisionProps}
                /* 下面两个属性，为了更新initialValues */
                modalProps={{
                    destroyOnClose: true,
                }}
                preserve={false}
                initialValues={initialValues}
                onFinish={async (formData: API.InsertCommentParams | API.UpdateCommentParams) => {
                    // 新增
                    if (!initialValues) {
                        await insertComment({
                            ...formData,
                            // @ts-ignore
                            parentId: formData.parentId ?? 0,
                        } as API.InsertCommentParams);
                        message.success("新增成功");
                    } else {
                        // 更改
                        await updateComment(formData as API.UpdateCommentParams);
                        message.success("更新成功");
                    }
                    actionRef.current?.reload();
                    return true;
                }}
            >
                <EditCommentForm isUpdateForm={!!initialValues} />
            </ModalForm>
        </PageContainer>
    );
}

export const statusOptions = halfStart(
    map(CommentEnums.Status, (value, key) => ({
        label: value,
        value: parseInt(key),
    })),
);
const metas: ProListMetas<API.FetchCommentResponse> = {
    title: {
        dataIndex: "author",
    },
    avatar: {
        render(dom, entity, index, action, schema) {
            return (
                <Image
                    width={40}
                    height={40}
                    src={getAvatarUrlByEmail(entity.email)}
                    fallback="/admin/notFound.png"
                    style={{ borderRadius: "50%" }}
                />
            );
        },
    },
    subTitle: {
        render(dom, entity, index, action, schema) {
            const agent = JSON.parse(entity.agent);
            return (
                <Space>
                    {getIcon(agent.browser, "browser")}
                    {getIcon(agent.os, "os")}
                </Space>
            );
        },
    },
    description: {
        render(dom, entity, index, action, schema) {
            return (
                <>
                    <span style={{ color: "rgb(134, 160, 175)" }}>{entity.email}</span>
                    <br />
                    <span>{entity.ip}</span>
                </>
            );
        },
    },
    content: {},
};
