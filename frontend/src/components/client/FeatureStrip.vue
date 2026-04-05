<script setup lang="ts">
const features = [
  {
    icon: 'van',
    title: '免费配送',
    description: '订单满¥999即享包邮'
  },
  {
    icon: 'shield',
    title: '正品保障',
    description: '官方授权渠道'
  },
  {
    icon: 'service',
    title: '售后无忧',
    description: '7天无理由退换'
  },
  {
    icon: 'credit-card',
    title: '分期免息',
    description: '3/6/12期免息可选'
  }
]
</script>

<template>
  <section class="feature-strip">
    <div class="feature-container">
      <div class="feature-grid">
        <div
          v-for="feature in features"
          :key="feature.icon"
          class="feature-item"
        >
          <div class="feature-icon-wrapper">
            <svg
              v-if="feature.icon === 'van'"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.8"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feature-svg"
            >
              <path d="M14 18V6a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v11a1 1 0 0 0 1 1h2" />
              <path d="M15 18H9" />
              <path d="M19 18h2a1 1 0 0 0 1-1v-3.65a1 1 0 0 0-.22-.624l-3.48-4.35A1 1 0 0 0 17.52 8H7" />
              <circle cx="17" cy="18" r="2" />
              <circle cx="7" cy="18" r="2" />
            </svg>
            <svg
              v-else-if="feature.icon === 'shield'"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.8"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feature-svg"
            >
              <path d="M20 13c0 5-3.5 7.5-7.66 8.95a1 1 0 0 1-.67-.01C7.5 20.5 4 18 4 13V6a1 1 0 0 1 1-1c2 0 4.5-1.2 6.24-2.72a1.17 1.17 0 0 1 1.52 0C14.51 3.81 17 5 19 5a1 1 0 0 1 1 1z" />
            </svg>
            <svg
              v-else-if="feature.icon === 'service'"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.8"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feature-svg"
            >
              <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" />
            </svg>
            <svg
              v-else-if="feature.icon === 'credit-card'"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.8"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="feature-svg"
            >
              <rect x="2" y="5" width="20" height="14" rx="2" />
              <line x1="2" y1="10" x2="22" y2="10" />
            </svg>
          </div>
          <h4 class="feature-title">{{ feature.title }}</h4>
          <p class="feature-description">{{ feature.description }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.feature-strip {
  background-color: #ffffff;
  border-top: 1px solid #e8e8ed;
  border-bottom: 1px solid #e8e8ed;
}

.feature-container {
  max-width: 80rem;
  margin-left: auto;
  margin-right: auto;
  padding-left: 1.5rem;
  padding-right: 1.5rem;
  padding-top: 4rem;
  padding-bottom: 4rem;
}

.feature-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 2rem;
}

@media (min-width: 768px) {
  .feature-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

.feature-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.feature-icon-wrapper {
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 9999px;
  background-color: #f5f5f7;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s ease;
}
.feature-item:hover .feature-icon-wrapper {
  background-color: #e8e8ed;
}

.feature-svg {
  color: #424245;
}

.feature-title {
  font-size: 0.875rem;
  font-weight: 600;
  color: #1d1d1f;
  margin-top: 0.75rem;
}

.feature-description {
  font-size: 0.875rem;
  color: #6e6e73;
  line-height: 1.625;
  margin-top: 0.25rem;
}
</style>
