import { useModelVisionModalForm } from '@/hooks/props';
import { API } from '@/services/ant-design-pro/typings';
import { fetchTags, insertTag, updateTag } from '@/services/api';
import { timestampToTime } from '@/utils';
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import { message, Space } from 'antd';
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from 'react';
import { fetchWidthNormalizedResponse } from '../article';
import ProTableToolBar from '../article/components/ProTableToolBar';
import EditForm from './components/EditForm';
// import css from "./Tag.module.scss"
type TagProps = HTMLAttributes<HTMLDivElement>;
export default function Tag(props: TagProps): ReactElement {
  const { ...rest } = props;
  const actionRef = useRef<ActionType>();
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const [initialValues, setInitialValues] = useState<API.UpdateTagBody>();

  columns[2].render = (dom, entity) => (
    <Space>
      <a
        onClick={() => {
          modalVisionState.setOpen(true);
          setInitialValues(entity);
        }}
      >
        编辑
      </a>
      {/* <PopConfirmDelete
        onConfirm={async () => {
          try {
            await deleteUsers({ ids: [entity.id] });
            message.success('删除成功');
            actionRef.current?.reload();
          } catch (error) {}
        }}
      /> */}
    </Space>
  );

  useEffect(() => {
    if (!modalVisionState.open) {
      setInitialValues(undefined);
    }
  }, [modalVisionState.open]);

  return (
    <div className={`${rest.className ?? ''}`} {...rest}>
      <PageContainer
        // 标签页
        {...rest}
      >
        <ProTable<API.FetchTagResponse, API.PaginationResponse>
          search={false}
          columns={columns}
          rowKey="id"
          defaultSize="small"
          pagination={{
            defaultPageSize: 10,
            showSizeChanger: true,
          }}
          actionRef={actionRef}
          request={fetchWidthNormalizedResponse(fetchTags)}
          toolBarRender={() => [
            <ProTableToolBar key={1} onInsertButtonClick={() => modalVisionState.setOpen(true)} />,
          ]}
        />
        <ModalForm
          width={400}
          title="编辑标签"
          {...modalVisionProps}
          initialValues={initialValues}
          preserve={false}
          modalProps={{
            destroyOnClose: true,
          }}
          onFinish={async (values: API.InsertTagBody | API.UpdateTagBody) => {
            if (!initialValues) {
              await insertTag(values as API.InsertTagBody);
              message.success('新增成功');
            } else {
              await updateTag(values as API.UpdateTagBody);
              message.success('更新成功');
            }
            actionRef.current?.reload();
            return true;
          }}
        >
          <EditForm isUpdateForm={!!initialValues} />
        </ModalForm>
      </PageContainer>
    </div>
  );
}

const columns: ProColumns<API.FetchTagResponse>[] = [
  {
    title: '标签名',
    dataIndex: 'tagName',
    hideInSearch: true,
    width: 100,
  },
  // {
  //   title: '状态',
  //   dataIndex: 'status',
  //   hideInSearch: true,
  // },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    hideInSearch: true,
    width: 100,
    renderText: (text) => timestampToTime(Date.parse(text)),
  },
  {
    title: '操作',
    hideInSearch: true,
    width: 20,
  },
];
