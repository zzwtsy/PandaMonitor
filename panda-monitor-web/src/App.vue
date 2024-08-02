<script setup lang="ts">
import DashboardView from '@/views/DashboardView.vue'
import Header from '@/components/Header.vue'
import { Toaster } from '@/components/ui/sonner'
</script>

<template>
  <Header />
  <main class="md:container px-1 mt-2">
    <DashboardView />
  </main>
  <Toaster close-button rich-colors position="top-right" style="margin-top: 2.5rem" />
</template>
