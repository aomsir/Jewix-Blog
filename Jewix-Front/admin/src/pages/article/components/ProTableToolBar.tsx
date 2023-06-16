import { OPERATIONS } from "@/access";
import { PlusOutlined } from "@ant-design/icons";
import { FormattedMessage, useAccess, useRouteProps } from "@umijs/max";
import { Button, message } from "antd";
import { HTMLAttributes, ReactElement, useEffect, useRef } from "react";
type TableToolBarProps = HTMLAttributes<HTMLDivElement> & {
  onInsertButtonClick?: () => void;
  routerProps?: { name: string };
};
export default function TableToolBar(props: TableToolBarProps): ReactElement {
  const { onInsertButtonClick, routerProps: routerProps2, ...rest } = props;
  const containerRef = useRef<HTMLDivElement>(null);
  const { operationFilter } = useAccess();
  const routeProps = useRouteProps();

  const access = operationFilter(routerProps2 ?? routeProps, OPERATIONS.CREATE);

  useEffect(() => {
    containerRef.current?.addEventListener(
      "click",
      () => {
        if (!access) {
          message.warning("权限不足");
        }
      },
      { capture: true },
    );
  }, []);
  return (
    <div className={`${rest.className ?? ""}`} {...rest} ref={containerRef}>
      <Button disabled={!access} type="primary" key="primary" onClick={onInsertButtonClick}>
        <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="New" />
      </Button>
    </div>
  );
}
