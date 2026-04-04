<script setup lang="ts">
import { useRoute } from 'vue-router'
const route = useRoute()
</script>

<template>
  <div class="flex flex-col md:flex-row gap-6">
    <!-- Sidebar -->
    <aside class="w-full md:w-64 shrink-0">
      <div class="bg-white rounded-xl shadow-sm border border-earth-200 p-4">
        <h3 class="text-lg font-bold text-earth-800 mb-4 px-4">个人中心</h3>
        <nav class="space-y-1">
          <router-link
            to="/user/profile"
            class="block px-4 py-2.5 rounded-lg transition-colors"
            :class="route.path === '/user/profile' ? 'bg-earth-100 text-earth-800 font-medium' : 'text-earth-600 hover:bg-earth-50 hover:text-earth-800'"
          >
            资料修改
          </router-link>
          <router-link
            to="/user/address"
            class="block px-4 py-2.5 rounded-lg transition-colors"
            :class="route.path === '/user/address' ? 'bg-earth-100 text-earth-800 font-medium' : 'text-earth-600 hover:bg-earth-50 hover:text-earth-800'"
          >
            收货地址
          </router-link>
          <router-link
            to="/user/favorites"
            class="block px-4 py-2.5 rounded-lg transition-colors"
            :class="route.path === '/user/favorites' ? 'bg-earth-100 text-earth-800 font-medium' : 'text-earth-600 hover:bg-earth-50 hover:text-earth-800'"
          >
            我的收藏
          </router-link>
        </nav>
      </div>
    </aside>

    <!-- Content -->
    <main class="flex-1 bg-white rounded-xl shadow-sm border border-earth-200 p-6 min-h-[500px]">
      <router-view />
    </main>
  </div>
</template>
