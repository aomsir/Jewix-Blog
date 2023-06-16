import { useAccess, useRouteProps } from "@umijs/max";
import { message } from "antd";
import { HTMLAttributes, ReactElement, useEffect, useRef } from "react";
import css from "./HasOperation.module.scss";
type HasOperationProps = HTMLAttributes<HTMLDivElement> & {
  operation: string;
  routerProps?: { name: string };
};
export default function HasOperation(props: HasOperationProps): ReactElement {
  const { operation, children, routerProps: routerProps2, ...rest } = props;
  const { operationFilter } = useAccess();
  const routeProps = useRouteProps();

  const access = operationFilter(routerProps2 ?? routeProps, operation);
  const containerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    containerRef.current?.addEventListener(
      "click",
      (e) => {
        if (!access) {
          e.stopPropagation();
        }
      },
      { capture: true },
    );
    containerRef.current?.addEventListener(
      "mousedown",
      (e) => {
        if (!access) {
          e.stopPropagation();
          message.warning("权限不足");
        }
      },
      { capture: true },
    );
  }, []);

  return (
    <div
      className={`${rest.className ?? ""} ${css.HasOperation} ${access || "forbidden"}`}
      {...rest}
      ref={containerRef}
    >
      {children}
    </div>
  );
}
