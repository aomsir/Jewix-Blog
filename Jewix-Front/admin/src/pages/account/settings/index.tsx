import { API } from '@/services/ant-design-pro/typings';
import { updateCurrentUserInfo } from '@/services/api';
import { PageContainer, ProForm, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import { message } from 'antd';
import { HTMLAttributes, ReactElement } from 'react';
// import css from './AccountSettings.module.scss';
type AccountSettingsProps = HTMLAttributes<HTMLDivElement>;
export default function AccountSettings(props: AccountSettingsProps): ReactElement {
  const { ...rest } = props;
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState || {};

  return (
    <PageContainer className={`${rest.className ?? ''} `} {...rest}>
      <ProForm
        initialValues={currentUser}
        onFinish={async (formData: API.UpdateUserDetailBody) => {
          try {
            await updateCurrentUserInfo(formData);
            message.success('修改成功');
          } catch (error) {}
        }}
      >
        <ProFormText name="nickname" label="昵称" rules={[{ required: true }]} />
        <ProFormTextArea name="description" label="描述" rules={[{ required: true }]} />
        <ProFormText name="email" label="邮箱" rules={[{ required: true }]} />
        {/* <ProFormText.Password name="password" label="密码" rules={[{ required: true }]} /> */}
        <ProFormText name="webSite" label="个人链接" rules={[{ required: true }]} />
        <ProFormText hidden name="uuid" />
        <ProFormText hidden name="id" />
      </ProForm>
    </PageContainer>
  );
}
