import { Popconfirm } from 'antd';
import { ReactElement } from 'react';
import { HTMLAttributes } from 'react';
type PopConfirmDeleteProps = HTMLAttributes<HTMLDivElement> & {
  onConfirm: () => void;
};
export default function PopConfirmDelete(props: PopConfirmDeleteProps): ReactElement {
  const { ...rest } = props;
  return (
    <Popconfirm className={`${rest.className ?? ''}`} title="确定删除" {...rest}>
      <a>删除</a>
    </Popconfirm>
  );
}
