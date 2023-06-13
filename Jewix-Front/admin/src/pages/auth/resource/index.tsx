import { fetchWidthNormalizedResponse } from "@/pages/article";
import { fetchResources } from "@/services/api/resource";
import { timestampToTime } from "@/utils";
import { PageContainer, ProColumns, ProTable } from "@ant-design/pro-components";
import { HTMLAttributes, ReactElement } from "react";
import css from "./AuthResource.module.scss";
type AuthResourceProps = HTMLAttributes<HTMLDivElement>;
export default function AuthAuthResource(props: AuthResourceProps): ReactElement {
  const { ...rest } = props;
  return (
    <PageContainer className={`${rest.className ?? ""} ${css.AuthResource}`} {...rest}>
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
        request={fetchWidthNormalizedResponse(fetchResources)}
      />
    </PageContainer>
  );
}
const columns: ProColumns<any>[] = [
  {
    title: "资源名",
    dataIndex: "name",
  },
  {
    title: "权限标签",
    dataIndex: "label",
    width: 400,
  },
  {
    title: "资源路径",
    dataIndex: "route",
  },
  {
    title: "请求方式",
    dataIndex: "method",
    renderText: (text) => (text === "NULL" ? "" : text),
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    renderText: (text) => timestampToTime(Date.parse(text)),
  },
];
