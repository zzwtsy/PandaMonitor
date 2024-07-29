<script setup lang="ts">
import ToggleTheme from '@/components/ToggleTheme.vue'
</script>

<template>
  <header
    class="sticky top-0 left-0 right-0 z-50 border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60"
  >
    <nav class="container flex h-14 items-center justify-center">
      <h2
        class="hidden sm:flex text-2xl font-bold text-primary items-center justify-start h-full flex-1"
      >
        <a href="/">Panda Monitor</a>
      </h2>
      <div class="flex gap-4 items-center text-primary justify-end flex-1">
        <ToggleTheme />
      </div>
    </nav>
  </header>
</template>

<style scoped></style>
