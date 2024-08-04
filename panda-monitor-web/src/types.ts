import type { Dynamic_ServerHost, Dynamic_ServerState } from '@/__generated/model/dynamic'

export type WebSocketDTO = {
  readonly id: number
  readonly groupName: string
  readonly serverMonitor: ServerMonitorDTO[]
}

export type ServerMonitorDTO = {
  readonly id: number
  readonly sortId: number
  readonly serverName: string
  readonly serverHost: Dynamic_ServerHost | null
  readonly serverState: Dynamic_ServerState | null
}

export type UserInfo = {
  userId: number
  userName: string
  token: string
}
