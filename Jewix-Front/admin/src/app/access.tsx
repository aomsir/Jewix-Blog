import { createContext, useContext, useMemo, type PropsWithChildren } from "react";
import createAccess from "@/access";
import { useAuth } from "./auth";

type AccessState = ReturnType<typeof createAccess>;
export const AccessContext = createContext<AccessState | undefined>(undefined);

export function AccessProvider({ children }: PropsWithChildren) {
  const { currentUser } = useAuth();
  const value = useMemo(() => createAccess({ currentUser }), [currentUser]);
  return <AccessContext.Provider value={value}>{children}</AccessContext.Provider>;
}

export function useAuthorization() {
  const value = useContext(AccessContext);
  if (!value) throw new Error("useAuthorization must be used inside AccessProvider");
  return value;
}
