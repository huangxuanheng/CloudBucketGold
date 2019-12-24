# CloudBucketGold
金斗云，又名筋斗云，意味着灵活多变，轻量级云，帮助企业解决营销问题


1.定位：具有营销能力的企业平台
2.功能：登录，添加用户，部门管理，权限管理，分销商管理，分销商佣金管理，订单管理，个人佣金查询，团队佣金查询
3.面向客户：小微商户，企业








CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
  `username` varchar(20) NOT NULL default "" COMMENT '用户名',
  `nick_name` varchar(20) NOT NULL default "" COMMENT '昵称',
  `email` varchar(20) NOT NULL default "" COMMENT '电子邮箱',
  `mobile` varchar(20) NOT NULL default "" COMMENT '手机号码',
  `addr` varchar(20) NOT NULL default "" COMMENT '家庭住址',
  `passwd` varchar(20) NOT NULL default "" COMMENT '登录密码',
  `wechat` varchar(20) NOT NULL default "" COMMENT '微信号',
  `age` int not null DEFAULT 0 COMMENT '年龄',
  `identify` varchar(20) NOT NULL default "" COMMENT '身份号',
  `id_type` TINYINT NOT NULL default 0 COMMENT '身份类型：0-未指定，1-身份证，2-驾驶证，3-港澳通行证',
   `isdeleted` TINYINT DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='用户信息表';


CREATE TABLE `t_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
  `username` varchar(20) NOT NULL default "" COMMENT '用户名',
  `nick_name` varchar(20) NOT NULL default "" COMMENT '昵称',
  `email` varchar(20) NOT NULL default "" COMMENT '电子邮箱',
  `mobile` varchar(20) NOT NULL default "" COMMENT '手机号码',
  `addr` varchar(20) NOT NULL default "" COMMENT '家庭住址',
  `passwd` varchar(20) NOT NULL default "" COMMENT '登录密码',
  `wechat` varchar(20) NOT NULL default "" COMMENT '微信号',
  `age` int not null DEFAULT 0 COMMENT '年龄',
  `identify` varchar(20) NOT NULL default "" COMMENT '身份号',
  `id_type` TINYINT NOT NULL default 0 COMMENT '身份类型：0-未指定，1-身份证，2-驾驶证，3-港澳通行证',
   `isdeleted` TINYINT DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='部门信息表';

