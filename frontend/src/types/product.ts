export interface HeroData {
  title: string
  subtitle: string
  description: string
  primaryCta: string
  secondaryCta: string
  image: string
  bgColor: string
}

export interface ProductCardData {
  id: number
  name: string
  categoryName: string
  mainImage: string
  price: number
  originalPrice?: number
  badge?: string
  gridSpan?: number
  bgVariant?: string
}

export interface SkuOption {
  value: string
  label: string
  disabled?: boolean
  imageUrl?: string
}

export interface SkuGroup {
  name: string
  options: SkuOption[]
}

export interface FeatureItem {
  icon: string
  title: string
  description: string
}

export interface SpecItem {
  label: string
  value: string
}

export interface ProductDetailVO {
  id: number
  name: string
  shortName: string
  subtitle: string
  categoryName: string
  mainImage: string
  images: string[]
  price: number
  originalPrice?: number
  stock: number
  skuGroups: SkuGroup[]
  features: FeatureItem[]
  specifications: SpecItem[]
}
