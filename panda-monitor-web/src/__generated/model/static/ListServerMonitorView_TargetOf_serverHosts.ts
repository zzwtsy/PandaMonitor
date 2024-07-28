/**
 * 服务器主机信息
 */
export interface ListServerMonitorView_TargetOf_serverHosts {
  readonly id: number
  /**
   * 主机 IPV4 地址
   */
  readonly ipv4: string
  /**
   * 主机 IPV6 地址
   */
  readonly ipv6: string
  /**
   * 探针版本号
   */
  readonly agentVersion: string
}
