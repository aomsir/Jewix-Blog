import { useCallback } from "react";
import { useAuthorization } from "@/app/access-context";
import { useAuth } from "@/app/auth-context";
import type { API } from "@/services/ant-design-pro/typings";

type InitialState = {
  currentUser?: API.FetchUserDetailResponse;
  settings?: unknown;
  fetchUserInfo?: () => Promise<API.FetchUserDetailResponse | undefined>;
};

type InitialStateUpdater =
  | InitialState
  | undefined
  | ((state: InitialState | undefined) => InitialState | undefined);

export function useModel(_namespace: string) {
  const { currentUser, setCurrentUser, clearUser, refreshUser } = useAuth();

  const applyCurrentUser = useCallback(
    (next: InitialState | undefined) => {
      if (next?.currentUser === undefined) {
        clearUser();
        return;
      }
      setCurrentUser(next.currentUser);
    },
    [clearUser, setCurrentUser],
  );

  const setInitialState = useCallback(
    (updater: InitialStateUpdater) => {
      const previous: InitialState = {
        currentUser,
        fetchUserInfo: refreshUser,
      };
      const next = typeof updater === "function" ? updater(previous) : updater;
      applyCurrentUser(next);
    },
    [applyCurrentUser, currentUser, refreshUser],
  );

  return {
    initialState: {
      currentUser,
      fetchUserInfo: refreshUser,
    } satisfies InitialState,
    setInitialState,
    refresh: refreshUser,
  };
}

export function useAccess() {
  return useAuthorization();
}
