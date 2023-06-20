import { fetchWebSiteSetting, updateWebSiteSetting } from "@/services/api";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
import {
    PageContainer,
    ProForm,
    ProFormDatePicker,
    ProFormText,
    ProFormTextArea,
} from "@ant-design/pro-components";
import { Button, Form, Input, Row } from "antd";
import { HTMLAttributes, ReactElement, useEffect, useState } from "react";
import css from "./WebSiteConfig.module.scss";
type WebSiteConfigProps = HTMLAttributes<HTMLDivElement>;
export default function WebSiteConfig(props: WebSiteConfigProps): ReactElement {
    const { ...rest } = props;
    // 表单初始值
    const [initialValues, setInitialValues] = useState();

    useEffect(() => {
        (async () => {
            const config = await fetchWebSiteSetting();
            setInitialValues(JSON.parse(config.result!.config));
        })();
    }, []);
    return (
        <PageContainer className={`${rest.className ?? ""} ${css.WebSiteConfig}`} {...rest}>
            <ProForm
                key={initialValues}
                initialValues={initialValues}
                onFinish={async (formData) => {
                    updateWebSiteSetting(formData);
                }}
            >
                <h2>SEO</h2>
                <ProFormText label="首页标题" name="title" />
                <ProFormTextArea label="首页描述" name="description" />
                <p style={{ marginBottom: "5px" }}>关键词</p>
                <Form.List name="keywords">
                    {(fields, { add, remove }) => (
                        <Row style={{ marginBottom: "20px", gap: "20px" }}>
                            {fields.map((field) => (
                                <Row key={field.key} style={{ gap: "10px" }}>
                                    <Form.Item {...field} style={{ margin: 0 }}>
                                        <Input placeholder="关键词" />
                                    </Form.Item>
                                    <MinusCircleOutlined
                                        className="dynamic-delete-button"
                                        onClick={() => remove(field.name)}
                                    />
                                </Row>
                            ))}
                            <Button type="dashed" onClick={() => add()} icon={<PlusOutlined />}>
                                添加
                            </Button>
                        </Row>
                    )}
                </Form.List>
                <h2>基础</h2>
                <ProFormText label="网站图标" name="favicon" placeholder="请输入图片url地址" />
                <ProForm.Group labelLayout="inline">
                    <ProFormText
                        label="亮色LOGO"
                        name={["logo", "light"]}
                        placeholder="请输入图片url地址"
                    />
                    <ProFormText
                        label="暗色LOGO"
                        name={["logo", "dark"]}
                        placeholder="请输入图片url地址"
                    />
                </ProForm.Group>
                <ProFormDatePicker name="buildDate" label="创建时间" />
                <ProFormText name="webSite" label="网站链接" />
                <p style={{ marginBottom: "5px" }}>社交链接</p>
                <Form.List name="links">
                    {(fields, { add, remove }) => (
                        <Row style={{ marginBottom: "20px", gap: "20px" }}>
                            {fields.map((field) => (
                                <Row key={field.key} style={{ gap: "10px" }}>
                                    <Form.Item
                                        {...field}
                                        name={[field.name, "label"]}
                                        style={{ margin: 0 }}
                                    >
                                        <Input placeholder="名称" />
                                    </Form.Item>
                                    <Form.Item
                                        {...field}
                                        name={[field.name, "url"]}
                                        style={{ margin: 0, width: 300 }}
                                    >
                                        <Input placeholder="链接" />
                                    </Form.Item>
                                    <MinusCircleOutlined
                                        className="dynamic-delete-button"
                                        onClick={() => remove(field.name)}
                                    />
                                </Row>
                            ))}
                            <Button type="dashed" onClick={() => add()} icon={<PlusOutlined />}>
                                添加
                            </Button>
                        </Row>
                    )}
                </Form.List>
                <p style={{ marginBottom: "5px" }}>赞赏码</p>
                <Row style={{ gap: "10px" }}>
                    <ProFormText name={["reward", "alipay"]} placeholder="支付宝图片url" />
                    <ProFormText name={["reward", "wechat"]} placeholder="微信图片url" />
                </Row>
                <h2>备案信息</h2>
                <p style={{ marginBottom: "5px" }}>ICP</p>
                <ProForm.Group>
                    <ProFormText name={["record", "ICP", "icon"]} placeholder="图标url" />
                    <ProFormText name={["record", "ICP", "province"]} placeholder="省（简）" />
                    <ProFormText name={["record", "ICP", "number"]} placeholder="备案号" />
                    <ProFormText name={["record", "ICP", "url"]} placeholder="链接" />
                </ProForm.Group>
                <p style={{ marginBottom: "5px" }}>公网安</p>
                <ProForm.Group>
                    <ProFormText name={["record", "公网安", "icon"]} placeholder="图标url" />
                    <ProFormText name={["record", "公网安", "province"]} placeholder="省（简）" />
                    <ProFormText name={["record", "公网安", "number"]} placeholder="备案号" />
                    <ProFormText name={["record", "公网安", "url"]} placeholder="链接" />
                </ProForm.Group>
            </ProForm>
        </PageContainer>
    );
}
