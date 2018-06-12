/*
Navicat MySQL Data Transfer

Source Server         : windows_mysql
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : springmvc

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2018-06-13 07:54:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_course`
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cname` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '课程名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('1', '语文');
INSERT INTO `tb_course` VALUES ('2', '数学');
INSERT INTO `tb_course` VALUES ('3', '英语');

-- ----------------------------
-- Table structure for `tb_student`
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_student
-- ----------------------------
INSERT INTO `tb_student` VALUES ('1', '阿斯顿发生');
INSERT INTO `tb_student` VALUES ('2', '撒的发生');

-- ----------------------------
-- Table structure for `tb_student_course`
-- ----------------------------
DROP TABLE IF EXISTS `tb_student_course`;
CREATE TABLE `tb_student_course` (
  `s_id` int(255) unsigned NOT NULL DEFAULT '0',
  `c_id` int(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`s_id`,`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_student_course
-- ----------------------------
INSERT INTO `tb_student_course` VALUES ('1', '1');
INSERT INTO `tb_student_course` VALUES ('1', '2');
INSERT INTO `tb_student_course` VALUES ('2', '1');
INSERT INTO `tb_student_course` VALUES ('2', '3');

-- ----------------------------
-- Table structure for `tb_wx_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wx_user`;
CREATE TABLE `tb_wx_user` (
  `user_name` varchar(100) DEFAULT NULL,
  `age` varchar(10) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_wx_user
-- ----------------------------
INSERT INTO `tb_wx_user` VALUES ('java', '123', '12', '123456');
