
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
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
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
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `isshow` TINYINT DEFAULT 0 COMMENT '部门是否可见',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='部门信息表';


CREATE TABLE `t_dept_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_id`  bigint(20) NOT NULL default 0 COMMENT '部门ID',
  `user_id`  bigint(20) NOT NULL default 0 COMMENT '用户ID',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
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
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
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
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='权限信息表';


CREATE TABLE `t_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default "" COMMENT '名称',
   `commission` float NOT NULL default 0 COMMENT '佣金比例，消费商获得的实际佣金比例',
   `discount` float NOT NULL default 0 COMMENT '自购打折',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='级别表';

CREATE TABLE `t_consumer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id`  bigint(20) NOT NULL default 0 COMMENT '父ID，上一级消费商',
  `user_id`  bigint(20) NOT NULL default 0 COMMENT '用户ID',
  `grade_id` TINYINT  NOT NULL DEFAULT 0  COMMENT '级别id',
  `sub_count` int not null default 0 COMMENT '子消费商数目',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='消费商信息表';


CREATE TABLE `t_config_commission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `generation_type` TINYINT  NOT NULL DEFAULT 2  COMMENT '佣金发放代数，即多少代获得，默认2,0-表示无限代',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='佣金配置表';


CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL default 0 COMMENT '用户ID',
  `target_id` bigint(20) NOT NULL default 0 COMMENT '目标ID或者说商品ID',
  `pay_type` TINYINT NOT NULL default 0 COMMENT '支付类型：0-未选择，1-支付宝，2-微信，3-纯现金，4-虚拟币，5-混合（线上+线下）',
  `price` bigint(20) NOT NULL default 0 COMMENT '商品价格，单位分',
  `virtual_cash` bigint(20) NOT NULL default 0 COMMENT '虚拟现金(微信或者支付宝)，单位分',
  `virtual_currency` bigint(20) NOT NULL default 0 COMMENT '虚拟币，单位分',
  `cash` bigint(20) NOT NULL default 0 COMMENT '现金（线下支付的现金人民币），单位分',
  `pay_time` datetime NOT NULL default now() COMMENT '支付时间',
  `remark` varchar(200) NOT NULL default "" COMMENT '备注',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='订单表';


CREATE TABLE `t_commission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL default 0 COMMENT '订单ID',
  `consumer_id` bigint(20) NOT NULL default 0 COMMENT '消费商ID',
  `income` bigint(20) NOT NULL default 0 COMMENT '佣金收入，单位分',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='消费商佣金表';




CREATE TABLE `t_promotion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default '' COMMENT '促销方案名称',
  `introduct` varchar(200) NOT NULL default '' COMMENT '促销简介',
  `rule` int not null default 0 comment '促销规则：1-满多少减多少，2-满多少打折，3-第几杯半价',
  `condition` int not null default 0 comment 'rule促销规则中满足的条件',
  `reduce` int not null default 0 comment '促销规则：1时，表示减少金额(分)，2时，表示折扣（计算时需要除以100），3时，表示数量',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='促销方案';


CREATE TABLE `t_charge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL default '' COMMENT '名称',
  `introduct` varchar(200) NOT NULL default '' COMMENT '充值简介',
  `rule` int not null default 1 comment '规则：1-指定时间免费兑换指定商品，2-消费满返抵现',
  `unit` int not null default 0 comment '循环单位：1-每天，2-每月，3-每季，4-每年',
   `num` int not null default 0 comment '限制数量，如每天兑换1个产品，则是1',
     `condition` int not null default 0 comment 'rule规则是2时，表示满多少钱（单位分）',
    `ret` int not null default 0 comment '规则：2时，返多少钱（单位分）',
    `start_time` datetime not null comment '当规则是1时，表示开始时间',
     `end_time` datetime not null comment '当规则是1时，表示结束时间',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='充值方案';


CREATE TABLE `t_handsel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(200) NOT NULL default '' COMMENT '赠送商品ID，逗号隔开',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='充值赠送商品信息表';



CREATE TABLE `t_charge_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `charge_id` bigint(20) NOT NULL default 0 COMMENT '充值ID',
   `handsel_id` bigint(20) NOT NULL default 0 COMMENT '赠送商品ID',
   `user_id` bigint(20) NOT NULL default 0 COMMENT '用户ID',
  `money` bigint(20) NOT NULL default 0 COMMENT '充值金额',
  `type` int not null default 0 comment '充值类型：1-微信，2-支付宝，3-现金，4-虚拟币',
   `status` int not null default 0 comment '状态：1-未支付，2-已支付，3-支付异常，4-退款',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='充值明细';



CREATE TABLE `t_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `targer_id` bigint(20) NOT NULL default 0 COMMENT '目标ID',
   `user_id` bigint(20) NOT NULL default 0 COMMENT '用户ID',
  `money` bigint(20) NOT NULL default 0 COMMENT '退款金额',
  `type` int not null default 0 comment '类型：1-充值，2-订单',
   `status` int not null default 0 comment '状态：0-初始化，1-退款成功，2-退款失败',
   `is_del` bit(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，0正常,1删除',
   `create_by` varchar(255) NOT NULL default '' COMMENT '创建人ID',
    `update_by` varchar(255) NOT NULL default '' COMMENT '更新人ID',
  `create_time` datetime NOT NULL default now() COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='退款明细';








