<script setup lang="ts">
import { ref } from 'vue'
import { Delete, ShoppingCart } from '@element-plus/icons-vue'

const favorites = ref([
  {
    id: 1,
    name: '【自然系列】原木纹理书桌 简约现代办公桌',
    price: '¥ 1,299.00',
    image: 'https://via.placeholder.com/300?text=Desk',
    status: '在售'
  },
  {
    id: 2,
    name: '日式棉麻混纺抱枕 纯色家居靠垫',
    price: '¥ 89.00',
    image: 'https://via.placeholder.com/300?text=Pillow',
    status: '在售'
  },
  {
    id: 3,
    name: '手作陶瓷咖啡杯 粗陶复古马克杯',
    price: '¥ 68.00',
    image: 'https://via.placeholder.com/300?text=Mug',
    status: '缺货'
  }
])

const handleRemove = (id: number) => {
  console.log('Remove from favorites', id)
}

const handleAddToCart = (id: number) => {
  console.log('Add to cart', id)
}
</script>

<template>
  <div>
    <h2 class="text-2xl font-bold text-earth-800 mb-6 border-b border-earth-100 pb-4">我的收藏</h2>
    
    <div v-if="favorites.length === 0" class="py-16 text-center text-earth-500">
      <el-empty description="暂无收藏商品" />
    </div>

    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div 
        v-for="item in favorites" 
        :key="item.id" 
        class="group bg-white rounded-xl border border-earth-200 overflow-hidden hover:shadow-md hover:border-earth-300 transition-all duration-300"
      >
        <div class="aspect-square bg-earth-100 overflow-hidden relative">
          <img :src="item.image" :alt="item.name" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
          <div v-if="item.status === '缺货'" class="absolute inset-0 bg-white/60 flex items-center justify-center backdrop-blur-[2px]">
            <span class="bg-earth-800 text-white px-4 py-1.5 rounded-full text-sm font-medium tracking-wider">暂时缺货</span>
          </div>
        </div>
        
        <div class="p-5">
          <h3 class="text-earth-800 font-medium mb-2 line-clamp-2 min-h-[48px] hover:text-primary cursor-pointer transition-colors">{{ item.name }}</h3>
          <div class="flex items-center justify-between mt-4">
            <span class="text-lg font-bold text-earth-800">{{ item.price }}</span>
            <div class="flex gap-2">
              <el-button 
                circle 
                :icon="Delete" 
                size="small" 
                title="取消收藏"
                @click="handleRemove(item.id)" 
              />
              <el-button 
                circle 
                type="primary" 
                :icon="ShoppingCart" 
                size="small" 
                :disabled="item.status === '缺货'"
                title="加入购物车"
                @click="handleAddToCart(item.id)" 
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
