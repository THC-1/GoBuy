<script setup lang="ts">
const currentYear = new Date().getFullYear()

const productLinks = [
  { label: '智能手机', path: '/products/smartphone' },
  { label: '笔记本电脑', path: '/products/laptop' },
  { label: '平板电脑', path: '/products/tablet' },
  { label: '智能穿戴', path: '/products/wearable' },
  { label: '音频设备', path: '/products/audio' }
]

const serviceLinks = [
  { label: '免费配送', path: '/service/shipping' },
  { label: '正品保障', path: '/service/authenticity' },
  { label: '售后无忧', path: '/service/warranty' },
  { label: '分期免息', path: '/service/installment' },
  { label: '以旧换新', path: '/service/trade-in' }
]

const aboutLinks = [
  { label: '关于我们', path: '/about' },
  { label: '品牌故事', path: '/about/story' },
  { label: '新闻资讯', path: '/about/news' },
  { label: '加入我们', path: '/about/careers' },
  { label: '联系我们', path: '/about/contact' }
]

const supportLinks = [
  { label: '帮助中心', path: '/support/help' },
  { label: '订单追踪', path: '/support/orders' },
  { label: '退换货政策', path: '/support/returns' },
  { label: '隐私政策', path: '/privacy' },
  { label: '服务条款', path: '/terms' }
]
</script>

<template>
  <footer class="bg-lumina-900 text-lumina-400 border-t border-white/5 mt-auto">
    <div class="max-w-7xl mx-auto px-6 py-16">
      <div class="grid grid-cols-2 md:grid-cols-4 gap-8 mb-12">
        <div>
          <h4 class="text-sm font-semibold text-lumina-200 uppercase tracking-wider mb-4">产品</h4>
          <ul class="space-y-2.5">
            <li v-for="link in productLinks" :key="link.path">
              <a :href="link.path" class="text-sm hover:text-white transition-colors duration-200">{{ link.label }}</a>
            </li>
          </ul>
        </div>

        <div>
          <h4 class="text-sm font-semibold text-lumina-200 uppercase tracking-wider mb-4">服务</h4>
          <ul class="space-y-2.5">
            <li v-for="link in serviceLinks" :key="link.path">
              <a :href="link.path" class="text-sm hover:text-white transition-colors duration-200">{{ link.label }}</a>
            </li>
          </ul>
        </div>

        <div>
          <h4 class="text-sm font-semibold text-lumina-200 uppercase tracking-wider mb-4">关于</h4>
          <ul class="space-y-2.5">
            <li v-for="link in aboutLinks" :key="link.path">
              <a :href="link.path" class="text-sm hover:text-white transition-colors duration-200">{{ link.label }}</a>
            </li>
          </ul>
        </div>

        <div>
          <h4 class="text-sm font-semibold text-lumina-200 uppercase tracking-wider mb-4">支持</h4>
          <ul class="space-y-2.5">
            <li v-for="link in supportLinks" :key="link.path">
              <a :href="link.path" class="text-sm hover:text-white transition-colors duration-200">{{ link.label }}</a>
            </li>
          </ul>
        </div>
      </div>

      <div class="border-t border-white/10 pt-8 mt-12">
        <div class="flex flex-col sm:flex-row justify-between items-center gap-4">
          <p class="text-sm text-lumina-500">
            Copyright © {{ currentYear }} Lumina Digital. All rights reserved.
          </p>
          <div class="flex items-center gap-6">
            <a href="/privacy" class="text-sm text-lumina-500 hover:text-lumina-200 transition-colors duration-200">隐私政策</a>
            <a href="/terms" class="text-sm text-lumina-500 hover:text-lumina-200 transition-colors duration-200">条款</a>
            <a href="/cookies" class="text-sm text-lumina-500 hover:text-lumina-200 transition-colors duration-200">Cookie设置</a>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>
