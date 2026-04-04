<script setup lang="ts">
import { ref, reactive } from 'vue'
import { Search, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([
  {
    id: 1,
    menuName: '系统管理',
    icon: 'Setting',
    orderNum: 1,
    perms: '',
    component: 'Layout',
    menuType: 'M',
    status: 1,
    createTime: '2023-01-01 10:00:00',
    children: [
      { id: 2, menuName: '用户管理', icon: 'User', orderNum: 1, perms: 'system:user:list', component: 'system/user/index', menuType: 'C', status: 1, createTime: '2023-01-01 10:00:00' },
      { id: 3, menuName: '角色管理', icon: 'Lock', orderNum: 2, perms: 'system:role:list', component: 'system/role/index', menuType: 'C', status: 1, createTime: '2023-01-01 10:00:00' }
    ]
  },
  { id: 4, menuName: '商品管理', icon: 'Goods', orderNum: 2, perms: 'product:list', component: 'product/index', menuType: 'C', status: 1, createTime: '2023-01-02 11:30:00' },
])

const loading = ref(false)
const queryParams = reactive({
  menuName: '',
  status: ''
})

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const form = reactive({
  id: undefined,
  parentId: 0,
  menuType: 'M',
  menuName: '',
  orderNum: 0,
  icon: '',
  path: '',
  component: '',
  perms: '',
  status: 1
})

const rules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  orderNum: [{ required: true, message: '请输入显示排序', trigger: 'blur' }]
}

const handleSearch = () => {
  loading.value = true
  setTimeout(() => loading.value = false, 500)
}

const resetQuery = () => {
  queryParams.menuName = ''
  queryParams.status = ''
  handleSearch()
}

const handleAdd = (row?: any) => {
  dialogTitle.value = '添加菜单'
  Object.assign(form, {
    id: undefined,
    parentId: row ? row.id : 0,
    menuType: 'M',
    menuName: '',
    orderNum: 0,
    icon: '',
    path: '',
    component: '',
    perms: '',
    status: 1
  })
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogTitle.value = '编辑菜单'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`确认删除菜单 "${row.menuName}" 吗？`, '警告', { type: 'warning' })
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
      <el-form-item label="菜单名称">
        <el-input v-model="queryParams.menuName" placeholder="请输入菜单名称" clearable @keyup.enter="handleSearch" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="菜单状态" clearable class="!w-32">
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
      <el-button type="primary" :icon="Plus" @click="handleAdd()">新增</el-button>
    </div>

    <el-table v-loading="loading" :data="tableData" row-key="id" border :tree-props="{children: 'children', hasChildren: 'hasChildren'}" style="width: 100%">
      <el-table-column prop="menuName" label="菜单名称" width="200" />
      <el-table-column prop="icon" label="图标" align="center" width="80" />
      <el-table-column prop="orderNum" label="排序" width="60" align="center" />
      <el-table-column prop="perms" label="权限标识" />
      <el-table-column prop="component" label="组件路径" />
      <el-table-column prop="status" label="状态" align="center" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250">
        <template #default="scope">
          <el-button type="primary" link :icon="Edit" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="primary" link :icon="Plus" @click="handleAdd(scope.row)">新增</el-button>
          <el-button type="danger" link :icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="600px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="上级菜单">
          <el-tree-select
            v-model="form.parentId"
            :data="[{ id: 0, menuName: '主类目', children: tableData }]"
            :props="{ value: 'id', label: 'menuName', children: 'children' }"
            value-key="id"
            placeholder="选择上级菜单"
            check-strictly
            class="w-full"
          />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-radio-group v-model="form.menuType">
            <el-radio value="M">目录</el-radio>
            <el-radio value="C">菜单</el-radio>
            <el-radio value="F">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="显示排序" prop="orderNum">
          <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="路由地址" prop="path" v-if="form.menuType !== 'F'">
          <el-input v-model="form.path" placeholder="请输入路由地址" />
        </el-form-item>
        <el-form-item label="组件路径" prop="component" v-if="form.menuType === 'C'">
          <el-input v-model="form.component" placeholder="请输入组件路径" />
        </el-form-item>
        <el-form-item label="权限字符" prop="perms" v-if="form.menuType !== 'M'">
          <el-input v-model="form.perms" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">正常</el-radio>
            <el-radio :value="0">停用</el-radio>
          </el-radio-group>
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
