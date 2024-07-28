import type { Dynamic_ServerGroup, Dynamic_ServerHost, Dynamic_ServerState } from './'

/**
 * 监控的服务器
 *
 */
export interface Dynamic_ServerMonitor {
  readonly id?: number
  readonly serverStates?: ReadonlyArray<Dynamic_ServerState>
  readonly serverStateIds?: ReadonlyArray<number>
  readonly serverHosts?: ReadonlyArray<Dynamic_ServerHost>
  readonly serverHostIds?: ReadonlyArray<number>
  readonly key?: string
  /**
   * 服务器名称
   */
  readonly serverName?: string
  /**
   * 是否对游客隐藏
   */
  readonly visitorHiding?: boolean
  /**
   * 排序 id，用于服务器展示顺序
   */
  readonly sortId?: number
  readonly serverGroup?: Dynamic_ServerGroup
  /**
   * 服务器分组 id
   */
  readonly serverGroupId?: number
}
