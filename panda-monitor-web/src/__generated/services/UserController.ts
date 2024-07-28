import type { Executor } from '../'
import type { AddUserInput, Result, UpdateUserInput, UserView } from '../model/static/'

export class UserController {
  constructor(private executor: Executor) {}

  /**
   * 添加用户
   *
   * @param input 用户信息
   */
  readonly addUser: (options: UserControllerOptions['addUser']) => Promise<Result<UserView>> =
    async (options) => {
      let _uri = '/user'
      let _separator = _uri.indexOf('?') === -1 ? '?' : '&'
      let _value: any = undefined
      _value = options.input.name
      _uri += _separator
      _uri += 'name='
      _uri += encodeURIComponent(_value)
      _separator = '&'
      _value = options.input.password
      _uri += _separator
      _uri += 'password='
      _uri += encodeURIComponent(_value)
      _separator = '&'
      return (await this.executor({ uri: _uri, method: 'POST' })) as Promise<Result<UserView>>
    }

  /**
   * 删除用户
   * @param userId 用户 id
   */
  readonly deleteUser: (options: UserControllerOptions['deleteUser']) => Promise<Result<string>> =
    async (options) => {
      let _uri = '/user/'
      _uri += encodeURIComponent(options.userId)
      return (await this.executor({ uri: _uri, method: 'DELETE' })) as Promise<Result<string>>
    }

  /**
   * 获取用户信息
   * @param userId 用户 id
   */
  readonly getUser: (options: UserControllerOptions['getUser']) => Promise<Result<UserView>> =
    async (options) => {
      let _uri = '/user/'
      _uri += encodeURIComponent(options.userId)
      return (await this.executor({ uri: _uri, method: 'GET' })) as Promise<Result<UserView>>
    }

  /**
   * 修改用户信息
   * @param input 用户信息
   */
  readonly updateUser: (options: UserControllerOptions['updateUser']) => Promise<Result<UserView>> =
    async (options) => {
      let _uri = '/user'
      let _separator = _uri.indexOf('?') === -1 ? '?' : '&'
      let _value: any = undefined
      _value = options.input.id
      if (_value !== undefined && _value !== null) {
        _uri += _separator
        _uri += 'id='
        _uri += encodeURIComponent(_value)
        _separator = '&'
      }
      _value = options.input.name
      _uri += _separator
      _uri += 'name='
      _uri += encodeURIComponent(_value)
      _separator = '&'
      _value = options.input.password
      _uri += _separator
      _uri += 'password='
      _uri += encodeURIComponent(_value)
      _separator = '&'
      return (await this.executor({ uri: _uri, method: 'PUT' })) as Promise<Result<UserView>>
    }
}

export type UserControllerOptions = {
  getUser: {
    /**
     * 用户 id
     */
    readonly userId: number
  }
  updateUser: {
    /**
     * 用户信息
     */
    readonly input: UpdateUserInput
  }
  addUser: {
    /**
     * 用户信息
     */
    readonly input: AddUserInput
  }
  deleteUser: {
    /**
     * 用户 id
     */
    readonly userId: number
  }
}
