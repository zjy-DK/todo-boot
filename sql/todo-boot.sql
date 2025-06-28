/*
 Navicat Premium Data Transfer

 Source Server         : 内网mysql
 Source Server Type    : MySQL
 Source Server Version : 80040
 Source Host           : 192.168.31.114:3306
 Source Schema         : todo-boot

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 28/06/2025 15:14:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for exception_log
-- ----------------------------
DROP TABLE IF EXISTS `exception_log`;
CREATE TABLE `exception_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime(3) NULL DEFAULT NULL COMMENT '创建时间',
  `type` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '异常类型',
  `message` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `stack_log` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '异常栈信息',
  `read_flag` tinyint NULL DEFAULT NULL COMMENT '是否已读(0未读，1已读)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统异常日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for slow_request_log
-- ----------------------------
DROP TABLE IF EXISTS `slow_request_log`;
CREATE TABLE `slow_request_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `request_path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `request_type` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求类型',
  `request_param` varchar(3000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'param请求参数',
  `request_body` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '请求体',
  `request_time` datetime(3) NULL DEFAULT NULL COMMENT '请求时间',
  `response_time` datetime(3) NULL DEFAULT NULL COMMENT '响应时间',
  `ip` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求ip',
  `execute_time` int NULL DEFAULT NULL COMMENT '执行时间',
  `request_content_type` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求数据类型',
  `read_flag` tinyint NULL DEFAULT NULL COMMENT '是否已读(0未读，1已读)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '慢请求日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for todo
-- ----------------------------
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `remark` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户ID',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标题',
  `priority` tinyint NOT NULL COMMENT '优先级',
  `status` tinyint NOT NULL COMMENT '状态',
  `complete_time` datetime(3) NULL DEFAULT NULL COMMENT '完成时间',
  `top` tinyint NULL DEFAULT NULL COMMENT '是否置顶',
  `create_id` int NULL DEFAULT NULL COMMENT '创建人ID',
  `create_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新人ID',
  `update_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '更新人名称',
  `update_time` datetime(3) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'todo' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_id` int NULL DEFAULT NULL COMMENT '创建人ID',
  `create_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新人ID',
  `update_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '更新人名称',
  `update_time` datetime(3) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名称',
  `email` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户邮箱',
  `phone_number` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `sex` tinyint NULL DEFAULT NULL COMMENT 'sex',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `status` tinyint NOT NULL COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime(3) NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户对象' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
