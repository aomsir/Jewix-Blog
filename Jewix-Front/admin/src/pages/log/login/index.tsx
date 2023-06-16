import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { useSelection } from "@/hooks/props";
import { fetchWidthNormalizedResponse } from "@/pages/article";
import { API } from "@/services/ant-design-pro/typings";
import { deleteLogLogins, fetchLogLogin } from "@/services/api/log";
import { timestampToTime } from "@/utils";
import { ActionType, PageContainer, ProColumns, ProTable } from "@ant-design/pro-components";
import { message, Space } from "antd";
import { HTMLAttributes, ReactElement, useRef } from "react";
type LogLoginProps = HTMLAttributes<HTMLDivElement>;
export default function LogLogin(props: LogLoginProps): ReactElement {
  const { ...rest } = props;
  const actionRef = useRef<ActionType>();
  const [selectionState, selectionProps] = useSelection();
  // 渲染操作列
  columns[5].render = (dom, entity) => (
    <Space>
      <HasOperation operation={"删除数据"} routerProps={{ name: "日志管理" }}>
        <PopConfirmDelete
          onConfirm={async () => {
            try {
              await deleteLogLogins({ ids: [entity.id] });
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
        request={fetchWidthNormalizedResponse(fetchLogLogin)}
        actionRef={actionRef}
        // 批量操作
        rowSelection={selectionProps}
        //批量删除
        tableAlertRender={({ selectedRowKeys, onCleanSelected }) => (
          <Space size={24}>
            <span>已选择 {selectedRowKeys.length} 项</span>
            <HasOperation operation={"删除数据"} routerProps={{ name: "日志管理" }}>
              <PopConfirmDelete
                onConfirm={async () => {
                  try {
                    await deleteLogLogins({ ids: selectedRowKeys });
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
    </PageContainer>
  );
}

const columns: ProColumns<API.FetchLogLoginResponse>[] = [
  {
    title: "用户名",
    dataIndex: "nickname",
  },
  {
    title: "用户ID",
    dataIndex: "userId",
  },
  {
    title: "IP地址",
    dataIndex: "ip",
  },
  {
    title: "地址",
    dataIndex: "location",
  },
  {
    title: "操作时间",
    dataIndex: "operateTime",
    renderText: (text) => timestampToTime(Date.parse(text)),
  },
  {
    title: "操作",
  },
];
