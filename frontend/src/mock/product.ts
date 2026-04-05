import type { ProductDetailVO } from '@/types/product'

export const mockProductDetail: ProductDetailVO = {
  id: 1,
  name: 'iPhone 15 Pro',
  shortName: 'iPhone 15 Pro',
  subtitle: '钛金属设计。A17 Pro芯片。相机系统大有可为。',
  categoryName: '智能手机',
  mainImage: 'https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=800&h=800&fit=crop',
  images: [
    'https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=800&h=800&fit=crop',
    'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=800&h=800&fit=crop',
    'https://images.unsplash.com/photo-1510557880182-3d4d3cba35a5?w=800&h=800&fit=crop',
    'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=800&h=800&fit=crop'
  ],
  price: 7999,
  originalPrice: 8999,
  stock: 100,
  skuGroups: [
    {
      name: '颜色',
      options: [
        { value: 'titanium_blue', label: '钛金属蓝', imageUrl: 'https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=800&h=800&fit=crop' },
        { value: 'natural_titanium', label: '自然钛', imageUrl: 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=800&h=800&fit=crop' },
        { value: 'white', label: '白色钛金属', imageUrl: 'https://images.unsplash.com/photo-1510557880182-3d4d3cba35a5?w=800&h=800&fit=crop' }
      ]
    },
    {
      name: '容量',
      options: [
        { value: '128gb', label: '128GB' },
        { value: '256gb', label: '256GB' },
        { value: '512gb', label: '512GB' },
        { value: '1tb', label: '1TB' }
      ]
    }
  ],
  features: [
    { icon: 'CpuChip', title: 'A17 Pro芯片', description: '下一代GPU架构，主机级游戏体验' },
    { icon: 'Camera', title: 'Pro相机系统', description: '4800万像素主摄，4倍光学变焦' },
    { icon: 'Battery', title: '续航升级', description: '视频播放最长29小时' }
  ],
  specifications: [
    { label: '显示屏', value: '6.7英寸 Super Retina XDR显示屏' },
    { label: '芯片', value: 'A17 Pro芯片' },
    { label: '存储', value: '128GB / 256GB / 512GB / 1TB' },
    { label: '后置摄像头', value: '4800万像素主摄 + 1200万超广角 + 1200万长焦' },
    { label: '前置摄像头', value: '1200万像素 TrueDepth' },
    { label: '电池', value: '视频播放最长29小时' },
    { label: '充电', value: 'USB-C, 支持快充和MagSafe无线充电' },
    { label: '尺寸', value: '159.9 x 76.7 x 8.25 mm' },
    { label: '重量', value: '221克' },
    { label: '防水', value: 'IP68' }
  ]
}
