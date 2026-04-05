<template>
  <div>
    <div class="aspect-square rounded-3xl overflow-hidden bg-lumina-900 relative">
      <img
        :key="currentIndex"
        :src="images[currentIndex]"
        alt="产品图片"
        class="absolute inset-0 w-full h-full object-contain transition-opacity duration-300"
      />
    </div>

    <div class="flex gap-3 mt-4 justify-center">
      <button
        v-for="(image, index) in images"
        :key="index"
        @click="selectImage(index)"
        class="w-16 h-16 rounded-xl overflow-hidden border-2 cursor-pointer transition-all duration-200"
        :class="currentIndex === index
          ? 'border-white'
          : 'border-transparent opacity-60 hover:opacity-100'"
      >
        <img :src="image" class="w-full h-full object-cover" />
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps<{
  images: string[]
}>()

const currentIndex = ref(0)

const selectImage = (index: number) => {
  currentIndex.value = index
}

defineExpose({ selectImage })
</script>
