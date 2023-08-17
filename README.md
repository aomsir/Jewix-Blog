**简体中文** | [English](README_en.md)
<p align=center>
    <a href="https://www.say521.cn">
    <img src="https://avatars.githubusercontent.com/u/136668345?s=88&v=4"  width="200" hight="200" alt="Jewix博客系统" style="border-radius: 50%">
    </a>
</p>

<p align="center">
   <a target="_blank" href="#">
      <img style="display: inline-block;" src="https://img.shields.io/badge/JDK-8-blue"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/TypeScript-4.9.5-blue"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/SpringBoot-2.5.6-green"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/Mybatis-2.2.2-red"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/React-18.2.0-blue"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/Next.js-13.1.6-orange"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/Antd-5.2.2-pink"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/Redis-6.0-red"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/Docker-24.0.2-blue"/>
      <img style="display: inline-block;" src="https://img.shields.io/badge/Rocky Linux-8.5.0-yellow"/>
    </a>
</p>

# Jewix-Blog-System
Jewix - 一款简约、高效、多样化的前后端分离博客系统

# 项目特性
- <b>编辑器选择</b>：支持富文本编辑器和 Markdown 编辑器，让用户可根据个人喜好选择适合自己的编辑器
- <b>权限管理</b>：使用 Spring Security 实现 RBAC 权限管理，确保安全性和稳定性
- <b>分类与标签分类</b>：提供多种分类方式和标签分类功能，方便对文章进行管理、查找和分类
- <b>评论功能</b>：方便读者与博主进行交流、留言、提出建议和反馈
- <b>文章浏览量统计功能</b>：实时记录文章的浏览量，让博主了解文章受欢迎程度
- <b>响应式布局</b>：适配不同终端设备，使用起来更加便捷和愉悦
- <b>友情链接</b>：支持友情链接功能，为站点提供更丰富的内容和资源
- <b>页面功能</b>：提供静态页面、动态页面等多种页面功能，让用户可根据自己需求进行选择
- <b>服务端渲染</b>：支持服务端渲染，优化网站性能和用户体验
- <b>相册功能</b>：支持图片上传、浏览和下载，并提供多个相册分类方式，方便对照片进行管理和查找
- <b>登录日志管理</b>：记录用户的登录信息，方便管理员了解用户的操作行为和登录历史
- <b>操作日志管理</b>：记录系统的操作行为，方便管理员进行异常排查和审核操作记录
- <b>人员管理</b>：提供人员信息录入、修改和删除等管理功能，方便管理员进行人员管控
- <b>容器化部署</b>：支持 Docker 容器化部署，方便快速部署和管理博客系统

# 技术栈
- 前端：React、Next、AntDesign、AntDesignPro等
- 后端：SpringBoot、SpringMVC、SpringSecurity、Jasypt、JWT、Mybatis等
- 中间件：MySQL、Redis、ElasticSearch(计划)、RocketMQ(计划)
- 运维：Rocky Linux、Docker
- 其他：又拍云存储、OSS、COS、腾讯地图

# 快速开始
- 方式一：克隆项目，进入Jewix-Deploy构建Docker镜像启动即可
- 方式二：克隆项目，手动编译，分别启动前后端项目即可
- 注意：
  - 记得修改IP、SpringBoot配置文件等
  - 前端启动使用 npm run server
  - 后台默认账号：admin@say521.cn
  - 后台默认密码：123456
- TODO：后续会发布wiki，有问题先提issue

# 后续计划
- 优化后端性能
- 优化前端样式
- 接入Elasticsearch中间件
- 接入RocketMQ中间件
- 接入ChatGPT


# 注意事项
- Jewix使用 [GPL V3.0](https://github.com/aomsir/jewix-blog/blob/master/LICENSE)协议开源。请遵守此协议进行二次开发等
- Jewix1.0版本存在些许问题，等待后续版本的优化扩充
- 欢迎大家踊跃提出Pull Request

# 联系我们
> - 添加好友，备注Jewix博客进微信交流群

![联系我们](./assests/connect.jpg)
