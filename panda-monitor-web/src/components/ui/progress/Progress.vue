<script setup lang="ts">
import { type HTMLAttributes, computed } from 'vue'
import { ProgressIndicator, ProgressRoot, type ProgressRootProps } from 'radix-vue'
import { cn } from '@/lib/utils'

const props = withDefaults(defineProps<ProgressRootProps & { class?: HTMLAttributes['class'] }>(), {
  modelValue: 0,
})

const delegatedProps = computed(() => {
  const { class: _, ...delegated } = props

  return { delegated }
})

function progressIndicatorColor() {
  const progressValue = props.modelValue ?? 0
  if (progressValue >= 86 && progressValue <= 100) {
    return 'bg-error'
  }
  else if (progressValue >= 51 && progressValue <= 85) {
    return 'bg-warning'
  }
  else if (progressValue >= 0 && progressValue <= 50) {
    return 'bg-success'
  }
  return 'bg-primary'
}
</script>

<template>
  <ProgressRoot
    v-bind="delegatedProps.delegated"
    :class="cn('relative h-4 w-full overflow-hidden rounded-full bg-secondary', props.class)"
  >
    <ProgressIndicator
      :class="cn('h-full w-full flex-1 transition-all', progressIndicatorColor())"
      :style="`transform: translateX(-${100 - (props.modelValue ?? 0)}%);`"
    />
  </ProgressRoot>
</template>
