<script setup lang="ts">
import { ServerStatusCard } from '@/components/server-status-card'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue
} from '@/components/ui/select'
import { WifiOffIcon } from 'lucide-vue-next'
import { useServerData } from '@/hooks/useServerData'

const { serverData, serverGroupList, serverGroupName } = useServerData()
</script>

<template>
  <!-- 服务器分组 -->
  <Select
    v-if="!serverGroupList || serverGroupList.length > 0"
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
  <!-- 如果果没有服务器数据，则显示暂无可监控的服务器，并添加淡入淡出效果 -->
  <div
    v-if="serverData.length === 0"
    class="absolute top-1/2 left-1/2 translate-x-[-50%] flex flex-col items-center justify-center"
  >
    <WifiOffIcon class="w-8 h-8" />
    <h1 class="text-xl mt-3">暂无可监控的服务器</h1>
  </div>
</template>

<style scoped></style>
