import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { MAPPING } from "@/config/mapping";
import { useSelection } from "@/hooks/props";
import { fetchWidthNormalizedResponse } from "@/pages/article";
import { API } from "@/services/ant-design-pro/typings";
import { deleteLogOperation, fetchLogOperation } from "@/services/api/log";
import { timestampToTime } from "@/utils";
import { ActionType, PageContainer, ProColumns, ProTable } from "@ant-design/pro-components";
import { Drawer, message, Space, Tag } from "antd";
import { HTMLAttributes, ReactElement, useRef, useState } from "react";
import LogOperationDetail from "./components/detail";
import { tableConfig } from "/config/table";
type LogOperationProps = HTMLAttributes<HTMLDivElement>;
export default function LogOperation(props: LogOperationProps): ReactElement {
  const { ...rest } = props;
  const actionRef = useRef<ActionType>();
  const [open, setOpen] = useState(false);
  const [detailData, setDetailData] = useState<API.FetchLogOperationResponse>();
  const [selectionState, selectionProps] = useSelection();
  // 渲染操作列
  columns[9].render = (dom, entity) => (
    <Space>
      <HasOperation operation={OPERATIONS.DELETE} routerProps={{ name: "日志管理" }}>
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
      <a onClick={() => (setOpen(true), setDetailData(entity))}>查看</a>
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
          pageSizeOptions: tableConfig.pageSizes,
        }}
        request={fetchWidthNormalizedResponse(fetchLogOperation)}
        actionRef={actionRef}
        // 批量操作
        rowSelection={selectionProps}
        //批量删除
        tableAlertRender={({ selectedRowKeys, onCleanSelected }) => (
          <Space size={24}>
            <span>已选择 {selectedRowKeys.length} 项</span>
            <HasOperation operation={OPERATIONS.DELETE} routerProps={{ name: "日志管理" }}>
              <PopConfirmDelete
                onConfirm={async () => {
                  try {
                    await deleteLogOperation({ ids: selectedRowKeys });
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
      {detailData && (
        <Drawer title="详细信息" placement="right" onClose={() => setOpen(false)} open={open}>
          <LogOperationDetail data={detailData} />
        </Drawer>
      )}
    </PageContainer>
  );
}

const columns: ProColumns<API.FetchLogOperationResponse>[] = [
  {
    title: "用户名",
    dataIndex: "nickname",
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
    title: "操作模块",
    dataIndex: "optModule",
  },
  {
    title: "操作类型",
    dataIndex: "optType",
  },
  {
    title: "请求方法",
    dataIndex: "reqMethod",
    render: (_, record) => (
      // @ts-ignore
      <Tag color={MAPPING.METHOD_COLOR[record.reqMethod]}>{record.reqMethod}</Tag>
    ),
  },
  {
    title: "请求地址",
    dataIndex: "reqUrl",
  },
  {
    title: "操作类型",
    dataIndex: "optType",
  },
  {
    title: "操作时间",
    dataIndex: "optTime",
    renderText: (text) => timestampToTime(Date.parse(text)),
  },
  {
    title: "操作",
  },
];
