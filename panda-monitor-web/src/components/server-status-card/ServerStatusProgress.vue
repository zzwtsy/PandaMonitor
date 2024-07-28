<script setup lang="ts">
import { Progress } from '@/components/ui/progress'

type Props = {
  title: string
  progress: number
}
const props = defineProps<Props>()
</script>

<template>
  <div class="grid gap-1.5 text-sm">
    <div class="flex items-center gap-1.5">
      <slot />
      <div class="w-14 text-right mr-1.5">{{ props.title }}</div>
      <Progress class="flex-1 h-3 bg-zinc-200 dark:bg-zinc-800" :model-value="props.progress" />
      <div class="ml-1.5 text-left w-[2.375rem]">{{ props.progress }} %</div>
    </div>
  </div>
</template>

<style scoped></style>
