import request from '@/utils/request'
import type { LoginParams, RegisterParams, LoginVO, UserVO } from '@/types'

export function login(data: LoginParams): Promise<LoginVO> {
  return request.post('/auth/login', data)
}

export function register(data: RegisterParams): Promise<void> {
  return request.post('/auth/register', data)
}

export function refreshToken(refreshTokenValue: string): Promise<LoginVO> {
  return request.post('/auth/refresh-token', { refreshToken: refreshTokenValue })
}

export function getUserInfo(): Promise<UserVO> {
  return request.get('/users/me')
}

export function logout(): Promise<void> {
  return request.post('/auth/logout')
}
