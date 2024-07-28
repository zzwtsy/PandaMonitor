import type { UpdateServerMonitorInput_TargetOf_serverGroup } from './'

/**
 * 监控的服务器
 */
export interface UpdateServerMonitorInput {
  readonly id?: number | undefined
  /**
   * 服务器名称
   */
  readonly serverName?: string | undefined
  /**
   * 是否对游客隐藏
   */
  readonly visitorHiding?: boolean | undefined
  /**
   * 排序 id，用于服务器展示顺序
   */
  readonly sortId?: number | undefined
  readonly serverGroup: UpdateServerMonitorInput_TargetOf_serverGroup
}
