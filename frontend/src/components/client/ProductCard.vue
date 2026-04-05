<script setup lang="ts">
import type { ProductCardData } from '@/types/product'
import { useRouter } from 'vue-router'

const props = defineProps<{
  product: ProductCardData
}>()

const router = useRouter()

function navigateToDetail() {
  router.push(`/product/${props.product.id}`)
}

function formatPrice(price: number): string {
  return price.toLocaleString('zh-CN')
}
</script>

<template>
  <div
    class="product-card group cursor-pointer"
    @click="navigateToDetail"
  >
    <span class="product-category">{{ product.categoryName }}</span>

    <div class="mt-auto">
      <h3 class="product-name">{{ product.name }}</h3>
      <div class="product-price-row">
        <span class="product-price">¥{{ formatPrice(product.price) }}</span>
        <span v-if="product.originalPrice" class="product-original-price">
          ¥{{ formatPrice(product.originalPrice) }}
        </span>
      </div>
    </div>

    <img
      :src="product.mainImage"
      :alt="product.name"
      class="product-image"
      loading="lazy"
    />

    <span
      v-if="product.badge"
      class="product-badge"
    >
      {{ product.badge }}
    </span>
  </div>
</template>

<style scoped>
.product-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #ffffff;
  border-radius: 1.5rem;
  overflow: hidden;
  padding: 1.5rem;
  transition: all 0.3s cubic-bezier(0.25, 1, 0.5, 1);
}
.product-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 40px rgb(0, 0, 0, 0.08);
}

.product-category {
  font-size: 0.75rem;
  font-weight: 500;
  color: #6e6e73;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.product-name {
  font-size: 1.125rem;
  font-weight: 600;
  color: #000000;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  margin-top: 0.25rem;
}

.product-price-row {
  margin-top: 0.5rem;
  display: flex;
  align-items: baseline;
  gap: 0.5rem;
}

.product-price {
  font-size: 1rem;
  font-weight: 700;
  color: #000000;
}

.product-original-price {
  font-size: 0.875rem;
  color: #86868b;
  text-decoration: line-through;
}

.product-image {
  aspect-ratio: 1 / 1;
  object-fit: cover;
  border-radius: 1rem;
  margin-top: 1rem;
  transition: transform 0.5s cubic-bezier(0.16, 1, 0.3, 1);
}
.group:hover .product-image {
  transform: translateY(-4px);
}

.product-badge {
  position: absolute;
  top: 1rem;
  left: 1.5rem;
  padding: 0.25rem 0.625rem;
  background-color: rgba(29, 29, 31, 0.8);
  backdrop-filter: blur(4px);
  color: #ffffff;
  font-size: 0.6875rem;
  font-weight: 500;
  border-radius: 9999px;
}
</style>
