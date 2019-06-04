
DROP TABLE IF EXISTS `data_group`;
CREATE TABLE `data_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,0:删除,1:正常,2:停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据分组';


DROP TABLE IF EXISTS `data_item`;
CREATE TABLE `data_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_group_id` bigint(20) DEFAULT NULL COMMENT '组id',
  `key_name` varchar(256) DEFAULT NULL COMMENT '名称',
  `key_value` varchar(256) DEFAULT NULL COMMENT '值',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,0删除,1:正常,2:停用',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据';


DROP TABLE IF EXISTS `ip_forbidden`;
CREATE TABLE `ip_forbidden` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(256) DEFAULT NULL COMMENT 'IP地址',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,0:删除,1:正常,2:停用',
  `expire_time` datetime DEFAULT NULL COMMENT '到期时间',
  `description` varchar(256) DEFAULT NULL COMMENT '说明',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ip数据';


DROP TABLE IF EXISTS `login_status`;
CREATE TABLE `login_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `session_id` varchar(256) DEFAULT NULL COMMENT 'session id',
  `session_expires` datetime DEFAULT NULL COMMENT 'session过期时间',
  `user_login_name` varchar(256) DEFAULT NULL COMMENT '登录名',
  `last_login_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  `platform` tinyint(4) DEFAULT NULL COMMENT '登录平台 1:web 2:android 3:ios',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,1:正常,2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志';


DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `full_name` varchar(256) DEFAULT NULL COMMENT '全称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) DEFAULT '0',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,0:删除,1:正常,2:停用',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';


DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `parent_id` bigint(20) DEFAULT '0',
  `state` int(11) DEFAULT NULL COMMENT '数据状态0:删除,1:正常,2:停用',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `state` int(11) DEFAULT NULL COMMENT '数据状态0:删除,1:正常,2:停用',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


DROP TABLE IF EXISTS `role_organization`;
CREATE TABLE `role_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL COMMENT '组织id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `name` varchar(256) DEFAULT NULL,
  `full_name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `state` int(11) DEFAULT NULL COMMENT '数据状态0:删除,1:正常,2:停用',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门角色表';


DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限关系ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(256) DEFAULT NULL COMMENT '登陆名',
  `nick_name` varchar(256) DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `email` varchar(255) DEFAULT NULL COMMENT 'Email',
  `phone` varchar(255) DEFAULT NULL COMMENT '座机',
  `mobile` varchar(255) DEFAULT NULL COMMENT '电话',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '数据状态,0:删除,1:正常,2:禁用账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL COMMENT '用户角色关系ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
