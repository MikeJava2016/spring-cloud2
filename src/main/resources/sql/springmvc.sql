/*
Navicat MySQL Data Transfer

Source Server         : windows_mysql
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : springmvc

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2018-06-13 22:33:08
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
-- Table structure for `tb_items`
-- ----------------------------
DROP TABLE IF EXISTS `tb_items`;
CREATE TABLE `tb_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '商品名称',
  `price` float(10,1) NOT NULL COMMENT '商品定价',
  `detail` text COMMENT '商品描述',
  `pic` varchar(64) DEFAULT NULL COMMENT '商品图片',
  `createtime` datetime NOT NULL COMMENT '生产日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_items
-- ----------------------------
INSERT INTO `tb_items` VALUES ('1', '铅笔', '2.0', '一种用来书写以及绘画素描专用的笔类', 'pen.jpg', '2018-06-13 20:18:38');
INSERT INTO `tb_items` VALUES ('2', '钢笔', '10.0', '钢笔是人们普遍使用的书写工具，发明于19世纪初。笔头由金属制成，书写起来圆滑而有弹性，相当流畅。在笔套口处或笔尖表面，均有明显的商标牌号、型号。还分为蘸水式钢笔和自来水式钢笔、墨囊钢笔。', 'gangbi.jpg', '2018-06-13 20:20:13');
INSERT INTO `tb_items` VALUES ('3', '小刀', '2.0', '普通的铅笔刀一般是有三部分构成：刀片，刀鞘以及废料盒。', 'qianbidao.jpg', '2018-06-13 20:21:09');

-- ----------------------------
-- Table structure for `tb_orderdetail`
-- ----------------------------
DROP TABLE IF EXISTS `tb_orderdetail`;
CREATE TABLE `tb_orderdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orders_id` int(11) NOT NULL COMMENT '订单id',
  `items_id` int(11) NOT NULL COMMENT '商品id',
  `items_num` int(11) DEFAULT NULL COMMENT '商品购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_orderdetail
-- ----------------------------
INSERT INTO `tb_orderdetail` VALUES ('1', '1', '1', '1');
INSERT INTO `tb_orderdetail` VALUES ('2', '1', '2', '9');
INSERT INTO `tb_orderdetail` VALUES ('3', '2', '1', '5');
INSERT INTO `tb_orderdetail` VALUES ('4', '2', '2', '5');

-- ----------------------------
-- Table structure for `tb_orders`
-- ----------------------------
DROP TABLE IF EXISTS `tb_orders`;
CREATE TABLE `tb_orders` (
  `user_id` int(11) NOT NULL COMMENT '下单用户id',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(32) NOT NULL COMMENT '订单号',
  `createtime` datetime NOT NULL COMMENT '创建订单时间',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_orders
-- ----------------------------
INSERT INTO `tb_orders` VALUES ('1', '1', '1', '2018-06-13 20:21:59', null);
INSERT INTO `tb_orders` VALUES ('1', '2', '2', '2018-06-13 21:06:47', null);

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
INSERT INTO `tb_wx_user` VALUES ('java', '123', '1', '123456');
