<script setup lang="ts">
import {Progress} from '@/components/ui/progress'
import ServerStatusItem from "@/components/server-status-card/ServerStatusItem.vue";

type Props = {
  title: string
  progress: number
}
const props = defineProps<Props>()
</script>

<template>
  <ServerStatusItem :title="props.title">
    <template #icon>
      <slot/>
    </template>
    <template #content>
      <Progress class="flex-1 h-3 bg-zinc-200 dark:bg-zinc-800" :model-value="props.progress"/>
      <div class="ml-1.5 text-left w-[2.375rem]">{{ props.progress }} %</div>
    </template>
  </ServerStatusItem>
</template>

<style scoped></style>
