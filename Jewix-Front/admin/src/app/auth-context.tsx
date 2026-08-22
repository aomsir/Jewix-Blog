import { createContext, useContext } from "react";
import type { API } from "@/services/ant-design-pro/typings";

export type AuthState = {
  currentUser?: API.FetchUserDetailResponse;
  loading: boolean;
  refreshUser: () => Promise<API.FetchUserDetailResponse | undefined>;
  setCurrentUser: (user?: API.FetchUserDetailResponse) => void;
  clearUser: () => void;
};

export const AuthContext = createContext<AuthState | undefined>(undefined);

export function useAuth() {
  const value = useContext(AuthContext);
  if (!value) throw new Error("useAuth must be used inside AuthProvider");
  return value;
}
