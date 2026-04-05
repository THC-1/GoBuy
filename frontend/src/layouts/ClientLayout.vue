<script setup lang="ts">
import { ref } from 'vue'
import AppHeader from '@/components/client/AppHeader.vue'
import AppFooter from '@/components/client/AppFooter.vue'
import MobileMenuDrawer from '@/components/client/MobileMenuDrawer.vue'

const isMobileMenuOpen = ref(false)

function toggleMobileMenu() {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
}
</script>

<template>
  <div class="min-h-screen bg-lumina-100 flex flex-col">
    <AppHeader @toggle-menu="toggleMobileMenu" />
    <main class="flex-1 pt-14">
      <RouterView />
    </main>
    <AppFooter />
    <MobileMenuDrawer v-model="isMobileMenuOpen" />
  </div>
</template>
