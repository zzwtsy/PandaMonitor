import type { Executor } from './'
import { ServerGroupController, ServerMonitorController, UserController } from './services/'

export class Api {
  readonly serverGroupController: ServerGroupController

  readonly serverMonitorController: ServerMonitorController

  readonly userController: UserController

  constructor(executor: Executor) {
    this.serverGroupController = new ServerGroupController(executor)
    this.serverMonitorController = new ServerMonitorController(executor)
    this.userController = new UserController(executor)
  }
}
