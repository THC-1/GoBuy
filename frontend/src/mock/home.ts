import type { HeroData, ProductCardData } from '@/types/product'

export const heroData: HeroData = {
  title: 'iPhone 15 Pro',
  subtitle: '钛金属设计。A17 Pro芯片。',
  description: '一部开创先机的iPhone。',
  primaryCta: '购买',
  secondaryCta: '进一步了解',
  image: 'https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=800&q=80',
  bgColor: '#0a0a0a'
}

export const featuredProducts: ProductCardData[] = [
  {
    id: 1,
    name: 'MacBook Pro 14"',
    categoryName: '笔记本电脑',
    mainImage: 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600&q=80',
    price: 14999,
    originalPrice: 16499,
    gridSpan: 4,
    bgVariant: 'dark'
  },
  {
    id: 2,
    name: 'AirPods Pro 第二代',
    categoryName: '耳机',
    mainImage: 'https://images.unsplash.com/photo-1600294037801-47f3e0eaa580?w=400&q=80',
    price: 1899,
    originalPrice: 2199,
    badge: '热销',
    gridSpan: 1,
    bgVariant: 'light'
  },
  {
    id: 3,
    name: 'Apple Watch Ultra 2',
    categoryName: '智能手表',
    mainImage: 'https://images.unsplash.com/photo-1546868871-af0de0ae72be?w=400&q=80',
    price: 6499,
    badge: '新品',
    gridSpan: 1,
    bgVariant: 'dark'
  },
  {
    id: 4,
    name: 'iPad Pro 12.9" M2',
    categoryName: '平板电脑',
    mainImage: 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=500&q=80',
    price: 9299,
    originalPrice: 9999,
    gridSpan: 2,
    bgVariant: 'light'
  },
  {
    id: 5,
    name: 'Sony WH-1000XM5 头戴式降噪耳机',
    categoryName: '耳机',
    mainImage: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&q=80',
    price: 2499,
    originalPrice: 2999,
    gridSpan: 1,
    bgVariant: 'light'
  },
  {
    id: 6,
    name: 'Samsung Galaxy S24 Ultra',
    categoryName: '手机',
    mainImage: 'https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=400&q=80',
    price: 9699,
    badge: '新品',
    gridSpan: 1,
    bgVariant: 'light'
  }
]
