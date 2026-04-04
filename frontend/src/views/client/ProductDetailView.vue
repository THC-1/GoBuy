<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const productId = route.params.id

// Mock product data
const product = ref({
  id: productId,
  name: '纯棉轻盈透气长袖衬衫',
  price: 199,
  originalPrice: 299,
  description: '采用优质天然纯棉材质，亲肤透气。简约的剪裁设计，适合各种日常场合。带来舒适与优雅并存的穿着体验。',
  images: [
    'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&h=600&fit=crop',
    'https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=600&h=600&fit=crop',
    'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&h=600&fit=crop',
  ],
  skus: [
    { name: '颜色', options: ['米白色', '浅咖色', '薄荷绿'] },
    { name: '尺码', options: ['S', 'M', 'L', 'XL'] }
  ],
  sales: 1256,
  stock: 342,
  rating: 4.8
})

const activeImage = ref(0)
const selectedSkus = ref<Record<string, string>>({
  '颜色': '米白色',
  '尺码': 'M'
})
const quantity = ref(1)
const activeTab = ref('details')

// Mock reviews
const reviews = ref([
  { id: 1, user: '张**', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix', rating: 5, date: '2023-10-15', content: '非常舒服的面料，透气性很好，颜色也很正，很百搭！', images: [] },
  { id: 2, user: '李**', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Aneka', rating: 4, date: '2023-10-12', content: '款式简约大方，做工精细，没有多余的线头。尺码稍微偏大一点。', images: ['https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=200&h=200&fit=crop'] },
  { id: 3, user: '王**', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Oliver', rating: 5, date: '2023-10-10', content: '买来送给男朋友的，他很喜欢，说穿着很轻盈，下次还会回购。', images: [] },
])

const selectSku = (name: string, option: string) => {
  selectedSkus.value[name] = option
}

const decreaseQuantity = () => {
  if (quantity.value > 1) quantity.value--
}

const increaseQuantity = () => {
  if (quantity.value < product.value.stock) quantity.value++
}

const addToCart = () => {
  // Mock add to cart logic
  alert(`已将 ${quantity.value} 件商品加入购物车！\n规格: ${Object.values(selectedSkus.value).join(', ')}`)
}

const buyNow = () => {
  router.push('/checkout')
}
</script>

<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Breadcrumb -->
    <nav class="flex text-earth-500 text-sm mb-8" aria-label="Breadcrumb">
      <ol class="inline-flex items-center space-x-1 md:space-x-3">
        <li class="inline-flex items-center">
          <router-link to="/" class="hover:text-earth-700 transition-colors">首页</router-link>
        </li>
        <li>
          <div class="flex items-center">
            <span class="mx-2">/</span>
            <span class="text-earth-700 font-medium" aria-current="page">{{ product.name }}</span>
          </div>
        </li>
      </ol>
    </nav>

    <div class="bg-white rounded-xl shadow-sm overflow-hidden mb-12">
      <div class="flex flex-col md:flex-row">
        <!-- Product Images -->
        <div class="md:w-1/2 p-6 md:p-8">
          <div class="aspect-square rounded-lg overflow-hidden bg-earth-100 mb-4">
            <img :src="product.images[activeImage]" :alt="product.name" class="w-full h-full object-cover" />
          </div>
          <div class="flex space-x-4 overflow-x-auto pb-2">
            <button 
              v-for="(img, index) in product.images" 
              :key="index"
              @click="activeImage = index"
              class="w-20 h-20 rounded-md overflow-hidden flex-shrink-0 border-2 transition-colors"
              :class="activeImage === index ? 'border-earth-500' : 'border-transparent hover:border-earth-300'"
            >
              <img :src="img" alt="Thumbnail" class="w-full h-full object-cover" />
            </button>
          </div>
        </div>

        <!-- Product Info -->
        <div class="md:w-1/2 p-6 md:p-8 md:border-l border-earth-100 flex flex-col">
          <h1 class="text-2xl md:text-3xl font-bold text-earth-800 mb-2">{{ product.name }}</h1>
          <p class="text-earth-500 mb-6 line-clamp-2">{{ product.description }}</p>
          
          <div class="bg-earth-50 p-4 rounded-lg mb-6 flex items-baseline">
            <span class="text-xl text-earth-600 font-medium mr-1">¥</span>
            <span class="text-3xl font-bold text-earth-700 mr-4">{{ product.price }}</span>
            <span class="text-earth-400 line-through text-sm">¥{{ product.originalPrice }}</span>
          </div>

          <!-- SKUs -->
          <div class="space-y-6 mb-8 flex-1">
            <div v-for="sku in product.skus" :key="sku.name">
              <h3 class="text-sm font-medium text-earth-700 mb-3">{{ sku.name }}</h3>
              <div class="flex flex-wrap gap-3">
                <button
                  v-for="option in sku.options"
                  :key="option"
                  @click="selectSku(sku.name, option)"
                  class="px-4 py-2 text-sm rounded-md border transition-all duration-200"
                  :class="selectedSkus[sku.name] === option 
                    ? 'border-earth-500 bg-earth-50 text-earth-700 font-medium' 
                    : 'border-earth-200 text-earth-600 hover:border-earth-400'"
                >
                  {{ option }}
                </button>
              </div>
            </div>

            <!-- Quantity -->
            <div>
              <h3 class="text-sm font-medium text-earth-700 mb-3">数量</h3>
              <div class="flex items-center space-x-4">
                <div class="flex items-center border border-earth-200 rounded-md overflow-hidden">
                  <button @click="decreaseQuantity" class="w-10 h-10 flex items-center justify-center bg-earth-50 text-earth-600 hover:bg-earth-100 transition-colors" :disabled="quantity <= 1">
                    <span class="text-xl">-</span>
                  </button>
                  <input type="number" v-model.number="quantity" class="w-16 h-10 text-center text-earth-800 focus:outline-none border-x border-earth-200" min="1" :max="product.stock" />
                  <button @click="increaseQuantity" class="w-10 h-10 flex items-center justify-center bg-earth-50 text-earth-600 hover:bg-earth-100 transition-colors" :disabled="quantity >= product.stock">
                    <span class="text-xl">+</span>
                  </button>
                </div>
                <span class="text-sm text-earth-500">库存 {{ product.stock }} 件</span>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex space-x-4 mt-auto">
            <button @click="addToCart" class="flex-1 py-3 px-6 rounded-md border-2 border-earth-500 text-earth-600 font-medium hover:bg-earth-50 transition-colors">
              加入购物车
            </button>
            <button @click="buyNow" class="flex-1 py-3 px-6 rounded-md bg-earth-500 text-white font-medium hover:bg-earth-600 transition-colors shadow-sm">
              立即购买
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Details and Reviews Tabs -->
    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
      <div class="flex border-b border-earth-100">
        <button 
          @click="activeTab = 'details'"
          class="flex-1 py-4 text-center font-medium transition-colors border-b-2"
          :class="activeTab === 'details' ? 'border-earth-500 text-earth-700' : 'border-transparent text-earth-500 hover:text-earth-700'"
        >
          商品详情
        </button>
        <button 
          @click="activeTab = 'reviews'"
          class="flex-1 py-4 text-center font-medium transition-colors border-b-2"
          :class="activeTab === 'reviews' ? 'border-earth-500 text-earth-700' : 'border-transparent text-earth-500 hover:text-earth-700'"
        >
          用户评价 ({{ reviews.length }})
        </button>
      </div>

      <div class="p-6 md:p-8">
        <!-- Details Content -->
        <div v-show="activeTab === 'details'" class="prose max-w-none text-earth-700">
          <p class="mb-6">这是一款注重质感与舒适度的产品。我们精选上乘材质，经过多道工序精心打磨，旨在为您带来自然、优雅的生活体验。</p>
          <img src="https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=1200&h=600&fit=crop" alt="Detail 1" class="w-full rounded-lg mb-6" />
          <h3 class="text-xl font-bold mb-4 text-earth-800">材质说明</h3>
          <p class="mb-6">100% 优质纯棉，亲肤透气，不易起球，洗涤后保持良好形态。每一寸面料都经过严格检测，确保安全无刺激。</p>
          <img src="https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=1200&h=600&fit=crop" alt="Detail 2" class="w-full rounded-lg" />
        </div>

        <!-- Reviews Content -->
        <div v-show="activeTab === 'reviews'">
          <div class="flex items-center mb-8 bg-earth-50 p-6 rounded-lg">
            <div class="text-center mr-8">
              <div class="text-4xl font-bold text-earth-700 mb-1">{{ product.rating }}</div>
              <div class="text-sm text-earth-500">好评率 98%</div>
            </div>
            <div class="flex-1 space-y-2">
              <!-- Mock Rating Bars -->
              <div class="flex items-center text-sm text-earth-600">
                <span class="w-8">5星</span>
                <div class="flex-1 h-2 mx-3 bg-earth-200 rounded-full overflow-hidden">
                  <div class="h-full bg-morandi-green" style="width: 85%"></div>
                </div>
                <span class="w-8 text-right">85%</span>
              </div>
              <div class="flex items-center text-sm text-earth-600">
                <span class="w-8">4星</span>
                <div class="flex-1 h-2 mx-3 bg-earth-200 rounded-full overflow-hidden">
                  <div class="h-full bg-morandi-green" style="width: 10%"></div>
                </div>
                <span class="w-8 text-right">10%</span>
              </div>
              <div class="flex items-center text-sm text-earth-600">
                <span class="w-8">3星</span>
                <div class="flex-1 h-2 mx-3 bg-earth-200 rounded-full overflow-hidden">
                  <div class="h-full bg-morandi-green" style="width: 5%"></div>
                </div>
                <span class="w-8 text-right">5%</span>
              </div>
            </div>
          </div>

          <div class="space-y-6">
            <div v-for="review in reviews" :key="review.id" class="border-b border-earth-100 pb-6 last:border-0 last:pb-0">
              <div class="flex items-center mb-3">
                <img :src="review.avatar" alt="Avatar" class="w-10 h-10 rounded-full bg-earth-200 mr-3" />
                <div>
                  <div class="font-medium text-earth-800">{{ review.user }}</div>
                  <div class="text-xs text-earth-500">{{ review.date }}</div>
                </div>
                <div class="ml-auto flex text-morandi-green">
                  <span v-for="i in 5" :key="i" class="text-lg" :class="i <= review.rating ? 'opacity-100' : 'opacity-30'">★</span>
                </div>
              </div>
              <p class="text-earth-700 mb-3">{{ review.content }}</p>
              <div v-if="review.images && review.images.length > 0" class="flex gap-2">
                <img v-for="(img, idx) in review.images" :key="idx" :src="img" alt="Review Image" class="w-20 h-20 rounded object-cover border border-earth-200 cursor-pointer hover:border-earth-400 transition-colors" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
