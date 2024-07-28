<script setup lang="ts">
import { Tabs, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { useServerGroupStore } from '@/stores/server-group'
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area'

const list = [
  'server',
  'group',
  'test',
  'test2',
  'test3',
  'test4',
  'test5',
  'test6',
  'test7',
  'test8',
  'test9',
  'test10',
  'test11',
  'test12',
  'test13',
  'test14',
  'test15',
  'test16',
  'test17',
  'test18',
  'test19',
  'test20',
  'test21',
  'test22',
  'test23',
  'test24',
  'test25',
  'test26',
  'test27',
  'test28',
  'test29',
  'test30',
  'test31',
  'test32',
  'test33',
  'test34'
]
const { setServerGroupName } = useServerGroupStore()
setServerGroupName(list[0])
</script>

<template>
  <Tabs :default-value="list[0]">
    <TabsList class="max-w-[60%]">
      <ScrollArea class="w-full flex items-center h-full whitespace-nowrap">
        <TabsTrigger
          v-for="(item, index) in list"
          :key="index"
          :value="item"
          @click="setServerGroupName(item)"
        >
          {{ item }}
        </TabsTrigger>
        <ScrollBar orientation="horizontal" />
      </ScrollArea>
    </TabsList>
  </Tabs>
</template>

<style scoped></style>
