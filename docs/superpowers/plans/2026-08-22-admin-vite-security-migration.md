# Admin Vite Security Migration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace the vulnerable Umi/Max admin runtime and development toolchain with Vite and React Router while preserving `/admin/`, routes, menus, authentication, permissions, requests, pages, and production behavior with zero npm audit findings.

**Architecture:** Keep Ant Design, Ant Design Pro Components, pages, services, styles, and API contracts. Introduce an explicit Vite entry, a shared typed route tree, Auth and Access contexts, a local Axios client, and a ProLayout shell; then remove all Umi-generated code and legacy Pro tooling.

**Tech Stack:** Node.js 22.12+, npm, Vite, React 18, React Router, Axios, Ant Design 5, Ant Design Pro Components 2, Vitest, Testing Library, TypeScript.

---

### Task 1: Capture The Vulnerable Admin Baseline

**Files:**
- Inspect: `Jewix-Front/admin/package.json`
- Inspect: `Jewix-Front/admin/.gitignore`
- Inspect: `Jewix-Front/admin/config/config.ts`
- Inspect: `Jewix-Front/admin/config/routes.ts`

- [ ] **Step 1: Record the existing Umi dependency tree**

Run:

```bash
cd Jewix-Front/admin
node --version
npm --version
npm ls @umijs/max umi-presets-pro @ant-design/pro-cli @umijs/lint --all
```

Expected: Umi and legacy Pro packages are present.

- [ ] **Step 2: Verify the complete online audit fails**

Run:

```bash
npm audit --registry=https://registry.npmjs.org --audit-level=low
```

Expected: non-zero exit and the known Umi/Pro development chains, including critical and high findings. Do not use `--omit=dev`.

- [ ] **Step 3: Preserve the production behavior baseline**

Run:

```bash
npm run build
```

Expected: PASS and emit the existing admin routes under `dist/`.

- [ ] **Step 4: Record all non-generated Umi imports**

Run:

```bash
rg '@umijs/max|useModel\("@@initialState"\)|useAccess\(|useRouteProps\(|UMI_ENV|max build|umi-serve' src config package.json --glob '!src/.umi/**' --glob '!src/.umi-production/**'
```

Expected: matches in `src/app.tsx`, `src/global.tsx`, auth/operation components, selected pages, services, request utilities, Umi config, and scripts.

- [ ] **Step 5: Review checkpoint**

Run:

```bash
git status --short
git diff -- Jewix-Front/admin/package.json Jewix-Front/admin/src/components/bases/editor/BundledEditor.tsx
```

Expected: retain the already-approved TinyMCE and dependency changes. Do not commit unless requested.

### Task 2: Add The Vite And Vitest Foundation

**Files:**
- Create: `Jewix-Front/admin/index.html`
- Create: `Jewix-Front/admin/vite.config.ts`
- Create: `Jewix-Front/admin/vitest.config.ts`
- Create: `Jewix-Front/admin/src/test/setup.ts`
- Move: `Jewix-Front/admin/src/global.less` to `Jewix-Front/admin/src/global.scss`
- Move: `Jewix-Front/admin/src/styles/global.less` to `Jewix-Front/admin/src/styles/global.scss`
- Move: `Jewix-Front/admin/src/pages/login/index.less` to `Jewix-Front/admin/src/pages/login/index.module.scss`
- Modify: `Jewix-Front/admin/src/pages/login/index.tsx`
- Modify: `Jewix-Front/admin/tsconfig.json`
- Modify: `Jewix-Front/admin/package.json`
- Modify: `Jewix-Front/admin/.gitignore`

- [ ] **Step 1: Replace scripts and install only the foundation dependencies**

Set scripts to:

```json
{
  "scripts": {
    "dev": "vite",
    "build": "tsc --noEmit && vite build",
    "preview": "vite preview --port 8000",
    "typecheck": "tsc --noEmit",
    "test": "vitest run",
    "test:watch": "vitest",
    "audit": "npm audit --registry=https://registry.npmjs.org --audit-level=low"
  },
  "engines": {
    "node": ">=22.12.0"
  }
}
```

Install the current registry versions with:

```bash
npm install react-router-dom react-intl axios
npm install --save-dev vite @vitejs/plugin-react vitest @testing-library/react @testing-library/jest-dom @testing-library/user-event jsdom sass typescript
```

Do not remove Umi yet; the old build remains available until the replacement shell can render.

- [ ] **Step 2: Write the Vite entry HTML**

Create `index.html`:

```html
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" href="/admin/favicon.ico" />
    <title>Jewix Admin</title>
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.tsx"></script>
  </body>
</html>
```

- [ ] **Step 3: Write the Vite configuration**

Create `vite.config.ts`:

```typescript
import { fileURLToPath, URL } from "node:url";
import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

export default defineConfig({
  base: "/admin/",
  plugins: [react()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url))
    }
  },
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:7891",
        changeOrigin: true
      }
    }
  },
  build: {
    outDir: "dist",
    sourcemap: false
  }
});
```

- [ ] **Step 4: Write the Vitest configuration and setup**

Create `vitest.config.ts`:

```typescript
import { fileURLToPath, URL } from "node:url";
import react from "@vitejs/plugin-react";
import { defineConfig } from "vitest/config";

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url))
    }
  },
  test: {
    environment: "jsdom",
    setupFiles: ["./src/test/setup.ts"],
    clearMocks: true
  }
});
```

Create `src/test/setup.ts`:

```typescript
import "@testing-library/jest-dom/vitest";
```

- [ ] **Step 5: Replace the TypeScript compiler boundary**

Set the relevant `tsconfig.json` options to:

```json
{
  "compilerOptions": {
    "target": "ES2022",
    "useDefineForClassFields": true,
    "lib": ["ES2022", "DOM", "DOM.Iterable"],
    "module": "ESNext",
    "moduleResolution": "Bundler",
    "allowImportingTsExtensions": false,
    "resolveJsonModule": true,
    "isolatedModules": true,
    "noEmit": true,
    "jsx": "react-jsx",
    "strict": true,
    "skipLibCheck": true,
    "baseUrl": ".",
    "paths": {
      "@/*": ["./src/*"]
    },
    "types": ["vite/client", "vitest/globals"]
  },
  "include": ["src", "vite.config.ts", "vitest.config.ts"]
}
```

- [ ] **Step 6: Migrate the three Less files to Sass**

Rename `src/global.less` to `src/global.scss`, `src/styles/global.less` to `src/styles/global.scss`, and `src/pages/login/index.less` to `src/pages/login/index.module.scss`. Their existing nesting syntax is valid SCSS. Change the global import to:

```scss
@use "./styles/global";
```

Change the login page import to:

```typescript
import css from "./index.module.scss";
```

Expected: no `.less` files remain under `src/`; Vite needs `sass` but does not need the vulnerable Less dependency chain.

- [ ] **Step 7: Track the admin lockfile**

Remove `package-lock.json` from `admin/.gitignore`, run `npm install`, and verify:

```bash
git status --short package-lock.json .gitignore
```

Expected: `package-lock.json` is visible as a tracked candidate and `.gitignore` contains no lockfile exclusion.

### Task 3: Build The Local Axios Request Layer With TDD

**Files:**
- Create: `Jewix-Front/admin/src/lib/http/errors.ts`
- Create: `Jewix-Front/admin/src/lib/http/client.ts`
- Create: `Jewix-Front/admin/src/lib/http/client.test.ts`
- Modify later: `Jewix-Front/admin/src/services/**/*.ts`
- Modify later: `Jewix-Front/admin/src/utils/request.ts`

- [ ] **Step 1: Write failing request-client tests**

Create `src/lib/http/client.test.ts` with tests that use a custom Axios adapter:

```typescript
import type { AxiosAdapter, InternalAxiosRequestConfig } from "axios";
import { describe, expect, it, vi } from "vitest";
import { LocalToken } from "@/utils/token";
import { createHttpClient } from "./client";
import { ApiError, UnauthorizedError } from "./errors";

describe("createHttpClient", () => {
  it("injects the local token", async () => {
    vi.spyOn(LocalToken, "get").mockReturnValue("token-value");
    const adapter: AxiosAdapter = async (config: InternalAxiosRequestConfig) => ({
      data: { code: 200, status: true }, status: 200, statusText: "OK", headers: {}, config
    });
    const client = createHttpClient(adapter);
    const response = await client.get("user/current");
    expect(response.config.headers.token).toBe("token-value");
  });

  it("throws ApiError for business failures", async () => {
    const adapter: AxiosAdapter = async (config: InternalAxiosRequestConfig) => ({
      data: { code: 400, msg: "bad request" }, status: 200, statusText: "OK", headers: {}, config
    });
    await expect(createHttpClient(adapter).get("failing")).rejects.toEqual(
      new ApiError(400, "bad request")
    );
  });

  it("clears the token and throws UnauthorizedError", async () => {
    vi.spyOn(LocalToken, "remove");
    const adapter: AxiosAdapter = async (config: InternalAxiosRequestConfig) => ({
      data: { code: 401, msg: "expired" }, status: 200, statusText: "OK", headers: {}, config
    });
    await expect(createHttpClient(adapter).get("private")).rejects.toBeInstanceOf(UnauthorizedError);
    expect(LocalToken.remove).toHaveBeenCalledOnce();
  });
});
```

- [ ] **Step 2: Run the test and verify RED**

Run:

```bash
npm test -- src/lib/http/client.test.ts
```

Expected: FAIL because `client.ts` and `errors.ts` do not exist.

- [ ] **Step 3: Implement typed API errors**

Create `src/lib/http/errors.ts`:

```typescript
export class ApiError extends Error {
  constructor(public readonly code: number, message: string) {
    super(message);
    this.name = "ApiError";
  }
}

export class UnauthorizedError extends ApiError {
  constructor(message = "登录状态失效,请重新登录") {
    super(401, message);
    this.name = "UnauthorizedError";
  }
}
```

- [ ] **Step 4: Implement the Axios client**

Create `src/lib/http/client.ts`:

```typescript
import axios, { type AxiosAdapter } from "axios";
import { LocalToken } from "@/utils/token";
import { ApiError, UnauthorizedError } from "./errors";

export function createHttpClient(adapter?: AxiosAdapter) {
  const client = axios.create({ baseURL: "/api/", adapter });

  client.interceptors.request.use((config) => {
    const token = LocalToken.get();
    if (token) config.headers.token = token;
    return config;
  });

  client.interceptors.response.use((response) => {
    const { code, msg } = response.data ?? {};
    if (code === 401 || msg === "登录状态失效,请重新登录") {
      LocalToken.remove();
      throw new UnauthorizedError(msg);
    }
    if (typeof code === "number" && code >= 400) {
      throw new ApiError(code, msg ?? "Request failed");
    }
    return response;
  });

  return client;
}

export const http = createHttpClient();
```

- [ ] **Step 5: Verify GREEN**

Run:

```bash
npm test -- src/lib/http/client.test.ts
```

Expected: all three request-client tests pass.

### Task 4: Build Authentication And Access Contexts With TDD

**Files:**
- Create: `Jewix-Front/admin/src/app/auth.tsx`
- Create: `Jewix-Front/admin/src/app/auth.test.tsx`
- Create: `Jewix-Front/admin/src/app/access.tsx`
- Create: `Jewix-Front/admin/src/router/ProtectedRoute.tsx`
- Create: `Jewix-Front/admin/src/router/ProtectedRoute.test.tsx`
- Modify: `Jewix-Front/admin/src/access.ts`

- [ ] **Step 1: Write failing protected-route tests**

Create `src/router/ProtectedRoute.test.tsx`:

```tsx
import { render, screen } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { AuthContext } from "@/app/auth";
import { ProtectedRoute } from "./ProtectedRoute";

describe("ProtectedRoute", () => {
  it("redirects unauthenticated users and preserves the destination", () => {
    render(
      <AuthContext.Provider value={{ currentUser: undefined, loading: false, refreshUser: async () => undefined }}>
        <MemoryRouter initialEntries={["/article"]}>
          <Routes>
            <Route element={<ProtectedRoute />}>
              <Route path="/article" element={<div>article</div>} />
            </Route>
            <Route path="/login" element={<div>login</div>} />
          </Routes>
        </MemoryRouter>
      </AuthContext.Provider>
    );
    expect(screen.getByText("login")).toBeInTheDocument();
  });
});
```

- [ ] **Step 2: Verify RED**

Run:

```bash
npm test -- src/router/ProtectedRoute.test.tsx
```

Expected: FAIL because the Auth context and protected route do not exist.

- [ ] **Step 3: Implement the Auth context API**

Create `src/app/auth.tsx` with this public contract:

```tsx
import { createContext, useContext, useEffect, useState, type PropsWithChildren } from "react";
import type { API } from "@/services/ant-design-pro/typings";
import { fetchCurrentUserInfo } from "@/services/api/user";

export type AuthState = {
  currentUser?: API.FetchUserDetailResponse;
  loading: boolean;
  refreshUser: () => Promise<API.FetchUserDetailResponse | undefined>;
};

export const AuthContext = createContext<AuthState | undefined>(undefined);

export function useAuth() {
  const value = useContext(AuthContext);
  if (!value) throw new Error("useAuth must be used inside AuthProvider");
  return value;
}

export function AuthProvider({ children }: PropsWithChildren) {
  const [currentUser, setCurrentUser] = useState<API.FetchUserDetailResponse>();
  const [loading, setLoading] = useState(true);
  const refreshUser = async () => {
    const response = await fetchCurrentUserInfo({ skipErrorHandler: true });
    setCurrentUser(response.status);
    return response.status;
  };
  useEffect(() => {
    if (window.location.pathname.endsWith("/login")) {
      setLoading(false);
      return;
    }
    refreshUser().finally(() => setLoading(false));
  }, []);
  return <AuthContext.Provider value={{ currentUser, loading, refreshUser }}>{children}</AuthContext.Provider>;
}
```

- [ ] **Step 4: Implement the protected route**

Create `src/router/ProtectedRoute.tsx`:

```tsx
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "@/app/auth";

export function ProtectedRoute() {
  const { currentUser, loading } = useAuth();
  const location = useLocation();
  if (loading) return null;
  if (!currentUser) {
    const redirect = encodeURIComponent(location.pathname + location.search);
    return <Navigate to={`/login?redirect=${redirect}`} replace />;
  }
  return <Outlet />;
}
```

- [ ] **Step 5: Implement the Access context from the existing pure access function**

Create `src/app/access.tsx`:

```tsx
import { createContext, useContext, useMemo, type PropsWithChildren } from "react";
import createAccess from "@/access";
import { useAuth } from "./auth";

type AccessState = ReturnType<typeof createAccess>;
const AccessContext = createContext<AccessState | undefined>(undefined);

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
```

- [ ] **Step 6: Verify GREEN and add AuthProvider behavior tests**

Run:

```bash
npm test -- src/router/ProtectedRoute.test.tsx src/app/auth.test.tsx
```

Expected: unauthenticated redirect, login-route initialization skip, and user refresh tests pass.

### Task 5: Build One Shared Route Tree And ProLayout Shell

**Files:**
- Create: `Jewix-Front/admin/src/router/routes.tsx`
- Create: `Jewix-Front/admin/src/router/routeMeta.tsx`
- Create: `Jewix-Front/admin/src/layouts/AdminLayout.tsx`
- Create: `Jewix-Front/admin/src/App.tsx`
- Create: `Jewix-Front/admin/src/main.tsx`
- Create: `Jewix-Front/admin/src/router/routes.test.tsx`
- Move: `Jewix-Front/admin/config/defaultSettings.ts` to `Jewix-Front/admin/src/config/defaultSettings.ts`

- [ ] **Step 1: Write failing route metadata tests**

Test the exact route inventory in `src/router/routes.test.tsx`:

```typescript
import { describe, expect, it } from "vitest";
import { routeMeta } from "./routeMeta";

describe("routeMeta", () => {
  it("preserves every admin route", () => {
    expect(routeMeta.map((route) => route.path)).toEqual([
      "/article", "/article/edit", "/comment", "/page", "/blogroll",
      "/category", "/tag", "/photo", "/user", "/account/settings",
      "/auth/menu", "/auth/resource", "/auth/role", "/log/operation",
      "/log/login", "/setting", "/login", "/", "*"
    ]);
  });
});
```

- [ ] **Step 2: Verify RED**

Run:

```bash
npm test -- src/router/routes.test.tsx
```

Expected: FAIL because `routeMeta.tsx` does not exist.

- [ ] **Step 3: Create typed route metadata**

Define this type in `src/router/routeMeta.tsx` and transcribe every entry from `config/routes.ts`:

```typescript
import type { ComponentType, LazyExoticComponent, ReactNode } from "react";

export type AdminRouteMeta = {
  path: string;
  name: string;
  icon?: ReactNode;
  hidden?: boolean;
  layout: boolean;
  access?: "normalRouteFilter";
  component?: LazyExoticComponent<ComponentType>;
  redirect?: string;
  parent?: "auth" | "log";
};
```

Use `lazy(() => import("@/pages/..."))` for all page components and Ant Design icons matching the existing names. Set `layout: false` only for `/article/edit`, `/login`, and `*`; set `hidden: true` for article edit and account settings.

- [ ] **Step 4: Create React Router route objects**

In `src/router/routes.tsx`, map metadata into routes under `ProtectedRoute` and `AdminLayout`, using `<Navigate to="/article" replace />` for `/` and the existing 404 page for `*`. Use `<Suspense fallback={null}>` around lazy elements and export the browser router with exactly one admin basename:

```tsx
export const router = createBrowserRouter(routeObjects, {
  basename: "/admin"
});
```

- [ ] **Step 5: Implement the explicit ProLayout shell**

In `src/layouts/AdminLayout.tsx`, render:

```tsx
import { ProLayout } from "@ant-design/pro-components";
import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import Footer from "@/components/bases/Footer";
import defaultSettings from "@/config/defaultSettings";
import { routeMeta } from "@/router/routeMeta";
import { useAuthorization } from "@/app/access";

export default function AdminLayout() {
  const location = useLocation();
  const navigate = useNavigate();
  const access = useAuthorization();
  const menuRoutes = routeMeta.filter((route) =>
    route.layout && !route.hidden && (!route.access || access.normalRouteFilter(route))
  );
  return (
    <ProLayout
      {...defaultSettings}
      location={location}
      route={{ routes: menuRoutes }}
      menuItemRender={(item, dom) => item.path ? <Link to={item.path}>{dom}</Link> : dom}
      onMenuHeaderClick={() => navigate("/article")}
      footerRender={() => <Footer />}
    >
      <Outlet />
    </ProLayout>
  );
}
```

Restore the current avatar dropdown and SettingDrawer behavior after the minimal layout route test passes.

- [ ] **Step 6: Create providers and browser entry**

Create `src/App.tsx`:

```tsx
import { HelmetProvider } from "react-helmet-async";
import { RouterProvider } from "react-router-dom";
import { AccessProvider } from "@/app/access";
import { AuthProvider } from "@/app/auth";
import { router } from "@/router/routes";

export default function App() {
  return (
    <HelmetProvider>
      <AuthProvider>
        <AccessProvider>
          <RouterProvider router={router} />
        </AccessProvider>
      </AuthProvider>
    </HelmetProvider>
  );
}
```

Create `src/main.tsx`:

```tsx
import React from "react";
import ReactDOM from "react-dom/client";
import "antd/dist/reset.css";
import "github-markdown-css";
import "./global.scss";
import App from "./App";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode><App /></React.StrictMode>
);
```

- [ ] **Step 7: Verify route and shell tests**

Run:

```bash
npm test -- src/router src/layouts
npm run typecheck
```

Expected: route inventory and protected-layout tests pass; TypeScript exposes only remaining Umi imports in business files.

### Task 6: Migrate Umi Runtime Imports Without Changing Business Logic

**Files:**
- Modify: `Jewix-Front/admin/src/pages/login/index.tsx`
- Modify: `Jewix-Front/admin/src/pages/404.tsx`
- Modify: `Jewix-Front/admin/src/pages/article/**/*.tsx`
- Modify: `Jewix-Front/admin/src/pages/user/index.tsx`
- Modify: `Jewix-Front/admin/src/pages/account/settings/index.tsx`
- Modify: `Jewix-Front/admin/src/components/bases/RightContent/**/*.tsx`
- Modify: `Jewix-Front/admin/src/components/bases/hasOperation/hasOperation.tsx`
- Modify: `Jewix-Front/admin/src/hooks/auth.ts`
- Modify: `Jewix-Front/admin/src/services/**/*.ts`
- Modify: `Jewix-Front/admin/src/utils/request.ts`
- Modify: `Jewix-Front/admin/src/services/api/photo.ts`

- [ ] **Step 1: Replace navigation imports**

Apply these mechanical mappings only in non-generated source:

```text
history.push(path)       -> const navigate = useNavigate(); navigate(path)
history.replace(path)    -> const navigate = useNavigate(); navigate(path, { replace: true })
Link from @umijs/max     -> Link from react-router-dom
Helmet from @umijs/max   -> Helmet from react-helmet-async
```

Run `npm run typecheck` after this mapping and fix hook placement without introducing global mutable navigation.

- [ ] **Step 2: Replace initial-state and access imports**

Apply:

```text
useModel("@@initialState") -> useAuth()
useAccess()                 -> useAuthorization()
useRouteProps()             -> explicit routeName prop or useRouteMeta()
```

For operation controls, pass the route's existing Chinese `name` into `operationFilter({ name: routeName }, operation)` so permission matching remains identical to `src/access.ts`.

- [ ] **Step 3: Replace all Umi request imports**

In each service, replace:

```typescript
import { request } from "@umijs/max";
```

with:

```typescript
import { http } from "@/lib/http/client";
```

Convert calls using this exact shape:

```typescript
const response = await http.request<ResponseType>({ url, method, params, data });
return response.data;
```

Preserve every exported service function name, argument, and return type.

- [ ] **Step 4: Replace Umi locale APIs**

Use `react-intl` for `useIntl` and `FormattedMessage`. Add a local `SelectLang` component backed by an `IntlProvider`, retaining the existing locale message files and language menu. Do not change message IDs.

- [ ] **Step 5: Verify no non-generated Umi imports remain**

Run:

```bash
rg '@umijs|max build|umi-serve|UMI_ENV|useModel\(|useAccess\(|useRouteProps\(' src package.json --glob '!src/.umi/**' --glob '!src/.umi-production/**'
```

Expected: no runtime source or script matches.

- [ ] **Step 6: Run focused and full tests**

Run:

```bash
npm test
npm run typecheck
npm run build
```

Expected: all migrated services and shell tests pass; Vite emits `dist/` under `/admin/` asset URLs.

### Task 7: Remove Umi, Legacy Pro Tooling, And Generated Files

**Files:**
- Delete: `Jewix-Front/admin/config/config.ts`
- Delete: `Jewix-Front/admin/config/routes.ts`
- Delete: `Jewix-Front/admin/config/request.ts`
- Delete: `Jewix-Front/admin/config/proxy.ts`
- Delete: `Jewix-Front/admin/jest.config.ts`
- Delete: `Jewix-Front/admin/src/app.tsx`
- Delete: `Jewix-Front/admin/src/.umi/`
- Delete: `Jewix-Front/admin/src/.umi-production/`
- Delete if unreferenced: `Jewix-Front/admin/config/oneapi.json`
- Modify: `Jewix-Front/admin/package.json`
- Modify: `Jewix-Front/admin/package-lock.json`

- [ ] **Step 1: Remove obsolete packages through npm**

Run:

```bash
npm uninstall @umijs/max @umijs/lint @umijs/route-utils umi-presets-pro @ant-design/pro-cli @types/history raw-loader jest jest-environment-jsdom ts-node react-dev-inspector tailwindcss gh-pages
```

Remove `i18n-remove`, Umi environment scripts, `postinstall: max setup`, Jest scripts, and gh-pages scripts from `package.json`. Deployment continues to consume the Vite `dist/` output through the existing outer server/Nginx path.

- [ ] **Step 2: Delete generated and superseded Umi files**

Delete only the files listed in this task after the Vite build passes. Preserve `config/defaultSettings.ts` and `config/table.ts` only at their new `src/config/` locations.

- [ ] **Step 3: Remove obsolete aliases and generated includes**

Verify `tsconfig.json` has no `@@/*`, `@@test/*`, `src/.umi`, or repository-root `/*` alias.

- [ ] **Step 4: Regenerate the admin lockfile**

Run:

```bash
npm install --registry=https://registry.npmjs.org
npm ls --all
```

Expected: no Umi, Alita, DVA, old request, Carlo, Puppeteer, or mockjs packages remain; no invalid or extraneous packages.

- [ ] **Step 5: Verify the complete audit is zero**

Run:

```bash
npm audit --registry=https://registry.npmjs.org --audit-level=low
```

Expected: exit zero and `found 0 vulnerabilities`. If a current direct package has a new advisory, upgrade or replace that package and repeat clean install, tests, and build; do not add an ignore rule.

### Task 8: Final Admin Regression And Browser Verification

**Files:**
- Verify: `Jewix-Front/admin/package.json`
- Verify: `Jewix-Front/admin/package-lock.json`
- Verify: `Jewix-Front/admin/src/`
- Verify: `Jewix-Front/admin/public/`

- [ ] **Step 1: Perform a clean lockfile installation**

Run:

```bash
npm ci --registry=https://registry.npmjs.org
```

Expected: installation succeeds and does not change `package-lock.json`.

- [ ] **Step 2: Run all admin gates**

Run:

```bash
npm run typecheck
npm test
npm run build
npm audit --registry=https://registry.npmjs.org --audit-level=low
npm ls --all
git diff --check
```

Expected: every command exits zero, audit reports no vulnerabilities, and the dependency tree is valid.

- [ ] **Step 3: Start Vite preview**

Run:

```bash
npm run preview
```

Expected: preview listens on `http://localhost:8000/admin/`.

- [ ] **Step 4: Browser smoke-test route and layout behavior**

Visit every route below with the browser:

```text
/admin/
/admin/login
/admin/article
/admin/article/edit
/admin/comment
/admin/page
/admin/blogroll
/admin/category
/admin/tag
/admin/photo
/admin/user
/admin/account/settings
/admin/auth/menu
/admin/auth/resource
/admin/auth/role
/admin/log/operation
/admin/log/login
/admin/setting
/admin/not-found
```

Expected: root redirects to article; login and article edit follow their intended layout rules; protected pages redirect when logged out; nested menus render; unknown paths render 404; static assets use one `/admin/` prefix; console has no runtime errors.

- [ ] **Step 5: Verify proxy and request behavior with the API available**

Check one read request, one mutation, token injection, business error display, and 401 redirect. Expected: service payloads and response shapes match the pre-migration application.

- [ ] **Step 6: Final review checkpoint**

Run:

```bash
git status --short
git diff --stat
git diff --check
```

Expected: only the approved Vite migration, dependency upgrades, both plans/spec, and pre-existing approved dependency changes. Do not commit or push without explicit user instruction.
