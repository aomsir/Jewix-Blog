import PopConfirmDelete from '@/components/bases/popConfirmDelete/PopConfirmDelete';
import { CommentEnums } from '@/config/enums';
import { useModelVisionModalForm } from '@/hooks/props';
import { API } from '@/services/ant-design-pro/typings';
import {
  deleteComment,
  fetchComments,
  insertComment,
  updateComment,
  updateCommentStatus,
} from '@/services/api';
import { halfStart } from '@/utils/array';
import { timestampToTime } from '@/utils/convert';
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { message, Select, Space, Table } from 'antd';
import { map } from 'lodash';
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from 'react';
import { fetchWidthNormalizedResponse } from '../article';
import EditCommentForm from './components/EditCommentForm';
type CommentProps = HTMLAttributes<HTMLDivElement>;
// 评论列表
export default function Comment(props: CommentProps): ReactElement {
  const { ...rest } = props;
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const actionRef = useRef<ActionType>();
  const [initialValues, setInitialValues] = useState<API.UpdateCommentParams>();

  // 评论类型
  columns[6].render = (dom, entity) => CommentEnums.Type[entity.type] ?? '未知';
  // 评论状态
  columns[7].render = (dom, entity, _, action) => (
    <span style={{ cursor: 'pointer' }} onClick={() => action?.startEditable(entity.id)}>
      {dom}
    </span>
  );
  // 创建时间
  columns[9].render = (dom, entity) => timestampToTime(Date.parse(entity.createTime)); // 渲染操作列
  // 操作
  columns[10].render = (dom, entity) => (
    <Space>
      <a
        onClick={() => {
          modalVisionState.setOpen(true);
          setInitialValues(entity);
        }}
      >
        编辑
      </a>
      <PopConfirmDelete
        onConfirm={async () => {
          try {
            await deleteComment({ ids: [entity.id] });
            message.success('删除成功');
            actionRef.current?.reload();
          } catch (error) {}
        }}
      />
    </Space>
  );

  useEffect(() => {
    if (!modalVisionState.open) {
      setInitialValues(undefined);
    }
  }, [modalVisionState.open]);

  return (
    <PageContainer className={rest.className ?? ''} {...rest}>
      <ProTable<API.FetchCommentResponse, API.PaginationResponse>
        scroll={{ x: 1300 }}
        search={false}
        columns={columns}
        rowKey="id"
        defaultSize="small"
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        actionRef={actionRef}
        request={fetchWidthNormalizedResponse(fetchComments)}
        expandable={{
          expandedRowRender: (record) => <p style={{ margin: 0 }}>{record.content}</p>,
        }}
        // 编辑评论状态
        editable={{
          onSave: async (_, data) => {
            await updateCommentStatus({
              id: data.id,
              status: data.status,
            });
            message.success('更新成功');
            actionRef.current?.reload();
          },
          // 编辑时显示的操作
          actionRender: (row, config, dom) => [dom.save, dom.cancel],
        }}
        // 批量操作
        rowSelection={{
          // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
          // 注释该行则默认不显示下拉选项
          selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
        }}
        // 批量删除
        tableAlertOptionRender={({ selectedRowKeys }) => {
          return <PopConfirmDelete onConfirm={() => deleteComment({ ids: selectedRowKeys })} />;
        }}
      ></ProTable>
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
            message.success('新增成功');
          } else {
            // 更改
            await updateComment(formData as API.UpdateCommentParams);
            message.success('更新成功');
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

const columns: ProColumns<API.FetchCommentResponse>[] = [
  {
    title: '评论者',
    dataIndex: 'author',
    ellipsis: true,
    width: 70,
    tip: '评论者名称过长会自动收缩',
    editable: false,
  },
  Table.EXPAND_COLUMN,
  {
    title: '评论内容',
    dataIndex: 'content',
    ellipsis: true,
    width: 100,
    tip: '评论内容过长会自动收缩',
    editable: false,
  },
  {
    title: '评论者邮箱',
    dataIndex: 'email',
    width: 110,
    editable: false,
  },
  {
    title: '评论者ip',
    dataIndex: 'ip',
    width: 100,
    editable: false,
  },
  {
    title: '评论者地址',
    dataIndex: 'location',
    width: 100,
    editable: false,
  },

  {
    title: '评论类型',
    dataIndex: 'type',
    width: 100,
    editable: false,
  },
  {
    title: '评论状态',
    dataIndex: 'status',
    width: 110,
    valueEnum: {
      0: { text: '待审核', status: 'Processing' },
      1: { text: '开放', status: 'Success' },
      2: { text: '垃圾', status: 'Error' },
    },
    // 默认选择框, 枚举类型的值是字符串 "0","1","2"，需要重写
    renderFormItem() {
      return <Select options={statusOptions} />;
    },
  },
  {
    title: 'UA',
    dataIndex: 'agent',
    width: 300,
    editable: false,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 150,
    editable: false,
  },
  {
    title: '操作',
    width: 100,
    // 这个属性使表单编辑时显示保存删除取消按钮
    valueType: 'option',
    fixed: 'right',
  },
];
