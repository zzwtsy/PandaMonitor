import type { Executor } from '../'
import type { Dynamic_ServerGroup } from '../model/dynamic/'
import type { Result, ServerGroupInput } from '../model/static/'

export class ServerGroupController {
  constructor(private executor: Executor) {}

  /**
   * 添加服务器分组
   *
   * @param input 服务器分组信息
   */
  readonly addServerGroup: (
    options: ServerGroupControllerOptions['addServerGroup']
  ) => Promise<Result<Dynamic_ServerGroup>> = async (options) => {
    let _uri = '/serverGroup'
    let _separator = _uri.indexOf('?') === -1 ? '?' : '&'
    let _value: any = undefined
    _value = options.input.name
    _uri += _separator
    _uri += 'name='
    _uri += encodeURIComponent(_value)
    _separator = '&'
    return (await this.executor({ uri: _uri, method: 'POST' })) as Promise<
      Result<Dynamic_ServerGroup>
    >
  }

  /**
   * 删除服务器分组
   *
   * @param serverName 服务器分组名称
   */
  readonly deleteServerGroup: (
    options: ServerGroupControllerOptions['deleteServerGroup']
  ) => Promise<Result<string>> = async (options) => {
    let _uri = '/serverGroup/'
    _uri += encodeURIComponent(options.serverName)
    return (await this.executor({ uri: _uri, method: 'DELETE' })) as Promise<Result<string>>
  }

  /**
   * 获取服务器分组列表
   */
  readonly listServerGroup: () => Promise<Result<ReadonlyArray<Dynamic_ServerGroup>>> =
    async () => {
      let _uri = '/serverGroup/list'
      return (await this.executor({ uri: _uri, method: 'GET' })) as Promise<
        Result<ReadonlyArray<Dynamic_ServerGroup>>
      >
    }

  /**
   * 修改服务器分组名称
   *
   * @param input 服务器分组信息
   */
  readonly updateServerGroup: (
    options: ServerGroupControllerOptions['updateServerGroup']
  ) => Promise<Result<Dynamic_ServerGroup>> = async (options) => {
    let _uri = '/serverGroup'
    let _separator = _uri.indexOf('?') === -1 ? '?' : '&'
    let _value: any = undefined
    _value = options.input.name
    _uri += _separator
    _uri += 'name='
    _uri += encodeURIComponent(_value)
    _separator = '&'
    return (await this.executor({ uri: _uri, method: 'PUT' })) as Promise<
      Result<Dynamic_ServerGroup>
    >
  }
}

export type ServerGroupControllerOptions = {
  addServerGroup: {
    /**
     * 服务器分组信息
     */
    readonly input: ServerGroupInput
  }
  deleteServerGroup: {
    /**
     * 服务器分组名称
     */
    readonly serverName: string
  }
  updateServerGroup: {
    /**
     * 服务器分组信息
     */
    readonly input: ServerGroupInput
  }
  listServerGroup: {}
}
