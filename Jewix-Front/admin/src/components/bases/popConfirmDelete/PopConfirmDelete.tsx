import { Popconfirm } from "antd";
import { HTMLAttributes, ReactElement } from "react";
type PopConfirmDeleteProps = HTMLAttributes<HTMLDivElement> & {
    onConfirm: () => void;
};
export default function PopConfirmDelete(props: PopConfirmDeleteProps): ReactElement {
    const { children, ...rest } = props;
    return (
        <Popconfirm className={`${rest.className ?? ""}`} title="确定删除" {...rest}>
            {children ?? <a style={{ color: "#fc3a52" }}>删除</a>}
        </Popconfirm>
    );
}
