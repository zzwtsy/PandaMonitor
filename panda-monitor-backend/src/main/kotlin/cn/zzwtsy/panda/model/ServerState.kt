package cn.zzwtsy.panda.model

import org.babyfish.jimmer.jackson.JsonConverter
import org.babyfish.jimmer.jackson.LongToStringConverter
import org.babyfish.jimmer.sql.*

/**
 * 服务器主机状态
 *
 * @author zzwtsy
 */
@Entity
@Table(name = "server_state")
interface ServerState {
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

    /**
     * server_monitor 表 id
     */
    @IdView("serverMonitor")
    val serverMonitorId: Long

    /**
     * CPU 使用率
     */
    @Column(name = "cpu_usage")
    val cpuUsage: Float

    /**
     * 已使用内存(字节)
     */
    @Column(name = "mem_used")
    @JsonConverter(LongToStringConverter::class)
    val memUsed: Long

    /**
     * 已使用交换空间(字节)
     */
    @Column(name = "swap_used")
    @JsonConverter(LongToStringConverter::class)
    val swapUsed: Long

    /**
     * 已使用磁盘空间(字节)
     */
    @Column(name = "disk_used")
    @JsonConverter(LongToStringConverter::class)
    val diskUsed: Long

    /**
     * 网络接收数据量(字节)
     */
    @Column(name = "net_in_transfer")
    @JsonConverter(LongToStringConverter::class)
    val netInTransfer: Long

    /**
     * 网络发送数据量(字节)
     */
    @Column(name = "net_out_transfer")
    @JsonConverter(LongToStringConverter::class)
    val netOutTransfer: Long

    /**
     * 网络接收速度(字节)
     */
    @Column(name = "net_in_speed")
    @JsonConverter(LongToStringConverter::class)
    val netInSpeed: Long

    /**
     * 网络发送速度(字节)
     */
    @Column(name = "net_out_speed")
    @JsonConverter(LongToStringConverter::class)
    val netOutSpeed: Long

    /**
     * 1分钟平均负载
     */
    @Column(name = "load1")
    val load1: Float

    /**
     * 5分钟平均负载
     */
    @Column(name = "load5")
    val load5: Float

    /**
     * 15分钟平均负载
     */
    @Column(name = "load15")
    val load15: Float

    /**
     * 上报时间 Unix 时间戳(秒)
     */
    @Column(name = "upload_time")
    val uploadTime: Long

    /**
     * 探针版本
     */
    @Column(name = "agent_version")
    val agentVersion: String
}
