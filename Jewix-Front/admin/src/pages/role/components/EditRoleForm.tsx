import { fetchAllParentCategories } from '@/services/api/category';
import { ProFormSelect, ProFormText } from '@ant-design/pro-components';
import { DefaultOptionType, OptionProps } from 'antd/es/select';
import { ReactElement, useEffect, useMemo, useState } from 'react';
import { HTMLAttributes } from 'react';
type EditRoleFormProps = HTMLAttributes<HTMLDivElement> & {
  isUpdateForm?: boolean;
  options: DefaultOptionType[];
};
export default function EditRoleForm(props: EditRoleFormProps): ReactElement {
  const { isUpdateForm, options, ...rest } = props;

  return (
    <div className={rest.className ?? ''} {...rest}>
      <ProFormText label="分类名称" name="categoryName" rules={[{ required: true }]} />
      <ProFormSelect
        placeholder="默认一级分类"
        label="父级父类"
        name="parentId"
        options={options}
      />
    </div>
  );
}
