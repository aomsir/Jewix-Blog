import { ProFormText } from '@ant-design/pro-components';
import { ReactElement } from 'react';
import { HTMLAttributes } from 'react';
type EditUserFormProps = HTMLAttributes<HTMLDivElement> & {
  isUpdateForm?: boolean;
};
export default function EditUserForm(props: EditUserFormProps): ReactElement {
  const { isUpdateForm, ...rest } = props;

  return (
    <div className={rest.className ?? ''} {...rest}>
      <ProFormText label="邮箱" name="email" rules={[{ required: true }]} />
      <ProFormText label="名称" name="nickname" rules={[{ required: true }]} />
      <ProFormText.Password label="密码" name="password" rules={[{ required: !isUpdateForm }]} />
      <ProFormText label="描述" name="description" rules={[{ required: true }]} />
      <ProFormText
        label="个人网站"
        name="webSite"
        placeholder="以http/https开头"
        rules={[{ required: true }]}
      />
      {isUpdateForm && (
        <div hidden>
          <ProFormText name="id" />
          <ProFormText name="uuid" />
        </div>
      )}
    </div>
  );
}
