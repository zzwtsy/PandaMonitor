<script setup lang="ts">
import { useWebSocket } from '@vueuse/core'
import { ref, watch } from 'vue'
import type { ServerMonitorDTO, WebSocketDTO } from '@/types'
import { ServerStatusCard } from '@/components/server-status-card'
import type { Result } from '@/__generated/model/static'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue
} from '@/components/ui/select'
import { toast } from 'vue-sonner'
import { WifiOffIcon, LoaderIcon } from 'lucide-vue-next'

const serverGroupName = ref('')
const serverGroupList = ref<string[]>([])
const serverData = ref<ServerMonitorDTO[]>()
const { data } = useWebSocket<string>('ws://localhost:8080/api/ws', {
  onConnected(ws) {
    ws.send(`Authorization: Bearer ${localStorage.getItem('token')}`)
    if (ws.readyState === WebSocket.OPEN) {
      toast.success('连接服务器成功')
    }
  },
  onError() {
    toast.error(`连接服务器失败`)
    serverData.value = []
  }
})

watch(data, (data) => {
  if (!data) return
  const webSocketDTO: Result<WebSocketDTO[]> = JSON.parse(data)
  if (!webSocketDTO || webSocketDTO.code !== 200 || !webSocketDTO.data) {
    toast.error('连接服务器失败')
    return
  }
  // 获取服务器分组
  serverGroupList.value = webSocketDTO.data.map((item) => item.groupName) ?? []
  // 默认选中第一个分组
  serverGroupName.value = serverGroupList.value[0]
  // 获取服务器数据
  serverData.value =
    webSocketDTO.data
      .find((item) => item.groupName === serverGroupName.value)
      ?.serverMonitor.sort((a, b) => a.sortId - b.sortId) ?? []
})
</script>

<template>
  <Select
    v-if="serverGroupList.length > 0"
    :default-value="serverGroupName"
    @select="(value: string) => (serverGroupName = value)"
  >
    <SelectTrigger class="w-[180px]">
      <SelectValue placeholder="Select Server Group" />
    </SelectTrigger>
    <SelectContent>
      <SelectGroup>
        <SelectItem v-for="(item, index) in serverGroupList" :key="index" :value="item">
          {{ item }}
        </SelectItem>
      </SelectGroup>
    </SelectContent>
  </Select>
  <div class="grid gap-4 mt-2 w-full lg:grid-cols-2 xl:grid-cols-3">
    <ServerStatusCard
      v-for="(serverMonitor, index) in serverData"
      :key="index"
      :serverMonitor="serverMonitor"
    />
  </div>
  <div
    v-if="!serverData"
    class="absolute top-1/2 left-1/2 translate-x-[-50%] flex flex-col items-center justify-center"
  >
    <LoaderIcon class="w-8 h-8 animate-spin" />
    <h1 class="text-xl mt-3">正在连接服务器</h1>
  </div>
  <div
    v-else-if="serverData.length === 0"
    class="absolute top-1/2 left-1/2 translate-x-[-50%] flex flex-col items-center justify-center"
  >
    <WifiOffIcon class="w-8 h-8" />
    <h1 class="text-xl mt-3">暂无可监控的服务器</h1>
  </div>
</template>

<style scoped></style>
