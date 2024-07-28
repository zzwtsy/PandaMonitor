import type {
  ListServerMonitorView_TargetOf_serverGroup,
  ListServerMonitorView_TargetOf_serverHosts
} from './'

/**
 * 监控的服务器
 */
export interface ListServerMonitorView {
  readonly id: number
  /**
   * 服务器名称
   */
  readonly serverName: string
  /**
   * 是否对游客隐藏
   */
  readonly visitorHiding: boolean
  /**
   * 排序 id，用于服务器展示顺序
   */
  readonly sortId: number
  readonly serverGroup: ListServerMonitorView_TargetOf_serverGroup
  readonly serverHosts: ReadonlyArray<ListServerMonitorView_TargetOf_serverHosts>
}
