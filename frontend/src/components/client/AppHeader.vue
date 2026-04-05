<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { useScroll } from '@vueuse/core'
import { Search, ShoppingBag, Menu, User } from '@element-plus/icons-vue'

const emit = defineEmits<{
  (e: 'toggle-menu'): void
}>()

const route = useRoute()
const { y: scrollY } = useScroll(window)

const isScrolled = computed(() => scrollY.value > 10)

const navItems = [
  { label: '首页', path: '/' },
  { label: '商品', path: '/products' },
  { label: '关于', path: '/about' },
  { label: '支持', path: '/support' }
]

function isActive(path: string): boolean {
  if (path === '/') {
    return route.path === '/'
  }
  return route.path.startsWith(path)
}
</script>

<template>
  <header
    class="fixed top-0 left-0 right-0 z-50 h-14 transition-all duration-500 ease-out"
    :class="isScrolled ? 'bg-lumina-100/80 backdrop-blur-xl border-b border-white/10 shadow-sm' : 'bg-transparent'"
  >
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-full flex items-center justify-between">
      <RouterLink to="/" class="flex items-center group">
        <span class="text-lg font-semibold tracking-tight text-lumina-800 group-hover:text-lumina-900 transition-colors duration-300">
          Lumina.
        </span>
      </RouterLink>

      <nav class="hidden md:flex items-center gap-8">
        <RouterLink
          v-for="item in navItems"
          :key="item.path"
          :to="item.path"
          class="relative py-1 text-xs font-medium tracking-wider transition-colors duration-300"
          :class="isActive(item.path) ? 'text-lumina-900' : 'text-lumina-600 hover:text-lumina-900'"
        >
          {{ item.label }}
          <span
            class="absolute bottom-0 left-0 h-0.5 bg-titanium transition-all duration-300"
            :class="isActive(item.path) ? 'w-full' : 'w-0'"
          ></span>
        </RouterLink>
      </nav>

      <div class="flex items-center gap-3">
        <button class="p-2 text-lumina-600 hover:text-lumina-800 rounded-full hover:bg-lumina-200/50 transition-colors duration-200">
          <el-icon :size="18"><Search /></el-icon>
        </button>

        <button class="relative p-2 text-lumina-600 hover:text-lumina-800 rounded-full hover:bg-lumina-200/50 transition-colors duration-200">
          <el-icon :size="18"><ShoppingBag /></el-icon>
          <el-badge :value="0" :max="99" class="absolute -top-0.5 -right-0.5" />
        </button>

        <button class="w-8 h-8 rounded-full bg-lumina-200 flex items-center justify-center hover:ring-2 hover:ring-lumina-300 transition-all duration-200 hidden sm:flex">
          <el-icon :size="16" class="text-lumina-600"><User /></el-icon>
        </button>

        <button
          class="text-xs font-medium rounded-full border border-lumina-300 px-4 py-1.5 text-lumina-600 hover:bg-lumina-100 hover:border-lumina-400 transition-all duration-300 hidden sm:block"
        >
          登录
        </button>

        <button
          class="md:hidden p-2 text-lumina-800 hover:bg-lumina-200/50 rounded-full transition-colors duration-200"
          @click="emit('toggle-menu')"
        >
          <el-icon :size="20"><Menu /></el-icon>
        </button>
      </div>
    </div>
  </header>
</template>
