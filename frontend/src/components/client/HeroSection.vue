<script setup lang="ts">
import { heroData } from '@/mock/home'
import { useRouter } from 'vue-router'

const router = useRouter()
const hero = heroData

function handlePrimaryCta() {
  router.push('/product/1')
}

function handleSecondaryCta() {
  router.push('/product/1')
}
</script>

<template>
  <section class="relative h-[85vh] min-h-[600px] w-full overflow-hidden bg-lumina-950">
    <div class="absolute inset-0 bg-gradient-to-b from-[#0a0a0a] via-[#121214] to-[#1a1a1c]" />

    <div class="relative z-10 h-full flex flex-col items-center justify-center px-6">
      <div class="text-center">
        <h1 class="hero-title animate-fade-in-up delay-0">
          {{ hero.title }}
        </h1>
        <p class="hero-subtitle animate-fade-in-up delay-200">
          {{ hero.subtitle }}
        </p>
        <p class="hero-description animate-fade-in-up delay-200">
          {{ hero.description }}
        </p>
      </div>

      <div class="flex justify-center gap-4 mt-8 animate-fade-in-up delay-400">
        <button
          @click="handlePrimaryCta"
          class="hero-btn-primary"
        >
          {{ hero.primaryCta }}
        </button>
        <button
          @click="handleSecondaryCta"
          class="hero-btn-secondary"
        >
          {{ hero.secondaryCta }}
        </button>
      </div>

      <div class="relative mt-12 w-full max-w-3xl mx-auto">
        <img
          :src="hero.image"
          :alt="hero.title"
          class="hero-image"
          loading="eager"
        />
      </div>
    </div>

    <div class="absolute bottom-8 left-1/2 -translate-x-1/2 animate-bounce">
      <svg
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="rgba(255,255,255,0.5)"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
      >
        <path d="M12 5v14M19 12l-7 7-7-7" />
      </svg>
    </div>
  </section>
</template>

<style scoped>
.hero-title {
  font-size: clamp(2.75rem, 6vw, 6rem);
  font-weight: 700;
  letter-spacing: -0.025em;
  line-height: 0.95;
  color: #ffffff;
}

.hero-subtitle {
  font-size: 1.125rem;
  line-height: 1.625;
  color: rgba(255, 255, 255, 0.7);
  margin-top: 1rem;
  max-width: 42rem;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

@media (min-width: 768px) {
  .hero-subtitle {
    font-size: 1.25rem;
  }
}

.hero-description {
  display: none;
}

@media (min-width: 768px) {
  .hero-description {
    display: block;
    font-size: 1.125rem;
    line-height: 1.625;
    color: rgba(255, 255, 255, 0.7);
    margin-top: 0.5rem;
    text-align: center;
  }
}

.hero-btn-primary {
  padding: 0.875rem 2rem;
  background-color: #ffffff;
  color: #0a0a0a;
  border-radius: 9999px;
  font-size: 0.875rem;
  font-weight: 500;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
}
.hero-btn-primary:hover {
  background-color: #f5f5f7;
}
.hero-btn-primary:active {
  transform: scale(0.95);
}

.hero-btn-secondary {
  padding: 0.875rem 2rem;
  background-color: transparent;
  color: #ffffff;
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 9999px;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}
.hero-btn-secondary:hover {
  background-color: rgba(255, 255, 255, 0.1);
  border-color: rgba(255, 255, 255, 0.5);
}
.hero-btn-secondary:active {
  transform: scale(0.95);
}

.hero-image {
  width: 100%;
  max-width: 42rem;
  margin-left: auto;
  margin-right: auto;
  object-fit: contain;
  filter: drop-shadow(0 20px 60px rgba(0, 0, 0, 0.5));
  animation: hero-float 6s ease-in-out infinite;
}

@keyframes fade-in-up {
  from {
    opacity: 0;
    transform: translateY(24px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in-up {
  animation: fade-in-up 0.8s cubic-bezier(0.16, 1, 0.3, 1) both;
}

.delay-0 {
  animation-delay: 0ms;
}

.delay-200 {
  animation-delay: 200ms;
}

.delay-400 {
  animation-delay: 400ms;
}

@keyframes hero-float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}
</style>
