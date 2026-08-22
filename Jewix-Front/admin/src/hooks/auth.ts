import { OPERATIONS } from "@/access";
import { useAuthorization } from "@/app/access";
import { useRouteMeta } from "@/router/routeMeta";

export function useHasOperation(operation: (typeof OPERATIONS)[keyof typeof OPERATIONS]) {
  const { operationFilter } = useAuthorization();
  const { name } = useRouteMeta();

  const hasOperation = operationFilter({ name }, operation);

  return hasOperation;
}
