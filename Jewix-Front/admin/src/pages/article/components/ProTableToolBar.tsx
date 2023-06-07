import { PlusOutlined } from '@ant-design/icons';
import { FormattedMessage } from '@umijs/max';
import { Button } from 'antd';
import { ReactElement, MouseEventHandler } from 'react';
import { HTMLAttributes } from 'react';
type TableToolBarProps = HTMLAttributes<HTMLDivElement> & {
  onInsertButtonClick?: () => void;
};
export default function TableToolBar(props: TableToolBarProps): ReactElement {
  const { onInsertButtonClick, ...rest } = props;
  return (
    <div className={`${rest.className ?? ''}`} {...rest}>
      <Button type="primary" key="primary" onClick={onInsertButtonClick}>
        <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="New" />
      </Button>
    </div>
  );
}
