<script setup lang="ts">
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import {
  ActivityIcon,
  ArrowUpDownIcon,
  CircleArrowDownIcon,
  CircleArrowUpIcon,
  ClockIcon,
  CpuIcon,
  HardDriveIcon,
  HardDriveUploadIcon,
  MemoryStickIcon,
  ReplaceIcon,
  ServerCrashIcon,
  ServerIcon
} from 'lucide-vue-next'
import ServerStatusProgress from './ServerStatusProgress.vue'
import { onMounted, ref } from 'vue'
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip'
import type { ServerMonitorDTO } from '@/types'
import { cn } from '@/lib/utils'
import ServerStatusItem from '@/components/server-status-card/ServerStatusItem.vue'
import { useServerMonitorState } from '@/hooks/useServerMonitorState'

type Props = {
  serverMonitor: ServerMonitorDTO
}

const props = defineProps<Props>()

const cardTitleRef = ref<HTMLTitleElement>()
const cardTitleIsOverflowed = ref(false)

// 监听标题是否溢出
onMounted(() => {
  const element = cardTitleRef.value
  if (!element) return
  cardTitleIsOverflowed.value = element.scrollWidth > element.offsetWidth
})

const state = useServerMonitorState(props.serverMonitor)
</script>

<template>
  <Card class="bg-zinc-100 dark:bg-zinc-900">
    <CardHeader class="flex flex-row items-center justify-between">
      <div class="flex flex-col space-y-0.5">
        <CardTitle class="overflow-hidden w-52">
          <TooltipProvider
            :disabled="!cardTitleIsOverflowed"
            :delay-duration="300"
            :disableClosingTrigger="true"
            :ignore-non-keyboard-focus="true"
          >
            <Tooltip>
              <TooltipTrigger class="select-text cursor-auto">
                <p
                  ref="cardTitleRef"
                  class="text-2xl font-medium w-52 text-left text-nowrap text-ellipsis overflow-hidden"
                >
                  {{ props.serverMonitor.serverName }}
                </p>
              </TooltipTrigger>
              <TooltipContent>
                <p>{{ props.serverMonitor.serverName }}</p>
              </TooltipContent>
            </Tooltip>
          </TooltipProvider>
        </CardTitle>
        <CardDescription class="text-xs leading-none text-left">
          <span
            :class="cn('fi', `fi-${props.serverMonitor.serverHost?.countryCode?.toLowerCase()}`)"
            role="img"
            aria-hidden="true"
          />
          <span
            class="ml-1 fi"
            :style="`background-image: url('/os/${props.serverMonitor.serverHost?.distributionId}.svg')`"
            role="img"
            aria-hidden="true"
          />
        </CardDescription>
      </div>
      <ServerIcon v-if="props.serverMonitor.serverState" class="w-6 h-6 text-success" />
      <ServerCrashIcon v-else class="w-6 h-6 text-error" />
    </CardHeader>
    <CardContent>
      <!--   CPU 使用率   -->
      <ServerStatusProgress title="CPU" :progress="state.cpuUsage">
        <CpuIcon class="w-4 h-4 mr-1.5" />
      </ServerStatusProgress>
      <!--   内存使用率   -->
      <ServerStatusProgress title="内存" :progress="state.memoryUsage">
        <MemoryStickIcon class="w-4 h-4 mr-1.5" />
      </ServerStatusProgress>
      <!--   磁盘使用率   -->
      <ServerStatusProgress title="磁盘" :progress="state.diskUsage">
        <HardDriveIcon class="w-4 h-4 mr-1.5" />
      </ServerStatusProgress>
      <!--   交换分区使用率  -->
      <ServerStatusProgress title="交换" :progress="state.swapUsage">
        <ReplaceIcon class="w-4 h-4 mr-1.5" />
      </ServerStatusProgress>
      <!-- 网络上下行速率 -->
      <ServerStatusItem title="网络">
        <template #icon>
          <ArrowUpDownIcon class="w-4 h-4 mr-1.5" />
        </template>
        <template #content>
          <div class="flex flex-1 items-center">
            <CircleArrowUpIcon class="w-4 h-4" />
            <span class="text-sm mx-1">{{ state.netOutSpeed }}</span>
          </div>
          <div class="flex flex-1 items-center">
            <CircleArrowDownIcon class="w-4 h-4" />
            <span class="text-sm mx-1">{{ state.netInSpeed }}</span>
          </div>
        </template>
      </ServerStatusItem>
      <!-- 上下行总流量 -->
      <ServerStatusItem title="流量">
        <template #icon>
          <HardDriveUploadIcon class="w-4 h-4 mr-1.5" />
        </template>
        <template #content>
          <div class="flex flex-1 items-center">
            <CircleArrowUpIcon class="w-4 h-4" />
            <span class="text-sm mx-1">{{ state.netOutTransfer }}</span>
          </div>
          <div class="flex flex-1 items-center">
            <CircleArrowDownIcon class="w-4 h-4" />
            <span class="text-sm mx-1">{{ state.netInTransfer }}</span>
          </div>
        </template>
      </ServerStatusItem>
      <!-- 1分钟、5分钟、15分钟负载 -->
      <ServerStatusItem title="负载">
        <template #icon>
          <ActivityIcon class="w-4 h-4 mr-1.5" />
        </template>
        <template #content>
          {{ props.serverMonitor.serverState?.load1 ?? 0 }}
          <span class="text-sm mx-1">/</span>
          {{ props.serverMonitor.serverState?.load5 ?? 0 }}
          <span class="text-sm mx-1">/</span>
          {{ props.serverMonitor.serverState?.load15 ?? 0 }}
        </template>
      </ServerStatusItem>
      <!-- 运行时间 -->
      <ServerStatusItem title="在线">
        <template #icon>
          <ClockIcon class="w-4 h-4 mr-1.5" />
        </template>
        <template #content>
          {{ state.uptime }}
        </template>
      </ServerStatusItem>
    </CardContent>
  </Card>
</template>

<style scoped></style>
