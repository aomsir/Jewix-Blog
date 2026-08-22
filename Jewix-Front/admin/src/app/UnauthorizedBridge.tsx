import { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "@/app/auth-context";
import { setUnauthorizedHandler } from "@/lib/http/unauthorized";

export function UnauthorizedBridge() {
  const navigate = useNavigate();
  const { clearUser } = useAuth();
  useEffect(() => {
    setUnauthorizedHandler((path) => {
      clearUser();
      navigate(path, { replace: true });
    });
    return () => setUnauthorizedHandler(undefined);
  }, [clearUser, navigate]);
  return <Outlet />;
}
