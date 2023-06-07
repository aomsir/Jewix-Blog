import RichTextEditor from '@/components/bases/editor/RichTextEditor';
import { PageEnums } from '@/config/enums';
import { API } from '@/services/ant-design-pro/typings';
import { halfStart } from '@/utils';
import { ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import { Form } from 'antd';
import { HTMLAttributes, ReactElement } from 'react';
import css from './EditPageForm.module.scss';
type EditPageFormProps = HTMLAttributes<HTMLDivElement> & {
  initialValues?: API.FetchPagesResponse;
};
export default function EditPageForm(props: EditPageFormProps): ReactElement {
  const { initialValues, ...rest } = props;

  const isUpdating = !!initialValues;

  return (
    <div className={`${rest.className ?? ''} ${css.EditPageForm}`} {...rest}>
      <Form.Item
        hidden={!hasContentPageTypes.includes(initialValues?.type ?? hasContentPageTypes[0])}
        name="content"
        rules={[{ required: true }]}
      >
        <RichTextEditor initialValue={initialValues?.content} />
      </Form.Item>
      <ProFormText name="title" label="页面标题" rules={[{ required: true }]} />
      <ProFormText name="omit" label="页面路径" rules={[{ required: true }]} />
      <ProFormSelect
        hidden={isUpdating}
        name="type"
        label="页面类型"
        options={halfStart(
          Object.keys(PageEnums.Type).map((key) => ({
            label: PageEnums.Type[parseInt(key)],
            value: parseInt(key),
          })),
        ).filter((item) => !readonlyPageTypes.includes(item.value))}
        rules={[{ required: true }]}
      />
      <ProFormTextArea name="description" label="页面描述" rules={[{ required: true }]} />
      <div hidden>
        <ProFormText name="uuid" />
        <ProFormText name="id" />
        <ProFormText name="views" />
      </div>
    </div>
  );
}

export const readonlyPageTypes = [
  PageEnums.Type.友人帐,
  PageEnums.Type.文章归档,
  PageEnums.Type.时光机,
  PageEnums.Type.留言板,
];

const hasContentPageTypes = [PageEnums.Type.友人帐, PageEnums.Type.通用模板];
