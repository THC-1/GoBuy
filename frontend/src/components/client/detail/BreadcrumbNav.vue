<template>
  <nav class="flex items-center gap-2 text-sm">
    <template v-for="(item, index) in items" :key="index">
      <component
        :is="item.path ? 'router-link' : 'span'"
        :to="item.path"
        class="transition-colors duration-200"
        :class="index === items.length - 1
          ? 'text-white/80'
          : 'text-lumina-400 hover:text-white'"
      >
        {{ item.label }}
      </component>
      <span v-if="index < items.length - 1" class="text-lumina-600">/</span>
    </template>
  </nav>
</template>

<script setup lang="ts">
interface BreadcrumbItem {
  label: string
  path?: string
}

defineProps<{
  items: BreadcrumbItem[]
}>()
</script>
