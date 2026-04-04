<script setup lang="ts">
import { ref } from 'vue'

const profileForm = ref({
  nickname: 'GoBuy 用户',
  phone: '13800138000',
  email: 'user@example.com',
  gender: 'secret',
  bio: '这个人很懒，什么都没留下。'
})

const saveProfile = () => {
  console.log('Save profile', profileForm.value)
}
</script>

<template>
  <div>
    <h2 class="text-2xl font-bold text-earth-800 mb-6 border-b border-earth-100 pb-4">资料修改</h2>
    
    <div class="max-w-2xl">
      <el-form label-position="top" size="large">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <el-form-item label="昵称">
            <el-input v-model="profileForm.nickname" placeholder="请输入昵称" />
          </el-form-item>
          
          <el-form-item label="手机号码">
            <el-input v-model="profileForm.phone" placeholder="请输入手机号码" />
          </el-form-item>
          
          <el-form-item label="邮箱地址">
            <el-input v-model="profileForm.email" placeholder="请输入邮箱地址" />
          </el-form-item>
          
          <el-form-item label="性别">
            <el-radio-group v-model="profileForm.gender">
              <el-radio value="male">男</el-radio>
              <el-radio value="female">女</el-radio>
              <el-radio value="secret">保密</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        
        <el-form-item label="个人简介" class="mt-2">
          <el-input 
            v-model="profileForm.bio" 
            type="textarea" 
            :rows="4" 
            placeholder="介绍一下你自己..." 
          />
        </el-form-item>
        
        <div class="mt-8">
          <el-button type="primary" size="large" class="w-32" @click="saveProfile">
            保存修改
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>
