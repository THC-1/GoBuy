<script setup lang="ts">
import { ref } from 'vue'

const addresses = ref([
  {
    id: 1,
    name: '张三',
    phone: '13812345678',
    province: '广东省',
    city: '深圳市',
    district: '南山区',
    detail: '科技园路1号',
    isDefault: true
  },
  {
    id: 2,
    name: '李四',
    phone: '13987654321',
    province: '北京市',
    city: '北京市',
    district: '朝阳区',
    detail: '建国路88号',
    isDefault: false
  }
])

const dialogVisible = ref(false)
const addressForm = ref({
  name: '',
  phone: '',
  region: '',
  detail: '',
  isDefault: false
})

const handleAdd = () => {
  addressForm.value = { name: '', phone: '', region: '', detail: '', isDefault: false }
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  addressForm.value = {
    name: row.name,
    phone: row.phone,
    region: `${row.province} / ${row.city} / ${row.district}`,
    detail: row.detail,
    isDefault: row.isDefault
  }
  dialogVisible.value = true
}

const handleDelete = (id: number) => {
  console.log('Delete address', id)
}

const saveAddress = () => {
  console.log('Save address', addressForm.value)
  dialogVisible.value = false
}
</script>

<template>
  <div>
    <div class="flex items-center justify-between mb-6 border-b border-earth-100 pb-4">
      <h2 class="text-2xl font-bold text-earth-800">收货地址</h2>
      <el-button type="primary" @click="handleAdd">新增地址</el-button>
    </div>

    <div class="space-y-4">
      <div 
        v-for="item in addresses" 
        :key="item.id" 
        class="border border-earth-200 rounded-lg p-5 hover:border-earth-400 transition-colors"
      >
        <div class="flex items-start justify-between">
          <div>
            <div class="flex items-center gap-3 mb-2">
              <span class="text-lg font-bold text-earth-800">{{ item.name }}</span>
              <span class="text-earth-600">{{ item.phone }}</span>
              <el-tag v-if="item.isDefault" type="primary" size="small" effect="light" class="ml-2">默认</el-tag>
            </div>
            <p class="text-earth-600">
              {{ item.province }} {{ item.city }} {{ item.district }} {{ item.detail }}
            </p>
          </div>
          <div class="flex gap-2">
            <el-button link type="primary" @click="handleEdit(item)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(item.id)">删除</el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- Address Dialog -->
    <el-dialog v-model="dialogVisible" title="收货地址" width="500px" destroy-on-close>
      <el-form :model="addressForm" label-width="80px" class="mt-4">
        <el-form-item label="收货人">
          <el-input v-model="addressForm.name" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="addressForm.phone" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="所在地区">
          <el-input v-model="addressForm.region" placeholder="省/市/区" />
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input 
            v-model="addressForm.detail" 
            type="textarea" 
            placeholder="街道门牌、楼层房间号等信息" 
          />
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="addressForm.isDefault">设为默认收货地址</el-checkbox>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveAddress">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
