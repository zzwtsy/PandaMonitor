CREATE TABLE IF NOT EXISTS "user"
(
    "id"       INTEGER NOT NULL UNIQUE,
    "name"     TEXT    NOT NULL,
    "password" TEXT    NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "server_host"
(
    "id"              INTEGER NOT NULL UNIQUE,
    -- 自定义服务器名称
    "name"            TEXT    NOT NULL,
    -- 操作系统名称
    "os_name"         TEXT    NOT NULL,
    -- 操作系统版本
    "os_version"      TEXT    NOT NULL,
    "long_os_version" TEXT    NOT NULL,
    -- 内核版本
    "kernel_version"  TEXT    NOT NULL,
    -- CPU型号
    "cpu"             TEXT    NOT NULL,
    -- CPU核心数
    "cpu_cores"       INTEGER NOT NULL,
    -- 总内存
    "mem_total"       INTEGER NOT NULL,
    -- 总磁盘空间
    "disk_total"      INTEGER NOT NULL,
    -- 总交换空间
    "swap_total"      INTEGER NOT NULL,
    -- 系统架构
    "arch"            TEXT    NOT NULL,
    -- 开机时间 Unix 时间戳(秒)
    "boot_time"       TEXT    NOT NULL,
    -- 主机 IPV4 地址
    "ipv4"            TEXT    NOT NULL,
    -- 主机 IPV6 地址
    "ipv6"            TEXT    NOT NULL,
    -- 国家代码
    "country_code"    TEXT    NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "server_state"
(
    "id"               INTEGER NOT NULL UNIQUE,
    "server_host_id"   INTEGER NOT NULL UNIQUE,
    -- CPU 使用率
    "cpu_usage"        REAL    NOT NULL,
    -- 已使用内存(字节)
    "mem_used"         INTEGER NOT NULL,
    -- 已使用交换空间(字节)
    "swap_used"        INTEGER NOT NULL,
    -- 已使用磁盘空间(字节)
    "disk_used"        INTEGER NOT NULL,
    -- 网络接收数据量(字节)
    "net_in_transfer"  INTEGER NOT NULL,
    -- 网络发送数据量(字节)
    "net_out_transfer" INTEGER NOT NULL,
    -- 网络接收速度(字节)
    "net_in_speed"     INTEGER NOT NULL,
    -- 网络发送速度(字节)
    "net_out_speed"    INTEGER NOT NULL,
    -- 1分钟平均负载
    "load1"            REAL    NOT NULL,
    -- 5分钟平均负载
    "load5"            REAL    NOT NULL,
    -- 15分钟平均负载
    "load15"           REAL    NOT NULL,
    -- 探针版本
    "agent_version"    TEXT    NOT NULL,
    -- 上报时间 Unix 时间戳(秒)
    "upload_time"      INTEGER NOT NULL,
    PRIMARY KEY ("id")
);
