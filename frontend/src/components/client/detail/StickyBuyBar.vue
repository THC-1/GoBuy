<template>
  <transition name="slide-down">
    <div
      v-if="show"
      class="fixed top-14 left-0 right-0 z-40 bg-white/95 backdrop-blur-lg border-b border-lumina-200 shadow-sm h-14 flex items-center px-6 transition-all duration-300"
    >
      <span class="truncate max-w-md text-sm font-medium text-lumina-900">{{ name }}</span>

      <div class="ml-auto flex items-center gap-4">
        <span class="text-lg font-bold text-lumina-900">¥{{ formatPrice(price) }}</span>
        <button
          @click="$emit('buy')"
          class="bg-lumina-900 text-white rounded-full px-6 py-2 text-sm font-medium hover:bg-lumina-800 active:scale-[0.98] transition-all"
        >
          购买
        </button>
      </div>
    </div>
  </transition>
</template>

<script setup lang="ts">
defineProps<{
  show: boolean
  name: string
  price: number
}>()

defineEmits<{
  buy: []
}>()

const formatPrice = (price: number) => price.toLocaleString()
</script>

<style scoped>
.slide-down-enter-active {
  transition: transform 0.3s cubic-bezier(0.16, 1, 0.3, 1);
}
.slide-down-leave-active {
  transition: transform 0.2s ease-in;
}
.slide-down-enter-from {
  transform: translateY(-100%);
}
.slide-down-leave-to {
  transform: translateY(-100%);
}
</style>
