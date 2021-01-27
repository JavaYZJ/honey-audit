/*
Navicat MySQL Data Transfer

Source Server         : 腾讯云179号机
Source Server Version : 50730
Source Host           : 49.235.39.179:3305
Source Database       : honey_audit

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-01-27 15:58:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_audit
-- ----------------------------
DROP TABLE IF EXISTS `t_audit`;
CREATE TABLE `t_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` varchar(32) NOT NULL COMMENT '唯一id',
  `operator_id` varchar(32) NOT NULL COMMENT '操作者id',
  `operator_name` varchar(30) NOT NULL COMMENT '操作者',
  `operator_ip` varchar(15) NOT NULL COMMENT '操作者ip',
  `operation_type` varchar(6) NOT NULL COMMENT '操作类型',
  `operation_method` varchar(200) NOT NULL COMMENT '操作的接口方法签名',
  `operation_parameters` varchar(200) DEFAULT NULL COMMENT '操作的接口参数',
  `operation_details` tinyblob NOT NULL COMMENT '操作详情',
  `app_id` varchar(50) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0 -- 正常 1 -- 删除）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid` (`uid`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
