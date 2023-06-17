import Editor from "@/components/bases/editor/Editor";
import { ArticleEnums } from "@/config/enums";
import { API } from "@/services/ant-design-pro/typings";
import {
  ProFormRadio,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect,
} from "@ant-design/pro-components";
import { Form } from "antd";
import { HTMLAttributes, ReactElement, useState } from "react";
import css from "./EditArticleForm.module.scss";
type EditArticleFormProps = HTMLAttributes<HTMLDivElement> & {
  tags: API.FetchTagResponse[];
  categories: API.FetchCategoryResponse[];
  isUpdateForm: boolean;
  initialValues?: API.FetchArticleDetailResponse;
};
// 编辑文章表单
export default function EditArticleForm({
  tags,
  categories,
  isUpdateForm,
  initialValues,
  ...rest
}: EditArticleFormProps): ReactElement {
  const [showLinkInput, setShowLinkInput] = useState(false);

  return (
    <div className={`${rest.className ?? ""} ${css["edit-article-form"]}`} {...rest}>
      <Form.Item name="title" rules={[{ required: true }]}>
        <input className="title" type="text" placeholder="在这里输入文章标题" />
      </Form.Item>
      <Form.Item name="content" rules={[{ required: true }]}>
        <Editor />
      </Form.Item>
      <div className="footer">
        <ProFormTextArea name="description" label="文章描述"/>
        <ProFormText width={300} name="cover" label="文章封面" placeholder="请输入url地址" />
        <ProFormSelect
          rules={[{ required: true }]}
          name="tagIds"
          label="文章标签"
          mode="tags"
          options={tags.map((tag) => ({ label: tag.tagName, value: tag.id }))}
          width={300}
        />
        <ProFormTreeSelect
          rules={[{ required: true }]}
          name="categoryIds"
          label="文章分类"
          width={300}
          fieldProps={{
            fieldNames: {
              label: "categoryName",
              value: "id",
              key: "id",
              children: "sonList",
            },
            treeData: categories,
            showSearch: false, // 因为值是id，所以不支持输入名字搜索
            placeholder: "请选择",
            allowClear: true,
            multiple: true,
          }}
        />
        <ProFormSwitch name="isTop" label="是否顶置" initialValue={false} />
        <ProFormRadio.Group
          name="type"
          label="文章类型"
          initialValue={ArticleEnums.Type.原创}
          options={[
            {
              label: "原创",
              value: ArticleEnums.Type.原创,
            },
            {
              label: "转载",
              value: ArticleEnums.Type.转载,
            },
          ]}
          fieldProps={{
            onChange: (e) => setShowLinkInput(e.target.value === ArticleEnums.Type.转载),
          }}
        />
        {showLinkInput && (
          <ProFormText width={300} name="originUrl" label="原文链接" placeholder="请输入原文链接" />
        )}
        <ProFormRadio.Group
          name="status"
          label="文章状态"
          initialValue={ArticleEnums.Status.公开}
          options={[
            {
              label: "公开",
              value: ArticleEnums.Status.公开,
            },
            {
              label: "私密",
              value: ArticleEnums.Status.私密,
            },
          ]}
        />
      </div>
      {isUpdateForm && (
        <div hidden>
          <ProFormText name="id" rules={[{ required: true }]} />
          <ProFormText name="uuid" rules={[{ required: true }]} />
        </div>
      )}
    </div>
  );
}
