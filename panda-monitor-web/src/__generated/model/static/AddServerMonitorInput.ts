/**
 * 监控的服务器
 */
export interface AddServerMonitorInput {
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
  /**
   * 服务器分组 id
   */
  readonly serverGroupId: number
}
