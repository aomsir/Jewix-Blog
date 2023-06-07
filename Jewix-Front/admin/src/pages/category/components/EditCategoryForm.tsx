import { useFetch } from '@/hooks/fetch';
import { fetchAllParentCategories } from '@/services/api';
import { ProFormSelect, ProFormText } from '@ant-design/pro-components';
import { HTMLAttributes, ReactElement } from 'react';
type EditCategoryFormProps = HTMLAttributes<HTMLDivElement> & {
  isUpdateForm?: boolean;
};
export default function EditCategoryForm(props: EditCategoryFormProps): ReactElement {
  const { isUpdateForm, ...rest } = props;
  // 所有父级分类
  const [parentCategories, _, reload] = useFetch(fetchAllParentCategories);

  return (
    <div className={rest.className ?? ''} {...rest}>
      <ProFormText label="分类名称" name="categoryName" rules={[{ required: true }]} />
      <ProFormSelect
        placeholder="默认一级分类"
        label="父级父类"
        name="parentId"
        options={
          parentCategories?.map((c) => ({
            label: c.categoryName,
            value: c.id,
          })) ?? []
        }
      />
    </div>
  );
}
