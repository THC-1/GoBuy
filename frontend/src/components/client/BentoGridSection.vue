<script setup lang="ts">
import { featuredProducts } from '@/mock/home'
import { useRouter } from 'vue-router'
import ProductCard from './ProductCard.vue'

const router = useRouter()
const products = featuredProducts

const heroProduct = products[0]
const smallProducts = products.slice(1, 3)
const wideProduct = products[3]
const extraSmallProducts = products.slice(4)

function navigateToDetail(id: number) {
  router.push(`/product/${id}`)
}

function formatPrice(price: number): string {
  return price.toLocaleString('zh-CN')
}
</script>

<template>
  <section class="bento-section">
    <div class="bento-container">
      <h2 class="bento-title">热门产品</h2>

      <div class="bento-grid">
        <div
          class="bento-hero-card group"
          @click="navigateToDetail(heroProduct.id)"
        >
          <div class="bento-hero-glow" />
          <div class="bento-hero-content">
            <span class="bento-hero-category">{{ heroProduct.categoryName }}</span>
            <div class="mt-auto">
              <h3 class="bento-hero-name">{{ heroProduct.name }}</h3>
              <p class="bento-hero-price">¥{{ formatPrice(heroProduct.price) }}</p>
            </div>
            <img
              :src="heroProduct.mainImage"
              :alt="heroProduct.name"
              class="bento-hero-image"
              loading="lazy"
            />
          </div>
        </div>

        <ProductCard
          v-for="product in smallProducts"
          :key="product.id"
          :product="product"
        />

        <div
          class="bento-wide-card group"
          @click="navigateToDetail(wideProduct.id)"
        >
          <div class="bento-wide-text">
            <span class="bento-wide-category">{{ wideProduct.categoryName }}</span>
            <h3 class="bento-wide-name">{{ wideProduct.name }}</h3>
            <p class="bento-wide-price">¥{{ formatPrice(wideProduct.price) }}</p>
          </div>
          <img
            :src="wideProduct.mainImage"
            :alt="wideProduct.name"
            class="bento-wide-image"
            loading="lazy"
          />
        </div>

        <ProductCard
          v-for="product in extraSmallProducts"
          :key="product.id"
          :product="product"
        />
      </div>
    </div>
  </section>
</template>

<style scoped>
.bento-section {
  background-color: #f5f5f7;
  padding: 5rem 0;
}

.bento-container {
  max-width: 80rem;
  margin-left: auto;
  margin-right: auto;
  padding-left: 1.5rem;
  padding-right: 1.5rem;
}

.bento-title {
  font-size: 1.875rem;
  font-weight: 600;
  letter-spacing: -0.025em;
  color: #000000;
  margin-bottom: 3rem;
}

.bento-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

@media (min-width: 768px) {
  .bento-grid {
    grid-template-columns: repeat(4, 1fr);
    grid-auto-rows: 17.5rem;
  }
}

.bento-hero-card {
  position: relative;
  border-radius: 1.5rem;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.5s cubic-bezier(0.25, 1, 0.5, 1);
}
@media (min-width: 768px) {
  .bento-hero-card {
    grid-column: span 2;
    grid-row: span 2;
  }
}
.bento-hero-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 16px 48px rgb(0, 0, 0, 0.15);
}

.bento-hero-glow {
  position: absolute;
  top: -5rem;
  right: -5rem;
  width: 15rem;
  height: 15rem;
  border-radius: 9999px;
  filter: blur(48px);
  opacity: 0.6;
  pointer-events: none;
}

.bento-hero-content {
  position: relative;
  z-index: 10;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  padding: 2rem;
  background: linear-gradient(to bottom right, #1d1d1f, #0a0a0a);
}

.bento-hero-category {
  font-size: 0.75rem;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  color: rgba(134, 134, 139, 0.8);
}

.bento-hero-name {
  font-size: 1.5rem;
  font-weight: 600;
  color: #ffffff;
}

.bento-hero-price {
  font-size: 1.125rem;
  color: rgba(255, 255, 255, 0.6);
  margin-top: 0.25rem;
}

.bento-hero-image {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 60%;
  height: 80%;
  object-fit: contain;
  opacity: 0.9;
  transition: transform 0.7s cubic-bezier(0.16, 1, 0.3, 1);
}
.bento-hero-card:hover .bento-hero-image {
  transform: scale(1.05);
}

.bento-wide-card {
  display: flex;
  align-items: center;
  gap: 2rem;
  padding: 2rem;
  border-radius: 1.5rem;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.25, 1, 0.5, 1);
  background-color: #0a0a0a;
}
@media (min-width: 768px) {
  .bento-wide-card {
    grid-column: span 2;
  }
}
.bento-wide-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 16px 40px rgb(0, 0, 0, 0.12);
}

.bento-wide-text {
  flex: 1;
  min-width: 0;
}

.bento-wide-category {
  font-size: 0.75rem;
  color: rgba(134, 134, 139, 0.8);
}

.bento-wide-name {
  font-size: 1.5rem;
  font-weight: 600;
  color: #ffffff;
  margin-top: 0.25rem;
}

.bento-wide-price {
  font-size: 1.125rem;
  color: rgba(255, 255, 255, 0.6);
  margin-top: 0.5rem;
}

.bento-wide-image {
  width: 33.333%;
  aspect-ratio: 1 / 1;
  object-fit: contain;
  flex-shrink: 0;
  transition: transform 0.7s cubic-bezier(0.16, 1, 0.3, 1);
}
.bento-wide-card:hover .bento-wide-image {
  transform: scale(1.05);
}
</style>
