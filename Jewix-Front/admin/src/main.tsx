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
