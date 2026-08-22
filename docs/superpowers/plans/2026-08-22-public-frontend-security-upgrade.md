# Public Frontend Security Upgrade Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Upgrade the public Pages Router application to secure Next.js 16, React 19, Sharp, and Node versions with a reproducible lockfile and zero npm audit findings.

**Architecture:** Keep the Pages Router, SSR data flow, API routes, and custom Express proxy unchanged. Run Next 16 with Webpack because the project uses a custom alias, migrate removed configuration options, and validate the existing routes and image sources without introducing App Router code.

**Tech Stack:** Node.js 22.12+, npm, Next.js 16, React 19, TypeScript 5.9, Sharp 0.35, Webpack, Sass.

---

### Task 1: Capture The Security And Build Failure Baseline

**Files:**
- Inspect: `Jewix-Front/package.json`
- Inspect: `Jewix-Front/package-lock.json`
- Inspect: `Jewix-Front/next.config.js`

- [ ] **Step 1: Record the current dependency versions**

Run:

```bash
cd Jewix-Front
node --version
npm --version
npm ls next react react-dom sharp postcss --all
```

Expected: Node is at least `22.12.0`; Next is `15.5.21`; React is `18.2.0`; Sharp is below `0.35.0`.

- [ ] **Step 2: Run the full online audit and verify the security gate fails**

Run:

```bash
npm audit --registry=https://registry.npmjs.org --audit-level=low
```

Expected: non-zero exit with the Sharp/Next vulnerability chain. Do not use `--omit=dev`, `--omit=optional`, or `--force`.

- [ ] **Step 3: Run the pre-upgrade build**

Run:

```bash
npm run build
```

Expected: PASS on Next 15.5.21. Preserve the output as the route-size and warning baseline.

- [ ] **Step 4: Review checkpoint**

Run:

```bash
git status --short
git diff -- Jewix-Front/package.json Jewix-Front/package-lock.json Jewix-Front/next.config.js
```

Expected: no new source changes from baseline commands. Do not commit unless the user explicitly requests it.

### Task 2: Upgrade The Runtime And Framework Dependencies

**Files:**
- Modify: `Jewix-Front/package.json`
- Modify: `Jewix-Front/package-lock.json`

- [ ] **Step 1: Replace the framework and runtime declarations**

Update the relevant `package.json` entries to:

```json
{
  "scripts": {
    "dev": "next dev --webpack",
    "build": "next build --webpack",
    "start": "next start",
    "typecheck": "tsc --noEmit",
    "server": "cd admin && npm run build && cd .. && concurrently \"npm run build && npm run start \"  \"node server.js\""
  },
  "dependencies": {
    "@types/node": "22.18.6",
    "@types/react": "19.2.14",
    "@types/react-dom": "19.2.3",
    "next": "16.3.2",
    "react": "19.2.8",
    "react-dom": "19.2.8",
    "sharp": "0.35.3",
    "typescript": "5.9.3"
  },
  "engines": {
    "node": ">=22.12.0"
  }
}
```

Keep all unrelated dependency entries and the existing PostCSS override. Remove the obsolete `lint: next lint` script because Next 16 no longer provides that command; lint modernization is outside this security-only plan.

- [ ] **Step 2: Regenerate the root lockfile through npm**

Run:

```bash
npm install --registry=https://registry.npmjs.org
```

Expected: install succeeds without `--legacy-peer-deps`; `package-lock.json` records Next `16.3.2`, React `19.2.8`, and Sharp `0.35.3`.

- [ ] **Step 3: Verify the installed tree uses only secure framework versions**

Run:

```bash
npm ls next react react-dom sharp postcss --all
```

Expected: no invalid peer dependencies; no Sharp below `0.35.0`; no PostCSS at or below `8.5.22`.

- [ ] **Step 4: Run the typecheck to expose migration errors**

Run:

```bash
npm run typecheck
```

Expected: FAIL only on concrete Next 16, React 19, or third-party type incompatibilities. Save every error before changing source.

- [ ] **Step 5: Review checkpoint**

Run:

```bash
git diff --check
git diff -- Jewix-Front/package.json Jewix-Front/package-lock.json
```

Expected: dependency-only changes and no whitespace errors. Do not commit unless requested.

### Task 3: Migrate Next Configuration

**Files:**
- Modify: `Jewix-Front/next.config.js`

- [ ] **Step 1: Run the Next 16 build before changing configuration**

Run:

```bash
npm run build
```

Expected: FAIL or warn about removed `images.domains`, obsolete Sass `prependData`, or custom Webpack behavior. This is the red migration check.

- [ ] **Step 2: Replace deprecated image and Sass configuration**

Set `next.config.js` to:

```javascript
const { join } = require("path");

/** @type {import("next").NextConfig} */
const nextConfig = {
  webpack: (config) => {
    config.resolve.alias["@styles"] = join(__dirname, "src/styles");
    return config;
  },
  reactStrictMode: true,
  sassOptions: {
    additionalData: '@import "@styles/variables";'
  },
  images: {
    remotePatterns: [
      { protocol: "https", hostname: "img.say521.cn" },
      { protocol: "https", hostname: "aomblogtu.test.upcdn.net" },
      { protocol: "http", hostname: "localhost" },
      { protocol: "https", hostname: "www.gravatar.com" }
    ]
  }
};

module.exports = nextConfig;
```

- [ ] **Step 3: Re-run the production build**

Run:

```bash
npm run build
```

Expected: Next 16 production build reaches type checking and page compilation without configuration errors.

- [ ] **Step 4: Verify remote image hosts are not broadened**

Run:

```bash
git diff -- Jewix-Front/next.config.js
```

Expected: exactly four host patterns, with HTTP allowed only for localhost.

### Task 4: Verify Pages Router And React 19 Compatibility

**Files:**
- Verify: `Jewix-Front/src/pages/_app.tsx`
- Verify: `Jewix-Front/src/pages/index.tsx`
- Verify: `Jewix-Front/src/pages/[url].tsx`
- Verify: `Jewix-Front/src/pages/detail/[id].tsx`
- Verify: `Jewix-Front/src/pages/category/[id].tsx`
- Verify: `Jewix-Front/src/components/bases/card-carousel/CardCarousel.tsx`

- [ ] **Step 1: Run the full typecheck**

Run:

```bash
npm run typecheck
```

Expected: PASS. The project uses Pages Router APIs that remain supported by Next 16, and the prior Next 15 build already validated the Swiper 12 import.

- [ ] **Step 2: Verify removed React and Next APIs are absent**

Run:

```bash
rg 'ReactDOM\.render|findDOMNode|next/legacy/image|next lint|images:\s*\{\s*domains' src package.json next.config.js
```

Expected: no matches.

- [ ] **Step 3: Run the production build**

Run:

```bash
npm run build
```

Expected: PASS with the existing Pages Router route list and no React peer-dependency errors.

- [ ] **Step 4: Confirm the upgrade did not rewrite application behavior**

Run:

```bash
git diff -- src/pages src/components src/servers src/utils
```

Expected: no new behavior edits from the framework upgrade beyond the already-approved Swiper module import.

### Task 5: Prove Clean Installation And Security

**Files:**
- Verify: `Jewix-Front/package.json`
- Verify: `Jewix-Front/package-lock.json`

- [ ] **Step 1: Perform a clean lockfile installation**

Run:

```bash
npm ci --registry=https://registry.npmjs.org
```

Expected: install succeeds without peer overrides or lockfile modification.

- [ ] **Step 2: Run the complete online audit**

Run:

```bash
npm audit --registry=https://registry.npmjs.org --audit-level=low
```

Expected: exit zero and `found 0 vulnerabilities`.

- [ ] **Step 3: Run all static and production checks**

Run:

```bash
npm run typecheck
npm run build
npm ls --all
git diff --check
```

Expected: all commands exit zero; `npm ls` has no invalid or extraneous packages.

- [ ] **Step 4: Start the production server for browser smoke tests**

Run:

```bash
npm run start
```

Expected: Next listens on `http://localhost:3000`.

- [ ] **Step 5: Browser smoke-test the public application**

Verify with the browser at desktop and mobile widths:

```text
/
/detail/<existing-id>
/category/<existing-id>
/<existing-page-url>
/404
/api/hello
```

Expected: home and dynamic pages render, carousel controls load, remote images are accepted, 404 behavior remains intact, and the console has no React hydration/runtime errors. If backend data is unavailable, verify the stable `_app` fallback rather than treating network refusal as a frontend failure.

- [ ] **Step 6: Final review checkpoint**

Run:

```bash
git status --short
git diff --stat
git diff --check
```

Expected: only intended public security-upgrade files plus previously approved worktree changes. Do not commit or push without explicit user instruction.
