import Editor from "@/components/bases/editor/Editor";
import { ArticleEnums } from "@/config/enums";
import { API } from "@/services/ant-design-pro/typings";
import {
    fetchAllCategories,
    fetchAllTags,
    fetchArticleByUUID,
    insertArticle,
    insertTag,
    updateArticle,
} from "@/services/api";
import { md } from "@/utils/markdownIt";
import {
    ProForm,
    ProFormInstance,
    ProFormRadio,
    ProFormSelect,
    ProFormSwitch,
    ProFormText,
    ProFormTextArea,
    ProFormTreeSelect,
} from "@ant-design/pro-components";
import { history, useAccess } from "@umijs/max";
import { Form, message } from "antd";
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from "react";
import css from "./ArticleEdit.module.scss";
type ArticleEditProps = HTMLAttributes<HTMLDivElement>;

export default function ArticleEdit(props: ArticleEditProps): ReactElement {
    const { ...rest } = props;
    const { normalRouteFilter } = useAccess();
    const formRef = useRef<ProFormInstance>(null);
    const [tags, setTags] = useState<API.FetchTagResponse[]>([]);
    const [categories, setCategories] = useState<API.FetchCategoryResponse[]>([]);
    const [showLinkInput, setShowLinkInput] = useState(false);
    // 获取url的uuid参数
    const params = new URLSearchParams(location.search);
    const uuid = params.get("uuid");
    let isUpdateForm = false;
    useEffect(() => {
        (async () => {
            try {
                // 获取标签
                const tags = await fetchAllTags();
                // 获取分类
                const categories = await fetchAllCategories();
                setCategories((categories.result as any) ?? []);
                setTags(tags);
                if (uuid) {
                    // 获取文章详情
                    const { result } = await fetchArticleByUUID(uuid);
                    formRef.current?.setFieldsValue(result);
                    isUpdateForm = true;
                }
            } catch (error: any) {
                message.error(error.message);
            }
        })();
    }, []);

    if (!normalRouteFilter({ name: "文章编辑" })) {
        history.push("/exception/403");
    }

    return (
        <div className={`${rest.className ?? ""} ${css.ArticleEdit}`} {...rest}>
            <ProForm
                layout="horizontal"
                labelAlign="right"
                formRef={formRef}
                submitter={{
                    searchConfig: {
                        submitText: "发布",
                        resetText: "取消",
                    },
                }}
                onReset={() => history.push("/article")}
                onFinish={async (values: API.InsertArticleBody | API.UpdateArticleBody) => {
                    try {
                        let _article = {
                            ...values,
                            isTop: values.isTop ? ArticleEnums.IsTop.是 : ArticleEnums.IsTop.否,
                            description:
                                values.description ||
                                md
                                    .render(values.content)
                                    .replace(/<[^>]+>/g, "")
                                    .substring(0, 30),
                        };
                        // 新增标签
                        for (const index in values.tagIds) {
                            if (values.tagIds[index]) {
                                const tag = values.tagIds[index];
                                if (typeof tag === "string") {
                                    const {
                                        result: { id },
                                    } = await insertTag({ tagName: tag });

                                    values.tagIds[index] = id;
                                }
                            }
                        }
                        // 提交表单
                        if (!isUpdateForm) {
                            await insertArticle(_article as API.InsertArticleBody);
                            message.success("发布成功");
                        } else {
                            await updateArticle(_article as API.UpdateArticleBody);
                            message.success("修改成功");
                        }
                        return true;
                    } catch (error) {
                        return false;
                    }
                }}
            >
                <Form.Item name="title" rules={[{ required: true }]}>
                    <input className="title" type="text" placeholder="在这里输入文章标题" />
                </Form.Item>
                <Form.Item name="content" rules={[{ required: true }]}>
                    <Editor />
                </Form.Item>
                <div className="footer">
                    <ProFormTextArea name="description" label="文章描述" />
                    <ProFormText
                        width={300}
                        name="cover"
                        label="文章封面"
                        placeholder="请输入url地址"
                    />
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
                            onChange: (e) =>
                                setShowLinkInput(e.target.value === ArticleEnums.Type.转载),
                        }}
                    />
                    {showLinkInput && (
                        <ProFormText
                            width={300}
                            name="originUrl"
                            label="原文链接"
                            placeholder="请输入原文链接"
                        />
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
            </ProForm>
        </div>
    );
}
