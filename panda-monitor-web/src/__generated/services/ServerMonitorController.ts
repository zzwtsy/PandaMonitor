import type { Executor } from '../'
import type {
  AddServerMonitorInput,
  ListServerMonitorView,
  Result,
  UpdateServerMonitorInput
} from '../model/static/'

export class ServerMonitorController {
  constructor(private executor: Executor) {}

  /**
   * 添加服务器监控
   *
   * @param input 服务器监控信息
   */
  readonly addServerMonitor: (
    options: ServerMonitorControllerOptions['addServerMonitor']
  ) => Promise<Result<ListServerMonitorView>> = async (options) => {
    let _uri = '/serverMonitor'
    let _separator = _uri.indexOf('?') === -1 ? '?' : '&'
    let _value: any = undefined
    _value = options.input.serverName
    _uri += _separator
    _uri += 'serverName='
    _uri += encodeURIComponent(_value)
    _separator = '&'
    _value = options.input.visitorHiding
    _uri += _separator
    _uri += 'visitorHiding='
    _uri += encodeURIComponent(_value)
    _separator = '&'
    _value = options.input.sortId
    _uri += _separator
    _uri += 'sortId='
    _uri += encodeURIComponent(_value)
    _separator = '&'
    _value = options.input.serverGroupId
    _uri += _separator
    _uri += 'serverGroupId='
    _uri += encodeURIComponent(_value)
    _separator = '&'
    return (await this.executor({ uri: _uri, method: 'POST' })) as Promise<
      Result<ListServerMonitorView>
    >
  }

  /**
   * 删除服务器监控
   *
   * @param serverMonitorId 服务器监控 id
   */
  readonly deleteServerMonitor: (
    options: ServerMonitorControllerOptions['deleteServerMonitor']
  ) => Promise<Result<ReadonlyArray<ListServerMonitorView>>> = async (options) => {
    let _uri = '/serverMonitor/'
    _uri += encodeURIComponent(options.serverMonitorId)
    return (await this.executor({ uri: _uri, method: 'DELETE' })) as Promise<
      Result<ReadonlyArray<ListServerMonitorView>>
    >
  }

  /**
   * 获取服务器监控列表
   */
  readonly listServerMonitor: () => Promise<Result<ReadonlyArray<ListServerMonitorView>>> =
    async () => {
      let _uri = '/serverMonitor/list'
      return (await this.executor({ uri: _uri, method: 'GET' })) as Promise<
        Result<ReadonlyArray<ListServerMonitorView>>
      >
    }

  /**
   * 更新服务器监控信息
   *
   * @param input 更新的服务器监控信息
   */
  readonly updateServerMonitor: (
    options: ServerMonitorControllerOptions['updateServerMonitor']
  ) => Promise<Result<ReadonlyArray<ListServerMonitorView>>> = async (options) => {
    let _uri = '/serverMonitor'
    let _separator = _uri.indexOf('?') === -1 ? '?' : '&'
    let _value: any = undefined
    _value = options.input.id
    if (_value !== undefined && _value !== null) {
      _uri += _separator
      _uri += 'id='
      _uri += encodeURIComponent(_value)
      _separator = '&'
    }
    _value = options.input.serverName
    if (_value !== undefined && _value !== null) {
      _uri += _separator
      _uri += 'serverName='
      _uri += encodeURIComponent(_value)
      _separator = '&'
    }
    _value = options.input.visitorHiding
    if (_value !== undefined && _value !== null) {
      _uri += _separator
      _uri += 'visitorHiding='
      _uri += encodeURIComponent(_value)
      _separator = '&'
    }
    _value = options.input.sortId
    if (_value !== undefined && _value !== null) {
      _uri += _separator
      _uri += 'sortId='
      _uri += encodeURIComponent(_value)
      _separator = '&'
    }
    return (await this.executor({ uri: _uri, method: 'PUT' })) as Promise<
      Result<ReadonlyArray<ListServerMonitorView>>
    >
  }
}

export type ServerMonitorControllerOptions = {
  listServerMonitor: {}
  addServerMonitor: {
    /**
     * 服务器监控信息
     */
    readonly input: AddServerMonitorInput
  }
  updateServerMonitor: {
    /**
     * 更新的服务器监控信息
     */
    readonly input: UpdateServerMonitorInput
  }
  deleteServerMonitor: {
    /**
     * 服务器监控 id
     */
    readonly serverMonitorId: number
  }
}
