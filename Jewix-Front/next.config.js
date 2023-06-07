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
  // sass配置
  sassOptions: {
    // prependData 选项用来在每个 SCSS 文件的开头添加自定义的 SCSS 代码
    prependData: `@import "@styles/variables";`
  },
  images: {
    domains: ['img.say521.cn', "aomblogtu.test.upcdn.net", "localhost", "www.gravatar.com"],
  }
}

module.exports = nextConfig
