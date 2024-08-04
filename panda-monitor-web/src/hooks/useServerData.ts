import { ref, computed, watchEffect } from 'vue'
import { useLocalStorage, useWebSocket } from '@vueuse/core'
import { toast } from 'vue-sonner'
import type { Result } from '@/__generated/model/static'
import type { UserInfo, WebSocketDTO } from '@/types'

type ServerGroupItem = {
  readonly id: number
  readonly groupName: string
}

export function useServerData() {
  const serverGroupName = ref('')
  const userInfo = useLocalStorage<UserInfo>('userInfo', null)
  const { data } = useWebSocket<string>('ws://localhost:8080/api/ws', {
    onConnected(ws) {
      ws.send(`Authorization: Bearer ${userInfo.value.token}`)
      if (ws.readyState === WebSocket.OPEN) {
        toast.success('连接服务器成功')
      }
    },
    onError() {
      toast.error('连接服务器失败')
    }
  })

  const parseWebSocketData = (value: string) => {
    try {
      const webSocketDTO: Result<WebSocketDTO[]> = JSON.parse(value)
      if (!webSocketDTO || webSocketDTO.code !== 200 || !webSocketDTO.data) {
        toast.error('连接服务器失败')
        return null
      }
      return webSocketDTO.data
    } catch (error) {
      console.error('解析WebSocket数据时出错:', error)
      toast.error('连接服务器失败')
      return null
    }
  }

  // 获取服务器分组
  const serverGroupList = computed(() => {
    if (!data.value) return []
    const parsedData = parseWebSocketData(data.value)
    if (!parsedData) return []
    return parsedData.map((item) => {
      return { id: item.id, groupName: item.groupName } as ServerGroupItem
    })
  })

  // 计算服务器数据
  const serverData = computed(() => {
    if (!data.value) return []

    const parsedData = parseWebSocketData(data.value)
    if (!parsedData) return []

    const serverMonitor = parsedData
      .find((item) => item.groupName === serverGroupName.value)
      ?.serverMonitor.sort((a, b) => a.sortId - b.sortId)

    if (!serverMonitor || serverMonitor === void 0) {
      console.warn('未找到匹配的服务器组名')
      return []
    }

    return serverMonitor
  })

  // 初始化serverGroupName
  const initializedServerGroupName = computed({
    get: () => serverGroupName.value,
    set: (newValue) => {
      serverGroupName.value = newValue
    }
  })

  // 当serverGroupList变化时，设置默认值
  watchEffect(() => {
    if (serverGroupList.value.length > 0 && !initializedServerGroupName.value) {
      initializedServerGroupName.value = serverGroupList.value[0].groupName
    }
  })

  return {
    serverGroupName: initializedServerGroupName,
    data,
    serverGroupList,
    serverData
  }
}
