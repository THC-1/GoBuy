<script setup lang="ts">
import { ref, reactive } from 'vue'
import { Search, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([
  { id: 1, roleName: '超级管理员', roleKey: 'admin', status: 1, createTime: '2023-01-01 10:00:00' },
  { id: 2, roleName: '普通用户', roleKey: 'user', status: 1, createTime: '2023-01-02 11:30:00' },
  { id: 3, roleName: '访客', roleKey: 'guest', status: 0, createTime: '2023-01-03 14:20:00' },
])

const loading = ref(false)
const total = ref(3)
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  roleName: '',
  status: ''
})

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const form = reactive({
  id: undefined,
  roleName: '',
  roleKey: '',
  status: 1,
  remark: ''
})

const rules = {
  roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  roleKey: [{ required: true, message: '请输入权限字符', trigger: 'blur' }]
}

const handleSearch = () => {
  loading.value = true
  setTimeout(() => loading.value = false, 500)
}

const resetQuery = () => {
  queryParams.roleName = ''
  queryParams.status = ''
  handleSearch()
}

const handleAdd = () => {
  dialogTitle.value = '添加角色'
  Object.assign(form, { id: undefined, roleName: '', roleKey: '', status: 1, remark: '' })
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogTitle.value = '编辑角色'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`确认删除角色 "${row.roleName}" 吗？`, '警告', { type: 'warning' })
    .then(() => ElMessage.success('删除成功'))
    .catch(() => {})
}

const submitForm = () => {
  if (!formRef.value) return
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success(form.id ? '修改成功' : '添加成功')
      dialogVisible.value = false
    }
  })
}
</script>

<template>
  <div class="bg-white p-4 rounded-lg shadow-sm">
    <el-form :model="queryParams" ref="queryRef" :inline="true" class="mb-4">
      <el-form-item label="角色名称">
        <el-input v-model="queryParams.roleName" placeholder="请输入角色名称" clearable @keyup.enter="handleSearch" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="角色状态" clearable class="!w-32">
          <el-option label="正常" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="mb-4">
      <el-button type="primary" :icon="Plus" @click="handleAdd">新增</el-button>
    </div>

    <el-table v-loading="loading" :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="60" align="center" />
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleKey" label="权限字符" />
      <el-table-column prop="status" label="状态" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button type="primary" link :icon="Edit" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" link :icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="mt-4 flex justify-end">
      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="() => handleSearch()"
        @current-change="() => handleSearch()"
      />
    </div>

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="权限字符" prop="roleKey">
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">正常</el-radio>
            <el-radio :value="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
