<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Goods, Star, Discount, ShoppingCart } from '@element-plus/icons-vue'

const router = useRouter()

// Mock products
const products = ref([
  { id: 1, name: '纯棉轻盈透气长袖衬衫', price: 199, image: 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=400&h=400&fit=crop', category: '新品上市' },
  { id: 2, name: '亚麻宽松休闲长裤', price: 259, image: 'https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=400&h=400&fit=crop', category: '精选推荐' },
  { id: 3, name: '极简设计陶瓷咖啡杯', price: 89, image: 'https://images.unsplash.com/photo-1514228742587-6b1558fcca3d?w=400&h=400&fit=crop', category: '新品上市' },
  { id: 4, name: '手工编织收纳篮', price: 129, image: 'https://images.unsplash.com/photo-1584346133934-a3afd2a33c4c?w=400&h=400&fit=crop', category: '限时特惠' },
  { id: 5, name: '天然木质餐具套装', price: 159, image: 'https://images.unsplash.com/photo-1596649830500-2f6385dfc1d2?w=400&h=400&fit=crop', category: '精选推荐' },
  { id: 6, name: '莫兰迪色系纯棉四件套', price: 399, image: 'https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=400&h=400&fit=crop', category: '限时特惠' },
])

const goToDetail = (id: number) => {
  router.push(`/product/${id}`)
}
</script>

<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Hero Section -->
    <div class="bg-earth-100 rounded-xl overflow-hidden shadow-sm mb-12 flex flex-col md:flex-row items-center">
      <div class="p-8 md:p-12 md:w-1/2 flex flex-col justify-center">
        <h1 class="text-4xl md:text-5xl font-bold text-earth-800 mb-6 leading-tight">自然 · 优雅 · 生活</h1>
        <p class="text-lg text-earth-600 mb-8">在这里发现您心仪的商品，享受自然优雅的购物体验。精选天然材质，为您打造舒适生活。</p>
        <div>
          <button class="bg-earth-500 hover:bg-earth-600 text-white px-8 py-3 rounded-md transition-colors shadow-sm text-lg font-medium">
            立即探索
          </button>
        </div>
      </div>
      <div class="md:w-1/2 h-64 md:h-auto">
        <img src="https://images.unsplash.com/photo-1490481651871-ab68de25d43d?w=800&q=80" alt="Hero Image" class="w-full h-full object-cover" />
      </div>
    </div>
    
    <!-- Category Cards -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
      <div class="bg-earth-200 p-8 rounded-lg hover:shadow-md transition-shadow flex flex-col items-center text-center group cursor-pointer">
        <div class="w-16 h-16 bg-white rounded-full flex items-center justify-center mb-4 text-earth-600 group-hover:scale-110 transition-transform">
          <el-icon class="text-3xl"><Goods /></el-icon>
        </div>
        <h3 class="text-xl font-semibold text-earth-800 mb-2">新品上市</h3>
        <p class="text-earth-600">探索最新一季的自然风格好物。</p>
      </div>
      <div class="bg-morandi-green p-8 rounded-lg text-white hover:shadow-md transition-shadow flex flex-col items-center text-center group cursor-pointer">
        <div class="w-16 h-16 bg-white/20 rounded-full flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
          <el-icon class="text-3xl"><Star /></el-icon>
        </div>
        <h3 class="text-xl font-semibold mb-2">精选推荐</h3>
        <p class="text-white/80">为您量身打造的专属好物。</p>
      </div>
      <div class="bg-earth-300 p-8 rounded-lg hover:shadow-md transition-shadow flex flex-col items-center text-center group cursor-pointer">
        <div class="w-16 h-16 bg-white rounded-full flex items-center justify-center mb-4 text-earth-600 group-hover:scale-110 transition-transform">
          <el-icon class="text-3xl"><Discount /></el-icon>
        </div>
        <h3 class="text-xl font-semibold text-earth-800 mb-2">限时特惠</h3>
        <p class="text-earth-700">不容错过的优雅单品折扣。</p>
      </div>
    </div>

    <!-- Products Grid -->
    <div>
      <h2 class="text-3xl font-bold text-earth-800 mb-8 text-center">为你推荐</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
        <div 
          v-for="product in products" 
          :key="product.id"
          @click="goToDetail(product.id)"
          class="bg-white rounded-lg overflow-hidden shadow-sm hover:shadow-lg transition-all duration-300 cursor-pointer group flex flex-col"
        >
          <div class="relative h-64 overflow-hidden bg-earth-100">
            <img :src="product.image" :alt="product.name" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
            <span class="absolute top-3 left-3 bg-white/90 text-earth-700 px-3 py-1 rounded-full text-xs font-medium backdrop-blur-sm">
              {{ product.category }}
            </span>
          </div>
          <div class="p-5 flex-1 flex flex-col">
            <h3 class="text-lg font-medium text-earth-800 mb-2 line-clamp-1">{{ product.name }}</h3>
            <div class="mt-auto flex items-center justify-between">
              <span class="text-xl font-bold text-earth-600">¥{{ product.price }}</span>
              <button class="w-8 h-8 rounded-full bg-earth-100 text-earth-600 flex items-center justify-center group-hover:bg-earth-500 group-hover:text-white transition-colors">
                <el-icon><ShoppingCart /></el-icon>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
