// TODO 等待接口 待实现
import { useModelVisionModalForm } from '@/hooks/props';
import { API } from '@/services/ant-design-pro/typings';
import { fetchAllParentCategories, fetchCategories, insertCategory } from '@/services/api/category';
import { timestampToTime } from '@/utils/convert';
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { message, Space } from 'antd';
import { ReactElement, useEffect, useRef, useState } from 'react';
import { HTMLAttributes } from 'react';
import ProTableToolBar from '../article/components/ProTableToolBar';
import EditRoleForm from './components/EditRoleForm';
import { useFetch } from '@/hooks/fetch';
import { fetchWidthNormalizedResponse } from '../article';
type RoleProps = HTMLAttributes<HTMLDivElement>;
export default function Role(props: RoleProps): ReactElement {
  const { ...rest } = props;
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const actionRef = useRef<ActionType>();
  const [initialValues, setInitialValues] = useState<API.UpdateUserParams>();
  // 所有父级分类
  const [parentCategories, _, reload] = useFetch(fetchAllParentCategories);

  // columns[3].filter = (dom, entity) => <>操作</>;
  // 创建时间
  columns[1].render = (dom, entity) => timestampToTime(Date.parse(entity.createTime));
  // 渲染操作列
  // columns[2].render = (dom, entity) => (
  //   <Space>
  //     <a
  //       onClick={() => {
  //         modalVisionState.setOpen(true);
  //         setInitialValues(entity);
  //       }}
  //     >
  //       编辑
  //     </a>
  //     {<a>删除</a>}
  //   </Space>
  // );

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
        <EditRoleForm
          isUpdateForm={!!initialValues}
          options={
            parentCategories?.map((c) => ({
              label: c.categoryName,
              value: c.id,
            })) ?? []
          }
        />
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
  // {
  //   title: '操作',
  // },
];
