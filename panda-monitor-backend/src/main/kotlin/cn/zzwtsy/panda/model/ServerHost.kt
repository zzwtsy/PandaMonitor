package cn.zzwtsy.panda.model

import org.babyfish.jimmer.jackson.JsonConverter
import org.babyfish.jimmer.jackson.LongToStringConverter
import org.babyfish.jimmer.sql.*

/**
 * 服务器主机信息
 *
 * @author zzwtsy
 */
@Entity
@Table(name = "server_host")
interface ServerHost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @ManyToOne
    @JoinColumn(
        name = "server_monitor_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    val serverMonitor: ServerMonitor

    @IdView("serverMonitor")
    val serverMonitorId: Long

    /**
     * 操作系统名称
     */
    @Column(name = "os_name")
    val osName: String

    /**
     * 操作系统版本
     */
    @Column(name = "os_version")
    val osVersion: String

    /**
     * 操作系统版本(长)
     */
    @Column(name = "distribution_id")
    val distributionId: String

    /**
     * 内核版本
     */
    @Column(name = "kernel_version")
    val kernelVersion: String

    /**
     * CPU信息
     */
    @Column(name = "cpu")
    val cpu: String

    /**
     * CPU核心数
     */
    @Column(name = "cpu_cores")
    val cpuCores: Int

    /**
     * 总内存(字节)
     */
    @Column(name = "mem_total")
    @JsonConverter(LongToStringConverter::class)
    val memTotal: Long

    /**
     * 总磁盘空间(字节)
     */
    @Column(name = "disk_total")
    @JsonConverter(LongToStringConverter::class)
    val diskTotal: Long

    /**
     * 总交换空间(字节)
     */
    @Column(name = "swap_total")
    @JsonConverter(LongToStringConverter::class)
    val swapTotal: Long

    /**
     * 系统架构
     */
    @Column(name = "arch")
    val arch: String

    /**
     * 开机时间 Unix 时间戳(秒)
     */
    @Column(name = "boot_time")
    val bootTime: Long

    /**
     * 主机 IPV4 地址
     */
    @Column(name = "ipv4")
    val ipv4: String

    /**
     * 主机 IPV6 地址
     */
    @Column(name = "ipv6")
    val ipv6: String

    /**
     * 国家代码
     */
    @Column(name = "country_code")
    val countryCode: String

    /**
     * 上报时间 Unix 时间戳(秒)
     */
    @Column(name = "upload_time")
    val uploadTime: Long

    /**
     * 探针版本号
     */
    @Column(name = "agent_version")
    val agentVersion: String
}
