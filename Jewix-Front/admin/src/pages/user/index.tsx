import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { UserEnums } from "@/config/enums";
import { useModelVisionModalForm } from "@/hooks/props";
import { API } from "@/services/ant-design-pro/typings";
import {
  deleteUsers,
  fetchUsers,
  insertUser,
  updateUser,
  updateUserStatus,
} from "@/services/api/user";
import { halfStart } from "@/utils/array";
import { timestampToTime } from "@/utils/convert";
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProColumns,
  ProTable,
} from "@ant-design/pro-components";
import { message, Space, Table } from "antd";
import { map } from "lodash";
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from "react";
import { fetchWidthNormalizedResponse } from "../article";
import ProTableToolBar from "../article/components/ProTableToolBar";
import EditUserForm from "./components/EditUserForm";
type UserProps = HTMLAttributes<HTMLDivElement>;
export default function User(props: UserProps): ReactElement {
  const { ...rest } = props;
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const actionRef = useRef<ActionType>();
  const [initialValues, setInitialValues] = useState<API.UpdateUserParams>();

  // columns[3].filter = (dom, entity) => <>操作</>;
  // 创建时间
  columns[5].render = (dom, entity) => timestampToTime(Date.parse(entity.createTime));
  // 渲染操作列
  columns[6].render = (dom, entity) => (
    <Space>
      <HasOperation operation={OPERATIONS.UPDATE}>
        <a
          onClick={() => {
            modalVisionState.setOpen(true);
            setInitialValues(entity);
          }}
        >
          编辑
        </a>
      </HasOperation>
      <HasOperation operation={OPERATIONS.DELETE}>
        <PopConfirmDelete
          onConfirm={async () => {
            try {
              await deleteUsers({ ids: [entity.id] }, entity.status);
              message.success("删除成功");
              actionRef.current?.reload();
            } catch (error) {}
          }}
        />
      </HasOperation>
      <HasOperation operation={OPERATIONS.UPDATE_STATUS}>
        {entity.status === UserEnums.Status.正常 ? (
          <a
            style={{ color: "red" }}
            onClick={async () => {
              try {
                await updateUserStatus({ uuid: entity.uuid, status: UserEnums.Status.禁用 });
                message.success("禁用成功");
                actionRef.current?.reload();
              } catch (error) {}
            }}
          >
            禁用
          </a>
        ) : (
          <a
            style={{ color: "green" }}
            onClick={async () => {
              await updateUserStatus({ uuid: entity.uuid, status: UserEnums.Status.正常 });
              message.success("启用成功");
              actionRef.current?.reload();
            }}
          >
            启用
          </a>
        )}
      </HasOperation>
    </Space>
  );

  useEffect(() => {
    if (!modalVisionState.open) {
      setInitialValues(undefined);
    }
  }, [modalVisionState.open]);
  return (
    <PageContainer className={rest.className ?? ""} {...rest}>
      <ProTable<API.FetchUserResponse, API.PaginationResponse>
        actionRef={actionRef}
        columns={columns}
        rowKey="uuid"
        defaultSize="small"
        pagination={{ defaultPageSize: 10, showSizeChanger: true }}
        request={fetchWidthNormalizedResponse(fetchUsers as any)}
        toolBarRender={() => [
          <ProTableToolBar key={1} onInsertButtonClick={() => modalVisionState.setOpen(true)} />,
        ]}
        // 批量操作
        rowSelection={{
          // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
          // 注释该行则默认不显示下拉选项
          selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
        }}
        // 批量删除
        tableAlertOptionRender={({ selectedRowKeys, selectedRows }) => {
          return (
            <HasOperation operation={OPERATIONS.DELETE}>
              <PopConfirmDelete
                onConfirm={async () => {
                  try {
                    for (const item of selectedRows) {
                      await deleteUsers({ ids: [item.id] }, item.status);
                    }
                    message.success("删除成功");
                    actionRef.current?.reload();
                  } catch (error) {}
                }}
              />
            </HasOperation>
          );
        }}
      ></ProTable>
      <ModalForm
        title="新增用户"
        {...modalVisionProps}
        /* 下面两个属性，为了更新initialValues */
        modalProps={{
          destroyOnClose: true,
        }}
        preserve={false}
        initialValues={initialValues}
        onFinish={async (formData: API.InsertUserParams | API.UpdateUserParams) => {
          if (!initialValues) {
            await insertUser(formData as API.InsertUserParams);
            message.success("新增成功");
          } else {
            await updateUser(formData as API.UpdateUserParams);
            message.success("更新成功");
          }
          actionRef.current?.reload();
          return true;
        }}
      >
        <EditUserForm isUpdateForm={!!initialValues} />
      </ModalForm>
    </PageContainer>
  );
}

const columns: ProColumns<API.FetchUserResponse>[] = [
  {
    title: "昵称",
    dataIndex: "nickname",
    hideInSearch: true,
  },
  {
    title: "邮箱",
    dataIndex: "email",
  },
  {
    title: "描述",
    dataIndex: "description",
    hideInSearch: true,
  },
  // TODO 角色渲染待实现，没有返回数据
  {
    title: "角色",
    dataIndex: "roles",
    hideInSearch: true,
    hideInTable: true,
  },
  {
    title: "状态",
    dataIndex: "status",
    order: 1,
    valueEnum: {
      0: { text: "未验证", status: "Processing" },
      1: { text: "正常", status: "Success" },
      2: { text: "禁用", status: "Warning" },
      3: { text: "删除", status: "Error" },
    },
    // 过滤条件表单设置
    valueType: "select",
    fieldProps: {
      options: halfStart(
        map(UserEnums.Status, (value, key) => ({
          label: value,
          value: parseInt(key),
        })),
      ),
      allowClear: false,
    },
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    hideInSearch: true,
  },
  {
    title: "操作",
    hideInSearch: true,
  },
];
