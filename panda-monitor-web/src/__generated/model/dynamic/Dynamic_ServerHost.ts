import type { Dynamic_ServerMonitor } from './'

/**
 * 服务器主机信息
 *
 */
export interface Dynamic_ServerHost {
  readonly id?: number
  readonly serverMonitor?: Dynamic_ServerMonitor
  readonly serverMonitorId?: number
  /**
   * 操作系统名称
   */
  readonly osName?: string
  /**
   * 操作系统版本
   */
  readonly osVersion?: string
  /**
   * 操作系统版本(长)
   */
  readonly distributionId?: string
  /**
   * 内核版本
   */
  readonly kernelVersion?: string
  /**
   * CPU信息
   */
  readonly cpu?: string
  /**
   * CPU核心数
   */
  readonly cpuCores?: number
  /**
   * 总内存(字节)
   */
  readonly memTotal?: string
  /**
   * 总磁盘空间(字节)
   */
  readonly diskTotal?: string
  /**
   * 总交换空间(字节)
   */
  readonly swapTotal?: string
  /**
   * 系统架构
   */
  readonly arch?: string
  /**
   * 开机时间 Unix 时间戳(秒)
   */
  readonly bootTime?: number
  /**
   * 主机 IPV4 地址
   */
  readonly ipv4?: string
  /**
   * 主机 IPV6 地址
   */
  readonly ipv6?: string
  /**
   * 国家代码
   */
  readonly countryCode?: string
  /**
   * 上报时间 Unix 时间戳(秒)
   */
  readonly uploadTime?: number
  /**
   * 探针版本号
   */
  readonly agentVersion?: string
}
