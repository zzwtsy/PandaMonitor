import { calcServerTime, calcUsage, formatBytes } from '@/lib/utils'
import type { ServerMonitorDTO } from '@/types'
import { calc } from 'a-calc'
import { computed } from 'vue'

type State = {
  cpuUsage: number
  memoryUsage: number
  diskUsage: number
  swapUsage: number
  netInTransfer: string
  netOutTransfer: string
  netInSpeed: string
  netOutSpeed: string
  uptime: string
}

export function useServerMonitorState(props: ServerMonitorDTO): State {
  const initializeState = (): State => ({
    cpuUsage: 0,
    memoryUsage: 0,
    diskUsage: 0,
    swapUsage: 0,
    netInTransfer: '0',
    netOutTransfer: '0',
    netInSpeed: '0',
    netOutSpeed: '0',
    uptime: '0'
  })

  const state = computed(() => {
    const serverState = props.serverState
    const serverHost = props.serverHost
    if (!serverState) {
      return initializeState()
    }
    const cpu = serverState.cpuUsage ?? 0
    const result: State = {
      cpuUsage: calc('cpu | =0!n', { cpu }),
      memoryUsage: calcUsage(serverHost?.memTotal ?? '0', serverState.memUsed ?? '0'),
      diskUsage: calcUsage(serverHost?.diskTotal ?? '0', serverState.diskUsed ?? '0'),
      swapUsage: calcUsage(serverHost?.swapTotal ?? '0', serverState.swapUsed ?? '0'),
      netInTransfer: formatBytes(serverState.netInTransfer ?? '0'),
      netOutTransfer: formatBytes(serverState.netOutTransfer ?? '0'),
      netInSpeed: formatBytes(serverState.netInSpeed ?? '0'),
      netOutSpeed: formatBytes(serverState.netOutSpeed ?? '0'),
      uptime: calcServerTime(serverHost?.bootTime ?? 0)
    }
    return result
  })

  return state.value
}
