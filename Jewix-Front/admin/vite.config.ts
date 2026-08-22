import { fileURLToPath, URL } from "node:url";
import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

const umiMaxStub = fileURLToPath(new URL("./src/shims/umijs-max.tsx", import.meta.url));
const configDir = fileURLToPath(new URL("./config", import.meta.url));

export default defineConfig({
  base: "/admin/",
  plugins: [react()],
  resolve: {
    alias: [
      { find: "@", replacement: fileURLToPath(new URL("./src", import.meta.url)) },
      { find: "@umijs/max", replacement: umiMaxStub },
      { find: /^\/config\//, replacement: `${configDir}/` },
    ],
  },
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:7891",
        changeOrigin: true,
      },
    },
  },
  build: {
    outDir: "dist",
    sourcemap: false,
  },
});
