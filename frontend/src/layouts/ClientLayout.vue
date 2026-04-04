<script setup lang="ts">
import { RouterView } from 'vue-router'
import { ShoppingBag, User } from '@element-plus/icons-vue'
</script>

<template>
  <div class="min-h-screen flex flex-col bg-[var(--el-bg-color-page)]">
    <!-- Header -->
    <header class="bg-earth-100/90 backdrop-blur-md sticky top-0 z-50 border-b border-earth-200 h-20 flex items-center justify-between px-8 md:px-12 text-earth-800 transition-all duration-300">
      <div class="text-2xl font-bold text-earth-800 tracking-wider flex items-center gap-2">
        <el-icon class="text-earth-600"><ShoppingBag /></el-icon>
        GoBuy<span class="font-light text-earth-600 text-lg">商城</span>
      </div>
      <nav class="hidden md:flex gap-8">
        <router-link to="/" class="text-earth-700 hover:text-earth-900 font-medium relative group">
          首页
          <span class="absolute -bottom-1 left-0 w-0 h-0.5 bg-earth-500 transition-all duration-300 group-hover:w-full"></span>
        </router-link>
        <router-link to="/products" class="text-earth-700 hover:text-earth-900 font-medium relative group">
          商品
          <span class="absolute -bottom-1 left-0 w-0 h-0.5 bg-earth-500 transition-all duration-300 group-hover:w-full"></span>
        </router-link>
        <router-link to="/cart" class="text-earth-700 hover:text-earth-900 font-medium relative group">
          购物车
          <span class="absolute -bottom-1 left-0 w-0 h-0.5 bg-earth-500 transition-all duration-300 group-hover:w-full"></span>
        </router-link>
      </nav>
      <div class="flex items-center gap-6">
        <router-link to="/user/profile" class="text-earth-700 hover:text-earth-900 transition-colors flex items-center gap-1 group">
          <el-icon class="text-lg group-hover:scale-110 transition-transform"><User /></el-icon>
          <span class="text-sm font-medium">个人中心</span>
        </router-link>
        <el-button color="#7a684e" round @click="$router.push('/login')">登录</el-button>
      </div>
    </header>

    <!-- Main Content -->
    <main class="flex-1 container mx-auto px-4 py-8">
      <RouterView />
    </main>

    <!-- Footer -->
    <footer class="bg-white py-8 text-center text-[var(--el-text-color-secondary)]">
      <p>© 2026 GoBuy. All rights reserved.</p>
    </footer>
  </div>
</template>
