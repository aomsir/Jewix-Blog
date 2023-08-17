[简体中文](README_zh.md) | **English**

<p align="center">
    <a href="https://www.say521.cn">
    <img src="https://avatars.githubusercontent.com/u/136668345?s=88&v=4"  width="200" height="200" alt="Jewix Blog System" style="border-radius: 50%">
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

# Jewix Blog System
Jewix - A minimalist, efficient, and versatile blog system with a decoupled frontend and backend.

## Features
- **Editor Choices**: Supports rich text editor and Markdown editor, allowing users to choose the editor that suits their preferences.
- **Permission Management**: Uses Spring Security for RBAC permission management to ensure security and stability.
- **Categories and Tag Classification**: Offers various categorization methods and tag classification features, making it easier to manage, find, and categorize articles.
- **Commenting Feature**: Enables readers to interact with the blog owner through comments, messages, suggestions, and feedback.
- **Article View Count**: Real-time recording of article views, allowing the blog owner to understand the popularity of articles.
- **Responsive Layout**: Adapts to different devices for a more convenient and pleasing user experience.
- **Friendly Links**: Supports friendly link feature, providing the site with richer content and resources.
- **Page Features**: Offers various page features such as static and dynamic pages, allowing users to choose according to their needs.
- **Server-Side Rendering**: Supports server-side rendering to optimize website performance and user experience.
- **Album Feature**: Supports image upload, viewing, and downloading, and offers various album categorization methods for easier management and search of photos.
- **Login Log Management**: Records user login information, making it easier for administrators to understand user activities and login history.
- **Operation Log Management**: Records system operational activities, making it easier for administrators to troubleshoot and review operational records.
- **Personnel Management**: Provides personnel information entry, modification, and deletion features, making it easier for administrators to manage personnel.
- **ContainerizedDeployment**: Supports Docker containerized deployment, facilitating quick deployment and management of the blog system.

## Technology Stack
- Frontend: React, Next, AntDesign, AntDesignPro, etc.
- Backend: SpringBoot, SpringMVC, SpringSecurity, Jasypt, JWT, Mybatis, etc.
- Middleware: MySQL, Redis, ElasticSearch (planned), RocketMQ (planned).
- Operations: Rocky Linux, Docker.
- Others: UpYun storage, OSS, COS, Tencent Map.

## Quick Start
- **Method 1**: Clone the project, navigate to Jewix-Deploy, build Docker images, and start it.
- **Method 2**: Clone the project, manually compile, and separately start the frontend and backend projects.
- Note:
  - Remember to modify IP, SpringBoot configuration files, etc.
  - Start the frontend using `npm run server`.
  - Default backend account: admin@say521.cn.
  - Default backend password: 123456.
- TODO: A wiki will be published later. If there are any issues, please raise them.

## Future Plans
- Optimize backend performance.
- Optimize frontend styles.
- Integrate ElasticSearch middleware.
- Integrate RocketMQ middleware.
- Integrate ChatGPT.

## Cautions
- Jewix is open source under the [GPL V3.0](https://github.com/aomsir/jewix-blog/blob/master/LICENSE) license. Please comply with this license for secondary development and other uses.
- Version 1.0 of Jewix has some issues, which will be addressed in future releases.
- Contributions through Pull Requests are highly encouraged.
