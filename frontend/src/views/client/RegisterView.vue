<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const registerForm = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const handleRegister = () => {
  // Mock register logic
  console.log('Register attempt', registerForm.value)
  router.push('/login')
}
</script>

<template>
  <div class="min-h-screen bg-[var(--el-bg-color-page)] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full bg-white rounded-2xl shadow-sm border border-earth-200 p-8 space-y-8">
      <div>
        <h2 class="mt-2 text-center text-3xl font-extrabold text-earth-800">
          注册新账号
        </h2>
        <p class="mt-2 text-center text-sm text-earth-500">
          已有账号？
          <router-link to="/login" class="font-medium text-primary hover:text-earth-700 transition-colors">
            立即登录
          </router-link>
        </p>
      </div>
      <form class="mt-8 space-y-6" @submit.prevent="handleRegister">
        <div class="space-y-4">
          <div>
            <label for="username" class="block text-sm font-medium text-earth-700">用户名</label>
            <el-input
              id="username"
              v-model="registerForm.username"
              placeholder="请输入用户名"
              size="large"
              class="mt-1"
            />
          </div>
          <div>
            <label for="email" class="block text-sm font-medium text-earth-700">邮箱</label>
            <el-input
              id="email"
              v-model="registerForm.email"
              type="email"
              placeholder="请输入邮箱"
              size="large"
              class="mt-1"
            />
          </div>
          <div>
            <label for="password" class="block text-sm font-medium text-earth-700">密码</label>
            <el-input
              id="password"
              v-model="registerForm.password"
              type="password"
              placeholder="设置密码"
              show-password
              size="large"
              class="mt-1"
            />
          </div>
          <div>
            <label for="confirmPassword" class="block text-sm font-medium text-earth-700">确认密码</label>
            <el-input
              id="confirmPassword"
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              show-password
              size="large"
              class="mt-1"
            />
          </div>
        </div>

        <div>
          <el-button type="primary" size="large" class="w-full" @click="handleRegister">
            注 册
          </el-button>
        </div>
      </form>
    </div>
  </div>
</template>
