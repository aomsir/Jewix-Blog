import { ProFormText } from '@ant-design/pro-components';
import { ReactElement } from 'react';
import { HTMLAttributes } from 'react';
type EditCategoryFormProps = HTMLAttributes<HTMLDivElement> & {
  isUpdateForm?: boolean;
};
export default function EditCategoryForm(props: EditCategoryFormProps): ReactElement {
  const { isUpdateForm, ...rest } = props;

  return (
    <>
      <ProFormText label="标签名" name="tagName" rules={[{ required: true }]} />
      {isUpdateForm && (
        <div hidden>
          <ProFormText name="id" rules={[{ required: true }]} />
        </div>
      )}
    </>
  );
}
