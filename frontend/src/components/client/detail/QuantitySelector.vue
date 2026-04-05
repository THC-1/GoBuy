<template>
  <div class="inline-flex items-center rounded-full border border-white/20">
    <button
      @click="decrease"
      :disabled="modelValue <= min"
      class="w-10 h-10 flex items-center justify-center text-white/60 hover:text-white hover:bg-white/10 rounded-l-full transition-colors disabled:opacity-30 disabled:cursor-not-allowed"
    >
      <el-icon><Minus /></el-icon>
    </button>

    <input
      type="number"
      :value="modelValue"
      @input="handleInput"
      :min="min"
      :max="max"
      class="w-12 h-10 text-center text-white font-medium bg-transparent focus:outline-none [appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none"
    />

    <button
      @click="increase"
      :disabled="modelValue >= max"
      class="w-10 h-10 flex items-center justify-center text-white/60 hover:text-white hover:bg-white/10 rounded-r-full transition-colors disabled:opacity-30 disabled:cursor-not-allowed"
    >
      <el-icon><Plus /></el-icon>
    </button>
  </div>
</template>

<script setup lang="ts">
import { Minus, Plus } from '@element-plus/icons-vue'

const props = withDefaults(defineProps<{
  modelValue: number
  min?: number
  max?: number
}>(), {
  min: 1,
  max: 99
})

const emit = defineEmits<{
  'update:modelValue': [value: number]
}>()

const decrease = () => {
  if (props.modelValue > props.min) {
    emit('update:modelValue', props.modelValue - 1)
  }
}

const increase = () => {
  if (props.modelValue < props.max) {
    emit('update:modelValue', props.modelValue + 1)
  }
}

const handleInput = (event: Event) => {
  const target = event.target as HTMLInputElement
  let value = parseInt(target.value, 10)

  if (isNaN(value)) value = props.min
  if (value < props.min) value = props.min
  if (value > props.max) value = props.max

  emit('update:modelValue', value)
}
</script>
