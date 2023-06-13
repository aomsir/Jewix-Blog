import IconRender from "@/components/ui/iconRender/IconRender";
import { fetchWidthNormalizedResponse } from "@/pages/article";
import { API } from "@/services/ant-design-pro/typings";
import { fetchMenuItems } from "@/services/api/menu";
import { timestampToTime } from "@/utils";
import { PageContainer, ProColumns, ProTable } from "@ant-design/pro-components";
import { HTMLAttributes, ReactElement } from "react";
import css from "./AuthMenu.module.scss";
type AuthMenuProps = HTMLAttributes<HTMLDivElement>;
export default function AuthMenu(props: AuthMenuProps): ReactElement {
  const { ...rest } = props;

  return (
    <PageContainer className={`${rest.className ?? ""} ${css.AuthMenu}`} {...rest}>
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
          childrenColumnName: "sonList",
        }}
        request={fetchWidthNormalizedResponse(fetchMenuItems)}
      />
    </PageContainer>
  );
}

const columns: ProColumns<API.FetchMenuResponse>[] = [
  {
    title: "菜单名称",
    dataIndex: "name",
  },
  {
    title: "图标",
    dataIndex: "iconName",
    render(dom, entity) {
      return IconRender({ name: entity.iconName });
    },
  },
  {
    title: "访问路径",
    dataIndex: "path",
  },
  {
    title: "组件路径",
    dataIndex: "componentPath",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    renderText: (text) => timestampToTime(Date.parse(text)),
  },
];
