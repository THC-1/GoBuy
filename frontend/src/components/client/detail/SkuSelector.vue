<template>
  <div>
    <div v-for="group in skuGroups" :key="group.name" class="mb-6">
      <label class="text-sm font-medium text-white/60 mb-3 block">{{ group.name }}</label>

      <div class="flex flex-wrap gap-2">
        <button
          v-for="option in group.options"
          :key="option.value"
          @click="selectOption(group.name, option)"
          class="rounded-full px-5 py-2.5 text-sm font-medium border transition-all duration-200"
          :class="getOptionClass(group.name, option)"
          :disabled="option.disabled"
        >
          {{ option.label }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { SkuGroup, SkuOption } from '@/types/product'

const props = defineProps<{
  skuGroups: SkuGroup[]
}>()

const emit = defineEmits<{
  'update:modelValue': [value: Record<string, string>]
  'imageChange': [imageUrl: string]
}>()

const selectedOptions = ref<Record<string, string>>({})

watch(() => props.skuGroups, (groups) => {
  groups.forEach(group => {
    const defaultOption = group.options.find(opt => !opt.disabled) || group.options[0]
    if (defaultOption && !selectedOptions.value[group.name]) {
      selectedOptions.value[group.name] = defaultOption.value
      if (defaultOption.imageUrl) {
        emit('imageChange', defaultOption.imageUrl)
      }
    }
  })
}, { immediate: true })

const selectOption = (groupName: string, option: SkuOption) => {
  if (option.disabled) return

  selectedOptions.value[groupName] = option.value
  emit('update:modelValue', { ...selectedOptions.value })

  if (option.imageUrl) {
    emit('imageChange', option.imageUrl)
  }
}

const getOptionClass = (groupName: string, option: SkuOption) => {
  const isSelected = selectedOptions.value[groupName] === option.value

  if (option.disabled) {
    return 'opacity-30 cursor-not-allowed border-white/20 text-white/80'
  }

  return isSelected
    ? 'border-white bg-white text-lumina-950'
    : 'border-white/20 text-white/80 hover:border-white/50 hover:bg-white/5'
}
</script>
