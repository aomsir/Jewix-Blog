import { ProFormSelect, ProFormText, ProFormTextArea, ProForm } from '@ant-design/pro-components';
import { ReactElement } from 'react';
import { HTMLAttributes } from 'react';
import { statusOptions } from '..';
type EditCategoryFormProps = HTMLAttributes<HTMLDivElement> & {
  isUpdateForm?: boolean;
};
export default function EditCategoryForm(props: EditCategoryFormProps): ReactElement {
  const { isUpdateForm, ...rest } = props;

  return (
    <>
      <ProForm.Group>
        <ProFormText label="作者昵称" name="author" rules={[{ required: true }]} />
        <ProFormText label="邮箱" name="email" />
        <ProFormText label="个人链接" name="url" />
        <ProFormSelect
          label="状态"
          name="status"
          rules={[{ required: true }]}
          options={statusOptions}
        />
      </ProForm.Group>
      <ProFormTextArea label="评论内容" name="content" rules={[{ required: true }]} />
      <div hidden>
        <ProFormText name="id" rules={[{ required: true }]} />
      </div>
    </>
  );
}
