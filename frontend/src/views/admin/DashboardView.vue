<script setup lang="ts">
</script>

<template>
  <div>
    <h2 class="text-2xl font-bold text-earth-800 mb-6">仪表盘</h2>
    <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
      <div class="bg-white p-6 rounded-lg shadow-sm border border-earth-200">
        <div class="text-earth-500 mb-2">总销售额</div>
        <div class="text-3xl font-bold text-earth-800">￥12,345</div>
      </div>
      <div class="bg-white p-6 rounded-lg shadow-sm border border-earth-200">
        <div class="text-earth-500 mb-2">今日订单</div>
        <div class="text-3xl font-bold text-earth-800">128</div>
      </div>
      <div class="bg-white p-6 rounded-lg shadow-sm border border-earth-200">
        <div class="text-earth-500 mb-2">新增用户</div>
        <div class="text-3xl font-bold text-earth-800">45</div>
      </div>
      <div class="bg-white p-6 rounded-lg shadow-sm border border-earth-200">
        <div class="text-earth-500 mb-2">待处理售后</div>
        <div class="text-3xl font-bold text-danger">3</div>
      </div>
    </div>
    
    <div class="bg-white p-6 rounded-lg shadow-sm border border-earth-200 h-64 flex items-center justify-center">
      <p class="text-earth-400">销售趋势图表区域</p>
    </div>
  </div>
</template>
