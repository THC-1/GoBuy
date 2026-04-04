<template>
  <div class="attribute-container">
    <el-card class="box-card" shadow="never">
      <template #header>
        <div class="card-header flex justify-between items-center">
          <span class="text-lg font-medium text-gray-800">属性/规格模板管理</span>
          <el-button type="primary" :icon="Plus" @click="handleAddTemplate">新增模板</el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <div class="search-bar mb-6 flex gap-4">
        <el-input
          v-model="searchQuery"
          placeholder="搜索模板名称"
          class="w-64"
          clearable
          :prefix-icon="Search"
        />
        <el-button type="primary" plain @click="handleSearch">搜索</el-button>
      </div>

      <!-- 模板列表 -->
      <el-table :data="tableData" border style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" align="center" />
        <el-table-column prop="name" label="模板名称" min-width="150" />
        <el-table-column prop="attrCount" label="属性数量" width="120" align="center">
          <template #default="{ row }">
            <el-tag type="info" effect="plain">{{ row.attrCount }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="specCount" label="规格数量" width="120" align="center">
          <template #default="{ row }">
            <el-tag type="success" effect="plain">{{ row.specCount }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" align="center" />
        <el-table-column label="操作" width="280" align="center" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleEditAttrs(row)">
              属性列表
            </el-button>
            <el-button link type="success" size="small" @click="handleEditSpecs(row)">
              规格列表
            </el-button>
            <el-button link type="warning" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-popconfirm title="确定删除此模板吗？" @confirm="handleDelete(row)">
              <template #reference>
                <el-button link type="danger" size="small">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="flex justify-end mt-4">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        />
      </div>
    </el-card>

    <!-- 模板弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogType === 'add' ? '新增模板' : '编辑模板'" width="500px">
      <el-form :model="templateForm" label-width="100px" class="pr-6">
        <el-form-item label="模板名称" required>
          <el-input v-model="templateForm.name" placeholder="例如：手机规格模板"></el-input>
        </el-form-item>
        <el-form-item label="备注说明">
          <el-input type="textarea" v-model="templateForm.remark" :rows="3" placeholder="请输入备注"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Plus, Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

const tableData = ref([
  { id: 1, name: '手机规格模板', attrCount: 5, specCount: 3, createTime: '2023-10-01 10:00:00' },
  { id: 2, name: '服装规格模板', attrCount: 2, specCount: 2, createTime: '2023-10-02 11:30:00' },
  { id: 3, name: '家电规格模板', attrCount: 8, specCount: 1, createTime: '2023-10-03 14:15:00' },
])

const dialogVisible = ref(false)
const dialogType = ref<'add' | 'edit'>('add')
const templateForm = ref({
  id: 0,
  name: '',
  remark: ''
})

const handleSearch = () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
    ElMessage.success('搜索完成')
  }, 500)
}

const handleAddTemplate = () => {
  dialogType.value = 'add'
  templateForm.value = { id: 0, name: '', remark: '' }
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogType.value = 'edit'
  templateForm.value = { id: row.id, name: row.name, remark: '模板备注' }
  dialogVisible.value = true
}

const handleEditAttrs = (row: any) => {
  ElMessage.info(`查看模板 ${row.name} 的属性列表`)
}

const handleEditSpecs = (row: any) => {
  ElMessage.info(`查看模板 ${row.name} 的规格列表`)
}

const handleDelete = (row: any) => {
  ElMessage.success(`删除模板 ${row.name} 成功`)
}

const handleSave = () => {
  if (!templateForm.value.name) {
    ElMessage.warning('模板名称不能为空')
    return
  }
  ElMessage.success(dialogType.value === 'add' ? '新增成功' : '修改成功')
  dialogVisible.value = false
}
</script>

<style scoped>
.attribute-container {
  padding: 16px;
  background-color: transparent;
}
.box-card {
  border-radius: 8px;
  border: 1px solid #ebeef5;
}
</style>
