import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import PopImage from "@/components/ui/popImage/PopImage";
import { BlogrollEnums } from "@/config/enums";
import { useModelVisionModalForm } from "@/hooks/props";
import { API } from "@/services/ant-design-pro/typings";
import { deleteBlogroll, fetchBlogroll, insertBlogroll, updateBlogroll } from "@/services/api";
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
import EditBlogrollForm from "./components/EditBlogrollForm";
type BlogrollProps = HTMLAttributes<HTMLDivElement>;
// 评论列表
export default function Blogroll(props: BlogrollProps): ReactElement {
  const { ...rest } = props;
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const actionRef = useRef<ActionType>();
  const [initialValues, setInitialValues] = useState<API.UpdateBlogrollBody>();

  // 友链图片
  columns[4].render = (dom, entity) => <PopImage src={entity.photo} />;
  // 创建时间
  columns[6].render = (dom, entity) => timestampToTime(Date.parse(entity.createTime));
  // 操作
  columns[7].render = (dom, entity) => (
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
              await deleteBlogroll({ ids: [entity.id] });
              message.success("删除成功");
              actionRef.current?.reload();
            } catch (error) {}
          }}
        />
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
      <ProTable<API.FetchBlogrollResponse, API.PaginationResponse>
        columns={columns}
        rowKey="id"
        defaultSize="small"
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        actionRef={actionRef}
        request={fetchWidthNormalizedResponse((params: any) =>
          // 添加location默认值
          // fetchBlogroll({ ...params, location: params.location ?? BlogrollEnums.Location.首页 }),
          fetchBlogroll(params),
        )}
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
        tableAlertOptionRender={({ selectedRowKeys }) => {
          return (
            <HasOperation operation={OPERATIONS.DELETE}>
              <PopConfirmDelete
                onConfirm={async () => {
                  try {
                    await deleteBlogroll({ ids: selectedRowKeys });
                    message.success("删除成功");
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
        title="编辑友链"
        {...modalVisionProps}
        /* 下面两个属性，为了更新initialValues */
        modalProps={{
          destroyOnClose: true,
        }}
        preserve={false}
        initialValues={initialValues}
        onFinish={async (formData: API.InsertBlogrollBody | API.UpdateBlogrollBody) => {
          // 新增
          if (!initialValues) {
            await insertBlogroll({
              ...formData,
              // @ts-ignore
              parentId: formData.parentId ?? 0,
            } as API.InsertBlogrollBody);
            message.success("新增成功");
          } else {
            // 更改
            await updateBlogroll(formData as API.UpdateBlogrollBody);
            message.success("更新成功");
          }
          actionRef.current?.reload();
          return true;
        }}
      >
        <EditBlogrollForm isUpdateForm={!!initialValues} />
      </ModalForm>
    </PageContainer>
  );
}

export const locationOptions = halfStart(
  map(BlogrollEnums.Location, (value, key) => ({
    label: value,
    value: parseInt(key),
  })),
);

const columns: ProColumns<API.FetchBlogrollResponse>[] = [
  {
    title: "友链标题",
    dataIndex: "title",
    width: 100,
    tip: "友链标题过长会自动收缩",
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: "友链描述",
    dataIndex: "description",
    ellipsis: true,
    width: 150,
    tip: "友链描述过长会自动收缩",
    hideInSearch: true,
  },
  {
    title: "友链链接",
    dataIndex: "link",
    width: 100,
    hideInSearch: true,
    ellipsis: true,
    tip: "友链链接过长会自动收缩",
  },
  {
    title: "友链状态",
    dataIndex: "location",
    width: 100,
    // 过滤条件表单设置
    valueType: "select",
    fieldProps: {
      options: locationOptions,
      // defaultValue: BlogrollEnums.Location.首页,
      allowClear: false,
    },
    hideInTable: true,
  },
  {
    title: "友链图片",
    dataIndex: "photo",
    width: 100,
    ellipsis: true,
    tip: "图片过长会自动收缩",
    hideInSearch: true,
  },
  {
    title: "状态",
    dataIndex: "location",
    width: 50,
    valueEnum: {
      1: { text: "首页", status: "" },
      2: { text: "内页", status: "" },
      3: { text: "失效", status: "Error" },
    },
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    width: 120,
    hideInSearch: true,
  },
  {
    title: "操作",
    width: 50,
    // 这个属性使表单编辑时显示保存删除取消按钮
    valueType: "option",
    hideInSearch: true,
  },
];
