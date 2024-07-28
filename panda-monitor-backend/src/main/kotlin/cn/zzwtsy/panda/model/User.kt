package cn.zzwtsy.panda.model

import org.babyfish.jimmer.sql.*

/**
 * @author zzwtsy
 */
@Entity
@Table(name = "user")
interface User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    /**
     * 用户名
     */
    @Column(name = "name")
    val name: String

    /**
     * 密码
     */
    @Column(name = "password")
    val password: String
}
