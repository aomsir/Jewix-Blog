import UploadImage from '@/components/bases/upload/UploadImage';
import { ProFormSelect, ProFormText, ProFormTextArea, ProForm } from '@ant-design/pro-components';
import { Form } from 'antd';
import { ReactElement } from 'react';
import { HTMLAttributes } from 'react';
import { locationOptions } from '..';
type EditCategoryFormProps = HTMLAttributes<HTMLDivElement> & {
  isUpdateForm?: boolean;
};
export default function EditCategoryForm(props: EditCategoryFormProps): ReactElement {
  const { isUpdateForm, ...rest } = props;

  return (
    <>
      <ProForm.Group>
        <ProFormText label="友链标题" name="title" rules={[{ required: true }]} />
        <ProFormText label="友链链接" name="link" rules={[{ required: true }]} />
        <ProFormText label="友链图片" name="photo" rules={[{ required: true }]} />
        <ProFormSelect
          label="位置"
          name="location"
          rules={[{ required: true }]}
          options={locationOptions}
        />
      </ProForm.Group>
      <ProFormTextArea
        label="友链描述"
        name="description"
        fieldProps={{
          showCount: true,
          maxLength: 50,
        }}
        rules={[{ required: true }]}
      />
      {isUpdateForm && (
        <div hidden>
          <ProFormText name="id" rules={[{ required: true }]} />
        </div>
      )}
    </>
  );
}
