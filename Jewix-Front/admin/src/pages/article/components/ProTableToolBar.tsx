import { OPERATIONS } from "@/access";
import { useAuthorization } from "@/app/access";
import { useRouteMeta } from "@/router/routeMeta";
import { PlusOutlined } from "@ant-design/icons";
import { Button, message } from "antd";
import { HTMLAttributes, ReactElement, useEffect, useRef } from "react";
import { FormattedMessage } from "react-intl";
type TableToolBarProps = HTMLAttributes<HTMLDivElement> & {
  onInsertButtonClick?: () => void;
  routerProps?: { name: string };
};
export default function TableToolBar(props: TableToolBarProps): ReactElement {
  const { onInsertButtonClick, routerProps: routerProps2, ...rest } = props;
  const containerRef = useRef<HTMLDivElement>(null);
  const { operationFilter } = useAuthorization();
  const routeMeta = useRouteMeta();

  const access = operationFilter(routerProps2 ?? routeMeta, OPERATIONS.CREATE);

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
