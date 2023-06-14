import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { fetchWidthNormalizedResponse } from "@/pages/article";
import { API } from "@/services/ant-design-pro/typings";
import { deleteLogOperation, fetchLogOperation } from "@/services/api/log";
import { timestampToTime } from "@/utils";
import { ActionType, PageContainer, ProColumns, ProTable } from "@ant-design/pro-components";
import { message, Space } from "antd";
import { HTMLAttributes, ReactElement, useRef } from "react";
type LogOperationProps = HTMLAttributes<HTMLDivElement>;
export default function LogOperation(props: LogOperationProps): ReactElement {
  const { ...rest } = props;
  const actionRef = useRef<ActionType>();
  // 渲染操作列
  columns[3].render = (dom, entity) => (
    <Space>
      <HasOperation operation={OPERATIONS.DELETE}>
        <PopConfirmDelete
          onConfirm={async () => {
            try {
              await deleteLogOperation({ ids: [entity.id] });
              message.success("删除成功");
              actionRef.current?.reload();
            } catch (error) {}
          }}
        />
      </HasOperation>
    </Space>
  );
  return (
    <PageContainer>
      <ProTable
        search={false}
        columns={columns}
        rowKey="id"
        defaultSize="small"
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={fetchWidthNormalizedResponse(fetchLogOperation)}
        actionRef={actionRef}
      />
    </PageContainer>
  );
}

const columns: ProColumns<API.FetchLogOperationResponse>[] = [
  {
    title: "菜单名称",
    dataIndex: "name",
  },
  {
    title: "访问路径",
    dataIndex: "path",
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
