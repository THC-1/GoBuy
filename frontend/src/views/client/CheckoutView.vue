<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Mock addresses
const addresses = ref([
  { id: 1, name: '张三', phone: '138****8888', province: '浙江省', city: '杭州市', district: '西湖区', detail: '文一西路 999 号', isDefault: true },
  { id: 2, name: '李四', phone: '139****9999', province: '上海市', city: '市辖区', district: '浦东新区', detail: '世纪大道 1 号', isDefault: false },
])
const selectedAddressId = ref(1)

// Mock order items (from cart)
const orderItems = ref([
  { id: 1, name: '纯棉轻盈透气长袖衬衫', sku: '米白色, M', price: 199, quantity: 1, image: 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=200&h=200&fit=crop' },
  { id: 2, name: '亚麻宽松休闲长裤', sku: '卡其色, L', price: 259, quantity: 2, image: 'https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=200&h=200&fit=crop' },
])

const remark = ref('')

// Payment methods
const paymentMethods = ref([
  { id: 'alipay', name: '支付宝', icon: 'i-ep-money' },
  { id: 'wechat', name: '微信支付', icon: 'i-ep-chat-dot-round' },
])
const selectedPayment = ref('alipay')

const totalAmount = computed(() => {
  return orderItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
})

const isSubmitting = ref(false)
const showSuccessModal = ref(false)

const submitOrder = () => {
  if (!selectedAddressId.value) {
    alert('请选择收货地址')
    return
  }
  
  isSubmitting.value = true
  
  // Simulate API call
  setTimeout(() => {
    isSubmitting.value = false
    showSuccessModal.value = true
  }, 1500)
}

const goToHome = () => {
  showSuccessModal.value = false
  router.push('/')
}
</script>

<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <h1 class="text-2xl font-bold text-earth-800 mb-8">确认订单</h1>

    <div class="flex flex-col lg:flex-row gap-8">
      <!-- Main Content -->
      <div class="lg:w-2/3 flex flex-col gap-6">
        
        <!-- Address Section -->
        <div class="bg-white p-6 rounded-lg shadow-sm">
          <div class="flex justify-between items-center mb-4 border-b border-earth-100 pb-4">
            <h2 class="text-lg font-bold text-earth-800 flex items-center">
              <i class="i-ep-location mr-2 text-earth-600"></i>
              收货地址
            </h2>
            <button class="text-earth-500 hover:text-earth-700 text-sm transition-colors">管理地址</button>
          </div>
          
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div 
              v-for="addr in addresses" 
              :key="addr.id"
              @click="selectedAddressId = addr.id"
              class="border-2 rounded-lg p-4 cursor-pointer transition-all relative"
              :class="selectedAddressId === addr.id ? 'border-earth-500 bg-earth-50/50' : 'border-earth-200 hover:border-earth-300'"
            >
              <div class="flex justify-between items-start mb-2">
                <span class="font-bold text-earth-800">{{ addr.name }}</span>
                <span class="text-earth-600">{{ addr.phone }}</span>
              </div>
              <p class="text-sm text-earth-600 leading-relaxed">
                {{ addr.province }} {{ addr.city }} {{ addr.district }}<br/>
                {{ addr.detail }}
              </p>
              <span v-if="addr.isDefault" class="absolute top-4 right-4 text-xs bg-earth-200 text-earth-700 px-2 py-0.5 rounded">默认</span>
              
              <div v-if="selectedAddressId === addr.id" class="absolute -right-2 -bottom-2 w-6 h-6 bg-earth-500 rounded-tl-lg flex items-center justify-center text-white" style="clip-path: polygon(100% 0, 100% 100%, 0 100%);">
                <i class="i-ep-check text-xs ml-2 mt-2"></i>
              </div>
            </div>
            
            <div class="border-2 border-dashed border-earth-300 rounded-lg p-4 cursor-pointer hover:border-earth-500 hover:bg-earth-50/50 transition-all flex flex-col items-center justify-center text-earth-500 min-h-[120px]">
              <i class="i-ep-plus text-2xl mb-2"></i>
              <span>添加新地址</span>
            </div>
          </div>
        </div>

        <!-- Order Items Section -->
        <div class="bg-white p-6 rounded-lg shadow-sm">
          <h2 class="text-lg font-bold text-earth-800 mb-4 border-b border-earth-100 pb-4 flex items-center">
            <i class="i-ep-goods mr-2 text-earth-600"></i>
            商品清单
          </h2>
          
          <div class="space-y-4">
            <div v-for="item in orderItems" :key="item.id" class="flex gap-4 p-4 bg-earth-50/50 rounded-lg">
              <img :src="item.image" :alt="item.name" class="w-20 h-20 rounded-md object-cover bg-earth-100" />
              <div class="flex-1 flex flex-col justify-center">
                <h3 class="text-base font-medium text-earth-800 mb-1">{{ item.name }}</h3>
                <p class="text-sm text-earth-500">{{ item.sku }}</p>
              </div>
              <div class="text-right flex flex-col justify-center">
                <div class="font-bold text-earth-700">¥{{ item.price }}</div>
                <div class="text-sm text-earth-500">x {{ item.quantity }}</div>
              </div>
            </div>
          </div>
          
          <div class="mt-6">
            <label class="block text-sm font-medium text-earth-700 mb-2">订单备注</label>
            <textarea 
              v-model="remark"
              rows="2" 
              class="w-full border border-earth-300 rounded-md p-3 text-sm focus:ring-earth-500 focus:border-earth-500 text-earth-800 placeholder-earth-400"
              placeholder="选填，请先和商家协商一致"
            ></textarea>
          </div>
        </div>
        
        <!-- Payment Method Section -->
        <div class="bg-white p-6 rounded-lg shadow-sm">
          <h2 class="text-lg font-bold text-earth-800 mb-4 border-b border-earth-100 pb-4 flex items-center">
            <i class="i-ep-wallet mr-2 text-earth-600"></i>
            支付方式
          </h2>
          
          <div class="flex flex-wrap gap-4">
            <div 
              v-for="method in paymentMethods" 
              :key="method.id"
              @click="selectedPayment = method.id"
              class="border-2 rounded-lg py-3 px-6 cursor-pointer transition-all flex items-center"
              :class="selectedPayment === method.id ? 'border-earth-500 bg-earth-50/50 text-earth-700' : 'border-earth-200 text-earth-600 hover:border-earth-400'"
            >
              <i :class="[method.icon, 'text-xl mr-2', selectedPayment === method.id ? 'text-earth-600' : 'text-earth-400']"></i>
              <span class="font-medium">{{ method.name }}</span>
            </div>
          </div>
        </div>

      </div>

      <!-- Order Summary Sidebar -->
      <div class="lg:w-1/3">
        <div class="bg-white p-6 rounded-lg shadow-sm sticky top-8">
          <h2 class="text-lg font-bold text-earth-800 mb-6 border-b border-earth-100 pb-4">结算明细</h2>
          
          <div class="space-y-4 mb-6">
            <div class="flex justify-between text-earth-600">
              <span>商品件数</span>
              <span>{{ orderItems.reduce((sum, item) => sum + item.quantity, 0) }} 件</span>
            </div>
            <div class="flex justify-between text-earth-600">
              <span>商品总价</span>
              <span>¥{{ totalAmount.toFixed(2) }}</span>
            </div>
            <div class="flex justify-between text-earth-600">
              <span>运费</span>
              <span>¥0.00</span>
            </div>
            <div class="flex justify-between text-earth-600">
              <span>优惠金额</span>
              <span class="text-earth-500">- ¥0.00</span>
            </div>
          </div>
          
          <div class="border-t border-earth-100 pt-4 mb-6">
            <div class="flex justify-between items-end mb-2">
              <span class="text-earth-800 font-bold">实付款</span>
              <span class="text-3xl font-bold text-earth-700">¥{{ totalAmount.toFixed(2) }}</span>
            </div>
            <div class="text-right text-xs text-earth-500">
              寄送至: {{ addresses.find(a => a.id === selectedAddressId)?.province }} {{ addresses.find(a => a.id === selectedAddressId)?.city }}...
            </div>
          </div>
          
          <button 
            @click="submitOrder" 
            class="w-full py-4 rounded-md font-bold text-lg text-white transition-all shadow-sm flex items-center justify-center"
            :class="isSubmitting ? 'bg-earth-400 cursor-wait' : 'bg-earth-500 hover:bg-earth-600'"
            :disabled="isSubmitting"
          >
            <i v-if="isSubmitting" class="i-ep-loading animate-spin mr-2"></i>
            {{ isSubmitting ? '正在处理...' : '提交订单' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Success Modal -->
    <div v-if="showSuccessModal" class="fixed inset-0 bg-earth-900/50 flex items-center justify-center z-50 backdrop-blur-sm">
      <div class="bg-white rounded-xl shadow-xl p-8 max-w-sm w-full mx-4 text-center transform transition-all">
        <div class="w-20 h-20 bg-morandi-green/20 text-morandi-green rounded-full flex items-center justify-center mx-auto mb-6">
          <i class="i-ep-check text-4xl"></i>
        </div>
        <h3 class="text-2xl font-bold text-earth-800 mb-2">支付成功</h3>
        <p class="text-earth-600 mb-8">感谢您的购买，我们将尽快为您发货。</p>
        
        <div class="space-y-3">
          <button @click="goToHome" class="w-full py-3 bg-earth-500 hover:bg-earth-600 text-white rounded-md font-medium transition-colors">
            返回首页
          </button>
          <button @click="router.push('/user/profile')" class="w-full py-3 border border-earth-300 text-earth-700 hover:bg-earth-50 rounded-md font-medium transition-colors">
            查看订单
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Additional specific styles if needed */
</style>
