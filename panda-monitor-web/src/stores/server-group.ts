import { readonly, ref } from 'vue'
import { defineStore } from 'pinia'

export const useServerGroupStore = defineStore('serverGroup', () => {
  const serverGroupName = ref('')

  function setServerGroupName(name: string) {
    serverGroupName.value = name
  }

  return { serverGroupName: readonly(serverGroupName), setServerGroupName }
})
