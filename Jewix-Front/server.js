const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require("express");
const app = express();
const path = require('path');

// 前台页面代理
const proxyDefault = createProxyMiddleware('/', {
    target: 'http://localhost:3000',
    changeOrigin: true
});
// api代理
const proxyAPI = createProxyMiddleware('/api', {
    target: 'http://localhost:7891',
    changeOrigin: true
});

app.use('/admin', express.static(path.join(__dirname, 'admin/dist')))
app.use('/admin/*', function (req, res) {
    res.sendFile(path.join(__dirname, 'admin/dist/index.html'));
});
app.use('/api', proxyAPI);
app.use("/", proxyDefault);

// 启动代理服务
app.listen(8080, () => {
    console.log('Proxy server is running at http://localhost:8080');
});