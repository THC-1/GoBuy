<template>
  <section class="bg-lumina-900 py-20">
    <div class="max-w-4xl mx-auto px-6">
      <h2 class="text-3xl font-semibold text-white text-center mb-12 tracking-tight">技术规格</h2>

      <div class="divide-y divide-white/10">
        <div
          v-for="(spec, index) in specifications"
          :key="index"
          class="flex justify-between items-center py-4"
        >
          <span class="text-sm text-white/60">{{ spec.label }}</span>
          <span class="text-sm font-medium text-right text-white/90 max-w-[60%]">{{ spec.value }}</span>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import type { SpecItem } from '@/types/product'

defineProps<{
  specifications: SpecItem[]
}>()
</script>
