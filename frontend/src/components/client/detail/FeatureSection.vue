<template>
  <section class="bg-lumina-100 py-20">
    <div class="max-w-7xl mx-auto px-6">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div v-for="(feature, index) in features" :key="index" class="text-center">
          <div class="w-16 h-16 mx-auto rounded-2xl bg-lumina-200 flex items-center justify-center">
            <el-icon :size="28" class="text-lumina-600">
              <component :is="feature.icon" />
            </el-icon>
          </div>

          <h3 class="text-lg font-semibold text-lumina-800 mt-4">{{ feature.title }}</h3>
          <p class="text-sm text-lumina-500 mt-2 leading-relaxed">{{ feature.description }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import type { FeatureItem } from '@/types/product'

defineProps<{
  features: FeatureItem[]
}>()
</script>
