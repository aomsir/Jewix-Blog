import { useMemo, type PropsWithChildren } from "react";
import createAccess from "@/access";
import { AccessContext } from "./access-context";
import { useAuth } from "./auth-context";

export type { AccessState } from "./access-context";
export { AccessContext, useAuthorization } from "./access-context";

export function AccessProvider({ children }: PropsWithChildren) {
  const { currentUser } = useAuth();
  const value = useMemo(() => createAccess({ currentUser }), [currentUser]);
  return <AccessContext.Provider value={value}>{children}</AccessContext.Provider>;
}
