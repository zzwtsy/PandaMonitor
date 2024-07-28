import type { Dynamic_ServerMonitor } from './'

/**
 * 服务器分组
 *
 */
export interface Dynamic_ServerGroup {
  readonly id?: number
  readonly serverMonitors?: ReadonlyArray<Dynamic_ServerMonitor>
  readonly serverMonitorIds?: ReadonlyArray<number>
  /**
   * 服务器分组名称
   */
  readonly name?: string
}
