<template>
  <div class="pt-14 bg-lumina-100">
    <section class="bg-lumina-950">
      <div class="max-w-7xl mx-auto px-6 py-16 lg:py-24">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 lg:gap-16">
          <ProductGallery
            ref="galleryRef"
            :images="product.images"
          />
          <ProductInfoPanel
            :product="product"
            @buy="handleBuy"
            @image-change="handleImageChange"
          />
        </div>
      </div>
    </section>

    <FeatureSection :features="product.features" />

    <SpecSection :specifications="product.specifications" />

    <StickyBuyBar
      :show="showStickyBar"
      :name="product.shortName"
      :price="product.price"
      @buy="handleBuy"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useScroll } from '@vueuse/core'
import { ElMessage } from 'element-plus'
import { mockProductDetail } from '@/mock/product'
import ProductGallery from '@/components/client/detail/ProductGallery.vue'
import ProductInfoPanel from '@/components/client/detail/ProductInfoPanel.vue'
import FeatureSection from '@/components/client/detail/FeatureSection.vue'
import SpecSection from '@/components/client/detail/SpecSection.vue'
import StickyBuyBar from '@/components/client/detail/StickyBuyBar.vue'

const product = ref(mockProductDetail)
const galleryRef = ref<InstanceType<typeof ProductGallery>>()
const { y: scrollY } = useScroll(window)

const showStickyBar = computed(() => scrollY.value > 600)

const handleBuy = () => {
  ElMessage.info('购物车功能即将上线')
}

const handleImageChange = (imageUrl: string) => {
  const imageIndex = product.value.images.indexOf(imageUrl)
  if (imageIndex !== -1 && galleryRef.value) {
    galleryRef.value.selectImage(imageIndex)
  }
}
</script>
