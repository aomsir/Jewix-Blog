# Frontend Security Modernization Design

## Goal

Eliminate all npm security findings in both frontend applications while preserving the public site's URLs and SSR behavior and the admin application's production workflows.

Success means both complete dependency trees, including development dependencies, report `0 vulnerabilities` from the official npm registry.

## Constraints

- Use Node.js `>=22.12.0` for both frontend applications.
- Upgrade the public site to Next.js 16 and React 19 without converting the Pages Router to the App Router.
- Replace the admin application's Umi/Max build and runtime layer with Vite and React Router.
- Preserve the admin application's `/admin/` deployment base, routes, menus, authentication behavior, permissions, requests, pages, and Ant Design Pro Components UI.
- Remove obsolete development features supplied only by `@ant-design/pro-cli` and `umi-presets-pro`, including `pro i18n-remove`, `requestRecord`, and the unused OpenAPI preset.
- Do not include unrelated style or component refactoring.

## Architecture

### Public Site

The public site remains a Next.js Pages Router application. Next.js, React, React DOM, related type packages, and the Node engine requirement move to currently secure versions. Existing page routes, SSR data loading, API routes, and build output remain structurally unchanged.

Compatibility work is limited to APIs and configuration removed or changed by Next.js 16 and React 19. The migration does not introduce Server Components or rewrite pages into the App Router.

### Admin Application

The admin application retains its existing pages, components, services, styles, Ant Design, and Ant Design Pro Components. Umi-generated runtime behavior is replaced by a small explicit application shell made of:

- a Vite entry point and `/admin/` base configuration;
- a React Router route tree and lazy page imports;
- a ProLayout shell consuming the same route metadata as React Router;
- an authentication context that owns current-user initialization and refresh;
- an access context and protected-route component based on the existing `src/access.ts` rules;
- an Axios client that preserves token injection, response handling, notifications, and unauthorized redirects;
- route and top-level error boundaries.

Generated `.umi` and `.umi-production` code is not copied into the new architecture. Source imports from `@umijs/max` are replaced with focused imports from React Router, the local request client, and the local authentication/access contexts.

## Routing And Layout

The current `config/routes.ts` route information becomes an explicit typed route configuration. Each entry contains the path, lazy component, menu label, icon, visibility, layout mode, and required access rule.

React Router and ProLayout consume this single configuration so navigation and rendered routes cannot diverge. The route set remains:

- article list and edit;
- comments, pages, blogroll, categories, tags, photos, and users;
- account settings;
- menu, resource, and role authorization pages;
- operation and login logs;
- site settings;
- login, root redirect, and not-found routes.

Routes marked `layout: false` render outside ProLayout. Protected routes redirect unauthenticated users to `/login` while preserving the intended destination.

## Authentication And Access

`AuthProvider` calls the existing `fetchCurrentUserInfo` service during application initialization except on the login route. It exposes `currentUser`, `loading`, and `refreshUser`.

`AccessProvider` derives permissions from the existing access rules and current user. Route guards, menu filtering, and operation-level controls use the same access object. Existing `HasOperation` behavior is preserved by changing its data source from Umi's `useAccess` to the local context.

After login, users return to their original protected destination or `/article`. A 401 response clears the local token and redirects to `/login` once, avoiding redirect loops.

## Request Flow

The request layer remains Axios-based. A single client applies the existing base URL and timeout, reads `LocalToken` for authorization headers, preserves successful response shapes, displays existing error messages, and handles unauthorized responses.

Service modules import this local client instead of `@umijs/max`. Generated Swagger service files may keep their existing function signatures; only their request import changes.

## Error Handling

- Request interceptors handle transport errors, API errors, and authentication expiry.
- Route error boundaries isolate page rendering failures and provide a recoverable fallback.
- The top-level initialization fallback reports current-user initialization failures without rendering protected content.
- Errors are surfaced through the existing Ant Design notification/message UI and are not silently swallowed.

## Dependency And Lockfile Policy

Both applications commit their own npm lockfile. The admin `.gitignore` no longer excludes `package-lock.json`. Install and CI workflows use `npm ci` and the official npm registry for audit checks.

Dependencies used only for the removed Umi runtime and legacy Pro tooling are deleted. npm `overrides` are used only for patch-compatible transitive fixes with verified builds and tests; they are not used to force incompatible framework internals.

## Testing

The admin application uses current Vitest and Testing Library packages. Tests are written before the replacement shell behavior and cover:

- protected-route redirects and return destinations;
- current-user initialization and refresh;
- access denial and menu filtering;
- Axios token injection and one-time 401 redirect handling;
- route rendering for layout and layout-free pages.

The public application uses Next.js type checking and production builds as the framework migration regression gate. Browser smoke tests verify the public home, detail, and carousel views and the admin login route without console runtime errors.

## Acceptance Criteria

- `npm ci` succeeds independently in `Jewix-Front` and `Jewix-Front/admin`.
- Full `npm audit --registry=https://registry.npmjs.org` reports `0 vulnerabilities` in both directories.
- TypeScript checks and production builds pass in both applications.
- Admin Vitest tests pass.
- Browser smoke tests pass for the public home/detail/carousel flows and admin login page.
- Public URLs, admin routes, `/admin/` deployment base, authentication, permissions, requests, menus, and page behavior remain intact.
- `git diff --check` passes.
- Both lockfiles are tracked so GitHub Dependabot can rescan the complete dependency trees.

## Out Of Scope

- Converting the public site to the App Router.
- Redesigning pages or changing visual behavior.
- Refactoring backend APIs.
- Eliminating non-security style warnings unless they disappear as a direct result of removing the old toolchain.
