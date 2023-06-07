import PopConfirmDelete from '@/components/bases/popConfirmDelete/PopConfirmDelete';
import { PageEnums } from '@/config/enums';
import { useModelVisionModalForm } from '@/hooks/props';
import { API } from '@/services/ant-design-pro/typings';
import { deletePage, fetchPages, insertPage, updatePage } from '@/services/api/page';
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
import EditPageForm from './components/EditPageForm';
import css from './index.module.scss';

type PageProps = HTMLAttributes<HTMLDivElement>;
export default function Page(props: PageProps): ReactElement {
  const { ...rest } = props;
  const actionRef = useRef<ActionType>();
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const [initialValues, setInitialValues] = useState<API.FetchPagesResponse>();
  // 清空初始值
  useEffect(() => {
    if (!modalVisionState.open) {
      setInitialValues(undefined);
    }
  }, [modalVisionState.open]);

  // 操作
  columns[5].render = (dom, entity) => (
    <Space>
      <a
        onClick={async () => {
          setInitialValues(entity);
          modalVisionState.setOpen(true);
        }}
      >
        编辑
      </a>
      {entity.type === PageEnums.Type.通用模板 && (
        <PopConfirmDelete
          onConfirm={async () => {
            try {
              await deletePage({ uuid: entity.uuid });
              message.success('删除成功');
              actionRef.current?.reload();
            } catch (error) {}
          }}
        />
      )}
    </Space>
  );

  return (
    <PageContainer className={`${rest.className ?? ''}`} {...rest}>
      <ProTable<API.FetchPagesResponse, API.PaginationResponse>
        rowKey="uuid"
        defaultSize="small"
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        actionRef={actionRef}
        request={fetchWidthNormalizedResponse(fetchPages)}
        columns={columns}
        search={false}
        toolBarRender={() => [
          <ProTableToolBar key={1} onInsertButtonClick={() => modalVisionState.setOpen(true)} />,
        ]}
      />
      <ModalForm
        title="编辑页面"
        preserve={false}
        {...modalVisionProps}
        layout="horizontal"
        labelAlign="right"
        modalProps={{ className: css.editPageDialog, destroyOnClose: true }}
        initialValues={initialValues}
        submitter={{
          searchConfig: {
            submitText: '确定',
            resetText: '取消',
          },
        }}
        onFinish={async (values: API.InsertPageBody) => {
          try {
            if (initialValues) {
              // 编辑
              await updatePage({ ...(values as unknown as API.UpdatePageBody) });
              message.success('更新成功');
            } else {
              // 新增
              await insertPage({ ...values });
              message.success('添加成功');
            }
            actionRef.current?.reload();
            return true;
          } catch (error) {}
        }}
      >
        <EditPageForm initialValues={initialValues} />
      </ModalForm>
    </PageContainer>
  );
}

const columns: ProColumns<API.FetchPagesResponse>[] = [
  {
    title: '标题',
    width: 150,
    dataIndex: 'title',
  },
  {
    title: '路径',
    width: 150,
    dataIndex: 'omit',
  },
  {
    title: '描述',
    dataIndex: 'description',
    width: 400,
    tip: '描述过长会自动收缩',
    ellipsis: true,
  },
  {
    title: '类型',
    dataIndex: 'type',
    width: 150,
    renderText: (text) => PageEnums.Type[text],
  },
  {
    title: '创建时间',
    width: 150,
    dataIndex: 'createTime',
    renderText: (text) => timestampToTime(Date.parse(text)),
  },
  {
    width: 100,
    title: '操作',
  },
];
