package cn.zzwtsy.panda.model

import org.babyfish.jimmer.sql.*

/**
 * 监控的服务器
 *
 * @author zzwtsy
 */
@Entity
@Table(name = "server_monitor")
interface ServerMonitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @OneToMany(mappedBy = "serverMonitor")
    val serverStates: List<ServerState>

    @IdView("serverStates")
    val serverStateIds: List<Long>

    @OneToMany(mappedBy = "serverMonitor")
    val serverHosts: List<ServerHost>

    @IdView("serverHosts")
    val serverHostIds: List<Long>

    @Column(name = "key")
    val key: String

    /**
     * 服务器名称
     */
    @Column(name = "server_name")
    val serverName: String

    /**
     * 是否对游客隐藏
     */
    @Column(name = "visitor_hiding")
    val visitorHiding: Boolean

    /**
     * 排序 id，用于服务器展示顺序
     */
    @Column(name = "sort_id")
    val sortId: Int

    @ManyToOne
    @JoinColumn(
        name = "server_group_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.NONE)
    val serverGroup: ServerGroup

    /**
     * 服务器分组 id
     */
    @IdView("serverGroup")
    val serverGroupId: Long
}
