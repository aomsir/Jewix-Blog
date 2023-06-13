import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { useModelVisionModalForm, useSelection } from "@/hooks/props";
import { fetchWidthNormalizedResponse } from "@/pages/article";
import ProTableToolBar from "@/pages/article/components/ProTableToolBar";
import { API } from "@/services/ant-design-pro/typings";
import { deleteRoles, fetchRoles, insertRole, updateRole } from "@/services/api/role";
import { timestampToTime } from "@/utils";
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProColumns,
  ProTable,
} from "@ant-design/pro-components";
import { message, Space } from "antd";
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from "react";
import css from "./AuthRole.module.scss";
import EditRoleForm from "./components/EditRoleForm";
type AuthRoleProps = HTMLAttributes<HTMLDivElement>;
export default function AuthRole(props: AuthRoleProps): ReactElement {
  const { ...rest } = props;
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const actionRef = useRef<ActionType>();
  const [selectionState, selectionProps] = useSelection();
  const [initialValues, setInitialValues] = useState<API.UpdateRoleBody>();

  // 清空初始值
  useEffect(() => {
    if (!modalVisionState.open) {
      setInitialValues(undefined);
    }
  }, [modalVisionState.open]);

  // 渲染操作列
  columns[3].render = (dom, entity) => (
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
              await deleteRoles({ roleIds: [entity.id] }, entity.status);
              message.success("删除成功");
              actionRef.current?.reload();
            } catch (error) {}
          }}
        />
      </HasOperation>
    </Space>
  );

  return (
    <PageContainer className={`${rest.className ?? ""} ${css.AuthRole}`} {...rest}>
      <ProTable
        search={false}
        columns={columns}
        rowKey="id"
        defaultSize="small"
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        expandable={{
          // 如果行数据中存在sonList且有值就是可展开行
          childrenColumnName: "resourceSons",
        }}
        request={fetchWidthNormalizedResponse(fetchRoles)}
        toolBarRender={() => [
          <ProTableToolBar key={1} onInsertButtonClick={() => modalVisionState.setOpen(true)} />,
        ]}
        actionRef={actionRef}
        // 批量操作
        rowSelection={selectionProps}
        //批量删除
        tableAlertRender={({ selectedRowKeys, onCleanSelected }) => (
          <Space size={24}>
            <span>已选择 {selectedRowKeys.length} 项</span>
            <HasOperation operation={OPERATIONS.DELETE}>
              <PopConfirmDelete
                onConfirm={async () => {
                  try {
                    await deleteRoles({ roleIds: selectedRowKeys });
                    message.success("删除成功");
                    onCleanSelected();
                    actionRef.current?.reload();
                  } catch (error) {}
                }}
              >
                批量删除
              </PopConfirmDelete>
            </HasOperation>
          </Space>
        )}
      />
      <ModalForm
        width={400}
        title="编辑角色"
        {...modalVisionProps}
        /* 下面两个属性，为了更新initialValues */
        modalProps={{
          destroyOnClose: true,
        }}
        preserve={false}
        initialValues={initialValues}
        onFinish={async (formData: API.InsertRoleBody | API.UpdateRoleBody) => {
          // 新增
          if (!initialValues) {
            await insertRole(formData);
            message.success("新增成功");
          } else {
            // 更改
            await updateRole(formData as API.UpdateRoleBody);
            message.success("更新成功");
          }
          actionRef.current?.reload();
          return true;
        }}
      >
        <EditRoleForm />
      </ModalForm>
    </PageContainer>
  );
}
const columns: ProColumns<any>[] = [
  {
    title: "角色名",
    dataIndex: "roleName",
  },
  {
    title: "权限标签",
    dataIndex: "roleLabel",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    renderText: (text) => timestampToTime(Date.parse(text)),
  },
  {
    title: "操作",
  },
];
