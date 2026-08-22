import { fileURLToPath, URL } from "node:url";
import react from "@vitejs/plugin-react";
import { defineConfig } from "vitest/config";

const umiMaxStub = fileURLToPath(new URL("./src/shims/umijs-max.tsx", import.meta.url));
const configDir = fileURLToPath(new URL("./config", import.meta.url));

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: [
      { find: "@", replacement: fileURLToPath(new URL("./src", import.meta.url)) },
      { find: "@umijs/max", replacement: umiMaxStub },
      { find: /^\/config\//, replacement: `${configDir}/` },
    ],
  },
  test: {
    environment: "jsdom",
    setupFiles: ["./src/test/setup.ts"],
    clearMocks: true,
  },
});
