import { createContext, useContext } from "react";
import type createAccess from "@/access";

export type AccessState = ReturnType<typeof createAccess>;
export const AccessContext = createContext<AccessState | undefined>(undefined);

export function useAuthorization() {
  const value = useContext(AccessContext);
  if (!value) throw new Error("useAuthorization must be used inside AccessProvider");
  return value;
}
