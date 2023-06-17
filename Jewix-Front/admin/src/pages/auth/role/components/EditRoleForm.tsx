import { API } from "@/services/ant-design-pro/typings";
import { ProFormText } from "@ant-design/pro-components";
import { Col, Form, FormInstance, Row, Tree } from "antd";
import { HTMLAttributes, ReactElement, RefObject } from "react";
type EditRoleFormProps = HTMLAttributes<HTMLDivElement> & {
  menu: API.FetchMenuResponse[];
  resources: API.FetchResourceResponse[];
  formRef: RefObject<FormInstance>;
};
export default function EditRoleForm(props: EditRoleFormProps): ReactElement {
  const { menu, resources, formRef, ...rest } = props;
  // const [checkedKeys, setCheckedKeys] = useState<
  //   | Key[]
  //   | {
  //       checked: Key[];
  //       halfChecked: Key[];
  //     }
  // >([]);
  // 给menu的每个节点加上parent对象
  // const queue = JSON.parse(JSON.stringify(menu));
  // const mappingIdNode: { [key: number]: API.FetchMenuResponse } = {};
  // while (queue.length) {
  //   const item = queue.pop()!;

  //   mappingIdNode[item.id] = item;

  //   if (item.sonList) {
  //     queue.push(...item.sonList);
  //   }
  // }
  const [form] = Form.useForm();

  return (
    <div className={`${rest.className ?? ""}`} {...rest}>
      <ProFormText label="角色名称" name="roleName" rules={[{ required: true }]} />
      <ProFormText label="角色标签" name="roleLabel" rules={[{ required: true }]} />
      <ProFormText hidden name="id" />
      <Row>
        <Col span={12}>
          <Form.Item label="菜单权限" name="roleMenu">
            <Tree
              // checkedKeys={checkedKeys}
              // @ts-ignore
              // onCheck={(
              //   checkedKeysValue: {
              //     checked: Key[];
              //     halfChecked: Key[];
              //   },
              //   info,
              // ) => {
              //   let node = info.node;
              //   let currentNode = JSON.parse(JSON.stringify(node));
              //   // 获取所有父级id
              //   const parentIds = [];
              //   while (info.checked && currentNode.parentId !== 0) {
              //     const parentNode =
              //       mappingIdNode[currentNode.parentId as keyof typeof mappingIdNode];
              //     parentIds.push(parentNode.id);
              //     currentNode = parentNode;
              //   }
              //   console.log(checkedKeysValue);
              //   console.log(info);

              //   setCheckedKeys({
              //     checked: [
              //       ...checkedKeysValue.checked.filter((id) => {
              //         console.log(node.key, id, info.checked);

              //         if (node.key !== id) return true;
              //         return info.checked;
              //       }),
              //       ...parentIds,
              //     ],
              //     halfChecked: checkedKeysValue.halfChecked,
              //   });
              // }}
              checkable
              /* defaultExpandedKeys={["0-0-0", "0-0-1"]}
          defaultSelectedKeys={["0-0-0", "0-0-1"]}
          defaultCheckedKeys={["0-0-0", "0-0-1"]} */
              treeData={menu as any}
              fieldNames={{ title: "name", key: "id", children: "sonList" }}
              onCheck={(checkedKeys, info) => {
                formRef.current?.setFieldsValue({
                  // @ts-ignore
                  roleMenu: [...checkedKeys, ...info.halfCheckedKeys],
                });
              }}
            />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item label="资源权限" name="roleResources">
            <Tree
              checkable
              /* defaultExpandedKeys={["0-0-0", "0-0-1"]}
          defaultSelectedKeys={["0-0-0", "0-0-1"]}
          defaultCheckedKeys={["0-0-0", "0-0-1"]} */
              treeData={resources as any}
              fieldNames={{ title: "name", key: "id", children: "sonList" }}
              onCheck={(checkedKeys, info) => {
                formRef.current?.setFieldsValue({
                  // @ts-ignore
                  roleResources: [...checkedKeys, ...info.halfCheckedKeys],
                });
              }}
            />
          </Form.Item>
        </Col>
      </Row>
    </div>
  );
}
