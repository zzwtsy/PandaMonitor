import type { Dynamic_ServerMonitor } from './'

/**
 * 服务器主机状态
 *
 */
export interface Dynamic_ServerState {
  readonly id?: number
  readonly serverMonitor?: Dynamic_ServerMonitor
  /**
   * server_monitor 表 id
   */
  readonly serverMonitorId?: number
  /**
   * CPU 使用率
   */
  readonly cpuUsage?: number
  /**
   * 已使用内存(字节)
   */
  readonly memUsed?: string
  /**
   * 已使用交换空间(字节)
   */
  readonly swapUsed?: string
  /**
   * 已使用磁盘空间(字节)
   */
  readonly diskUsed?: string
  /**
   * 网络接收数据量(字节)
   */
  readonly netInTransfer?: string
  /**
   * 网络发送数据量(字节)
   */
  readonly netOutTransfer?: string
  /**
   * 网络接收速度(字节)
   */
  readonly netInSpeed?: string
  /**
   * 网络发送速度(字节)
   */
  readonly netOutSpeed?: string
  /**
   * 1分钟平均负载
   */
  readonly load1?: number
  /**
   * 5分钟平均负载
   */
  readonly load5?: number
  /**
   * 15分钟平均负载
   */
  readonly load15?: number
  /**
   * 上报时间 Unix 时间戳(秒)
   */
  readonly uploadTime?: number
  /**
   * 探针版本
   */
  readonly agentVersion?: string
}
