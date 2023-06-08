import { PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';
import { HTMLAttributes, ReactElement } from 'react';
import css from './AuthResource.module.scss';
type AuthResourceProps = HTMLAttributes<HTMLDivElement>;
export default function AuthAuthResource(props: AuthResourceProps): ReactElement {
  const { ...rest } = props;
  return (
    <PageContainer className={`${rest.className ?? ''} ${css.AuthResource}`} {...rest}>
      <ProTable search={false} columns={columns} />
    </PageContainer>
  );
}
const columns: ProColumns<any>[] = [
  {
    title: '资源名',
    dataIndex: 'name',
  },
  {
    title: '资源路径',
    dataIndex: 'path',
  },
  {
    title: '请求方式',
    dataIndex: 'method',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
];
