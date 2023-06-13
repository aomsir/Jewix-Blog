import { ProFormText } from "@ant-design/pro-components";
import { HTMLAttributes, ReactElement } from "react";
type EditRoleFormProps = HTMLAttributes<HTMLDivElement>;
export default function EditRoleForm(props: EditRoleFormProps): ReactElement {
  const { ...rest } = props;
  return (
    <div className={`${rest.className ?? ""}`} {...rest}>
      <ProFormText label="角色名称" name="roleName" rules={[{ required: true }]} />
      <ProFormText label="角色标签" name="roleLabel" rules={[{ required: true }]} />
      <ProFormText hidden name="id" />
    </div>
  );
}
