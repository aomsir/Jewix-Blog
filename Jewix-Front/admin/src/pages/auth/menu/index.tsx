import { PageContainer, ProColumns, ProTable } from '@ant-design/pro-components';
import { HTMLAttributes, ReactElement } from 'react';
import css from './AuthMenu.module.scss';
type AuthMenuProps = HTMLAttributes<HTMLDivElement>;
export default function AuthMenu(props: AuthMenuProps): ReactElement {
  const { ...rest } = props;
  return (
    <PageContainer className={`${rest.className ?? ''} ${css.AuthMenu}`} {...rest}>
      <ProTable search={false} columns={columns} />
    </PageContainer>
  );
}

const columns: ProColumns<any>[] = [
  {
    title: '菜单名称',
    dataIndex: 'name',
  },
  {
    title: '图标',
    dataIndex: 'icon',
  },
  {
    title: '访问路径',
    dataIndex: 'path',
  },
  {
    title: '组件路径',
    dataIndex: 'componentPath',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
];
