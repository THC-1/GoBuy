export type { HeroData, ProductCardData, SkuGroup, SkuOption, FeatureItem, SpecItem, ProductDetailVO } from './product'
export type { UserVO, LoginParams, RegisterParams, LoginVO, ApiResponse } from './user'

export interface PageParams {
  pageNum: number
  pageSize: number
}

export interface PageResult<T> {
  list: T[]
  total: number
  pageNum: number
  pageSize: number
  totalPages: number
}
