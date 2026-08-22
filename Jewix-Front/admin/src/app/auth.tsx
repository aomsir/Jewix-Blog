import { useCallback, useEffect, useMemo, useRef, useState, type PropsWithChildren } from "react";
import type { API } from "@/services/ant-design-pro/typings";
import { fetchCurrentUserInfo } from "@/services/api/user";
import { AuthContext } from "./auth-context";

export type { AuthState } from "./auth-context";
export { AuthContext, useAuth } from "./auth-context";

export function AuthProvider({ children }: PropsWithChildren) {
  const [currentUser, setCurrentUserState] = useState<API.FetchUserDetailResponse>();
  const [loading, setLoading] = useState(true);
  const requestSeq = useRef(0);
  const mountedRef = useRef(true);
  const initializedRef = useRef(false);

  const commit = useCallback((seq: number, write: () => void) => {
    if (!mountedRef.current || seq !== requestSeq.current) {
      return false;
    }
    write();
    return true;
  }, []);

  const setCurrentUser = useCallback((user?: API.FetchUserDetailResponse) => {
    if (!mountedRef.current) {
      return;
    }
    requestSeq.current += 1;
    setCurrentUserState(user);
    setLoading(false);
  }, []);

  const clearUser = useCallback(() => {
    if (!mountedRef.current) {
      return;
    }
    requestSeq.current += 1;
    setCurrentUserState(undefined);
    setLoading(false);
  }, []);

  const refreshUser = useCallback(async () => {
    if (!mountedRef.current) {
      return undefined;
    }
    const seq = ++requestSeq.current;
    setLoading(true);
    try {
      const response = await fetchCurrentUserInfo({ skipErrorHandler: true });
      if (!commit(seq, () => setCurrentUserState(response.status))) {
        return undefined;
      }
      return response.status;
    } catch {
      commit(seq, () => setCurrentUserState(undefined));
      return undefined;
    } finally {
      commit(seq, () => setLoading(false));
    }
  }, [commit]);

  useEffect(() => {
    mountedRef.current = true;
    const alreadyInitialized = initializedRef.current;
    const cleanup = () => {
      mountedRef.current = false;
      if (alreadyInitialized) {
        requestSeq.current += 1;
      }
    };
    if (window.location.pathname.endsWith("/login")) {
      setLoading(false);
      return cleanup;
    }
    if (alreadyInitialized) {
      return cleanup;
    }
    initializedRef.current = true;
    void refreshUser();
    return cleanup;
  }, [refreshUser]);

  const value = useMemo(
    () => ({ currentUser, loading, refreshUser, setCurrentUser, clearUser }),
    [currentUser, loading, refreshUser, setCurrentUser, clearUser],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
