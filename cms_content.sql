/*
Navicat MySQL Data Transfer

Source Server         : 192.168.3.37
Source Server Version : 50731
Source Host           : 192.168.3.37:3306
Source Database       : czq_demo

Target Server Type    : MYSQL
Target Server Version : 50731
File Encoding         : 65001

Date: 2020-09-11 17:54:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cms_content`
-- ----------------------------
DROP TABLE IF EXISTS `cms_content`;
CREATE TABLE `cms_content` (
  `contentId` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `releaseDate` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_content
-- ----------------------------
INSERT INTO `cms_content` VALUES ('e9901222-5c5a-4265-8f26-e82174edcd83', '海贼王第1卷 ', null, '2020-09-11 17:31:59');
INSERT INTO `cms_content` VALUES ('f057c1e0-8678-47bf-aba6-6cb01591b5a6', '海贼王第1卷 ', null, '2020-09-11 17:49:42');
INSERT INTO `cms_content` VALUES ('61dd5a49-741d-4970-9e1f-845099e9648f', '海贼王第1卷 ', null, '2020-09-11 17:50:04');
INSERT INTO `cms_content` VALUES ('c12ce54f-e22a-4c50-81b6-7e6b42748ee3', '海贼王第1卷 ', null, '2020-09-11 17:52:17');
