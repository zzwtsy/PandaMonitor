package cn.zzwtsy.panda.model

import org.babyfish.jimmer.sql.*

/**
 * 服务器分组
 *
 * @author zzwtsy
 */
@Entity
@Table(name = "server_group")
interface ServerGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @OneToMany(mappedBy = "serverGroup")
    val serverMonitors: List<ServerMonitor>

    @IdView("serverMonitors")
    val serverMonitorIds: List<Long>

    /**
     * 服务器分组名称
     */
    @Column(name = "name")
    val name: String
}
