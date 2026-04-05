<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { Close } from '@element-plus/icons-vue'

const props = defineProps<{
  modelValue: boolean
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
}>()

function closeDrawer() {
  emit('update:modelValue', false)
}

const navItems = [
  { label: '首页', path: '/' },
  { label: '商品', path: '/products' },
  { label: '关于', path: '/about' },
  { label: '支持', path: '/support' }
]
</script>

<template>
  <Teleport to="body">
    <Transition name="drawer">
      <div v-if="modelValue" class="fixed inset-0 z-[60]">
        <div
          class="absolute inset-0 bg-lumina-950/40 backdrop-blur-sm"
          @click="closeDrawer"
        ></div>

        <div
          class="absolute right-0 top-0 bottom-0 w-72 bg-white shadow-2xl transform transition-transform duration-300 ease-out"
          :class="modelValue ? 'translate-x-0' : 'translate-x-full'"
        >
          <div class="flex flex-col h-full">
            <div class="flex items-center justify-between px-6 py-5 border-b border-lumina-100">
              <span class="text-lg font-semibold tracking-tight text-lumina-800">Lumina.</span>
              <button
                class="p-2 text-lumina-400 hover:text-lumina-800 hover:bg-lumina-100 rounded-full transition-colors duration-200"
                @click="closeDrawer"
              >
                <el-icon :size="20"><Close /></el-icon>
              </button>
            </div>

            <nav class="flex-1 overflow-y-auto px-6 py-6">
              <RouterLink
                v-for="item in navItems"
                :key="item.path"
                :to="item.path"
                class="block py-3 text-base font-medium text-lumina-800 hover:text-titanium transition-colors border-b border-lumina-100 last:border-0"
                @click="closeDrawer"
              >
                {{ item.label }}
              </RouterLink>
            </nav>

            <div class="px-6 py-6 border-t border-lumina-200 space-y-3">
              <button class="w-full py-2.5 text-sm font-medium text-lumina-800 border border-lumina-300 rounded-full hover:bg-lumina-50 transition-colors duration-200">
                登录
              </button>
              <button class="w-full py-2.5 text-sm font-medium text-white bg-lumina-800 rounded-full hover:bg-lumina-900 transition-colors duration-200">
                注册
              </button>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped>
.drawer-enter-active,
.drawer-leave-active {
  transition: opacity 0.3s ease;
}

.drawer-enter-from,
.drawer-leave-to {
  opacity: 0;
}

.drawer-enter-active .absolute.right-0,
.drawer-leave-active .absolute.right-0 {
  transition: transform 0.3s cubic-bezier(0.16, 1, 0.3, 1);
}

.drawer-enter-from .absolute.right-0,
.drawer-leave-to .absolute.right-0 {
  transform: translateX(100%);
}
</style>
