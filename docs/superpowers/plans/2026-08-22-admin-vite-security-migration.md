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

Create `index.html` (title matches `config/defaultSettings.ts`; keep `public/scripts/loading.js` for first-paint white-screen cover):

```html
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" href="/admin/favicon.ico" />
    <title>Jewix博客系统后台管理</title>
    <script src="/admin/scripts/loading.js" async></script>
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

Replace `tsconfig.json` with this full file. Explicitly `exclude` Umi generated dirs: `postinstall` / `max setup` can recreate `src/.umi` and `src/.umi-production` until Task 7 removes Umi, and those trees would otherwise break Vite-era `tsc --noEmit`.

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
  "include": ["src", "vite.config.ts", "vitest.config.ts"],
  "exclude": ["node_modules", "src/.umi", "src/.umi-production"]
}
```

Remove legacy `@@/*`, `@@test/*`, and repository-root `/*` path aliases while editing this file.

Verify:

```bash
node -e 'const t=require("./tsconfig.json"); console.log(t.exclude.join(",")); console.log(Object.keys(t.compilerOptions.paths||{}).join(",")||"(paths:@ only)")'
# expect: node_modules,src/.umi,src/.umi-production
# expect: @/*   (no @@/* or /*)

# optional: if .umi exists from postinstall, typecheck must still not type those files
npm run typecheck
```

Expected: `exclude` lists all three entries; `paths` contains only `@/*`; typecheck does not fail on regenerated `.umi` sources.

- [ ] **Step 6: Migrate the three Less files to Sass**

Rename `src/global.less` → `src/global.scss`, `src/styles/global.less` → `src/styles/global.scss`, and `src/pages/login/index.less` → `src/pages/login/index.module.scss`. Nesting syntax stays valid SCSS.

**Critical — `@use` placement:** the current file ends with `@import "./styles/global.less";` at the bottom. Sass requires `@use` before any style rules. Do **not** replace that trailing import in place. Instead:

1. Delete the trailing `@import "./styles/global.less";` (and its `// 自己写的样式` comment if present).
2. Insert this as the **first line** of `src/global.scss` (before `html, body, #root`):

```scss
@use "./styles/global";
```

Change the login page import to:

```typescript
import css from "./index.module.scss";
```

Verify:

```bash
# first non-empty line must be @use
awk 'NF { print; exit }' src/global.scss
# must print: @use "./styles/global";

rg -n '@use|@import' src/global.scss
# expect exactly one @use at line 1 and zero @import

rg --files -g '*.less' src
# expect no output
```

Expected: `@use` is line 1 of `global.scss`; no `.less` under `src/`; Vite needs `sass` and does not need the Less chain.

- [ ] **Step 7: Track the admin lockfile**

Remove `package-lock.json` from `admin/.gitignore`, run `npm install`, and verify:

```bash
git status --short package-lock.json .gitignore
```

Expected: `package-lock.json` is visible as a tracked candidate and `.gitignore` contains no lockfile exclusion.

### Task 3: Build The Local Axios Request Layer With TDD

**Files:**
- Create: `Jewix-Front/admin/src/lib/http/errors.ts`
- Create: `Jewix-Front/admin/src/lib/http/paramsSerializer.ts`
- Create: `Jewix-Front/admin/src/lib/http/paramsSerializer.test.ts`
- Create: `Jewix-Front/admin/src/lib/http/unauthorized.ts`
- Create: `Jewix-Front/admin/src/lib/http/client.ts`
- Create: `Jewix-Front/admin/src/lib/http/client.test.ts`
- Modify later: `Jewix-Front/admin/src/services/**/*.ts`
- Modify later: `Jewix-Front/admin/src/utils/request.ts`

Parity target is the current `config/request.ts` + `src/utils/request.ts#deleteFactory`:
global `message.error` for business errors, 401 token clear + login redirect, per-request `skipErrorHandler`, and DELETE array query form `ids=1&ids=2`.

- [ ] **Step 1: Write failing DELETE paramsSerializer tests**

Create `src/lib/http/paramsSerializer.test.ts`:

```typescript
import { describe, expect, it } from "vitest";
import { serializeParams } from "./paramsSerializer";

describe("serializeParams", () => {
  it("repeats array keys as ids=1&ids=2", () => {
    expect(serializeParams({ ids: [1, 2] })).toBe("ids=1&ids=2");
  });

  it("keeps scalar keys", () => {
    expect(serializeParams({ ids: [9], status: 1 })).toBe("ids=9&status=1");
  });
});
```

- [ ] **Step 2: Verify RED for paramsSerializer**

Run:

```bash
npm test -- src/lib/http/paramsSerializer.test.ts
```

Expected: FAIL because `paramsSerializer.ts` does not exist.

- [ ] **Step 3: Implement paramsSerializer and turn GREEN**

Create `src/lib/http/paramsSerializer.ts`:

```typescript
export function serializeParams(params: Record<string, unknown>): string {
  return Object.keys(params)
    .map((key) => {
      const value = params[key];
      if (Array.isArray(value)) {
        return value.map((item) => `${key}=${item}`).join("&");
      }
      return `${key}=${value}`;
    })
    .join("&");
}
```

Run:

```bash
npm test -- src/lib/http/paramsSerializer.test.ts
```

Expected: both serializer tests pass.

- [ ] **Step 4: Write failing HTTP client tests (token, errors, skip, 401, login dual-channel)**

Create `src/lib/http/client.test.ts`:

```typescript
import type { AxiosAdapter, InternalAxiosRequestConfig } from "axios";
import { message } from "antd";
import { afterEach, describe, expect, it, vi } from "vitest";
import { LocalToken } from "@/utils/token";
import { createHttpClient } from "./client";
import { ApiError, UnauthorizedError } from "./errors";
import { setUnauthorizedHandler } from "./unauthorized";

const ok = (config: InternalAxiosRequestConfig, data: unknown) => ({
  data,
  status: 200,
  statusText: "OK",
  headers: {},
  config,
});

describe("createHttpClient", () => {
  afterEach(() => {
    vi.restoreAllMocks();
    setUnauthorizedHandler(undefined);
  });

  it("injects the local token", async () => {
    vi.spyOn(LocalToken, "get").mockReturnValue("token-value");
    const adapter: AxiosAdapter = async (config) => ok(config, { code: 200, status: true });
    const response = await createHttpClient(adapter).get("user/current");
    expect(response.config.headers?.token).toBe("token-value");
  });

  it("shows message.error and throws ApiError for business failures", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 400, msg: "bad request" });
    await expect(createHttpClient(adapter).get("failing")).rejects.toEqual(
      new ApiError(400, "bad request"),
    );
    expect(errorSpy).toHaveBeenCalledWith("bad request");
  });

  it("skips message.error when skipErrorHandler is true", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 400, msg: "hidden" });
    await expect(
      createHttpClient(adapter).get("failing", { skipErrorHandler: true }),
    ).rejects.toBeInstanceOf(ApiError);
    expect(errorSpy).not.toHaveBeenCalled();
  });

  it("clears token, redirects once, and throws UnauthorizedError on 401", async () => {
    vi.spyOn(LocalToken, "remove");
    const redirect = vi.fn();
    setUnauthorizedHandler(redirect);
    const adapter: AxiosAdapter = async (config) =>
      ok(config, { code: 401, msg: "登录状态失效,请重新登录" });
    await expect(createHttpClient(adapter).get("private")).rejects.toBeInstanceOf(
      UnauthorizedError,
    );
    expect(LocalToken.remove).toHaveBeenCalledOnce();
    expect(redirect).toHaveBeenCalledOnce();
  });

  it("login dual-channel: login uses toast path; current-user uses skipErrorHandler", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    const adapter: AxiosAdapter = async (config) => {
      if (String(config.url).includes("login")) {
        return ok(config, { code: 400, msg: "bad credentials" });
      }
      return ok(config, { code: 401, msg: "登录状态失效,请重新登录" });
    };
    const client = createHttpClient(adapter);
    await expect(client.post("login", {})).rejects.toBeInstanceOf(ApiError);
    expect(errorSpy).toHaveBeenCalledWith("bad credentials");
    errorSpy.mockClear();
    await expect(
      client.get("users/current", { skipErrorHandler: true }),
    ).rejects.toBeInstanceOf(UnauthorizedError);
    expect(errorSpy).not.toHaveBeenCalled();
  });
});
```

- [ ] **Step 5: Verify RED for the client**

Run:

```bash
npm test -- src/lib/http/client.test.ts
```

Expected: FAIL because `client.ts`, `errors.ts`, and `unauthorized.ts` do not exist.

- [ ] **Step 6: Implement typed errors and unauthorized redirect hook**

Create `src/lib/http/errors.ts`:

```typescript
export class ApiError extends Error {
  constructor(
    public readonly code: number,
    message: string,
  ) {
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

Create `src/lib/http/unauthorized.ts`:

```typescript
export type UnauthorizedHandler = (redirectPath: string) => void;

let unauthorizedHandler: UnauthorizedHandler | undefined;

export function setUnauthorizedHandler(handler: UnauthorizedHandler | undefined) {
  unauthorizedHandler = handler;
}

export function notifyUnauthorized(redirectPath: string) {
  unauthorizedHandler?.(redirectPath);
}
```

- [ ] **Step 7: Implement the Axios client with full error parity**

Create `src/lib/http/client.ts`:

```typescript
import axios, { type AxiosAdapter, type AxiosRequestConfig } from "axios";
import { message } from "antd";
import { LocalToken } from "@/utils/token";
import { ApiError, UnauthorizedError } from "./errors";
import { notifyUnauthorized } from "./unauthorized";

export type HttpRequestConfig = AxiosRequestConfig & {
  skipErrorHandler?: boolean;
};

export function createHttpClient(adapter?: AxiosAdapter) {
  const client = axios.create({ baseURL: "/api/", adapter });

  client.interceptors.request.use((config) => {
    const token = LocalToken.get();
    if (token) config.headers.token = token;
    return config;
  });

  client.interceptors.response.use(
    (response) => {
      const { code, msg } = response.data ?? {};
      const skip = Boolean((response.config as HttpRequestConfig).skipErrorHandler);
      if (code === 401 || msg === "登录状态失效,请重新登录") {
        LocalToken.remove();
        if (!skip) {
          const path = window.location.pathname.replace(/^\/admin/, "") || "/";
          const redirect = path === "/login" ? "/login" : `/login?redirect=${encodeURIComponent(path)}`;
          notifyUnauthorized(redirect);
          message.error(msg ?? "登录状态失效,请重新登录");
        }
        throw new UnauthorizedError(msg);
      }
      if (typeof code === "number" && code >= 400) {
        if (!skip) message.error(msg ?? "Request failed");
        throw new ApiError(code, msg ?? "Request failed");
      }
      return response;
    },
    (error) => {
      const skip = Boolean((error.config as HttpRequestConfig | undefined)?.skipErrorHandler);
      if (!skip) {
        if (error.response) message.error(`Response status:${error.message}`);
        else if (error.request) message.error("None response! Please retry.");
        else message.error("发送请求时出了点问题，请重试。");
      }
      return Promise.reject(error);
    },
  );

  return client;
}

export const http = createHttpClient();
```

In a later Task 5/6 step, `App` must call `setUnauthorizedHandler((path) => navigate(path, { replace: true }))` once so 401 uses React Router instead of a full reload.

- [ ] **Step 8: Verify GREEN**

Run:

```bash
npm test -- src/lib/http/paramsSerializer.test.ts src/lib/http/client.test.ts
```

Expected: serializer tests and all five client tests pass.

- [ ] **Step 9: Wire serializeParams into deleteFactory when services migrate**

When Task 6 updates `src/utils/request.ts`, keep this exact delete shape:

```typescript
import { serializeParams } from "@/lib/http/paramsSerializer";

export function deleteFactory<P = API.DeleteParams>(url: string) {
  return (rest: P, options?: Record<string, unknown>) =>
    http.request({
      url,
      method: "delete",
      params: { ...rest },
      paramsSerializer: serializeParams,
      ...(options ?? {}),
    }).then((response) => response.data);
}
```

Expected: DELETE with `{ ids: [1, 2] }` becomes query string `ids=1&ids=2`.

### Task 4: Build Authentication And Access Contexts With TDD

**Files:**
- Create: `Jewix-Front/admin/src/app/auth.tsx`
- Create: `Jewix-Front/admin/src/app/auth.test.tsx`
- Create: `Jewix-Front/admin/src/app/access.tsx`
- Create: `Jewix-Front/admin/src/router/ProtectedRoute.tsx`
- Create: `Jewix-Front/admin/src/router/ProtectedRoute.test.tsx`
- Modify: `Jewix-Front/admin/src/access.ts`

`ProtectedRoute` enforces login for every non-login route. For access, it inspects **every** `useMatches()` entry whose `handle.access === "normalRouteFilter"` and requires `normalRouteFilter({ name })` to pass for **each** of them (parent + leaf). A single failure redirects to `/article`. This preserves Umi parent inheritance: e.g. `/auth/menu` has no leaf `access`, but parent `/auth` carries `access` + name `"权限管理"`.

`/article/edit` and `*` sit under the authenticated branch (login required).

**Decision — `/article/edit` permission (do not change business rules):**
Route-level `normalRouteFilter({ name: "文章编辑" })` would always fail: `config/routes.ts` sets no `access` on `/article/edit`; menus use names like `"文章管理"`. Keep status quo: login-only at ProtectedRoute; keep page-local check via mechanical `useAuthorization` + `navigate`; do **not** remap `"文章编辑"` → `"文章管理"`.

- [ ] **Step 1: Write failing protected-route tests**

Create `src/router/ProtectedRoute.test.tsx`. Nested `/auth/*` routes must use real parent `path="/auth"` (not a pathless parent) so URLs stay `/auth/menu`:

```tsx
import { render, screen } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { describe, expect, it, vi } from "vitest";
import { AccessContext } from "@/app/access";
import { AuthContext } from "@/app/auth";
import { ProtectedRoute } from "./ProtectedRoute";

const authValue = {
  currentUser: { user: { id: 1 }, menuListPageDTO: [] } as never,
  loading: false,
  refreshUser: async () => undefined,
  setCurrentUser: vi.fn(),
  clearUser: vi.fn(),
};

const allowAll = {
  normalRouteFilter: () => true,
  operationFilter: () => true,
};

const denyAll = {
  normalRouteFilter: () => false,
  operationFilter: () => false,
};

function accessFor(allowedNames: string[]) {
  return {
    normalRouteFilter: (route: { name?: string }) => allowedNames.includes(route.name ?? ""),
    operationFilter: () => true,
  };
}

function renderTree(initialPath: string, access = allowAll, currentUser: unknown = authValue.currentUser) {
  return render(
    <AuthContext.Provider value={{ ...authValue, currentUser: currentUser as never }}>
      <AccessContext.Provider value={access as never}>
        <MemoryRouter initialEntries={[initialPath]}>
          <Routes>
            <Route path="/login" element={<div>login</div>} />
            <Route element={<ProtectedRoute />}>
              {/* /article is the denial redirect target; leave it unguarded in this fixture */}
              <Route path="/article" element={<div>article</div>} />
              <Route path="/article/edit" handle={{ name: "文章编辑" }} element={<div>article-edit</div>} />
              <Route
                path="/user"
                handle={{ name: "用户管理", access: "normalRouteFilter" }}
                element={<div>user</div>}
              />
              <Route
                path="/comment"
                handle={{ name: "评论管理", access: "normalRouteFilter" }}
                element={<div>comment</div>}
              />
              <Route path="/auth" handle={{ name: "权限管理", access: "normalRouteFilter" }}>
                <Route path="menu" handle={{ name: "菜单管理" }} element={<div>auth-menu</div>} />
                <Route
                  path="resource"
                  handle={{ name: "资源管理", access: "normalRouteFilter" }}
                  element={<div>auth-resource</div>}
                />
              </Route>
              <Route path="/log" handle={{ name: "日志管理", access: "normalRouteFilter" }}>
                <Route
                  path="operation"
                  handle={{ name: "操作日志", access: "normalRouteFilter" }}
                  element={<div>log-operation</div>}
                />
              </Route>
              <Route path="*" element={<div>not-found</div>} />
            </Route>
          </Routes>
        </MemoryRouter>
      </AccessContext.Provider>
    </AuthContext.Provider>,
  );
}

describe("ProtectedRoute", () => {
  it("redirects unauthenticated users and preserves the destination", () => {
    renderTree("/article", allowAll, undefined);
    expect(screen.getByText("login")).toBeInTheDocument();
  });

  it("blocks leaf routes when their own normalRouteFilter fails", () => {
    renderTree("/user", denyAll);
    expect(screen.getByText("article")).toBeInTheDocument();
    expect(screen.queryByText("user")).not.toBeInTheDocument();
  });

  it("denies ordinary leaf /comment without 评论管理", () => {
    renderTree("/comment", accessFor(["文章管理"]));
    expect(screen.getByText("article")).toBeInTheDocument();
    expect(screen.queryByText("comment")).not.toBeInTheDocument();
  });

  it("allows ordinary leaf /comment with 评论管理", () => {
    renderTree("/comment", accessFor(["评论管理"]));
    expect(screen.getByText("comment")).toBeInTheDocument();
  });

  it("denies /auth/menu when parent 权限管理 is missing even if leaf has no access handle", () => {
    renderTree("/auth/menu", accessFor(["菜单管理"]));
    expect(screen.getByText("article")).toBeInTheDocument();
    expect(screen.queryByText("auth-menu")).not.toBeInTheDocument();
  });

  it("allows /auth/menu when parent 权限管理 passes", () => {
    renderTree("/auth/menu", accessFor(["权限管理", "菜单管理"]));
    expect(screen.getByText("auth-menu")).toBeInTheDocument();
  });

  it("allows authenticated /article/edit without route-level access handle", () => {
    renderTree("/article/edit", denyAll);
    expect(screen.getByText("article-edit")).toBeInTheDocument();
  });

  it("keeps authenticated wildcard * inside the protected branch", () => {
    renderTree("/missing", allowAll);
    expect(screen.getByText("not-found")).toBeInTheDocument();
  });
});
```

- [ ] **Step 2: Verify RED**

Run:

```bash
npm test -- src/router/ProtectedRoute.test.tsx
```

Expected: FAIL because the Auth/Access contexts and protected route do not exist.

- [ ] **Step 3: Implement the Auth context API (read + write)**

Create `src/app/auth.tsx` with this public contract. Login success must call `setCurrentUser`; AvatarDropdown logout must call `clearUser` before `LocalToken.remove()` and navigate.

```tsx
import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
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

  const setCurrentUser = useCallback((user?: API.FetchUserDetailResponse) => {
    setCurrentUserState(user);
  }, []);

  const clearUser = useCallback(() => {
    setCurrentUserState(undefined);
  }, []);

  const refreshUser = useCallback(async () => {
    const response = await fetchCurrentUserInfo({ skipErrorHandler: true });
    setCurrentUserState(response.status);
    return response.status;
  }, []);

  useEffect(() => {
    if (window.location.pathname.endsWith("/login")) {
      setLoading(false);
      return;
    }
    refreshUser().finally(() => setLoading(false));
  }, [refreshUser]);

  const value = useMemo(
    () => ({ currentUser, loading, refreshUser, setCurrentUser, clearUser }),
    [currentUser, loading, refreshUser, setCurrentUser, clearUser],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
```

Replacement mapping for later Task 6:

```text
setInitialState((s) => ({ ...s, currentUser: userInfo }))  ->  setCurrentUser(userInfo)
setInitialState((s) => ({ ...s, currentUser: undefined })) ->  clearUser()
```

- [ ] **Step 4: Implement Access context and export AccessContext for tests**

Create `src/app/access.tsx`:

```tsx
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
```

- [ ] **Step 5: Implement ProtectedRoute with login + full match-chain access checks**

Create `src/router/ProtectedRoute.tsx`. Do **not** stop at the first/`reverse().find` access handle — every matched handle with `access: "normalRouteFilter"` must pass:

```tsx
import { Navigate, Outlet, useLocation, useMatches } from "react-router-dom";
import { useAuthorization } from "@/app/access";
import { useAuth } from "@/app/auth";

type RouteHandle = {
  name?: string;
  access?: "normalRouteFilter";
};

export function ProtectedRoute() {
  const { currentUser, loading } = useAuth();
  const access = useAuthorization();
  const location = useLocation();
  const matches = useMatches();
  if (loading) return null;
  if (!currentUser) {
    const redirect = encodeURIComponent(location.pathname + location.search);
    return <Navigate to={`/login?redirect=${redirect}`} replace />;
  }
  const denied = matches.some((match) => {
    const handle = match.handle as RouteHandle | undefined;
    return (
      handle?.access === "normalRouteFilter" &&
      !access.normalRouteFilter({ name: handle.name })
    );
  });
  if (denied) {
    return <Navigate to="/article" replace />;
  }
  return <Outlet />;
}
```

- [ ] **Step 6: Verify GREEN and add AuthProvider behavior tests**

Create `src/app/auth.test.tsx` covering: skip init on `/login`; `refreshUser` uses `skipErrorHandler: true`; successful user hydration; `setCurrentUser` writes `currentUser`; `clearUser` clears `currentUser`. Then run:

```bash
npm test -- src/router/ProtectedRoute.test.tsx src/app/auth.test.tsx
```

Expected: unauthenticated redirect; leaf denial for `/user`; `/auth/menu` denied without `"权限管理"` and allowed with it; `/article/edit` login-only; authenticated `*` 404; auth write/clear tests pass.

### Task 5: Build One Shared Route Tree And ProLayout Shell

**Files:**
- Create: `Jewix-Front/admin/src/router/routes.tsx`
- Create: `Jewix-Front/admin/src/router/routeMeta.tsx`
- Create: `Jewix-Front/admin/src/layouts/AdminLayout.tsx`
- Create: `Jewix-Front/admin/src/app/UnauthorizedBridge.tsx`
- Create: `Jewix-Front/admin/src/App.tsx`
- Create: `Jewix-Front/admin/src/main.tsx`
- Create: `Jewix-Front/admin/src/router/routes.test.tsx`
- Move: `Jewix-Front/admin/config/defaultSettings.ts` to `Jewix-Front/admin/src/config/defaultSettings.ts`
- Keep: `Jewix-Front/admin/public/scripts/loading.js`

- [ ] **Step 1: Write failing route metadata + menu nesting tests**

Create `src/router/routes.test.tsx`. `routeMeta` is a **tree** (not a flat list). Complete inventory from `config/routes.ts`:

| path | name | access | children / notes |
|---|---|---|---|
| `/article` | 文章管理 | yes | leaf |
| `/article/edit` | 文章编辑 | no | layout false, hidden |
| `/comment` | 评论管理 | yes | leaf |
| `/page` | 页面管理 | yes | leaf |
| `/blogroll` | 友链管理 | yes | leaf |
| `/category` | 分类管理 | yes | leaf |
| `/tag` | 标签管理 | yes | leaf |
| `/photo` | 相册管理 | yes | leaf |
| `/user` | 用户管理 | yes | leaf |
| `/account` | `""` | no | hidden group; child `/account/settings` name `profileSettings`, no access |
| `/auth` | 权限管理 | yes | children: `/auth/menu` (no access), `/auth/resource` (yes), `/auth/role` (yes) |
| `/log` | 日志管理 | yes | children: `/log/operation` (yes), `/log/login` (yes) |
| `/setting` | 网站配置 | yes | leaf |
| `/login` | login | no | layout false |
| `/` | `""` | — | redirect `/article` |
| `*` | `""` | no | layout false, 404 |

```typescript
import { describe, expect, it } from "vitest";
import { buildMenuRoutes, flattenRoutePaths, routeMeta } from "./routeMeta";

describe("routeMeta", () => {
  it("preserves every admin path including nested groups", () => {
    expect(flattenRoutePaths(routeMeta)).toEqual([
      "/article",
      "/article/edit",
      "/comment",
      "/page",
      "/blogroll",
      "/category",
      "/tag",
      "/photo",
      "/user",
      "/account",
      "/account/settings",
      "/auth",
      "/auth/menu",
      "/auth/resource",
      "/auth/role",
      "/log",
      "/log/operation",
      "/log/login",
      "/setting",
      "/login",
      "/",
      "*",
    ]);
  });

  it("keeps /auth and /log parent access and nested children", () => {
    const auth = routeMeta.find((route) => route.path === "/auth");
    const log = routeMeta.find((route) => route.path === "/log");
    expect(auth?.access).toBe("normalRouteFilter");
    expect(auth?.name).toBe("权限管理");
    expect(auth?.children?.map((child) => [child.path, child.access])).toEqual([
      ["/auth/menu", undefined],
      ["/auth/resource", "normalRouteFilter"],
      ["/auth/role", "normalRouteFilter"],
    ]);
    expect(log?.children?.map((child) => child.path)).toEqual(["/log/operation", "/log/login"]);
  });

  it("builds nested ProLayout menus for /log when parent permission exists", () => {
    const menus = buildMenuRoutes(routeMeta, {
      normalRouteFilter: (route) => ["日志管理", "操作日志", "登录日志", "文章管理"].includes(route.name),
    });
    const log = menus.find((route) => route.path === "/log");
    expect(log?.children?.map((child) => child.path)).toEqual(["/log/operation", "/log/login"]);
    expect(menus.some((route) => route.path === "/auth")).toBe(false);
  });
});
```

- [ ] **Step 2: Verify RED**

Run:

```bash
npm test -- src/router/routes.test.tsx
```

Expected: FAIL because `routeMeta.tsx` helpers do not exist.

- [ ] **Step 3: Create typed nested route metadata**

Define this type in `src/router/routeMeta.tsx` and transcribe **every** `config/routes.ts` entry, preserving parent groups as nodes with `children` (do not flatten `/auth` / `/log` / `/account`):

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
  children?: AdminRouteMeta[];
};

export function flattenRoutePaths(routes: AdminRouteMeta[]): string[] {
  return routes.flatMap((route) => [route.path, ...flattenRoutePaths(route.children ?? [])]);
}

export function buildMenuRoutes(
  routes: AdminRouteMeta[],
  access: { normalRouteFilter: (route: { name: string }) => boolean },
): AdminRouteMeta[] {
  return routes
    .filter((route) => route.layout && !route.hidden && !route.redirect && route.path !== "*")
    .filter((route) => !route.access || access.normalRouteFilter({ name: route.name }))
    .map((route) => ({
      ...route,
      children: route.children
        ? buildMenuRoutes(
            route.children.map((child) => ({ ...child, layout: true })),
            access,
          )
        : undefined,
    }))
    .filter((route) => !route.children || route.children.length > 0 || Boolean(route.component));
}
```

Use `lazy(() => import("@/pages/..."))` for leaf page components and Ant Design icons matching existing names. Set `layout: false` only for `/article/edit`, `/login`, and `*`. Set `hidden: true` on `/article/edit` and the `/account` group (and/or settings child) to match `hideInMenu`. Copy `access: "normalRouteFilter"` **only** where `config/routes.ts` declares it — including parent `/auth` and `/log`, and **not** on `/auth/menu` or `/article/edit`.

- [ ] **Step 4: Create React Router route objects from the same tree**

In `src/router/routes.tsx`:

1. Wrap everything in `UnauthorizedBridge`.
2. Public: `/login`.
3. Authenticated: `ProtectedRoute`.
4. Under `ProtectedRoute`, a **pathless** layout route `element={<AdminLayout />}` is allowed (layout-only; it does not change URLs). Do **not** make `/auth` or `/log` pathless.
5. Map each `AdminRouteMeta` node to a RR route:
   - Parent groups use `path: "/auth"` / `path: "/log"` / `path: "/account"` with `handle: { name, access }` and **relative** child paths (`menu`, `resource`, `role`, `operation`, `login`, `settings`) so final URLs remain `/auth/menu`, `/log/operation`, etc.
   - Parent group routes have **no** `element` (RR renders child through an implicit outlet); leaves supply the lazy page element.
6. `/article/edit` and `*` stay siblings under `ProtectedRoute` but **outside** `AdminLayout` (`layout: false`).
7. `/` → `<Navigate to="/article" replace />`.
8. `basename: "/admin"`.

```tsx
import { createBrowserRouter, Navigate, type RouteObject } from "react-router-dom";
import { UnauthorizedBridge } from "@/app/UnauthorizedBridge";
import AdminLayout from "@/layouts/AdminLayout";
import { ProtectedRoute } from "./ProtectedRoute";
import { routeMeta, type AdminRouteMeta } from "./routeMeta";

function toRelativeChildPath(parentPath: string, childPath: string) {
  const prefix = parentPath.endsWith("/") ? parentPath : `${parentPath}/`;
  return childPath.startsWith(prefix) ? childPath.slice(prefix.length) : childPath.replace(/^\//, "");
}

function metaToRoute(route: AdminRouteMeta): RouteObject {
  const handle = { name: route.name, access: route.access };
  if (route.children?.length) {
    return {
      path: route.path,
      handle,
      children: route.children.map((child) => {
        const childRoute = metaToRoute(child);
        return { ...childRoute, path: toRelativeChildPath(route.path, child.path) };
      }),
    };
  }
  if (route.redirect) {
    return { path: route.path, handle, element: <Navigate to={route.redirect} replace /> };
  }
  const Page = route.component!;
  return {
    path: route.path,
    handle,
    element: (
      <Suspense fallback={null}>
        <Page />
      </Suspense>
    ),
  };
}

// Split routeMeta into layout vs layout-false vs login, then assemble routeObjects.
export const router = createBrowserRouter(routeObjects, { basename: "/admin" });
```

Implementers must assemble `routeObjects` so layout leaves/groups nest under `<AdminLayout />`, while `/article/edit`, `*`, and `/login` follow the Task 4 placement rules. Add the missing `Suspense` import from React.

- [ ] **Step 5: Implement the explicit ProLayout shell from the nested tree**

In `src/layouts/AdminLayout.tsx`, preserve current `src/app.tsx` chrome: `layoutBgImgList`, `menuHeaderRender: undefined`, avatar dropdown, SettingDrawer, and document title from `defaultSettings.title`. Build menus with `buildMenuRoutes(routeMeta, access)` so `/auth` and `/log` remain nested groups:

```tsx
import { ProLayout, SettingDrawer } from "@ant-design/pro-components";
import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import Footer from "@/components/bases/Footer";
import defaultSettings from "@/config/defaultSettings";
import { buildMenuRoutes, routeMeta } from "@/router/routeMeta";
import { useAuthorization } from "@/app/access";
import { AvatarDropdown, AvatarName } from "@/components/bases/RightContent/AvatarDropdown";
import { getAvatarUrlByEmail } from "@/utils/avatar";
import { useAuth } from "@/app/auth";

const layoutBgImgList = [
  {
    src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr",
    left: 85,
    bottom: 100,
    height: "303px",
  },
  {
    src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr",
    bottom: -68,
    right: -45,
    height: "303px",
  },
  {
    src: "https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr",
    bottom: 0,
    left: 0,
    width: "331px",
  },
];

export default function AdminLayout() {
  const location = useLocation();
  const navigate = useNavigate();
  const access = useAuthorization();
  const { currentUser } = useAuth();
  const [settings, setSettings] = useState(defaultSettings);
  const menuRoutes = buildMenuRoutes(routeMeta, access);
  return (
    <ProLayout
      {...settings}
      location={location}
      route={{ routes: menuRoutes }}
      layoutBgImgList={layoutBgImgList}
      menuHeaderRender={undefined}
      avatarProps={{
        src:
          getAvatarUrlByEmail(currentUser?.user.email ?? "") ??
          "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png",
        title: <AvatarName />,
        render: (_, avatarChildren) => <AvatarDropdown menu>{avatarChildren}</AvatarDropdown>,
      }}
      menuItemRender={(item, dom) => (item.path ? <Link to={item.path}>{dom}</Link> : dom)}
      onMenuHeaderClick={() => navigate("/article")}
      footerRender={() => <Footer />}
    >
      <Outlet />
      <SettingDrawer
        disableUrlParams
        enableDarkTheme
        settings={settings}
        onSettingChange={setSettings}
      />
    </ProLayout>
  );
}
```

- [ ] **Step 6: Create providers and browser entry with ConfigProvider zhCN**

Create `src/app/UnauthorizedBridge.tsx`:

```tsx
import { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { setUnauthorizedHandler } from "@/lib/http/unauthorized";

export function UnauthorizedBridge() {
  const navigate = useNavigate();
  useEffect(() => {
    setUnauthorizedHandler((path) => navigate(path, { replace: true }));
    return () => setUnauthorizedHandler(undefined);
  }, [navigate]);
  return <Outlet />;
}
```

In `src/router/routes.tsx`, wrap the entire route tree (login + protected) with `<Route element={<UnauthorizedBridge />}>...</Route>` so 401 redirects use React Router.

Create `src/App.tsx` with both Ant Design `ConfigProvider` and `react-intl` `IntlProvider`. Default locale is `"zh-CN"`. Messages come from the existing barrel `src/locales/zh-CN.ts`, which statically merges `./zh-CN/{pages,globalHeader,menu,settingDrawer,settings,pwa,component}` plus the top-level nav strings already defined in that file.

```tsx
import { App as AntdApp, ConfigProvider } from "antd";
import antdZhCN from "antd/locale/zh_CN";
import { Helmet, HelmetProvider } from "react-helmet-async";
import { IntlProvider } from "react-intl";
import { RouterProvider } from "react-router-dom";
import { AccessProvider } from "@/app/access";
import { AuthProvider } from "@/app/auth";
import defaultSettings from "@/config/defaultSettings";
import zhCNMessages from "@/locales/zh-CN";
import { router } from "@/router/routes";

const defaultLocale = "zh-CN";

export default function App() {
  return (
    <HelmetProvider>
      <IntlProvider locale={defaultLocale} defaultLocale={defaultLocale} messages={zhCNMessages}>
        <ConfigProvider locale={antdZhCN}>
          <AntdApp>
            <Helmet>
              <title>{defaultSettings.title}</title>
            </Helmet>
            <AuthProvider>
              <AccessProvider>
                <RouterProvider router={router} />
              </AccessProvider>
            </AuthProvider>
          </AntdApp>
        </ConfigProvider>
      </IntlProvider>
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
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
```

- [ ] **Step 7: Verify route and shell tests**

Run:

```bash
npm test -- src/router src/layouts
npm run typecheck
```

Expected: nested route inventory (`/auth`, `/log`, `/account` groups), `/log` menu nesting, ProtectedRoute parent-inheritance tests, and layout shell tests pass; TypeScript exposes only remaining Umi imports in business files; Ant Design + zh-CN strings render.

### Task 6: Migrate Umi Runtime Imports Without Changing Business Logic

**Files:**
- Move: `Jewix-Front/admin/config/table.ts` → `Jewix-Front/admin/src/config/table.ts`
- Modify (all `/config/table` imports → `@/config/table`):
  - `Jewix-Front/admin/src/pages/article/index.tsx`
  - `Jewix-Front/admin/src/pages/auth/menu/index.tsx`
  - `Jewix-Front/admin/src/pages/auth/resource/index.tsx`
  - `Jewix-Front/admin/src/pages/auth/role/index.tsx`
  - `Jewix-Front/admin/src/pages/blogroll/index.tsx`
  - `Jewix-Front/admin/src/pages/category/index.tsx`
  - `Jewix-Front/admin/src/pages/comment/index.tsx`
  - `Jewix-Front/admin/src/pages/log/login/index.tsx`
  - `Jewix-Front/admin/src/pages/log/operation/index.tsx`
  - `Jewix-Front/admin/src/pages/page/index.tsx`
  - `Jewix-Front/admin/src/pages/tag/index.tsx`
- Modify (BASE_URL / same-origin API):
  - `Jewix-Front/admin/src/services/api/photo.ts`
  - `Jewix-Front/admin/src/components/bases/upload/UploadImage.tsx`
- Modify: `Jewix-Front/admin/src/components/bases/editor/BundledEditor.tsx`
- Create: `Jewix-Front/admin/src/pages/login/index.test.tsx`
- Modify: `Jewix-Front/admin/src/pages/login/index.tsx`
- Modify: `Jewix-Front/admin/src/pages/404.tsx`
- Modify: `Jewix-Front/admin/src/pages/article/**/*.tsx`
- Modify: `Jewix-Front/admin/src/pages/user/index.tsx`
- Modify: `Jewix-Front/admin/src/pages/account/settings/index.tsx`
- Modify: `Jewix-Front/admin/src/components/bases/RightContent/index.tsx` (local `SelectLang`)
- Modify: `Jewix-Front/admin/src/components/bases/RightContent/AvatarDropdown.tsx` (`clearUser` + object `navigate`)
- Modify: `Jewix-Front/admin/src/components/bases/hasOperation/hasOperation.tsx`
- Modify: `Jewix-Front/admin/src/hooks/auth.ts`
- Modify: `Jewix-Front/admin/src/services/**/*.ts`
- Modify: `Jewix-Front/admin/src/utils/request.ts`
- Modify: `Jewix-Front/admin/src/config/defaultSettings.ts` (set `pwa: false`)
- Delete: `Jewix-Front/admin/src/app.tsx` (Umi runtime entry; replaced by `src/App.tsx` + `src/main.tsx` + `src/layouts/AdminLayout.tsx`)
- Delete: `Jewix-Front/admin/src/global.tsx`
- Delete: `Jewix-Front/admin/src/pages/Admin.tsx`

Do **not** create `src/vite-env.d.ts`. Task 2 `tsconfig.json` already sets `"types": ["vite/client"]`, and Vite's client types already declare `*?raw`.

- [ ] **Step 1: Replace navigation imports**

Apply these mechanical mappings only in non-generated source:

```text
history.push(path) -> const navigate = useNavigate(); navigate(path)
history.replace(path) -> navigate(path, { replace: true })
history.replace({ pathname, search }) -> navigate({ pathname, search }, { replace: true })
history.back() -> navigate(-1)
Link from @umijs/max -> Link from react-router-dom
Helmet from @umijs/max -> Helmet from react-helmet-async
```

Concrete `src/pages/article/edit/index.tsx` conversions (only `history.*` hit for `back()` in admin src):

```typescript
import { useNavigate } from "react-router-dom";
import { useAuthorization } from "@/app/access";

const navigate = useNavigate();
const { normalRouteFilter } = useAuthorization();

if (!normalRouteFilter({ name: "文章编辑" })) {
  navigate("/exception/403");
}

// onReset
onReset={() => navigate("/article")}

// after successful save (was history.back())
navigate(-1);
```

Concrete AvatarDropdown logout conversion (current object-form `history.replace`):

```typescript
import { stringify } from "querystring";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/app/auth";

const navigate = useNavigate();
const { clearUser } = useAuth();

// inside loginOut after LocalToken.remove():
navigate(
  {
    pathname: "/login",
    search: stringify({
      redirect: pathname.replace("/admin", "") + search,
    }),
  },
  { replace: true },
);
```

Before navigate, logout menu handler must call `clearUser()` in place of `setInitialState((s) => ({ ...s, currentUser: undefined }))`.

Run `npm run typecheck` after this mapping and fix hook placement without introducing global mutable navigation.

- [ ] **Step 2: Replace initial-state and access imports**

Apply:

```text
useModel("@@initialState") -> useAuth()
useAccess()                 -> useAuthorization()
useRouteProps()             -> explicit routeName prop or useRouteMeta()
setInitialState(...currentUser: user) -> setCurrentUser(user)
setInitialState(...currentUser: undefined) -> clearUser()
```

For operation controls, pass the route's existing Chinese `name` into `operationFilter({ name: routeName }, operation)` so permission matching remains identical to `src/access.ts`.

In `src/pages/article/edit/index.tsx`, keep the page-local gate exactly (status quo):

```typescript
const { normalRouteFilter } = useAuthorization();
const navigate = useNavigate();
if (!normalRouteFilter({ name: "文章编辑" })) {
  navigate("/exception/403");
}
```

Do not remap the permission name and do not delete this branch in this migration.

- [ ] **Step 3: Replace all Umi request imports and keep DELETE serializer**

In each service, replace:

```typescript
import { request } from "@umijs/max";
```

with:

```typescript
import { http } from "@/lib/http/client";
```

Convert calls using this exact shape, forwarding `skipErrorHandler` through `HttpRequestConfig`:

```typescript
const response = await http.request<ResponseType>({ url, method, params, data, skipErrorHandler });
return response.data;
```

When rewriting `src/services/api/user.ts`, fix the existing gap where `fetchCurrentUserInfo(options)` ignored `options`. Use:

```typescript
export function fetchCurrentUserInfo(options?: HttpRequestConfig) {
  return http
    .request<API.ResponseStructure<API.FetchUserDetailResponse>>({
      url: "users/current",
      method: "get",
      ...options,
    })
    .then((response) => response.data);
}
```

Update `src/utils/request.ts` factories to use `http` and wire `paramsSerializer: serializeParams` inside `deleteFactory` exactly as Task 3 Step 9. Preserve every exported service function name, argument, and return type.

- [ ] **Step 4: Migrate absolute `/config/*` imports (13 call sites)**

Move `config/table.ts` to `src/config/table.ts`. Replace every:

```typescript
import { tableConfig } from "/config/table";
```

with:

```typescript
import { tableConfig } from "@/config/table";
```

in the 11 page files listed above.

For BASE_URL, prefer same-origin `/api` (Vite proxy in dev, Nginx in prod). In `src/services/api/photo.ts` replace:

```typescript
import { BASE_URL } from "/config/proxy";
export const getInsertImageUrl = (type = PhotoEnums.type.又拍云) =>
  `${BASE_URL}/api/admin/photos?type=${type}`;
export const getFetchImageUrl = (res: API.FetchPhotosResponse) =>
  res.url ?? `${BASE_URL}/api/photos/download?name=${res.fileName}`;
```

with:

```typescript
export const getInsertImageUrl = (type = PhotoEnums.type.又拍云) =>
  `/api/admin/photos?type=${type}`;
export const getFetchImageUrl = (res: API.FetchPhotosResponse) =>
  res.url ?? `/api/photos/download?name=${res.fileName}`;
```

In `UploadImage.tsx`, delete the unused `/config/proxy` import. Do not keep a client-side `BASE_URL` constant; the only remaining backend origin is `vite.config.ts` `server.proxy["/api"].target`.

Verify with:

```bash
rg 'from ["/'"'"']/config/|BASE_URL' src --glob '!**/.umi*/**'
```

Expected: zero matches.

- [ ] **Step 5: Convert BundledEditor raw-loader to Vite `?raw`**

In `src/components/bases/editor/BundledEditor.tsx`, replace:

```typescript
import contentCss from "!!raw-loader!tinymce/skins/content/default/content.min.css";
import contentUiCss from "!!raw-loader!tinymce/skins/ui/oxide/content.min.css";
```

with:

```typescript
import contentCss from "tinymce/skins/content/default/content.min.css?raw";
import contentUiCss from "tinymce/skins/ui/oxide/content.min.css?raw";
```

Keep the existing `content_style: [contentCss, contentUiCss].join("\n")` usage unchanged. Do not add `src/vite-env.d.ts`; rely on Task 2 `types: ["vite/client"]`.

- [ ] **Step 6: Replace Umi locale APIs and implement local SelectLang**

`App.tsx` already wraps `IntlProvider` (Task 5). Replace `useIntl` / `FormattedMessage` imports from `@umijs/max` with `react-intl`. Do not change message IDs.

Rewrite `src/components/bases/RightContent/index.tsx` so `SelectLang` no longer wraps `@umijs/max`. Keep the exported name `SelectLang` and the same padding style. Minimal API:

```tsx
import { GlobalOutlined, QuestionCircleOutlined } from "@ant-design/icons";
import { Dropdown } from "antd";
import type { MenuProps } from "antd";
import React from "react";

const locales = [
  { key: "zh-CN", label: "简体中文" },
  { key: "en-US", label: "English" },
] as const;

export type SelectLangProps = {
  style?: React.CSSProperties;
};

export const SelectLang = ({ style }: SelectLangProps) => {
  const items: MenuProps["items"] = locales.map((locale) => ({
    key: locale.key,
    label: locale.label,
  }));
  return (
    <Dropdown menu={{ items, selectedKeys: ["zh-CN"] }} placement="bottomRight">
      <span style={{ padding: 4, cursor: "pointer", ...style }}>
        <GlobalOutlined />
      </span>
    </Dropdown>
  );
};

export const Question = () => {
  return (
    <div
      style={{ display: "flex", height: 26 }}
      onClick={() => {
        window.open("https://pro.ant.design/docs/getting-started");
      }}
    >
      <QuestionCircleOutlined />
    </div>
  );
};
```

Login page currently imports `SelectLang` from `@umijs/max`. Change it to:

```typescript
import { SelectLang } from "@/components/bases/RightContent";
```

Mechanical verification (no separate SelectLang unit test required):

```bash
rg 'SelectLang|@umijs/max' src/pages/login/index.tsx src/components/bases/RightContent/index.tsx
```

Expected: login imports local `SelectLang`; RightContent has no `@umijs/max` import.

- [ ] **Step 7: Write login-page TDD for Auth write + navigate + failure dual-channel**

Create `src/pages/login/index.test.tsx` before finishing the login rewrite. Mock `@/services/api/user` and assert real page behavior (not only the HTTP client):

```tsx
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { message } from "antd";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { AuthContext, type AuthState } from "@/app/auth";
import Login from "./index";

const login = vi.fn();
const fetchCurrentUserInfo = vi.fn();

vi.mock("@/services/api/user", () => ({
  login: (...args: unknown[]) => login(...args),
  fetchCurrentUserInfo: (...args: unknown[]) => fetchCurrentUserInfo(...args),
}));

vi.mock("@/utils/token", () => ({
  LocalToken: {
    set: vi.fn(),
    get: vi.fn(),
    remove: vi.fn(),
  },
}));

function renderLogin(auth: Partial<AuthState> = {}) {
  const setCurrentUser = vi.fn();
  const value: AuthState = {
    currentUser: undefined,
    loading: false,
    refreshUser: vi.fn(async () => undefined),
    setCurrentUser,
    clearUser: vi.fn(),
    ...auth,
  };
  render(
    <AuthContext.Provider value={value}>
      <MemoryRouter initialEntries={["/login?redirect=%2Farticle"]}>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/article" element={<div>article-home</div>} />
        </Routes>
      </MemoryRouter>
    </AuthContext.Provider>,
  );
  return { setCurrentUser };
}

describe("Login page", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("writes currentUser and navigates to redirect on success", async () => {
    const userInfo = { user: { id: 1, nickname: "aom" }, menuListPageDTO: [] };
    login.mockResolvedValue({ msg: "success", token: "t-1" });
    fetchCurrentUserInfo.mockResolvedValue({ status: userInfo });
    const { setCurrentUser } = renderLogin();
    await userEvent.type(screen.getByLabelText(/username|账户|用户/i), "aom");
    await userEvent.type(screen.getByLabelText(/password|密码/i), "secret");
    await userEvent.click(screen.getByRole("button", { name: /登录|login/i }));
    await waitFor(() => expect(setCurrentUser).toHaveBeenCalledWith(userInfo));
    expect(await screen.findByText("article-home")).toBeInTheDocument();
  });

  it("failure dual-channel: login error toasts; post-login current-user uses skipErrorHandler", async () => {
    const errorSpy = vi.spyOn(message, "error").mockImplementation(() => undefined as never);
    login.mockRejectedValue(Object.assign(new Error("bad credentials"), { name: "ApiError" }));
    renderLogin();
    await userEvent.type(screen.getByLabelText(/username|账户|用户/i), "aom");
    await userEvent.type(screen.getByLabelText(/password|密码/i), "bad");
    await userEvent.click(screen.getByRole("button", { name: /登录|login/i }));
    await waitFor(() => expect(login).toHaveBeenCalled());
    expect(fetchCurrentUserInfo).not.toHaveBeenCalled();
    errorSpy.mockClear();
    login.mockResolvedValue({ msg: "success", token: "t-2" });
    fetchCurrentUserInfo.mockImplementation(async (options?: { skipErrorHandler?: boolean }) => {
      expect(options?.skipErrorHandler).toBe(true);
      throw Object.assign(new Error("expired"), { name: "UnauthorizedError" });
    });
    await userEvent.click(screen.getByRole("button", { name: /登录|login/i }));
    await waitFor(() => expect(fetchCurrentUserInfo).toHaveBeenCalled());
    expect(fetchCurrentUserInfo.mock.calls.at(-1)?.[0]).toMatchObject({ skipErrorHandler: true });
  });
});
```

Run RED then implement the login rewrite using `useAuth().setCurrentUser`, `LocalToken.set`, and `navigate(redirect || "/")`. Re-run:

```bash
npm test -- src/pages/login/index.test.tsx
```

Expected: both login-page tests pass. Adjust label matchers only if the existing ProForm field labels require exact zh-CN strings from `src/locales/zh-CN/pages.ts`.

- [ ] **Step 8: Delete Umi runtime entry, global bootstrap, and unused Admin scaffold**

After Steps 1–7 have moved layout/auth/request/locale off Umi, delete these three `src` files together:

1. `src/app.tsx` — Umi `getInitialState` / `layout` / `request` export; superseded by `src/App.tsx`, `src/main.tsx`, `src/layouts/AdminLayout.tsx`, and `src/lib/http/client.ts`.
2. `src/global.tsx` — Umi/Pro PWA service-worker listeners via `@umijs/max` `useIntl`. Set `pwa: false` in `src/config/defaultSettings.ts` first. Vite will not ship that SW; do not reintroduce a Service Worker in this migration.
3. `src/pages/Admin.tsx` — not referenced by `config/routes.ts`; unused Pro scaffold.

Do not leave `src/app.tsx` for Task 7; the Task 6 zero-Umi `src` gate below must see it gone.

- [ ] **Step 9: Verify zero Umi references under `src` (gate must be reachable now)**

Scope this gate to **`src` only**. `package.json` scripts/deps (`max build`, `UMI_ENV`, `@umijs/*`, `raw-loader`) and `config/*` / `jest.config.ts` remain until Task 7 by design.

Run:

```bash
rg '@umijs|useModel\(|useAccess\(|useRouteProps\(|raw-loader|/config/table|/config/proxy|!!raw-loader' src --glob '!**/.umi/**' --glob '!**/.umi-production/**'
test ! -e src/app.tsx
test ! -e src/global.tsx
test ! -e src/pages/Admin.tsx
```

Expected: `rg` prints no matches; the three `test ! -e` checks succeed; login imports `@/components/bases/RightContent` `SelectLang`.

Disposition of every current non-generated hit (verified by repo `rg` before this edit):

| Path | Task 6 disposition |
|---|---|
| `src/services/api/{user,article,tag,category,photo}.ts`, `src/services/ant-design-pro/{api,login}.ts`, `src/services/swagger/{pet,store,user}.ts`, `src/utils/request.ts` | Step 3 → `http` (+ photo BASE_URL in Step 4) |
| `src/components/bases/upload/UploadImage.tsx` | Step 4 drop `/config/proxy` |
| `src/pages/**/index.tsx` with `/config/table` (11 files) | Step 4 → `@/config/table` |
| `src/components/bases/editor/BundledEditor.tsx` | Step 5 `?raw` |
| `src/pages/login/index.tsx`, `src/pages/404.tsx`, `src/pages/article/index.tsx`, `src/pages/article/edit/index.tsx`, `src/pages/article/components/ProTableToolBar.tsx`, `src/pages/user/index.tsx`, `src/pages/account/settings/index.tsx`, `src/components/bases/RightContent/{index,AvatarDropdown}.tsx`, `src/components/bases/hasOperation/hasOperation.tsx`, `src/hooks/auth.ts` | Steps 1–2 + 6–7 |
| `src/app.tsx`, `src/global.tsx`, `src/pages/Admin.tsx` | Step 8 delete |
| `package.json` scripts/deps, `config/{config,routes,request,proxy}.ts`, `jest.config.ts`, `src/.umi*` | **Task 7 only** — intentionally outside this gate |

- [ ] **Step 10: Run focused and full tests**

Run:

```bash
npm test
npm run typecheck
npm run build
```

Expected: login-page Auth write/navigate tests, migrated services, and shell tests pass; Vite emits `dist/` under `/admin/` asset URLs; TinyMCE skins load from `?raw` strings.

### Task 7: Remove Umi, Legacy Pro Tooling, And Generated Files

**Files:**
- Delete: `Jewix-Front/admin/config/config.ts`
- Delete: `Jewix-Front/admin/config/routes.ts`
- Delete: `Jewix-Front/admin/config/request.ts`
- Delete: `Jewix-Front/admin/config/proxy.ts`
- Delete: `Jewix-Front/admin/config/table.ts` (only if the Task 6 move left a stub)
- Delete: `Jewix-Front/admin/jest.config.ts`
- Delete: `Jewix-Front/admin/src/.umi/`
- Delete: `Jewix-Front/admin/src/.umi-production/`
- Confirm already deleted in Task 6 (do not delete again): `Jewix-Front/admin/src/app.tsx`, `Jewix-Front/admin/src/global.tsx`, `Jewix-Front/admin/src/pages/Admin.tsx`
- Delete if unreferenced: `Jewix-Front/admin/config/oneapi.json`
- Modify: `Jewix-Front/admin/package.json`
- Modify: `Jewix-Front/admin/package-lock.json`

- [ ] **Step 1: Remove obsolete packages through npm**

Run:

```bash
npm uninstall @umijs/max @umijs/lint @umijs/route-utils umi-presets-pro @ant-design/pro-cli @types/history raw-loader jest jest-environment-jsdom ts-node react-dev-inspector tailwindcss gh-pages
```

`less` is currently only transitive via Umi. After the uninstall above, remove any remaining Less package and verify it is gone:

```bash
npm uninstall less less-loader || true
npm ls less
```

Expected: `npm ls less` reports the package is not found / empty (no Less left in the admin tree). Task 2 already migrated the three source `.less` files to SCSS, so no source import should require Less.

Remove `i18n-remove`, Umi environment scripts, `postinstall: max setup`, Jest scripts, and gh-pages scripts from `package.json`. Deployment continues to consume the Vite `dist/` output through the existing outer server/Nginx path.

- [ ] **Step 2: Delete generated and superseded Umi files**

Delete only the Task 7 file list after the Vite build passes. Preserve `defaultSettings.ts` and `table.ts` only under `src/config/`. Confirm `config/proxy.ts` is gone and no source imports `BASE_URL`. Confirm `src/app.tsx` / `src/global.tsx` / `src/pages/Admin.tsx` were already removed in Task 6 (not present to delete again).

After scripts/deps cleanup, close the package-level gate left open by Task 6 Step 9:

```bash
rg '@umijs|max build|umi-serve|UMI_ENV|raw-loader|umi-presets-pro|@ant-design/pro-cli' package.json config jest.config.ts
```

Expected: no matches.

- [ ] **Step 3: Remove obsolete aliases and generated includes**

Verify `tsconfig.json` still has no `@@/*`, `@@test/*`, or repository-root `/*` alias, and that Task 2 `exclude` still contains `node_modules`, `src/.umi`, and `src/.umi-production` until those directories are deleted in this task.

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

Expected: root redirects to article; login is public; `/article/edit` and unknown paths require login (edit has no route-level `access`); access-denied direct URLs for routes that already declare `normalRouteFilter` redirect to `/article`; nested menus render; authenticated unknown paths render 404; document title is `Jewix博客系统后台管理`; Ant Design + react-intl zh-CN strings render; static assets use one `/admin/` prefix; console has no runtime errors.

- [ ] **Step 5: Verify proxy and request behavior with the API available**

Check one read request, one DELETE with array ids (`ids=1&ids=2`), token injection, business `message.error`, login failure toast, current-user init with `skipErrorHandler`, and 401 redirect. Expected: service payloads and response shapes match the pre-migration application.

- [ ] **Step 6: Final review checkpoint**

Run:

```bash
git status --short
git diff --stat
git diff --check
```

Expected: only the approved Vite migration, dependency upgrades, both plans/spec, and pre-existing approved dependency changes. Do not commit or push without explicit user instruction.
