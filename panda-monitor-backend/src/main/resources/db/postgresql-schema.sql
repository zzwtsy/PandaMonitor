CREATE TABLE "user"
(
    id       BIGSERIAL PRIMARY KEY, -- 用户 ID
    name     VARCHAR(255) NOT NULL, -- 用户名
    password VARCHAR(255) NOT NULL  -- 密码
);
COMMENT ON TABLE "user" IS '用户';
COMMENT ON COLUMN "user".id IS '用户 ID';
COMMENT ON COLUMN "user".name IS '用户名';
COMMENT ON COLUMN "user".password IS '密码';

CREATE TABLE server_host
(
    id                BIGSERIAL PRIMARY KEY, -- 主机 ID
    server_monitor_id BIGINT       NOT NULL, -- server_monitor 表 ID
    os_name           VARCHAR(255) NOT NULL, -- 操作系统名称
    os_version        VARCHAR(255) NOT NULL, -- 操作系统版本
    distribution_id   VARCHAR(255) NOT NULL, -- 操作系统发行版 ID
    kernel_version    VARCHAR(255) NOT NULL, -- 内核版本
    cpu               TEXT         NOT NULL, -- CPU 信息
    cpu_cores         INTEGER      NOT NULL, -- CPU 核心数
    mem_total         BIGINT       NOT NULL, -- 总内存（字节）
    disk_total        BIGINT       NOT NULL, -- 总磁盘空间（字节）
    swap_total        BIGINT       NOT NULL, -- 总交换空间（字节）
    arch              VARCHAR(255) NOT NULL, -- 系统架构
    boot_time         TIMESTAMP    NOT NULL, -- 开机时间 Unix 时间戳（秒）
    ipv4              inet         NOT NULL, -- 主机 IPV4 地址
    ipv6              inet         NOT NULL, -- 主机 IPV6 地址
    country_code      VARCHAR(255) NOT NULL, -- 国家代码
    upload_time       TIMESTAMP    NOT NULL, -- 上报时间 Unix 时间戳（秒）
    agent_version     VARCHAR(255) NOT NULL  -- 探针版本号
);
COMMENT ON TABLE server_host IS '服务器主机';
COMMENT ON COLUMN server_host.server_monitor_id IS 'server_monitor 表 ID';
COMMENT ON COLUMN server_host.os_name IS '操作系统名称';
COMMENT ON COLUMN server_host.os_version IS '操作系统版本';
COMMENT ON COLUMN server_host.distribution_id IS '操作系统发行版 ID';
COMMENT ON COLUMN server_host.kernel_version IS '内核版本';
COMMENT ON COLUMN server_host.cpu IS 'CPU 信息';
COMMENT ON COLUMN server_host.cpu_cores IS 'CPU 核心数';
COMMENT ON COLUMN server_host.mem_total IS '总内存（字节）';
COMMENT ON COLUMN server_host.disk_total IS '总磁盘空间（字节）';
COMMENT ON COLUMN server_host.swap_total IS '总交换空间（字节）';
COMMENT ON COLUMN server_host.arch IS '系统架构';
COMMENT ON COLUMN server_host.boot_time IS '开机时间 Unix 时间戳（秒）';
COMMENT ON COLUMN server_host.ipv4 IS '主机 IPV4 地址';
COMMENT ON COLUMN server_host.ipv6 IS '主机 IPV6 地址';
COMMENT ON COLUMN server_host.country_code IS '国家代码';
COMMENT ON COLUMN server_host.upload_time IS '上报时间 Unix 时间戳（秒）';
COMMENT ON COLUMN server_host.agent_version IS '探针版本号';

CREATE TABLE server_state
(
    id                BIGSERIAL PRIMARY KEY, -- 状态 ID
    server_monitor_id BIGINT       NOT NULL, -- server_monitor 表 ID
    mem_used          BIGINT       NOT NULL, -- 已使用内存（字节）
    swap_used         BIGINT       NOT NULL, -- 已使用交换空间（字节）
    disk_used         BIGINT       NOT NULL, -- 已使用磁盘空间（字节）
    net_in_transfer   BIGINT       NOT NULL, -- 网络接收数据量（字节）
    net_out_transfer  BIGINT       NOT NULL, -- 网络发送数据量（字节）
    net_in_speed      BIGINT       NOT NULL, -- 网络接收速度（字节）
    net_out_speed     BIGINT       NOT NULL, -- 网络发送速度（字节）
    load1             REAL         NOT NULL, -- 1分钟平均负载
    load5             REAL         NOT NULL, -- 5分钟平均负载
    load15            REAL         NOT NULL, -- 15分钟平均负载
    upload_time       TIMESTAMP    NOT NULL, -- 上报时间 Unix 时间戳（秒）
    agent_version     VARCHAR(255) NOT NULL  -- 探针版本
);
COMMENT ON TABLE server_state IS '服务器状态';
COMMENT ON COLUMN server_state.server_monitor_id IS 'server_monitor 表 ID';
COMMENT ON COLUMN server_state.mem_used IS '已使用内存（字节）';
COMMENT ON COLUMN server_state.swap_used IS '已使用交换空间（字节）';
COMMENT ON COLUMN server_state.disk_used IS '已使用磁盘空间（字节）';
COMMENT ON COLUMN server_state.net_in_transfer IS '网络接收数据量（字节）';
COMMENT ON COLUMN server_state.net_out_transfer IS '网络发送数据量（字节）';
COMMENT ON COLUMN server_state.net_in_speed IS '网络接收速度（字节）';
COMMENT ON COLUMN server_state.net_out_speed IS '网络发送速度（字节）';
COMMENT ON COLUMN server_state.load1 IS '1分钟平均负载';
COMMENT ON COLUMN server_state.load5 IS '5分钟平均负载';
COMMENT ON COLUMN server_state.load15 IS '15分钟平均负载';
COMMENT ON COLUMN server_state.upload_time IS '上报时间 Unix 时间戳（秒）';
COMMENT ON COLUMN server_state.agent_version IS '探针版本';

CREATE TABLE server_monitor
(
    id              BIGSERIAL PRIMARY KEY, -- 服务器监控 ID
    key             VARCHAR(255) NOT NULL, -- 关键字
    server_name     VARCHAR(255) NOT NULL, -- 服务器名称
    visitor_hiding  BOOLEAN      NOT NULL, -- 是否对游客隐藏
    sort_id         INTEGER      NOT NULL, -- 用于服务器展示顺序
    server_group_id BIGINT       NOT NULL  -- 服务器分组 ID
);
COMMENT ON TABLE server_monitor IS '服务器监控';
COMMENT ON COLUMN server_monitor.key IS '关键字';
COMMENT ON COLUMN server_monitor.server_name IS '服务器名称';
COMMENT ON COLUMN server_monitor.visitor_hiding IS '是否对游客隐藏';
COMMENT ON COLUMN server_monitor.sort_id IS '用于服务器展示顺序';
COMMENT ON COLUMN server_monitor.server_group_id IS '服务器分组 ID';

CREATE TABLE server_group
(
    -- 服务器分组 ID
    id   BIGSERIAL PRIMARY KEY,
    -- 服务器分组名称
    name VARCHAR(255) NOT NULL
);
COMMENT ON TABLE server_group IS '服务器分组';
COMMENT ON COLUMN server_group.id IS '服务器分组 ID';
COMMENT ON COLUMN server_group.name IS '服务器分组名称';