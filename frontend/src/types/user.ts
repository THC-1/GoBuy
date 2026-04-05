export interface UserVO {
  id: number
  username: string
  email: string
  nickname: string
  avatarUrl: string
}

export interface LoginParams {
  account: string
  password: string
}

export interface RegisterParams {
  username: string
  email: string
  password: string
}

export interface LoginVO {
  accessToken: string
  refreshToken: string
  expiresIn: number
  user: UserVO
}

export interface ApiResponse<T = unknown> {
  code: number
  message: string
  data: T
}
