CREATE TABLE user
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    -- 用户名
    name     TEXT NOT NULL,
    -- 密码
    password TEXT NOT NULL
);

CREATE TABLE server_host
(
    -- 主机 ID
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    -- server_monitor 表 ID
    server_monitor_id INTEGER   NOT NULL,
    -- 操作系统名称
    os_name           TEXT      NOT NULL,
    -- 操作系统版本
    os_version        TEXT      NOT NULL,
    -- 操作系统发行版 ID
    distribution_id   TEXT      NOT NULL,
    -- 内核版本
    kernel_version    TEXT      NOT NULL,
    -- CPU 信息
    cpu               TEXT      NOT NULL,
    -- CPU 核心数
    cpu_cores         INTEGER   NOT NULL,
    -- 总内存（字节）
    mem_total         INTEGER   NOT NULL,
    -- 总磁盘空间（字节）
    disk_total        INTEGER   NOT NULL,
    -- 总交换空间（字节）
    swap_total        INTEGER   NOT NULL,
    -- 系统架构
    arch              TEXT      NOT NULL,
    -- 开机时间 Unix 时间戳（秒）
    boot_time         TIMESTAMP NOT NULL,
    -- 主机 IPV4 地址
    ipv4              TEXT      NOT NULL,
    -- 主机 IPV6 地址
    ipv6              TEXT      NOT NULL,
    -- 国家代码
    country_code      TEXT      NOT NULL,
    -- 上报时间
    upload_time       TIMESTAMP NOT NULL,
    -- 探针版本号
    agent_version     TEXT      NOT NULL
);

CREATE TABLE server_state
(
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    -- server_monitor 表 ID
    server_monitor_id INTEGER   NOT NULL,
    -- 已使用内存（字节）
    mem_used          INTEGER   NOT NULL,
    -- 已使用交换空间（字节）
    swap_used         INTEGER   NOT NULL,
    -- 已使用磁盘空间（字节）
    disk_used         INTEGER   NOT NULL,
    -- 网络接收数据量（字节）
    net_in_transfer   INTEGER   NOT NULL,
    -- 网络发送数据量（字节）
    net_out_transfer  INTEGER   NOT NULL,
    -- 网络接收速度（字节）
    net_in_speed      INTEGER   NOT NULL,
    -- 网络发送速度（字节）
    net_out_speed     INTEGER   NOT NULL,
    -- 1分钟平均负载
    load1             REAL      NOT NULL,
    -- 5分钟平均负载
    load5             REAL      NOT NULL,
    -- 15分钟平均负载
    load15            REAL      NOT NULL,
    -- 上报时间 Unix 时间戳（秒）
    upload_time       TIMESTAMP NOT NULL,
    -- 探针版本
    agent_version     TEXT      NOT NULL
);

CREATE TABLE server_monitor
(
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    key             TEXT    NOT NULL,
    -- 服务器名称
    server_name     TEXT    NOT NULL,
    -- 是否对游客隐藏
    visitor_hiding  BOOLEAN NOT NULL,
    -- 用于服务器展示顺序
    sort_id         INTEGER NOT NULL,
    -- 服务器分组 ID
    server_group_id INTEGER NOT NULL
);

CREATE TABLE server_group
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    -- 服务器分组名称
    name TEXT NOT NULL
);