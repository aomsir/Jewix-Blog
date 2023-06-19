import { API } from "@/services/ant-design-pro/typings";
import { ProFormText } from "@ant-design/pro-components";
import { Col, Form, FormInstance, Row, Tree } from "antd";
import { HTMLAttributes, ReactElement, RefObject } from "react";
type EditRoleFormProps = HTMLAttributes<HTMLDivElement> & {
    menu: API.FetchMenuResponse[];
    resources: API.FetchResourceResponse[];
    formRef: RefObject<FormInstance>;
    isUpdated: boolean;
};
export default function EditRoleForm(props: EditRoleFormProps): ReactElement {
    const { menu, resources, formRef, isUpdated, ...rest } = props;
    const menuIds = formRef.current?.getFieldValue("menuIds");
    const resourceIds = formRef.current?.getFieldValue("resourceIds");
    return (
        <div className={`${rest.className ?? ""}`} {...rest}>
            <ProFormText label="角色名称" name="roleName" rules={[{ required: true }]} />
            <ProFormText label="角色标签" name="roleLabel" rules={[{ required: true }]} />
            <ProFormText hidden name="id" />
            <Row hidden={!isUpdated}>
                <Col span={12}>
                    <Form.Item label="菜单权限" name="menuIds">
                        <Tree
                            key={menuIds}
                            defaultCheckedKeys={menuIds}
                            checkable
                            treeData={menu as any}
                            fieldNames={{ title: "name", key: "id", children: "sonList" }}
                            onCheck={(checkedKeys, info) => {
                                formRef.current?.setFieldsValue({
                                    // @ts-ignore
                                    menuIds: [...checkedKeys, ...info.halfCheckedKeys],
                                });
                            }}
                        />
                    </Form.Item>
                </Col>
                <Col span={12}>
                    <Form.Item label="资源权限" name="resourceIds">
                        <Tree
                            checkable
                            key={resourceIds}
                            defaultCheckedKeys={resourceIds}
                            treeData={resources as any}
                            fieldNames={{ title: "name", key: "id", children: "resourceSons" }}
                            onCheck={(checkedKeys, info) => {
                                formRef.current?.setFieldsValue({
                                    // @ts-ignore
                                    resourceIds: [...checkedKeys, ...info.halfCheckedKeys],
                                });
                            }}
                        />
                    </Form.Item>
                </Col>
            </Row>
        </div>
    );
}
