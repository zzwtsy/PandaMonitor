<script setup lang="ts">
import DashboardView from '@/views/DashboardView.vue'
import Header from '@/components/Header.vue'
import { useServerGroupStore } from '@/stores/server-group'
import ServerGroupBar from '@/components/ServerGroupBar.vue'

const store = useServerGroupStore()
</script>

<template>
  <Header />
  <ServerGroupBar class="mx-auto mt-5 text-center" />
  <main class="md:container px-1 py-8">
    <DashboardView />
  </main>
</template>
