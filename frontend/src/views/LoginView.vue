<template>
  <div class="min-h-screen bg-lumina-100 pt-14 flex items-center justify-center px-4">
    <div class="w-full max-w-md">
      <div class="bg-white rounded-2xl shadow-[0_8px_40px_rgb(0,0,0,0.08)] p-10">
        <div class="text-center mb-8">
          <h1 class="text-2xl font-semibold tracking-tight text-lumina-900">
            {{ isLogin ? '欢迎回来' : '创建账户' }}
          </h1>
          <div class="flex justify-center gap-6 mt-4">
            <button
              @click="switchMode(true)"
              class="text-sm font-medium cursor-pointer transition-colors pb-1 relative"
              :class="isLogin ? 'text-lumina-900' : 'text-lumina-500 hover:text-lumina-800'"
            >
              登录
              <span
                v-if="isLogin"
                class="absolute bottom-0 left-0 right-0 h-0.5 bg-titanium"
              ></span>
            </button>
            <button
              @click="switchMode(false)"
              class="text-sm font-medium cursor-pointer transition-colors pb-1 relative"
              :class="!isLogin ? 'text-lumina-900' : 'text-lumina-500 hover:text-lumina-800'"
            >
              注册
              <span
                v-if="!isLogin"
                class="absolute bottom-0 left-0 right-0 h-0.5 bg-titanium"
              ></span>
            </button>
          </div>
        </div>

        <el-form
          ref="formRef"
          :model="isLogin ? loginForm : registerForm"
          :rules="isLogin ? loginRules : registerRules"
          @submit.prevent="handleSubmit"
        >
          <Transition name="fade" mode="out-in">
            <div :key="isLogin ? 'login' : 'register'">
              <template v-if="isLogin">
                <el-form-item prop="account">
                  <el-input
                    v-model="loginForm.account"
                    placeholder="邮箱或用户名"
                    size="large"
                    :prefix-icon="User"
                    class="custom-input"
                  />
                </el-form-item>

                <el-form-item prop="password">
                  <el-input
                    v-model="loginForm.password"
                    :type="showPassword ? 'text' : 'password'"
                    placeholder="密码"
                    size="large"
                    :prefix-icon="Lock"
                    class="custom-input"
                  >
                    <template #suffix>
                      <el-icon
                        class="cursor-pointer text-lumina-400 hover:text-lumina-600 transition-colors"
                        @click="showPassword = !showPassword"
                      >
                        <View v-if="showPassword" />
                        <Hide v-else />
                      </el-icon>
                    </template>
                  </el-input>
                </el-form-item>

                <div class="flex items-center justify-between mb-6">
                  <el-checkbox v-model="loginForm.rememberMe">记住我</el-checkbox>
                  <a href="#" class="text-sm text-lumina-500 hover:text-lumina-800 transition-colors">
                    忘记密码？
                  </a>
                </div>

                <el-form-item>
                  <el-button
                    type="primary"
                    size="large"
                    class="w-full rounded-xl"
                    :loading="loading"
                    @click="handleLogin"
                  >
                    登录
                  </el-button>
                </el-form-item>
              </template>

              <template v-else>
                <el-form-item prop="username">
                  <el-input
                    v-model="registerForm.username"
                    placeholder="用户名"
                    size="large"
                    :prefix-icon="User"
                    class="custom-input"
                  />
                </el-form-item>

                <el-form-item prop="email">
                  <el-input
                    v-model="registerForm.email"
                    placeholder="邮箱地址"
                    size="large"
                    :prefix-icon="Message"
                    class="custom-input"
                  />
                </el-form-item>

                <el-form-item prop="password">
                  <el-input
                    v-model="registerForm.password"
                    :type="showPassword ? 'text' : 'password'"
                    placeholder="密码"
                    size="large"
                    :prefix-icon="Lock"
                    class="custom-input"
                  >
                    <template #suffix>
                      <el-icon
                        class="cursor-pointer text-lumina-400 hover:text-lumina-600 transition-colors"
                        @click="showPassword = !showPassword"
                      >
                        <View v-if="showPassword" />
                        <Hide v-else />
                      </el-icon>
                    </template>
                  </el-input>
                </el-form-item>

                <el-form-item prop="confirmPassword">
                  <el-input
                    v-model="registerForm.confirmPassword"
                    :type="showPassword ? 'text' : 'password'"
                    placeholder="确认密码"
                    size="large"
                    :prefix-icon="Lock"
                    class="custom-input"
                  />
                </el-form-item>

                <el-form-item>
                  <el-button
                    type="primary"
                    size="large"
                    class="w-full rounded-xl"
                    :loading="loading"
                    @click="handleRegister"
                  >
                    注册
                  </el-button>
                </el-form-item>
              </template>
            </div>
          </Transition>
        </el-form>

        <div class="my-6 h-px bg-lumina-200"></div>

        <div class="flex items-center gap-4 my-6">
          <div class="flex-1 h-px bg-lumina-200"></div>
          <span class="text-xs text-lumina-400">或</span>
          <div class="flex-1 h-px bg-lumina-200"></div>
        </div>

        <div class="space-y-3">
          <button class="w-full rounded-xl border border-lumina-200 py-2.5 flex items-center justify-center gap-2 hover:bg-lumina-50 transition-colors text-sm font-medium text-lumina-700">
            微信登录
          </button>
          <button class="w-full rounded-xl border border-lumina-200 py-2.5 flex items-center justify-center gap-2 hover:bg-lumina-50 transition-colors text-sm font-medium text-lumina-700">
            Apple登录
          </button>
        </div>

        <p class="text-center text-sm text-lumina-500 mt-6">
          {{ isLogin ? '还没有账号？' : '已有账号？' }}
          <button
            @click="switchMode(!isLogin)"
            class="font-medium text-lumina-800 hover:text-lumina-900 transition-colors"
          >
            {{ isLogin ? '立即注册' : '立即登录' }}
          </button>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { User, Lock, Message, View, Hide } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const isLogin = ref(true)
const loading = ref(false)
const showPassword = ref(false)
const formRef = ref<FormInstance>()

const loginForm = reactive({
  account: '',
  password: '',
  rememberMe: false
})

const registerForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const loginRules: FormRules = {
  account: [{ required: true, message: '请输入账号', trigger: ['blur', 'change'] }],
  password: [{ required: true, message: '请输入密码', trigger: ['blur', 'change'] }]
}

const registerRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: ['blur', 'change'] },
    { min: 2, max: 20, message: '用户名长度为2-20个字符', trigger: ['blur', 'change'] },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含字母、数字、下划线', trigger: ['blur', 'change'] }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: ['blur', 'change'] },
    { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
    { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: ['blur', 'change'] },
    { pattern: /^(?=.*[a-zA-Z])(?=.*\d).+$/, message: '密码必须包含字母和数字', trigger: ['blur', 'change'] }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: ['blur', 'change'] },
    {
      validator: (_rule: any, value: string, callback: Function) => {
        if (value !== registerForm.password) {
          callback(new Error('两次密码不一致'))
        } else {
          callback()
        }
      }, trigger: ['blur', 'change']
    }
  ]
}

function switchMode(login: boolean) {
  isLogin.value = login
  formRef.value?.clearValidate()
}

async function handleLogin() {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await userStore.login({
      account: loginForm.account,
      password: loginForm.password
    })
    ElMessage.success('登录成功')
    router.push('/')
  } catch (_error: any) {
  } finally {
    loading.value = false
  }
}

async function handleRegister() {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await userStore.register({
      username: registerForm.username,
      email: registerForm.email,
      password: registerForm.password
    })
    ElMessage.success('注册成功')
    router.push('/')
  } catch (_error: any) {
  } finally {
    loading.value = false
  }
}

function handleSubmit() {
  if (isLogin.value) {
    handleLogin()
  } else {
    handleRegister()
  }
}
</script>

<style scoped>
.custom-input :deep(.el-input__wrapper) {
  border-radius: 12px;
  background-color: #fafafa;
  box-shadow: 0 0 0 1px #e8e8ed inset;
  transition: all 0.2s;
}

.custom-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #d2d2d7 inset;
}

.custom-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #86868b inset, 0 0 0 2px rgba(134, 134, 139, 0.1);
}

.custom-input :deep(.el-input__inner) {
  color: #1d1d1f;
}

.custom-input :deep(.el-input__inner::placeholder) {
  color: #d2d2d7;
}

.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter-from {
  opacity: 0;
  transform: translateY(8px);
}

.fade-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}
</style>
