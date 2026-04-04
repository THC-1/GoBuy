<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, Key } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loginFormRef = ref()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: '',
  captcha: '',
  remember: false
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

// 模拟验证码刷新
const captchaImg = ref('https://dummyimage.com/100x40/f0f0f0/606266&text=1234')
const refreshCaptcha = () => {
  const randomStr = Math.random().toString().slice(-4)
  captchaImg.value = `https://dummyimage.com/100x40/f0f0f0/606266&text=${randomStr}`
}

const handleLogin = () => {
  if (!loginFormRef.value) return
  loginFormRef.value.validate((valid: boolean) => {
    if (valid) {
      loading.value = true
      // Simulate API call
      setTimeout(() => {
        loading.value = false
        ElMessage.success('登录成功')
        router.push('/admin/dashboard')
      }, 1000)
    } else {
      return false
    }
  })
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8 bg-[url('https://images.unsplash.com/photo-1497215728101-856f4ea42174?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80')] bg-cover bg-center">
    <!-- Overlay -->
    <div class="absolute inset-0 bg-black/40"></div>
    
    <div class="sm:mx-auto sm:w-full sm:max-w-md relative z-10">
      <div class="text-center">
        <h2 class="mt-6 text-center text-3xl font-extrabold text-white drop-shadow-md">
          GoBuy 管理系统
        </h2>
        <p class="mt-2 text-center text-sm text-gray-200">
          请输入您的账号和密码
        </p>
      </div>

      <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div class="bg-white/95 backdrop-blur-sm py-8 px-4 shadow-xl sm:rounded-lg sm:px-10 border border-gray-100">
          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="rules"
            size="large"
            @keyup.enter="handleLogin"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                :prefix-icon="User"
                placeholder="用户名"
                clearable
              />
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                :prefix-icon="Lock"
                placeholder="密码"
                show-password
              />
            </el-form-item>

            <el-form-item prop="captcha">
              <div class="flex w-full gap-4">
                <el-input
                  v-model="loginForm.captcha"
                  :prefix-icon="Key"
                  placeholder="验证码"
                  class="flex-1"
                />
                <img
                  :src="captchaImg"
                  @click="refreshCaptcha"
                  class="h-10 w-28 cursor-pointer rounded border border-gray-200 object-cover hover:opacity-80 transition-opacity"
                  title="点击刷新"
                  alt="验证码"
                />
              </div>
            </el-form-item>

            <div class="flex items-center justify-between mb-6">
              <el-checkbox v-model="loginForm.remember" class="!text-gray-500">记住我</el-checkbox>
              <a href="#" class="text-sm text-blue-600 hover:text-blue-500">忘记密码?</a>
            </div>

            <el-form-item class="mb-0">
              <el-button
                type="primary"
                class="w-full !bg-blue-600 hover:!bg-blue-700 !border-none !text-white !font-medium"
                :loading="loading"
                @click="handleLogin"
              >
                登 录
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 覆盖 Element Plus 输入框的默认样式，使其更符合现代设计 */
:deep(.el-input__wrapper) {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05) !important;
  background-color: #f9fafb !important;
  border: 1px solid transparent !important;
  transition: all 0.2s ease !important;
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2) !important;
  border-color: #3b82f6 !important;
  background-color: #ffffff !important;
}
</style>
