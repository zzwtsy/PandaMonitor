export interface UpdateUserInput {
  readonly id?: number | undefined
  /**
   * 用户名
   */
  readonly name: string
  /**
   * 密码
   */
  readonly password: string
}
