<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const loginForm = ref({
  username: '',
  password: ''
})

const handleLogin = () => {
  // Mock login logic
  console.log('Login attempt', loginForm.value)
  router.push('/')
}
</script>

<template>
  <div class="min-h-screen bg-[var(--el-bg-color-page)] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full bg-white rounded-2xl shadow-sm border border-earth-200 p-8 space-y-8">
      <div>
        <h2 class="mt-2 text-center text-3xl font-extrabold text-earth-800">
          登录 GoBuy
        </h2>
        <p class="mt-2 text-center text-sm text-earth-500">
          或者
          <router-link to="/register" class="font-medium text-primary hover:text-earth-700 transition-colors">
            注册新账号
          </router-link>
        </p>
      </div>
      <form class="mt-8 space-y-6" @submit.prevent="handleLogin">
        <div class="space-y-4">
          <div>
            <label for="username" class="block text-sm font-medium text-earth-700">用户名</label>
            <el-input
              id="username"
              v-model="loginForm.username"
              placeholder="请输入用户名"
              size="large"
              class="mt-1"
            />
          </div>
          <div>
            <label for="password" class="block text-sm font-medium text-earth-700">密码</label>
            <el-input
              id="password"
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              show-password
              size="large"
              class="mt-1"
            />
          </div>
        </div>

        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <el-checkbox>记住我</el-checkbox>
          </div>

          <div class="text-sm">
            <a href="#" class="font-medium text-primary hover:text-earth-700 transition-colors">
              忘记密码？
            </a>
          </div>
        </div>

        <div>
          <el-button type="primary" size="large" class="w-full" @click="handleLogin">
            登 录
          </el-button>
        </div>
      </form>
    </div>
  </div>
</template>
