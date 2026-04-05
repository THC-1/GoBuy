import { defineStore } from 'pinia'
import { login as apiLogin, register as apiRegister, refreshToken as apiRefreshToken, getUserInfo, logout as apiLogout } from '@/api/auth'
import type { LoginParams, RegisterParams, UserVO } from '@/types'

interface UserState {
  token: string | null
  refreshToken: string | null
  userInfo: UserVO | null
  isLoggedIn: boolean
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: null,
    refreshToken: null,
    userInfo: null,
    isLoggedIn: false
  }),

  actions: {
    async login(params: LoginParams) {
      const result = await apiLogin(params)
      this.token = result.accessToken
      this.refreshToken = result.refreshToken
      this.userInfo = result.user
      this.isLoggedIn = true
      this.syncToStorage()
      return result
    },

    async register(params: RegisterParams) {
      await apiRegister(params)
      const result = await apiLogin({ account: params.email, password: params.password })
      this.token = result.accessToken
      this.refreshToken = result.refreshToken
      this.userInfo = result.user
      this.isLoggedIn = true
      this.syncToStorage()
      return result
    },

    async logout() {
      try {
        await apiLogout()
      } catch {
        // ignore error
      }
      this.token = null
      this.refreshToken = null
      this.userInfo = null
      this.isLoggedIn = false
      localStorage.removeItem('access_token')
      localStorage.removeItem('refresh_token')
      localStorage.removeItem('user_info')
    },

    async fetchUserInfo() {
      const userInfo = await getUserInfo()
      this.userInfo = userInfo
      localStorage.setItem('user_info', JSON.stringify(userInfo))
    },

    async refreshTokenAction() {
      if (!this.refreshToken) return
      const result = await apiRefreshToken(this.refreshToken)
      this.token = result.accessToken
      this.refreshToken = result.refreshToken
      this.syncToStorage()
    },

    loadFromStorage() {
      const token = localStorage.getItem('access_token')
      const refreshToken = localStorage.getItem('refresh_token')
      const userInfoStr = localStorage.getItem('user_info')

      if (token) {
        this.token = token
        this.refreshToken = refreshToken
        this.userInfo = userInfoStr ? JSON.parse(userInfoStr) : null
        this.isLoggedIn = true
      }
    },

    syncToStorage() {
      if (this.token) {
        localStorage.setItem('access_token', this.token)
      }
      if (this.refreshToken) {
        localStorage.setItem('refresh_token', this.refreshToken)
      }
      if (this.userInfo) {
        localStorage.setItem('user_info', JSON.stringify(this.userInfo))
      }
    }
  }
})
