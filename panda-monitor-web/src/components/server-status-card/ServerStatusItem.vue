<script setup lang="ts">

type Props = {
  title: string
}

const props = defineProps<Props>()

</script>

<template>
  <div class="grid gap-1.5 text-sm">
    <div class="flex items-center gap-1.5">
      <slot name="icon"/>
      <div class="w-14 text-right mr-1.5">{{ props.title }}</div>
      <div class="flex flex-1 w-full h-4 items-center">
        <slot name="content"/>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>