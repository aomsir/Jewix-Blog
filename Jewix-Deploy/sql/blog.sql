/*
 Navicat Premium Data Transfer

 Source Server         : Docker-MySQL8.0
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3307
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 26/06/2023 13:50:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uuid` varchar(255) DEFAULT NULL COMMENT 'uuid',
  `title` varchar(100) DEFAULT NULL COMMENT '文章标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文章封面',
  `content` longtext COMMENT '文章内容',
  `description` varchar(64) DEFAULT NULL COMMENT '文章描述',
  `type` int DEFAULT NULL COMMENT '文章类型 1-原创,2-转载',
  `origin_url` varchar(100) DEFAULT NULL COMMENT '原文链接',
  `views` int DEFAULT NULL COMMENT '浏览量',
  `is_top` int DEFAULT NULL COMMENT '是否置顶 0-否,1-是',
  `is_delete` int DEFAULT NULL COMMENT '是否删除 0-否,1-是',
  `status` int DEFAULT NULL COMMENT '0-公开,1-私密',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
BEGIN;
INSERT INTO `tb_article` (`id`, `uuid`, `title`, `cover`, `content`, `description`, `type`, `origin_url`, `views`, `is_top`, `is_delete`, `status`, `create_time`, `update_time`) VALUES (1, '0598618a-a260-490f-bbe0-31de5a2d4349', 'HelloWorld', 'https://p3-passport.byteimg.com/img/user-avatar/7ac222f2192dad4ac7229410af0c1b56~180x180.awebp', '看到此页面，你的博客站点已经创建成功！', '看到此页面，你的博客站点已经创建成功！', 1, 'https://blog.say521.cn/archives/546.html', 10027, 1, 0, 0, '2023-02-24 16:28:23', '2023-04-19 11:54:45');
COMMIT;

-- ----------------------------
-- Table structure for tb_article_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_category`;
CREATE TABLE `tb_article_category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` bigint DEFAULT NULL COMMENT '角色外键id',
  `category_id` int DEFAULT NULL COMMENT '菜单外键id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_article_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_article_category` (`id`, `article_id`, `category_id`) VALUES (1, 1, 1);
INSERT INTO `tb_article_category` (`id`, `article_id`, `category_id`) VALUES (2, 1, 2);
COMMIT;

-- ----------------------------
-- Table structure for tb_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_tag`;
CREATE TABLE `tb_article_tag` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `article_id` bigint DEFAULT NULL COMMENT '文章外键id',
  `tag_id` int DEFAULT NULL COMMENT '分类外键id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_article_tag
-- ----------------------------
BEGIN;
INSERT INTO `tb_article_tag` (`id`, `article_id`, `tag_id`) VALUES (1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_article_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_user`;
CREATE TABLE `tb_article_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` bigint DEFAULT NULL COMMENT '文章id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_article_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_article_user` (`id`, `article_id`, `user_id`) VALUES (1, 1, 10000);
COMMIT;

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_name` varchar(20) DEFAULT NULL COMMENT '分类名',
  `parent_id` int DEFAULT NULL COMMENT '父级分类id 0-一级',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_category` (`id`, `category_name`, `parent_id`, `create_time`, `update_time`) VALUES (1, '测试父分类', 0, '2023-06-25 18:01:13', '2023-06-25 18:01:16');
INSERT INTO `tb_category` (`id`, `category_name`, `parent_id`, `create_time`, `update_time`) VALUES (2, '测试子分类', 1, '2023-06-25 18:01:29', '2023-06-25 18:01:32');
COMMIT;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `author` varchar(32) NOT NULL COMMENT '评论者昵称',
  `content` varchar(2000) NOT NULL COMMENT '评论内容',
  `email` varchar(64) NOT NULL COMMENT '评论者邮箱',
  `url` varchar(64) DEFAULT NULL COMMENT '评论者个人网站',
  `ip` varchar(39) DEFAULT NULL COMMENT '评论者IP',
  `location` varchar(30) DEFAULT NULL COMMENT '评论者地理位置',
  `agent` varchar(200) DEFAULT NULL COMMENT '评论者设备',
  `type` int DEFAULT NULL COMMENT '评论类型 1-文章,21-时光机,22-友人帐,23-留言板,24-关于',
  `target_id` bigint DEFAULT NULL COMMENT '文章/页面id',
  `parent_id` bigint DEFAULT NULL COMMENT '父级评论id',
  `perm_id` bigint DEFAULT NULL COMMENT '一级评论id',
  `status` int DEFAULT NULL COMMENT '状态 0-待审核,1-开放,2-垃圾',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
BEGIN;
INSERT INTO `tb_comment` (`id`, `author`, `content`, `email`, `url`, `ip`, `location`, `agent`, `type`, `target_id`, `parent_id`, `perm_id`, `status`, `create_time`, `update_time`) VALUES (30, 'Aomsir', 'Jewix', 'info@say521.cn', 'https://www.say521.cn', '0:0:0:0:0:0:0:1', '未知', '{\"os\":\"Mac OS X\",\"browser\":\"Chrome 11\"}', 1, 1, 0, 0, 1, '2023-05-02 17:15:07', '2023-06-22 11:47:16');
COMMIT;

-- ----------------------------
-- Table structure for tb_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend_link`;
CREATE TABLE `tb_friend_link` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(20) NOT NULL COMMENT '友链标题',
  `link` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链链接',
  `photo` varchar(80) DEFAULT NULL COMMENT '友链图片',
  `description` varchar(50) DEFAULT NULL COMMENT '友链描述',
  `location` int DEFAULT NULL COMMENT '链接位置 1-首页、2-内页、3-失效',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_friend_link
-- ----------------------------
BEGIN;
INSERT INTO `tb_friend_link` (`id`, `title`, `link`, `photo`, `description`, `location`, `create_time`, `update_time`) VALUES (1, '测试友链1', 'https://www.say5214.cn', 'https://w4ww.say521.cn', 'lll4事实上', 2, '2023-04-19 14:58:51', NULL);
INSERT INTO `tb_friend_link` (`id`, `title`, `link`, `photo`, `description`, `location`, `create_time`, `update_time`) VALUES (3, '测试友链-内页', 'http://localhost:3000/', 'https://img.say521.cn/blogimg/iShot_2022-04-27_13.20.46.jpeg', '测试友链-内页', 2, '2023-04-24 16:45:52', '2023-04-24 16:45:52');
INSERT INTO `tb_friend_link` (`id`, `title`, `link`, `photo`, `description`, `location`, `create_time`, `update_time`) VALUES (4, '测试友链-失效', 'http://localhost:3000/', 'https://img.say521.cn/blogimg/iShot_2022-04-27_13.20.46.jpeg', '测试友链-失效', 2, '2023-04-24 16:47:54', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_login_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_log`;
CREATE TABLE `tb_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint DEFAULT NULL COMMENT '登录用户id',
  `ip` varchar(39) DEFAULT NULL COMMENT '登录ip',
  `location` varchar(32) DEFAULT NULL COMMENT '地理位置',
  `operate_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_login_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int NOT NULL COMMENT '父菜单项的id(0为顶级菜单)',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单中文名称',
  `icon_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单图标英文名称',
  `path` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单内容的访问路径',
  `component_path` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单对应的组件路径',
  `type` tinyint NOT NULL COMMENT '0-菜单项, 1-菜单下的按钮',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (1, 0, '文章管理', 'BookOutlined', '/article', '/article', 0, '2023-06-07 19:52:40', '2023-06-07 19:52:42');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (2, 0, '评论管理', 'CommentOutlined', '/comment', '/comment', 0, '2023-06-07 19:52:43', '2023-06-07 19:52:45');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (3, 0, '页面管理', 'BookOutlined', '/page', '/page', 0, '2023-06-07 19:53:10', '2023-06-07 19:53:12');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (4, 0, '友链管理', 'TeamOutlined', '/blogroll', '/blogroll', 0, '2023-06-07 19:54:48', '2023-06-07 19:54:52');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (5, 0, '分类管理', 'PicCenterOutlined', '/category', '/category', 0, '2023-06-07 19:55:27', '2023-06-07 19:55:29');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (6, 0, '标签管理', 'TagOutlined', '/tag', '/tag', 0, '2023-06-07 19:55:53', '2023-06-07 19:55:55');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (7, 0, '相册管理', 'FolderOpenOutlined', '/photo', '/photo', 0, '2023-06-07 19:56:44', '2023-06-07 19:56:46');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (8, 0, '用户管理', 'UserOutlined', '/user', '/user', 0, '2023-06-07 19:58:32', '2023-06-07 19:58:36');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (9, 0, '权限管理', 'SecurityScanOutlined', '/auth', '', 0, '2023-06-07 19:59:58', '2023-06-07 20:00:01');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (10, 9, '菜单管理', 'MenuOutlined', '/auth/menu', '/auth/menu', 0, '2023-06-07 20:17:28', '2023-06-07 20:17:30');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (11, 9, '资源管理', 'ApiOutlined', '/auth/resource', '/auth/resource', 0, '2023-06-07 20:32:41', '2023-06-07 20:32:43');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (12, 9, '角色管理', 'IdcardOutlined', '/auth/role', '/auth/role', 0, '2023-06-07 20:32:57', '2023-06-07 20:33:00');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (13, 1, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:15:08', '2023-06-08 15:15:10');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (14, 1, '更新', NULL, NULL, NULL, 1, '2023-06-08 15:15:49', '2023-06-08 15:15:51');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (15, 1, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:16:10', '2023-06-08 15:16:13');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (16, 2, '更新', NULL, NULL, NULL, 1, '2023-06-08 15:18:07', '2023-06-08 15:18:09');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (17, 2, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:18:27', '2023-06-08 15:18:29');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (18, 3, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:18:56', '2023-06-08 15:18:58');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (19, 3, '更新', NULL, NULL, NULL, 1, '2023-06-08 15:19:08', '2023-06-08 15:19:11');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (20, 3, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:19:19', '2023-06-08 15:19:22');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (21, 4, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:19:35', '2023-06-08 15:19:40');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (22, 4, '更新', NULL, NULL, NULL, 1, '2023-06-08 15:19:51', '2023-06-08 15:19:53');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (23, 4, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:20:00', '2023-06-08 15:20:02');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (24, 6, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:20:44', '2023-06-08 15:20:47');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (25, 6, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:20:55', '2023-06-08 15:20:56');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (26, 7, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:21:49', '2023-06-08 15:21:51');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (27, 7, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:22:01', '2023-06-08 15:22:03');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (28, 5, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:23:50', '2023-06-08 15:23:54');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (29, 5, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:24:01', '2023-06-08 15:24:04');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (30, 8, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:24:32', '2023-06-08 15:24:35');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (31, 8, '更新', NULL, NULL, NULL, 1, '2023-06-08 15:24:45', '2023-06-08 15:24:47');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (32, 8, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:24:54', '2023-06-08 15:24:56');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (33, 8, '更新状态', NULL, NULL, NULL, 1, '2023-06-08 15:25:04', '2023-06-08 15:25:07');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (34, 2, '更新状态', NULL, NULL, NULL, 1, '2023-06-08 15:28:10', '2023-06-08 15:28:11');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (35, 9, '新增', NULL, NULL, NULL, 1, '2023-06-08 15:47:34', '2023-06-08 15:47:36');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (36, 9, '更新', NULL, NULL, NULL, 1, '2023-06-08 15:49:01', '2023-06-08 15:49:03');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (37, 9, '删除', NULL, NULL, NULL, 1, '2023-06-08 15:49:10', '2023-06-08 15:49:11');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (38, 2, '更新状态', NULL, NULL, NULL, 1, '2023-06-10 19:08:59', '2023-06-10 19:09:02');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (39, 0, '日志管理', 'FileTextOutlined', '/log', NULL, 0, '2023-06-14 19:48:36', '2023-06-14 19:48:39');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (40, 39, '操作日志', 'AuditOutlined', '/log/operation', '/log/operation', 0, '2023-06-14 19:49:20', '2023-06-14 19:49:24');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (41, 39, '登录日志', 'LoginOutlined', '/log/login', '/log/login', 0, '2023-06-14 19:49:42', '2023-06-14 19:49:44');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (42, 39, '删除', NULL, NULL, NULL, 1, '2023-06-15 17:25:07', '2023-06-15 17:25:12');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (43, 39, '删除数据', NULL, NULL, NULL, 1, '2023-06-15 17:25:20', '2023-06-15 17:25:21');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (44, 6, '更新', NULL, NULL, NULL, 1, '2023-06-15 18:46:42', '2023-06-15 18:46:44');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (45, 1, '文章编辑', 'NULL', '/article/edit', '/article/edit', 0, '2023-06-20 11:46:59', '2023-06-20 11:47:02');
INSERT INTO `tb_menu` (`id`, `parent_id`, `name`, `icon_name`, `path`, `component_path`, `type`, `create_time`, `update_time`) VALUES (46, 0, '网站配置', 'SettingOutlined', '/setting', '/setting', 0, '2023-06-20 21:04:30', '2023-06-20 21:04:32');
COMMIT;

-- ----------------------------
-- Table structure for tb_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_operate_log`;
CREATE TABLE `tb_operate_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `location` varchar(32) DEFAULT NULL COMMENT '地理位置',
  `opt_module` varchar(16) DEFAULT NULL COMMENT '操作模块',
  `opt_type` varchar(32) DEFAULT NULL COMMENT '操作类型',
  `req_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `req_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作方法',
  `java_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `opt_req_msg` longtext COMMENT '操作请求数据',
  `opt_resp_msg` longtext COMMENT '操作响应数据',
  `opt_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_operate_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tb_page
-- ----------------------------
DROP TABLE IF EXISTS `tb_page`;
CREATE TABLE `tb_page` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uuid` varchar(255) DEFAULT NULL COMMENT 'uuid',
  `user_id` bigint NOT NULL COMMENT '操作用户id',
  `title` varchar(100) DEFAULT NULL COMMENT '页面标题',
  `content` longtext COMMENT '页面内容',
  `description` varchar(64) DEFAULT NULL COMMENT '文章描述',
  `omit` varchar(255) DEFAULT NULL COMMENT '页面路径',
  `views` int DEFAULT NULL COMMENT '浏览量',
  `type` int NOT NULL COMMENT '类型 1-时光机,2-文章归档,3-友人帐,4-留言板,5-通用模板',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_page
-- ----------------------------
BEGIN;
INSERT INTO `tb_page` (`id`, `uuid`, `user_id`, `title`, `content`, `description`, `omit`, `views`, `type`, `create_time`, `update_time`) VALUES (1, 'de88eede-da6a-4f32-8260-0c4841b692d7', 10000, '时光机', '时光机', '时光机', 'cross', 1000, 1, '2023-05-05 10:50:35', '2023-05-05 10:50:39');
INSERT INTO `tb_page` (`id`, `uuid`, `user_id`, `title`, `content`, `description`, `omit`, `views`, `type`, `create_time`, `update_time`) VALUES (2, 'c2fa29af-acdc-4098-b294-f128bb2229e0', 10000, '文章归档', '文章归档', '文章归档', 'archive', 1000, 2, '2023-05-05 10:51:26', '2023-05-05 10:51:28');
INSERT INTO `tb_page` (`id`, `uuid`, `user_id`, `title`, `content`, `description`, `omit`, `views`, `type`, `create_time`, `update_time`) VALUES (3, 'b098358e-e626-46c6-9cb7-81a359194930', 10000, '友人帐', '<p>## 申请条件</p>\n<p>[scode type=\"green\"]</p>\n<p>全站HTTPS访问</p>\n<p>内容多为原创</p>\n<p>坚持更新</p>\n<p>无违反我国相关法律的文章</p>\n<p>[/scode]</p>\n<p>## 我的信息</p>\n<p>[scode type=\"share\"]</p>\n<p>网站名称：Mystery博客<br>网站地址：[https://blog.say521.cn](https://blog.say521.cn/)<br>头像链接：[https://img.say521.cn/Picplus/%E5%A4%B4%E5%83%8F.jpg](https://img.say521.cn/Picplus/%E5%A4%B4%E5%83%8F.jpg)<br>网站描述：一个还在成长路上不断摸索的软件生建的网站，主要用于分享一些专业，生活，摄影情感随笔等内容</p>\n<p>[/scode]</p>', '友人帐', 'links', 1000, 3, '2023-05-05 10:51:56', '2023-05-13 09:41:43');
INSERT INTO `tb_page` (`id`, `uuid`, `user_id`, `title`, `content`, `description`, `omit`, `views`, `type`, `create_time`, `update_time`) VALUES (4, '31193cef-abb2-40a2-bfb0-61290d6ec4d7', 10000, '留言板', '留言板', '留言板', 'comments', 1000, 4, '2023-05-05 10:52:28', '2023-05-05 10:52:31');
COMMIT;

-- ----------------------------
-- Table structure for tb_photo
-- ----------------------------
DROP TABLE IF EXISTS `tb_photo`;
CREATE TABLE `tb_photo` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` int DEFAULT NULL COMMENT '上传位置类型 0-本地,1-又拍云,2-阿里云,3-腾讯云',
  `location` varchar(512) DEFAULT NULL COMMENT '文件地址 本地地址或者远程url',
  `file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'UUID型文件名',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_photo
-- ----------------------------
BEGIN;
INSERT INTO `tb_photo` (`id`, `type`, `location`, `file_name`, `create_time`) VALUES (4, 0, '/2023/04/14/', '9e0bfea6-f17c-4097-b283-e6f44f9eb8d8.jpg', '2023-04-14 16:16:19');
INSERT INTO `tb_photo` (`id`, `type`, `location`, `file_name`, `create_time`) VALUES (6, 1, '/2023/06/17/', '9ed69d2d-ec9e-458f-ba59-4c2d1a280058.png', '2023-06-17 18:57:37');
INSERT INTO `tb_photo` (`id`, `type`, `location`, `file_name`, `create_time`) VALUES (7, 1, '/2023/06/18/', 'd4c5baa7-f431-4eca-a55b-9b931ab5f72a.png', '2023-06-18 15:24:17');
COMMIT;

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) DEFAULT NULL COMMENT '接口名称',
  `label` varchar(64) DEFAULT NULL COMMENT '标签名-即权限',
  `parent_id` int DEFAULT NULL COMMENT '父级-0代表为父级',
  `method` varchar(16) DEFAULT NULL COMMENT '请求方法',
  `route` varchar(64) DEFAULT NULL COMMENT '请求地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
BEGIN;
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (1, '文章接口', 'ADMIN_ARTICLE', 0, 'NULL', '/admin/articles', '2023-06-07 21:53:21');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (2, '无限制获取文章列表', 'ADMIN_ARTICLE_LIST', 1, 'GET', '/admin/articles', '2023-06-07 21:56:25');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (3, '添加文章', 'ADMIN_ARTICLE_ADD', 1, 'POST', '/admin/articles', '2023-06-07 21:57:16');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (4, '修改文章', 'ADMIN_ARTICLE_UPDATE', 1, 'PUT', '/admin/articles', '2023-06-07 21:57:59');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (5, '理论删除文章', 'ADMIN_ARTICLE_DELETE_ARCHIVE', 1, 'DELETE', '/admin/articles/archive', '2023-06-07 22:00:38');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (6, '物理删除文章', 'ADMIN_ARTICLE_DELETE_PHYSICS', 1, 'DELETE', '/admin/articles/physics', '2023-06-07 22:46:33');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (7, '分类接口', 'ADMIN_CATEGORY', 0, 'NULL', '/admin/categories', '2023-06-07 22:48:09');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (8, '添加分类', 'ADMIN_CATEGORY_ADD', 7, 'POST', '/admin/categories', '2023-06-07 22:49:25');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (9, '删除分类', 'ADMIN_CATEGORY_DELETE', 7, 'DELETE', '/admin/categories', '2023-06-07 22:50:11');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (10, '修改分类', 'ADMIN_CATEGOTY_UPDATE', 7, 'PUT', '/admin/categories', '2023-06-07 22:51:06');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (11, '评论接口', 'ADMIN_COMMENT', 0, 'NULL', '/admin/comments', '2023-06-07 22:52:10');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (12, '无限制获取评论列表', 'ADMIN_COMMENT_LIST', 11, 'GET', '/admin/comments', '2023-06-07 22:52:59');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (13, '删除评论', 'ADMIN_COMMENT_DELETE', 11, 'DELETE', '/admin/comments', '2023-06-07 22:54:11');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (14, '修改评论', 'ADMIN_COMMENT_UPDATE', 11, 'PUT', '/admin/comments', '2023-06-07 22:54:42');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (15, '友链接口', 'ADMIN_FRIENDLINK', 0, 'NULL', '/admin/friend-links', '2023-06-08 11:16:16');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (16, '添加友链', 'ADMIN_FRIENDLINK_ADD', 15, 'POST', '/admin/friend-links', '2023-06-08 11:19:10');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (17, '修改友链', 'ADMIN_FRIENDLINK_UPDATE', 15, 'PUT', '/admin/friend-links', '2023-06-08 11:19:10');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (18, '获取友链详情', 'ADMIN_FRIENDLINK_DETAIL', 15, 'GET', '/admin/friend-links/{id}', '2023-06-08 11:21:35');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (19, '删除友链', 'ADMIN_FRIENDLINK_DELETE', 15, 'DELETE', '/admin/friend-links', '2023-06-08 11:23:20');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (20, '页面接口', 'ADMIN_PAGE', 0, 'NULL', '/admin/pages', '2023-06-08 11:24:01');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (21, '添加页面', 'ADMIN_PAGE_ADD', 20, 'POST', '/admin/pages', '2023-06-08 11:26:02');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (22, '删除页面', 'ADMIN_PAGE_DELETE', 20, 'DELETE', '/admin/pages', '2023-06-08 11:26:51');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (23, '更新页面', 'ADMIN_PAGE_UPDATE', 20, 'PUT', '/admin/pages', '2023-06-08 11:27:19');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (24, '相册接口', 'ADMIN_PHOTO', 0, 'NULL', '/admin/photos', '2023-06-08 11:29:29');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (25, '上传照片', 'ADMIN_PHOTO_ADD', 24, 'POST', '/admin/photos', '2023-06-08 11:30:33');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (26, '获取照片列表', 'ADMIN_PHOTO_LIST', 24, 'GET', '/admin/photos', '2023-06-08 11:31:21');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (27, '删除照片', 'ADMIN_PHOTO_DELETE', 24, 'DELETE', '/admin/photos', '2023-06-08 11:32:17');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (28, '标签接口', 'ADMIN_TAG', 0, 'NULL', '/admin/tags', '2023-06-08 11:42:11');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (29, '添加标签', 'ADMIN_TAG_ADD', 28, 'POST', '/admin/tags', '2023-06-08 11:48:09');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (30, '修改标签', 'ADMIN_TAG_UPDATE', 28, 'PUT', '/admin/tags', '2023-06-08 11:48:13');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (31, '查询标签', 'ADMIN_TAG_DETAIL', 28, 'GET', '/admin/tags/{id}', '2023-06-08 11:48:18');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (32, '删除标签', 'ADMIN_TAG_DELETE', 28, 'DELETE', '/admin/tags', '2023-06-08 11:48:20');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (33, '用户接口', 'ADMIN_USER', 0, 'NULL', '/admin/users', '2023-06-08 11:52:14');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (34, '查询用户列表', 'ADMIN_USER_LIST', 33, 'GET', '/admin/users', '2023-06-08 11:55:25');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (35, '根据UUID查询用户', 'ADMIN_USER_DETAIL', 33, 'GET', '/admin/users/{uuid}', '2023-06-08 11:55:28');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (36, '添加用户', 'ADMIN_USER_ADD', 33, 'POST', '/admin/users', '2023-06-08 11:55:33');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (37, '修改用户', 'ADMIN_USER_UPDATE', 33, 'PUT', '/admin/users', '2023-06-08 11:55:39');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (38, '修改用户状态', 'ADMIN_USER_UPDATE_STATUS', 33, 'PUT', '/admin/users/status', '2023-06-08 11:55:43');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (39, '理论删除用户', 'ADMIN_USER_DELETE_ARCHIVE', 33, 'DELETE', '/admin/users/archive', '2023-06-08 11:55:47');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (40, '物理删除用户', 'ADMIN_USER_DELETE_PHYSICS', 33, 'DELETE', '/admin/users/physics', '2023-06-08 11:55:50');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (41, '用户更新个人信息', 'ADMIN_USER_UPDATE_MY', 33, 'PUT', '/admin/users/my', '2023-06-08 11:55:54');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (42, '配置接口', 'ADMIN_CONFIG', 0, 'NULL', '/admin/configs', '2023-06-08 12:07:16');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (43, '新增通用设置', 'ADMIN_CONFIG_ADD', 42, 'POST', '/admin/configs', '2023-06-08 12:08:44');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (44, '修改通用设置', 'ADMIN_CONFIG_UPDATE', 42, 'PUT', '/admin/configs', '2023-06-08 12:08:47');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (46, '修改评论状态', 'ADMIN_COMMENT_UPDATE_STATUS', 11, 'PUT', '/admin/comments/status', '2023-06-08 12:17:52');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (47, '日志接口', 'ADMIN_LOG', 0, 'NULL', '/admin/logs', '2023-06-14 14:51:53');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (48, '查询登录日志列表', 'ADMIN_LOG_LOGIN_LIST', 47, 'GET', '/admin/logs/login', '2023-06-14 14:53:11');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (49, '查询操作日志接口', 'ADMIN_LOG_OPERATE_LIST', 47, 'GET', '/admin/logs/operate', '2023-06-14 14:53:48');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (50, '删除登录日志', 'ADMIN_LOG_LOGIN_DELETE', 47, 'DELETE', '/admin/logs/login', '2023-06-14 14:54:34');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (51, '删除操作日志', 'ADMIN_LOG_OPERATE_DELETE', 47, 'DELETE', '/admin/logs/operate', '2023-06-14 14:55:20');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (52, '菜单接口', 'ADMIN_MENU', 0, 'NULL', '/admin/menus', '2023-06-14 14:56:08');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (53, '查询菜单列表', 'ADMIN_MENU_LIST', 52, 'GET', '/admin/menus', '2023-06-14 14:57:18');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (54, '为角色分配菜单', 'ADMIN_MENU_ROLE', 52, 'POST', '/admin/menus/doAssign', '2023-06-14 14:58:07');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (55, '资源接口', 'ADMIN_RESOURCE', 0, 'NULL', '/admin/resources', '2023-06-14 14:59:08');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (56, '查询资源列表', 'ADMIN_RESOURCE_LIST', 55, 'GET', '/admin/resources', '2023-06-14 15:00:02');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (57, '为角色分配资源', 'ADMIN_RESOURCE_ROLE', 55, 'POST', '/admin/resources/doAssign', '2023-06-14 15:01:15');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (58, '角色接口', 'ADMIN_ROLE', 0, 'NULL', '/admin/roles', '2023-06-14 15:02:29');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (59, '查询角色列表', 'ADMIN_ROLE_LIST', 58, 'GET', '/admin/roles', '2023-06-14 15:11:13');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (60, '新增角色', 'ADMIN_ROLE_ADD', 58, 'POST', '/admin/roles', '2023-06-14 15:11:15');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (61, '修改角色信息', 'ADMIN_ROLE_UPDATE', 58, 'UPDATE', '/admin/roles', '2023-06-14 15:11:18');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (62, '删除角色', 'ADMIN_ROLE_DELETE', 58, 'DELETE', '/admin/roles', '2023-06-14 15:11:21');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (63, '根据id查询角色详细信息', 'ADMIN_ROLE_DETAIL', 58, 'GET', '/admin/roles/{id}', '2023-06-14 15:11:23');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (64, '根据角色id获取菜单列表', 'ADMIN_ROLE_MENU_LIST', 58, 'GET', '/admin/roles/menu', '2023-06-18 15:01:03');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (65, '根据角色id获取资源列表', 'ADMIN_ROLE_RESOURCE_LIST', 58, 'GET', '/admin/roles/resource', '2023-06-18 15:01:06');
INSERT INTO `tb_resource` (`id`, `name`, `label`, `parent_id`, `method`, `route`, `create_time`) VALUES (66, '后台根据uuid获取文章详情', 'ADMIN_ARTICLE_BACKEND_DETAIL', 1, 'GET', '/admin/articles/{uuid}', '2023-06-18 15:05:00');
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_name` varchar(10) NOT NULL COMMENT '角色名',
  `role_label` varchar(20) NOT NULL COMMENT '角色标签',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` (`id`, `role_name`, `role_label`, `create_time`, `update_time`) VALUES (1, '超级管理员', 'SUPER_ADMIN_ROLE', '2023-06-08 13:45:34', '2023-06-20 11:07:39');
INSERT INTO `tb_role` (`id`, `role_name`, `role_label`, `create_time`, `update_time`) VALUES (2, '体验用户', 'TEST_ROLE', '2023-06-13 12:49:51', '2023-06-20 12:17:39');
COMMIT;

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int DEFAULT NULL COMMENT '角色外键id',
  `menu_id` int DEFAULT NULL COMMENT '菜单外键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (131, 1, 1);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (132, 1, 2);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (133, 1, 3);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (134, 1, 4);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (135, 1, 5);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (136, 1, 6);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (137, 1, 7);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (138, 1, 8);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (139, 1, 9);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (140, 1, 13);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (141, 1, 14);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (142, 1, 15);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (143, 1, 16);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (144, 1, 17);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (145, 1, 34);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (146, 1, 38);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (147, 1, 18);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (148, 1, 19);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (149, 1, 20);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (150, 1, 21);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (151, 1, 22);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (152, 1, 23);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (153, 1, 28);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (154, 1, 29);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (155, 1, 24);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (156, 1, 25);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (157, 1, 44);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (158, 1, 26);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (159, 1, 27);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (160, 1, 30);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (161, 1, 31);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (162, 1, 32);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (163, 1, 33);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (164, 1, 10);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (165, 1, 11);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (166, 1, 12);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (167, 1, 35);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (168, 1, 36);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (169, 1, 37);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (170, 1, 41);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (171, 1, 42);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (172, 1, 43);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (173, 1, 40);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (174, 1, 39);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (192, 1, 45);
INSERT INTO `tb_role_menu` (`id`, `role_id`, `menu_id`) VALUES (193, 1, 46);
COMMIT;

-- ----------------------------
-- Table structure for tb_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_resource`;
CREATE TABLE `tb_role_resource` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `resource_id` int DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_role_resource
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (197, 1, 1);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (198, 1, 7);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (199, 1, 11);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (200, 1, 15);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (201, 1, 20);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (202, 1, 24);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (203, 1, 28);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (204, 1, 33);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (205, 1, 42);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (206, 1, 47);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (207, 1, 52);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (208, 1, 55);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (209, 1, 58);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (210, 1, 2);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (211, 1, 3);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (212, 1, 4);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (213, 1, 5);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (214, 1, 6);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (215, 1, 66);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (216, 1, 8);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (217, 1, 9);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (218, 1, 10);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (219, 1, 12);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (220, 1, 13);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (221, 1, 14);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (222, 1, 46);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (223, 1, 16);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (224, 1, 17);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (225, 1, 18);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (226, 1, 19);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (227, 1, 21);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (228, 1, 22);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (229, 1, 23);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (230, 1, 25);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (231, 1, 26);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (232, 1, 27);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (233, 1, 29);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (234, 1, 30);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (235, 1, 31);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (236, 1, 32);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (237, 1, 34);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (238, 1, 35);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (239, 1, 36);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (240, 1, 37);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (241, 1, 38);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (242, 1, 39);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (243, 1, 40);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (244, 1, 41);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (245, 1, 43);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (246, 1, 44);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (247, 1, 48);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (248, 1, 49);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (249, 1, 50);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (250, 1, 51);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (251, 1, 53);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (252, 1, 54);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (253, 1, 56);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (254, 1, 57);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (255, 1, 59);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (256, 1, 60);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (257, 1, 61);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (258, 1, 62);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (259, 1, 63);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (260, 1, 64);
INSERT INTO `tb_role_resource` (`id`, `role_id`, `resource_id`) VALUES (261, 1, 65);
COMMIT;

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tag_name` varchar(20) NOT NULL COMMENT '标签名',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `tag_name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
BEGIN;
INSERT INTO `tb_tag` (`id`, `tag_name`, `create_time`, `update_time`) VALUES (1, '测试标签', '2023-06-25 18:00:44', '2023-06-25 18:00:46');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'UUID',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '验证盐',
  `description` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户谚语',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `web_site` varchar(30) DEFAULT 'https://www.say521.cn' COMMENT '个人网站地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` int DEFAULT NULL COMMENT '状态 0-未验证邮箱, 1-正常, 2-禁用, 3-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` (`id`, `uuid`, `nickname`, `email`, `salt`, `description`, `password`, `web_site`, `create_time`, `update_time`, `status`) VALUES (10000, '31717c9e-00ac-445f-8bd3-2bb9d46b7b84', 'Aomsir', 'admin@say521.cn', 'a04879dd-b2d0-47ba-8027-e40d254ba265', '累有所往,忙有所获', '$2a$10$7Ywdbh6Xuwdvl1jZ3HVxQuXvtXfDNt0yJl4eazuS0.uQPdVCV4kSq', 'https://www.say521.cn', '2023-02-19 19:52:17', '2023-06-25 17:57:39', 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int DEFAULT NULL COMMENT '用户外键id',
  `role_id` int DEFAULT NULL COMMENT '角色外键id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_role` (`id`, `user_id`, `role_id`) VALUES (1, 10000, 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_web_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_web_config`;
CREATE TABLE `tb_web_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config` longtext COMMENT '网站配置信息',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_web_config
-- ----------------------------
BEGIN;
INSERT INTO `tb_web_config` (`id`, `config`, `create_time`, `update_time`) VALUES (1, '{\"id\":1,\"title\":\"Jewix博客系统\",\"description\":\"一款简约、高效、多样化的前后端分离博客系统\",\"keyword\":[\"Jewix博客系统\",\"Aomsir\",\"Mystery博客\"],\"webSite\":\"https://www.say521.cn\",\"buildDate\":1545955200000,\"icp\":\"\",\"police\":\"\",\"socialInfo\":{\"GitHub\":\"https://github.com/aomsir\",\"Yuque\":\"https://yuque.com/aomsir\",\"Biliili\":\"https://space.bilibili.com/406964839\"}}', '2023-06-18 16:58:15', '2023-06-25 17:33:22');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
