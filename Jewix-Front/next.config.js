const { join } = require('path');

/** @type {import('next').NextConfig} */
const nextConfig = {
  // 不能使用绝对路径来引入 SCSS 文件，可以尝试使用 Webpack 的别名（alias）功能来简化路径。
  webpack: (config) => {
    config.resolve.alias['@styles'] = join(__dirname, 'src/styles');
    return config;
  },
  // 严格模式
  reactStrictMode: true,
  // sass配置（Next16 dart-sass 不走 webpack alias，用绝对路径注入 variables）
  sassOptions: {
    additionalData: `@import "${join(__dirname, 'src/styles/variables.scss').replace(/\\/g, '/')}";`,
  },
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'img.say521.cn',
      },
      {
        protocol: 'https',
        hostname: 'aomblogtu.test.upcdn.net',
      },
      {
        protocol: 'http',
        hostname: 'localhost',
      },
      {
        protocol: 'https',
        hostname: 'www.gravatar.com',
      },
    ],
  }
}

module.exports = nextConfig
