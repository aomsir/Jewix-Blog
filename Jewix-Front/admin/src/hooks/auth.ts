import { OPERATIONS } from "@/access";
import { useAccess, useRouteProps } from "@umijs/max";

export function useHasOperation(operation: (typeof OPERATIONS)[keyof typeof OPERATIONS]) {
  const { operationFilter } = useAccess();
  const routeProps = useRouteProps();

  const hasOperation = operationFilter(routeProps, operation);

  return hasOperation;
}
