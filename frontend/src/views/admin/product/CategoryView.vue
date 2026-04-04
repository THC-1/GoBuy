<template>
  <div class="category-container">
    <el-card class="box-card" shadow="never">
      <template #header>
        <div class="card-header flex justify-between items-center">
          <span class="text-lg font-medium text-gray-800">商品分类管理</span>
          <el-button type="primary" :icon="Plus" @click="handleAddRoot">添加一级分类</el-button>
        </div>
      </template>
      
      <div class="tree-container">
        <el-tree
          :data="categoryData"
          node-key="id"
          default-expand-all
          :expand-on-click-node="false"
          :props="defaultProps"
          class="custom-tree"
        >
          <template #default="{ node, data }">
            <span class="custom-tree-node flex-1 flex justify-between items-center pr-4">
              <span class="text-sm text-gray-700 font-medium">{{ node.label }}</span>
              <span class="action-group">
                <el-button
                  v-if="node.level < 3"
                  link
                  type="primary"
                  size="small"
                  @click.stop="append(data)"
                >
                  添加子分类
                </el-button>
                <el-button
                  link
                  type="info"
                  size="small"
                  @click.stop="edit(data)"
                >
                  编辑
                </el-button>
                <el-popconfirm
                  title="确认删除该分类吗？"
                  @confirm="remove(node, data)"
                  confirm-button-text="确认"
                  cancel-button-text="取消"
                >
                  <template #reference>
                    <el-button link type="danger" size="small" @click.stop>
                      删除
                    </el-button>
                  </template>
                </el-popconfirm>
              </span>
            </span>
          </template>
        </el-tree>
      </div>
    </el-card>

    <!-- 分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogType === 'add' ? '添加分类' : '编辑分类'" width="400px">
      <el-form :model="categoryForm" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="categoryForm.name" placeholder="请输入分类名称"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="categoryForm.sort" :min="0" :max="999"></el-input-number>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="categoryForm.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

interface Category {
  id: number
  name: string
  sort: number
  status: number
  children?: Category[]
}

const defaultProps = {
  children: 'children',
  label: 'name',
}

// 模拟数据
const categoryData = ref<Category[]>([
  {
    id: 1,
    name: '手机数码',
    sort: 1,
    status: 1,
    children: [
      {
        id: 11,
        name: '智能手机',
        sort: 1,
        status: 1,
        children: [
          { id: 111, name: '苹果', sort: 1, status: 1 },
          { id: 112, name: '华为', sort: 2, status: 1 },
        ],
      },
      {
        id: 12,
        name: '摄影摄像',
        sort: 2,
        status: 1,
        children: [
          { id: 121, name: '单反相机', sort: 1, status: 1 },
          { id: 122, name: '微单相机', sort: 2, status: 1 },
        ],
      },
    ],
  },
  {
    id: 2,
    name: '家用电器',
    sort: 2,
    status: 1,
    children: [
      {
        id: 21,
        name: '大家电',
        sort: 1,
        status: 1,
        children: [
          { id: 211, name: '电视', sort: 1, status: 1 },
          { id: 212, name: '空调', sort: 2, status: 1 },
        ],
      },
    ],
  },
])

const dialogVisible = ref(false)
const dialogType = ref<'add' | 'edit'>('add')
const categoryForm = ref({
  id: 0,
  name: '',
  sort: 0,
  status: 1,
  parentId: 0
})

let currentId = 1000

const handleAddRoot = () => {
  dialogType.value = 'add'
  categoryForm.value = { id: 0, name: '', sort: 0, status: 1, parentId: 0 }
  dialogVisible.value = true
}

const append = (data: Category) => {
  dialogType.value = 'add'
  categoryForm.value = { id: 0, name: '', sort: 0, status: 1, parentId: data.id }
  dialogVisible.value = true
}

const edit = (data: Category) => {
  dialogType.value = 'edit'
  categoryForm.value = { ...data, parentId: 0 } // 简单模拟
  dialogVisible.value = true
}

const remove = (node: any, data: Category) => {
  const parent = node.parent
  const children: Category[] = parent.data.children || parent.data
  const index = children.findIndex((d) => d.id === data.id)
  children.splice(index, 1)
  ElMessage.success('删除成功')
}

const handleSave = () => {
  if (!categoryForm.value.name) {
    ElMessage.warning('分类名称不能为空')
    return
  }

  if (dialogType.value === 'add') {
    const newChild: Category = {
      id: currentId++,
      name: categoryForm.value.name,
      sort: categoryForm.value.sort,
      status: categoryForm.value.status,
    }
    
    if (categoryForm.value.parentId === 0) {
      categoryData.value.push(newChild)
    } else {
      const findParent = (nodes: Category[], pid: number): Category | null => {
        for (const node of nodes) {
          if (node.id === pid) return node
          if (node.children) {
            const found = findParent(node.children, pid)
            if (found) return found
          }
        }
        return null
      }
      const parent = findParent(categoryData.value, categoryForm.value.parentId)
      if (parent) {
        if (!parent.children) parent.children = []
        parent.children.push(newChild)
      }
    }
    ElMessage.success('添加成功')
  } else {
    // 编辑逻辑简化版
    const updateNode = (nodes: Category[], id: number) => {
      for (let node of nodes) {
        if (node.id === id) {
          node.name = categoryForm.value.name
          node.sort = categoryForm.value.sort
          node.status = categoryForm.value.status
          return true
        }
        if (node.children && updateNode(node.children, id)) return true
      }
      return false
    }
    updateNode(categoryData.value, categoryForm.value.id)
    ElMessage.success('编辑成功')
  }
  dialogVisible.value = false
}
</script>

<style scoped>
.category-container {
  padding: 16px;
  background-color: transparent;
}
.box-card {
  border-radius: 8px;
  border: 1px solid #ebeef5;
}
.tree-container {
  margin-top: 10px;
}
.custom-tree {
  padding: 10px 0;
}
.custom-tree-node {
  height: 36px;
  border-bottom: 1px solid #f5f7fa;
  transition: background-color 0.2s;
}
.custom-tree-node:hover {
  background-color: #f9fafc;
}
.action-group {
  opacity: 0;
  transition: opacity 0.2s;
}
.el-tree-node__content:hover .action-group {
  opacity: 1;
}
</style>
