<script setup lang="ts">
import { useWebSocket } from '@vueuse/core'
import { ref, watch } from 'vue'
import type { ServerMonitorDTO, WebSocketDTO } from '@/types'
import { ServerStatusCard } from '@/components/server-status-card'
import type { Result } from '@/__generated/model/static'

// const { serverGroupName } = useServerGroupStore()
const serverData = ref<ServerMonitorDTO[]>()
const { data } = useWebSocket<string>('ws://localhost:8080/api/ws', {
  onConnected(ws) {
    ws.send('{"type":"subscribe","data":{"groupName":"default"}}')
  }
})
watch(data, (data) => {
  if (!data) return
  const webSocketDTO: Result<WebSocketDTO[]> = JSON.parse(data)
  serverData.value = webSocketDTO.data
    ?.find((item) => item.groupName === 'default')
    ?.serverMonitor.sort((a, b) => a.sortId - b.sortId)
})
</script>

<template>
  <div class="grid gap-4 w-full lg:grid-cols-2 xl:grid-cols-3">
    <ServerStatusCard
      v-for="(serverMonitor, index) in serverData"
      :key="index"
      :serverMonitor="serverMonitor"
    />
  </div>
</template>

<style scoped></style>
