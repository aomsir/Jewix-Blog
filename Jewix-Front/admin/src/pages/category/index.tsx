import PopConfirmDelete from '@/components/bases/popConfirmDelete/PopConfirmDelete';
import { useModelVisionModalForm } from '@/hooks/props';
import { API } from '@/services/ant-design-pro/typings';
import { deleteCategories, fetchCategories, insertCategory } from '@/services/api/category';
import { timestampToTime } from '@/utils/convert';
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { message, Table } from 'antd';
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from 'react';
import { fetchWidthNormalizedResponse } from '../article';
import ProTableToolBar from '../article/components/ProTableToolBar';
import EditCategoryForm from './components/EditCategoryForm';
type CategoryProps = HTMLAttributes<HTMLDivElement>;
export default function Category(props: CategoryProps): ReactElement {
  const { ...rest } = props;
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const actionRef = useRef<ActionType>();
  const [initialValues, setInitialValues] = useState<API.UpdateUserParams>();

  // columns[3].filter = (dom, entity) => <>操作</>;
  // 创建时间
  columns[1].render = (dom, entity) => timestampToTime(Date.parse(entity.createTime));
  // 渲染操作列
  columns[2].render = (dom, entity) => (
    <PopConfirmDelete
      onConfirm={async () => {
        try {
          await deleteCategories({ ids: [entity.id] });
          message.success('删除成功');
          actionRef.current?.reload();
        } catch (error) {}
      }}
    />
  );

  useEffect(() => {
    if (!modalVisionState.open) {
      setInitialValues(undefined);
    }
  }, [modalVisionState.open]);

  return (
    <PageContainer className={rest.className ?? ''} {...rest}>
      <ProTable<API.FetchCategoryResponse, API.PaginationResponse>
        search={false}
        columns={columns}
        rowKey="id"
        defaultSize="small"
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        actionRef={actionRef}
        request={fetchWidthNormalizedResponse(fetchCategories)}
        toolBarRender={() => [
          <ProTableToolBar key={1} onInsertButtonClick={() => modalVisionState.setOpen(true)} />,
        ]}
        expandable={{
          // 如果行数据中存在sonList且有值就是可展开行
          childrenColumnName: 'sonList',
        }}
        // 批量操作
        rowSelection={{
          // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
          // 注释该行则默认不显示下拉选项
          selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
        }}
        // 批量删除
        tableAlertOptionRender={({ selectedRowKeys }) => {
          return (
            <PopConfirmDelete
              onConfirm={async () => {
                try {
                  await deleteCategories({ ids: selectedRowKeys });
                  message.success('删除成功');
                  actionRef.current?.reload();
                } catch (error) {}
              }}
            />
          );
        }}
      ></ProTable>
      <ModalForm
        width={400}
        title="新增分类"
        {...modalVisionProps}
        /* 下面两个属性，为了更新initialValues */
        modalProps={{
          destroyOnClose: true,
        }}
        preserve={false}
        initialValues={initialValues}
        onFinish={async (formData: API.InsertCategoryParams) => {
          // 新增
          if (!initialValues) {
            await insertCategory({
              ...formData,
              parentId: formData.parentId ?? 0,
            } as API.InsertCategoryParams);
            message.success('新增成功');
            actionRef.current?.reload();
          } else {
            // 更改
            // await updateUser(formData as API.UpdateUserParams);
            // message.success('更新成功');
          }
          return true;
        }}
      >
        <EditCategoryForm isUpdateForm={!!initialValues} />
      </ModalForm>
    </PageContainer>
  );
}

const columns: ProColumns<API.FetchCategoryResponse>[] = [
  {
    title: '分类名',
    dataIndex: 'categoryName',
  },
  // {
  //   title: '状态',
  //   dataIndex: 'status',
  // },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '操作',
  },
];
