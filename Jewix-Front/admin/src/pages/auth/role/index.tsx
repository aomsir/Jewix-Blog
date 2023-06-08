import { PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';
import { HTMLAttributes, ReactElement } from 'react';
import css from './AuthRole.module.scss';
type AuthRoleProps = HTMLAttributes<HTMLDivElement>;
export default function AuthRole(props: AuthRoleProps): ReactElement {
  const { ...rest } = props;
  return (
    <PageContainer className={`${rest.className ?? ''} ${css.AuthRole}`} {...rest}>
      <ProTable search={false} columns={columns} />
    </PageContainer>
  );
}
const columns: ProColumns<any>[] = [
  {
    title: '角色名',
    dataIndex: 'name',
  },
  {
    title: '权限标签',
    dataIndex: 'tag',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '操作',
  },
];
