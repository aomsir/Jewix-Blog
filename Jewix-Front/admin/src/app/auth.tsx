import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useRef,
  useState,
  type PropsWithChildren,
} from "react";
import type { API } from "@/services/ant-design-pro/typings";
import { fetchCurrentUserInfo } from "@/services/api/user";

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

export function AuthProvider({ children }: PropsWithChildren) {
  const [currentUser, setCurrentUserState] = useState<API.FetchUserDetailResponse>();
  const [loading, setLoading] = useState(true);
  const requestSeq = useRef(0);

  const setCurrentUser = useCallback((user?: API.FetchUserDetailResponse) => {
    requestSeq.current += 1;
    setCurrentUserState(user);
    setLoading(false);
  }, []);

  const clearUser = useCallback(() => {
    requestSeq.current += 1;
    setCurrentUserState(undefined);
    setLoading(false);
  }, []);

  const refreshUser = useCallback(async () => {
    const seq = ++requestSeq.current;
    setLoading(true);
    try {
      const response = await fetchCurrentUserInfo({ skipErrorHandler: true });
      if (seq !== requestSeq.current) {
        return undefined;
      }
      setCurrentUserState(response.status);
      return response.status;
    } catch {
      if (seq === requestSeq.current) {
        setCurrentUserState(undefined);
      }
      return undefined;
    } finally {
      if (seq === requestSeq.current) {
        setLoading(false);
      }
    }
  }, []);

  useEffect(() => {
    if (window.location.pathname.endsWith("/login")) {
      setLoading(false);
      return;
    }
    void refreshUser();
  }, [refreshUser]);

  const value = useMemo(
    () => ({ currentUser, loading, refreshUser, setCurrentUser, clearUser }),
    [currentUser, loading, refreshUser, setCurrentUser, clearUser],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
