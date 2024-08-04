CREATE TABLE IF NOT EXISTS `user`
(
    `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    -- 用户名
    `name`     VARCHAR(255)    NOT NULL COMMENT '用户名',
    -- 密码
    `password` VARCHAR(255)    NOT NULL COMMENT '密码',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `server_host`
(
    `id`                BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
    -- server_monitor 表 id
    `server_monitor_id` BIGINT UNSIGNED  NOT NULL COMMENT 'server_monitor 表 id',
    -- 操作系统名称
    `os_name`           VARCHAR(255)     NOT NULL COMMENT '操作系统名称',
    -- 操作系统版本
    `os_version`        VARCHAR(255)     NOT NULL COMMENT '操作系统版本',
    -- 操作系统发行版 id
    `distribution_id`   VARCHAR(255)     NOT NULL COMMENT '操作系统发行版 id',
    -- 内核版本
    `kernel_version`    VARCHAR(255)     NOT NULL COMMENT '内核版本',
    -- CPU信息
    `cpu`               TEXT             NOT NULL COMMENT 'CPU信息',
    -- CPU核心数
    `cpu_cores`         INTEGER UNSIGNED NOT NULL COMMENT 'CPU核心数',
    -- 总内存(字节)
    `mem_total`         BIGINT UNSIGNED  NOT NULL COMMENT '总内存(字节)',
    -- 总磁盘空间(字节)
    `disk_total`        BIGINT UNSIGNED  NOT NULL COMMENT '总磁盘空间(字节)',
    -- 总交换空间(字节)
    `swap_total`        BIGINT UNSIGNED  NOT NULL COMMENT '总交换空间(字节)',
    -- 系统架构
    `arch`              VARCHAR(255)     NOT NULL COMMENT '系统架构',
    -- 开机时间 Unix 时间戳(秒)
    `boot_time`         TIMESTAMP        NOT NULL COMMENT '开机时间 Unix 时间戳(秒)',
    -- 主机 IPV4 地址
    `ipv4`              VARCHAR(255)     NOT NULL COMMENT '主机 IPV4 地址',
    -- 主机 IPV6 地址
    `ipv6`              VARCHAR(255)     NOT NULL COMMENT '主机 IPV6 地址',
    -- 国家代码
    `country_code`      VARCHAR(255)     NOT NULL COMMENT '国家代码',
    -- 上报时间
    `upload_time`       TIMESTAMP        NOT NULL COMMENT '上报时间',
    -- 探针版本号
    `agent_version`     VARCHAR(255)     NOT NULL COMMENT '探针版本号',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `server_state`
(
    `id`                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    -- server_monitor 表 id
    `server_monitor_id` BIGINT UNSIGNED NOT NULL COMMENT 'server_monitor 表 id',

    -- 已使用内存(字节)
    `mem_used`          BIGINT UNSIGNED NOT NULL COMMENT '已使用内存(字节)',
    -- 已使用交换空间(字节)
    `swap_used`         BIGINT UNSIGNED NOT NULL COMMENT '已使用交换空间(字节)',
    -- 已使用磁盘空间(字节)
    `disk_used`         BIGINT UNSIGNED NOT NULL COMMENT '已使用磁盘空间(字节)',
    -- 网络接收数据量(字节)
    `net_in_transfer`   BIGINT UNSIGNED NOT NULL COMMENT '网络接收数据量(字节)',
    -- 网络发送数据量(字节)
    `net_out_transfer`  BIGINT UNSIGNED NOT NULL COMMENT '网络发送数据量(字节)',
    -- 网络接收速度(字节)
    `net_in_speed`      BIGINT UNSIGNED NOT NULL COMMENT '网络接收速度(字节)',
    -- 网络发送速度(字节)
    `net_out_speed`     BIGINT UNSIGNED NOT NULL COMMENT '网络发送速度(字节)',
    -- 1分钟平均负载
    `load1`             FLOAT UNSIGNED  NOT NULL COMMENT '1分钟平均负载',
    -- 5分钟平均负载
    `load5`             FLOAT UNSIGNED  NOT NULL COMMENT '5分钟平均负载',
    -- 15分钟平均负载
    `load15`            FLOAT UNSIGNED  NOT NULL COMMENT '15分钟平均负载',
    -- 上报时间 Unix 时间戳(秒)
    `upload_time`       TIMESTAMP       NOT NULL COMMENT '上报时间 Unix 时间戳(秒)',
    -- 探针版本
    `agent_version`     VARCHAR(255)    NOT NULL COMMENT '探针版本',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `server_monitor`
(
    `id`              BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
    `key`             VARCHAR(255)     NOT NULL,
    -- 服务器名称
    `server_name`     VARCHAR(255)     NOT NULL COMMENT '服务器名称',
    -- 是否对游客隐藏
    `visitor_hiding`  BOOLEAN          NOT NULL COMMENT '是否对游客隐藏',
    -- 用于服务器展示顺序
    `sort_id`         INTEGER UNSIGNED NOT NULL COMMENT '用于服务器展示顺序',
    -- 服务器分组 id
    `server_group_id` BIGINT UNSIGNED  NOT NULL COMMENT '服务器分组 id',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `server_group`
(
    `id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    -- 服务器分组名称
    `name` VARCHAR(255)    NOT NULL COMMENT '服务器分组名称',
    PRIMARY KEY (`id`)
);
