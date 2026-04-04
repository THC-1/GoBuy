<script setup lang="ts">
import { RouterView, useRoute } from 'vue-router'
import { ref, computed } from 'vue'
import {
  Odometer,
  Goods,
  Tickets,
  User,
  Setting,
  Lock,
  Menu as IconMenu,
  Notebook
} from '@element-plus/icons-vue'

const isCollapse = ref(false)
const route = useRoute()
const activeMenu = computed(() => route.path)
</script>

<template>
  <div class="min-h-screen flex bg-[var(--el-bg-color-page)]">
    <!-- Sidebar -->
    <aside :class="['bg-white shadow-md transition-all duration-300 z-10 flex flex-col', isCollapse ? 'w-16' : 'w-64']">
      <div class="h-16 flex items-center justify-center border-b border-[var(--el-border-color-light)]">
        <span class="text-xl font-bold text-[var(--el-color-primary)] truncate" v-if="!isCollapse">管理后台</span>
        <span class="text-xl font-bold text-[var(--el-color-primary)]" v-else>后台</span>
      </div>
      <el-scrollbar class="flex-1">
        <el-menu
          :default-active="activeMenu"
          class="border-r-0 h-full"
          :collapse="isCollapse"
          router
        >
          <el-menu-item index="/admin/dashboard">
            <el-icon><Odometer /></el-icon>
            <template #title>仪表盘</template>
          </el-menu-item>
          
          <el-sub-menu index="/admin/system">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/admin/system/users">
              <el-icon><User /></el-icon>
              <template #title>用户管理</template>
            </el-menu-item>
            <el-menu-item index="/admin/system/roles">
              <el-icon><Lock /></el-icon>
              <template #title>角色管理</template>
            </el-menu-item>
            <el-menu-item index="/admin/system/menus">
              <el-icon><IconMenu /></el-icon>
              <template #title>菜单权限</template>
            </el-menu-item>
            <el-menu-item index="/admin/system/dicts">
              <el-icon><Notebook /></el-icon>
              <template #title>数据字典</template>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/admin/product">
            <template #title>
              <el-icon><Goods /></el-icon>
              <span>商品管理</span>
            </template>
            <el-menu-item index="/admin/product/category">
              <template #title>商品分类</template>
            </el-menu-item>
            <el-menu-item index="/admin/product/attribute">
              <template #title>规格属性</template>
            </el-menu-item>
            <el-menu-item index="/admin/product/spu-sku">
              <template #title>商品录入</template>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/admin/orders">
            <el-icon><Tickets /></el-icon>
            <template #title>订单管理</template>
          </el-menu-item>
        </el-menu>
      </el-scrollbar>
    </aside>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col overflow-hidden">
      <!-- Header -->
      <header class="h-16 bg-white shadow-sm flex items-center justify-between px-6 z-10">
        <div class="flex items-center gap-4">
          <el-button text @click="isCollapse = !isCollapse">
            <span class="text-lg">☰</span>
          </el-button>
          <span class="text-[var(--el-text-color-regular)]">欢迎回来，管理员</span>
        </div>
        <div class="flex items-center gap-4">
           <router-link to="/" class="text-sm text-[var(--el-color-primary)] hover:underline">返回前台</router-link>
          <el-avatar size="small" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
        </div>
      </header>

      <!-- Page Content -->
      <main class="flex-1 overflow-x-hidden overflow-y-auto bg-[var(--el-bg-color-page)] p-6">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped>
.el-menu-item.is-active {
  background-color: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
  border-right: 3px solid var(--el-color-primary);
}
</style>
