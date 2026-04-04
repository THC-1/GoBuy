<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Mock cart items
const cartItems = ref([
  {
    id: 1,
    productId: 1,
    name: '纯棉轻盈透气长袖衬衫',
    sku: '米白色, M',
    price: 199,
    quantity: 1,
    image: 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=200&h=200&fit=crop',
    selected: true,
    stock: 100
  },
  {
    id: 2,
    productId: 2,
    name: '亚麻宽松休闲长裤',
    sku: '卡其色, L',
    price: 259,
    quantity: 2,
    image: 'https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=200&h=200&fit=crop',
    selected: true,
    stock: 50
  },
  {
    id: 3,
    productId: 3,
    name: '极简设计陶瓷咖啡杯',
    sku: '哑光黑',
    price: 89,
    quantity: 1,
    image: 'https://images.unsplash.com/photo-1514228742587-6b1558fcca3d?w=200&h=200&fit=crop',
    selected: false,
    stock: 200
  }
])

const isAllSelected = computed({
  get: () => cartItems.value.length > 0 && cartItems.value.every(item => item.selected),
  set: (val) => {
    cartItems.value.forEach(item => item.selected = val)
  }
})

const selectedCount = computed(() => {
  return cartItems.value.filter(item => item.selected).reduce((sum, item) => sum + item.quantity, 0)
})

const totalPrice = computed(() => {
  return cartItems.value
    .filter(item => item.selected)
    .reduce((sum, item) => sum + item.price * item.quantity, 0)
})

const increaseQuantity = (item: any) => {
  if (item.quantity < item.stock) {
    item.quantity++
  }
}

const decreaseQuantity = (item: any) => {
  if (item.quantity > 1) {
    item.quantity--
  }
}

const removeItem = (id: number) => {
  cartItems.value = cartItems.value.filter(item => item.id !== id)
}

const checkout = () => {
  if (selectedCount.value === 0) {
    alert('请先选择要结算的商品！')
    return
  }
  router.push('/checkout')
}
</script>

<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <h1 class="text-2xl font-bold text-earth-800 mb-8">我的购物车</h1>

    <div v-if="cartItems.length > 0" class="flex flex-col lg:flex-row gap-8">
      <!-- Cart Items List -->
      <div class="lg:w-2/3 flex flex-col gap-4">
        <!-- List Header -->
        <div class="bg-white p-4 rounded-lg shadow-sm flex items-center text-sm font-medium text-earth-600 hidden md:flex">
          <div class="w-12 flex justify-center">
            <input type="checkbox" v-model="isAllSelected" class="w-5 h-5 rounded border-earth-300 text-earth-500 focus:ring-earth-500 cursor-pointer accent-earth-600" />
          </div>
          <div class="flex-1 ml-4">商品信息</div>
          <div class="w-24 text-center">单价</div>
          <div class="w-32 text-center">数量</div>
          <div class="w-24 text-center">金额</div>
          <div class="w-20 text-center">操作</div>
        </div>

        <!-- Cart Items -->
        <div v-for="item in cartItems" :key="item.id" class="bg-white p-4 rounded-lg shadow-sm flex flex-col md:flex-row items-center gap-4 transition-colors hover:bg-earth-50/50">
          <div class="flex items-center w-full md:w-auto self-start md:self-center">
            <input type="checkbox" v-model="item.selected" class="w-5 h-5 rounded border-earth-300 text-earth-500 focus:ring-earth-500 cursor-pointer accent-earth-600 mr-4" />
            <img :src="item.image" :alt="item.name" class="w-20 h-20 md:w-24 md:h-24 rounded-md object-cover bg-earth-100 cursor-pointer" @click="router.push(`/product/${item.productId}`)" />
          </div>
          
          <div class="flex-1 flex flex-col justify-center min-w-0 w-full md:w-auto">
            <h3 class="text-base font-medium text-earth-800 mb-1 truncate cursor-pointer hover:text-earth-600 transition-colors" @click="router.push(`/product/${item.productId}`)">{{ item.name }}</h3>
            <p class="text-sm text-earth-500 mb-2">{{ item.sku }}</p>
            <div class="md:hidden flex items-center justify-between mt-2">
              <span class="text-earth-700 font-medium">¥{{ item.price }}</span>
              <!-- Mobile Quantity -->
              <div class="flex items-center border border-earth-200 rounded-md overflow-hidden">
                <button @click="decreaseQuantity(item)" class="w-8 h-8 flex items-center justify-center bg-earth-50 text-earth-600 hover:bg-earth-100" :disabled="item.quantity <= 1">-</button>
                <input type="number" v-model.number="item.quantity" class="w-12 h-8 text-center text-sm text-earth-800 focus:outline-none border-x border-earth-200" min="1" :max="item.stock" />
                <button @click="increaseQuantity(item)" class="w-8 h-8 flex items-center justify-center bg-earth-50 text-earth-600 hover:bg-earth-100" :disabled="item.quantity >= item.stock">+</button>
              </div>
            </div>
          </div>

          <!-- Desktop Columns -->
          <div class="hidden md:flex items-center gap-4">
            <div class="w-24 text-center font-medium text-earth-700">¥{{ item.price }}</div>
            <div class="w-32 flex justify-center">
              <div class="flex items-center border border-earth-200 rounded-md overflow-hidden">
                <button @click="decreaseQuantity(item)" class="w-8 h-8 flex items-center justify-center bg-earth-50 text-earth-600 hover:bg-earth-100 transition-colors" :disabled="item.quantity <= 1">-</button>
                <input type="number" v-model.number="item.quantity" class="w-12 h-8 text-center text-sm text-earth-800 focus:outline-none border-x border-earth-200" min="1" :max="item.stock" />
                <button @click="increaseQuantity(item)" class="w-8 h-8 flex items-center justify-center bg-earth-50 text-earth-600 hover:bg-earth-100 transition-colors" :disabled="item.quantity >= item.stock">+</button>
              </div>
            </div>
            <div class="w-24 text-center font-bold text-earth-600">¥{{ item.price * item.quantity }}</div>
            <div class="w-20 text-center">
              <button @click="removeItem(item.id)" class="text-earth-400 hover:text-red-500 transition-colors">
                删除
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Order Summary -->
      <div class="lg:w-1/3">
        <div class="bg-white p-6 rounded-lg shadow-sm sticky top-8">
          <h2 class="text-lg font-bold text-earth-800 mb-6 border-b border-earth-100 pb-4">订单摘要</h2>
          
          <div class="space-y-4 mb-6">
            <div class="flex justify-between text-earth-600">
              <span>已选商品</span>
              <span>{{ selectedCount }} 件</span>
            </div>
            <div class="flex justify-between text-earth-600">
              <span>商品总计</span>
              <span>¥{{ totalPrice.toFixed(2) }}</span>
            </div>
            <div class="flex justify-between text-earth-600">
              <span>运费</span>
              <span>免运费</span>
            </div>
          </div>
          
          <div class="border-t border-earth-100 pt-4 mb-6">
            <div class="flex justify-between items-end">
              <span class="text-earth-800 font-medium">应付总额</span>
              <span class="text-3xl font-bold text-earth-700">¥{{ totalPrice.toFixed(2) }}</span>
            </div>
          </div>
          
          <button 
            @click="checkout" 
            class="w-full py-4 rounded-md font-medium text-lg transition-colors shadow-sm"
            :class="selectedCount > 0 
              ? 'bg-earth-500 hover:bg-earth-600 text-white' 
              : 'bg-earth-200 text-earth-400 cursor-not-allowed'"
            :disabled="selectedCount === 0"
          >
            去结算
          </button>
        </div>
      </div>
    </div>

    <!-- Empty Cart -->
    <div v-else class="bg-white rounded-lg shadow-sm p-16 flex flex-col items-center justify-center text-center">
      <div class="w-32 h-32 bg-earth-50 rounded-full flex items-center justify-center mb-6">
        <i class="i-ep-shopping-cart text-5xl text-earth-300"></i>
      </div>
      <h2 class="text-xl font-medium text-earth-800 mb-2">您的购物车是空的</h2>
      <p class="text-earth-500 mb-8">去发现一些心仪的自然好物吧</p>
      <button @click="router.push('/')" class="px-8 py-3 bg-earth-500 text-white rounded-md hover:bg-earth-600 transition-colors shadow-sm font-medium">
        去逛逛
      </button>
    </div>
  </div>
</template>
