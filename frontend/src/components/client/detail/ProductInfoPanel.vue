<template>
  <div>
    <BreadcrumbNav :items="breadcrumbItems" />

    <h1 class="text-3xl lg:text-4xl font-semibold tracking-tight text-white mt-6">
      {{ product.name }}
    </h1>

    <p class="text-lg text-white/60 mt-2">{{ product.subtitle }}</p>

    <div class="mt-6 flex items-baseline gap-3">
      <span class="text-3xl font-bold text-white">¥{{ formatPrice(product.price) }}</span>
      <span v-if="product.originalPrice" class="text-lg line-through text-white/40">
        ¥{{ formatPrice(product.originalPrice) }}
      </span>
    </div>

    <SkuSelector
      :sku-groups="product.skuGroups"
      class="mt-8"
      @image-change="handleImageChange"
    />

    <div class="mt-8">
      <label class="text-sm font-medium text-white/60 mb-3 block">数量</label>
      <QuantitySelector v-model="quantity" :max="product.stock" />
    </div>

    <div class="flex gap-4 mt-10">
      <button
        @click="$emit('buy')"
        class="flex-1 bg-white text-lumina-950 rounded-full py-3.5 font-medium hover:bg-lumina-100 active:scale-[0.98] transition-all shadow-[0_4px_20px_rgba(255,255,255,0.15)]"
      >
        加入购物袋
      </button>
      <button
        @click="$emit('buy')"
        class="flex-1 border border-white/30 text-white rounded-full py-3.5 font-medium hover:bg-white/10 active:scale-[0.98] transition-all"
      >
        立即购买
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import type { ProductDetailVO } from '@/types/product'
import BreadcrumbNav from './BreadcrumbNav.vue'
import SkuSelector from './SkuSelector.vue'
import QuantitySelector from './QuantitySelector.vue'

const props = defineProps<{
  product: ProductDetailVO
}>()

const emit = defineEmits<{
  buy: []
  imageChange: [imageUrl: string]
}>()

const quantity = ref(1)

const breadcrumbItems = computed(() => [
  { label: '首页', path: '/' },
  { label: props.product.categoryName, path: '/products' },
  { label: props.product.name }
])

const formatPrice = (price: number) => price.toLocaleString()

const handleImageChange = (imageUrl: string) => {
  emit('imageChange', imageUrl)
}
</script>
