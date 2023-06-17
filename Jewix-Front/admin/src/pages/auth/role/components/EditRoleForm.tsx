import { API } from "@/services/ant-design-pro/typings";
import { ProFormItem, ProFormText } from "@ant-design/pro-components";
import { Col, Row, Tree } from "antd";
import { HTMLAttributes, ReactElement, useState } from "react";
type EditRoleFormProps = HTMLAttributes<HTMLDivElement> & {
  menu: API.FetchMenuResponse[];
  resources: API.FetchResourceResponse[];
};
export default function EditRoleForm(props: EditRoleFormProps): ReactElement {
  const { menu, resources, ...rest } = props;
  const [checkedKeys, setCheckedKeys] = useState<(string | number)[]>([]);

  return (
    <div className={`${rest.className ?? ""}`} {...rest}>
      <ProFormText label="角色名称" name="roleName" rules={[{ required: true }]} />
      <ProFormText label="角色标签" name="roleLabel" rules={[{ required: true }]} />
      <ProFormText hidden name="id" />
      <Row>
        <Col span={12}>
          <ProFormItem label="菜单权限" name="roleMenu" rules={[{ required: true }]}>
            <Tree
              checkedKeys={checkedKeys}
              onCheck={(checkedKeysValue, e) => {
                // TODO flat tree， 设置选择父节点
                const { checkedNodes } = e;
                const keys = checkedNodes.map((node) => node.key);
                console.log(checkedKeysValue, e, keys);
                setCheckedKeys(keys);
              }}
              checkable
              /* defaultExpandedKeys={["0-0-0", "0-0-1"]}
          defaultSelectedKeys={["0-0-0", "0-0-1"]}
          defaultCheckedKeys={["0-0-0", "0-0-1"]} */
              treeData={menu as any}
              checkStrictly
              fieldNames={{ title: "name", key: "id", children: "sonList" }}
            />
          </ProFormItem>
        </Col>
        <Col span={12}>
          <ProFormItem label="资源权限" name="roleResources" rules={[{ required: true }]}>
            <Tree
              checkable
              /* defaultExpandedKeys={["0-0-0", "0-0-1"]}
          defaultSelectedKeys={["0-0-0", "0-0-1"]}
          defaultCheckedKeys={["0-0-0", "0-0-1"]} */
              treeData={resources as any}
              fieldNames={{ title: "name", key: "id", children: "sonList" }}
            />
          </ProFormItem>
        </Col>
      </Row>
    </div>
  );
}
