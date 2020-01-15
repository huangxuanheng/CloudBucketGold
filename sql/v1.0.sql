



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
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='用户信息表';


CREATE TABLE `t_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
  `parent_id`  bigint(20) NOT NULL default 0 COMMENT '父部门ID',
  `code` varchar(20) NOT NULL default "" COMMENT '部门编码',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `isshow` TINYINT DEFAULT 0 COMMENT '部门是否可见',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='部门信息表';


CREATE TABLE `t_dept_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_id`  bigint(20) NOT NULL default 0 COMMENT '部门ID',
  `user_id`  bigint(20) NOT NULL default 0 COMMENT '用户ID',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='部门用户关联表';


CREATE TABLE `t_role_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_id`  bigint(20) NOT NULL default 0 COMMENT '部门ID',
  `user_id`  bigint(20) NOT NULL default 0 COMMENT '用户ID',
  `role_id`  bigint(20) NOT NULL default 0 COMMENT '角色ID',
  `role_type` TINYINT  NOT NULL DEFAULT 0 COMMENT '角色类型：0-用户，1-部门',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='部门用户关联表';


CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
  `parent_id`  bigint(20) NOT NULL default 0 COMMENT '父ID',
  `code` varchar(20) NOT NULL default "" COMMENT '编码',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='权限信息表';


CREATE TABLE `t_distributor_level` (
  `id` TINYINT NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='分销商级别表';

CREATE TABLE `t_distributor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
  `parent_id`  bigint(20) NOT NULL default 0 COMMENT '父ID',
  `user_id`  bigint(20) NOT NULL default 0 COMMENT '用户ID',
  `distributor_level_id` TINYINT  NOT NULL DEFAULT 0  COMMENT '级别id',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='分销商信息表';

CREATE TABLE `t_commission_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
  `distributor_level_id` TINYINT  NOT NULL DEFAULT 0  COMMENT '级别id',
  `commission` bigint(20) NOT NULL default 0 COMMENT '佣金比例，分销商获得的实际佣金比例',
  `generation_type` TINYINT  NOT NULL DEFAULT 2  COMMENT '佣金发放代数，即多少代获得，默认2,0-表示无限代',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='佣金规格表';


CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `distributor_id` bigint(20) NOT NULL default 0 COMMENT '分销商ID',
  `target_id` bigint(20) NOT NULL default 0 COMMENT '目标ID或者说商品ID',
  `pay_type` TINYINT NOT NULL default 0 COMMENT '支付类型：0-未选择，1-支付宝，2-微信，3-零钱，4-虚拟币，5-混合',
  `price` bigint(20) NOT NULL default 0 COMMENT '商品价格，单位分',
  `virtual_cash` bigint(20) NOT NULL default 0 COMMENT '虚拟现金(微信或者支付宝)，单位分',
  `virtual_currency` bigint(20) NOT NULL default 0 COMMENT '虚拟币，单位分',
  `cash` bigint(20) NOT NULL default 0 COMMENT '现金（线下支付的现金人民币），单位分',
  `pay_time` datetime NOT NULL default now() COMMENT '支付时间',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='订单表';


CREATE TABLE `t_commission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL default 0 COMMENT '订单ID',
  `distributor_id` bigint(20) NOT NULL default 0 COMMENT '分销商ID',
  `commission_rule_id` bigint(20) NOT NULL default 0 COMMENT '分销商规则ID',
  `income` bigint(20) NOT NULL default 0 COMMENT '佣金收入，单位分',
   `isdeleted` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `creater` bigint(20) NOT NULL default 0 COMMENT '创建人ID',
   `updater` bigint(20) NOT NULL default 0 COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='分销商佣金表';













