<template>
  <div class="spu-container">
    <el-card class="box-card" shadow="never">
      <template #header>
        <div class="card-header flex justify-between items-center">
          <span class="text-lg font-medium text-gray-800">商品SPU管理</span>
          <el-button type="primary" :icon="Plus" @click="handleAddSpu">录入新商品</el-button>
        </div>
      </template>

      <!-- 搜索过滤 -->
      <div class="filter-container flex flex-wrap gap-4 mb-6 bg-gray-50 p-4 rounded-md">
        <el-input v-model="filters.keyword" placeholder="商品名称/SPU编码" class="w-64" clearable />
        <el-select v-model="filters.status" placeholder="上架状态" class="w-40" clearable>
          <el-option label="已上架" :value="1" />
          <el-option label="未上架" :value="0" />
        </el-select>
        <el-button type="primary" :icon="Search" @click="handleSearch">查询</el-button>
        <el-button @click="resetFilters">重置</el-button>
      </div>

      <!-- SPU列表 -->
      <el-table :data="tableData" border style="width: 100%" v-loading="loading">
        <el-table-column type="expand">
          <template #default="{ row }">
            <div class="sku-table-container p-4 bg-gray-50">
              <div class="flex justify-between items-center mb-3">
                <span class="text-sm font-bold text-gray-700">SKU 列表 (动态规格)</span>
                <el-button size="small" type="primary" plain @click="handleEditSku(row)">管理 SKU</el-button>
              </div>
              <el-table :data="row.skus" size="small" border>
                <el-table-column prop="skuCode" label="SKU编码" width="150" />
                <el-table-column prop="specs" label="规格组合" min-width="180">
                  <template #default="{ row }">
                    <el-tag v-for="(val, key) in row.specs" :key="key" size="small" class="mr-1 mb-1">
                      {{ key }}: {{ val }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="价格(元)" width="100">
                  <template #default="{ row }">
                    <span class="text-red-500 font-medium">¥{{ row.price }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="stock" label="库存" width="100" />
                <el-table-column prop="sales" label="销量" width="100" />
              </el-table>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="id" label="ID" width="80" align="center" />
        <el-table-column label="商品图片" width="100" align="center">
          <template #default="{ row }">
            <el-image
              class="w-12 h-12 rounded border border-gray-200"
              :src="row.pic"
              :preview-src-list="[row.pic]"
              fit="cover"
            />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称" min-width="200" show-overflow-tooltip />
        <el-table-column prop="categoryName" label="所属分类" width="150" />
        <el-table-column prop="brandName" label="品牌" width="120" />
        <el-table-column prop="status" label="上架状态" width="120" align="center">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
              active-text="上架"
              inactive-text="下架"
              inline-prompt
              @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" align="center" />
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleEdit(row)">
              编辑SPU
            </el-button>
            <el-popconfirm title="确定删除此商品吗？" @confirm="handleDelete(row)">
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

    <!-- SPU 录入/编辑抽屉 -->
    <el-drawer
      v-model="drawerVisible"
      :title="drawerType === 'add' ? '录入新商品' : '编辑商品'"
      size="50%"
    >
      <el-form :model="spuForm" label-width="100px" class="px-4">
        <el-divider content-position="left">基本信息</el-divider>
        <el-form-item label="商品名称" required>
          <el-input v-model="spuForm.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="副标题">
          <el-input v-model="spuForm.subTitle" placeholder="请输入促销副标题" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="商品分类" required>
              <el-cascader
                v-model="spuForm.categoryId"
                :options="categoryOptions"
                placeholder="请选择分类"
                class="w-full"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品品牌">
              <el-select v-model="spuForm.brandId" placeholder="请选择品牌" class="w-full">
                <el-option label="苹果 (Apple)" :value="1" />
                <el-option label="华为 (HUAWEI)" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="商品主图">
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :auto-upload="false"
          >
            <img v-if="spuForm.pic" :src="spuForm.pic" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        
        <el-divider content-position="left">详情描述</el-divider>
        <el-form-item label="商品描述">
          <el-input
            v-model="spuForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入商品详情描述..."
          />
        </el-form-item>

        <el-form-item label="上架状态">
          <el-switch v-model="spuForm.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="flex justify-end pr-4 pb-4">
          <el-button @click="drawerVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSaveSpu">保存商品</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Plus, Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const filters = ref({
  keyword: '',
  status: null as number | null
})
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(100)

const categoryOptions = [
  {
    value: 1,
    label: '手机数码',
    children: [
      {
        value: 11,
        label: '智能手机',
        children: [
          { value: 111, label: '苹果' },
          { value: 112, label: '华为' }
        ]
      }
    ]
  }
]

// 模拟SPU列表数据
const tableData = ref([
  {
    id: 1001,
    name: 'Apple iPhone 15 Pro Max 钛金属 5G手机',
    pic: 'https://img14.360buyimg.com/n0/jfs/t1/214196/23/40432/79981/653f58a3F512ff317/637eb852a466a96c.jpg',
    categoryName: '手机数码 > 智能手机',
    brandName: '苹果',
    status: 1,
    sort: 100,
    skus: [
      { skuCode: 'SKU-1001-1', specs: { '颜色': '原色钛金属', '内存': '256GB' }, price: 9999, stock: 120, sales: 50 },
      { skuCode: 'SKU-1001-2', specs: { '颜色': '白色钛金属', '内存': '512GB' }, price: 11999, stock: 80, sales: 30 }
    ]
  },
  {
    id: 1002,
    name: 'HUAWEI Mate 60 Pro 卫星通话 麒麟芯片',
    pic: 'https://img14.360buyimg.com/n0/jfs/t1/211997/15/38475/88636/651b72e7F3d321151/52e92c208479bbec.jpg',
    categoryName: '手机数码 > 智能手机',
    brandName: '华为',
    status: 0,
    sort: 99,
    skus: [
      { skuCode: 'SKU-1002-1', specs: { '颜色': '雅川青', '版本': '12GB+512GB' }, price: 6999, stock: 0, sales: 200 }
    ]
  }
])

const handleSearch = () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
    ElMessage.success('查询完成')
  }, 500)
}

const resetFilters = () => {
  filters.value.keyword = ''
  filters.value.status = null
  handleSearch()
}

const handleStatusChange = (row: any) => {
  const statusStr = row.status === 1 ? '上架' : '下架'
  ElMessage.success(`商品 ${row.name} 已${statusStr}`)
}

const handleDelete = (row: any) => {
  ElMessage.success(`删除商品 ${row.name} 成功`)
}

// 抽屉状态
const drawerVisible = ref(false)
const drawerType = ref<'add' | 'edit'>('add')
const spuForm = ref({
  id: 0,
  name: '',
  subTitle: '',
  categoryId: [],
  brandId: null,
  pic: '',
  description: '',
  status: 1
})

const handleAddSpu = () => {
  drawerType.value = 'add'
  spuForm.value = {
    id: 0,
    name: '',
    subTitle: '',
    categoryId: [],
    brandId: null,
    pic: '',
    description: '',
    status: 1
  }
  drawerVisible.value = true
}

const handleEdit = (row: any) => {
  drawerType.value = 'edit'
  spuForm.value = {
    id: row.id,
    name: row.name,
    subTitle: '模拟副标题',
    categoryId: [1, 11, 111],
    brandId: row.brandName === '苹果' ? 1 : 2,
    pic: row.pic,
    description: '模拟详情描述',
    status: row.status
  }
  drawerVisible.value = true
}

const handleSaveSpu = () => {
  if (!spuForm.value.name) {
    ElMessage.warning('商品名称不能为空')
    return
  }
  ElMessage.success(drawerType.value === 'add' ? '录入商品成功' : '保存商品成功')
  drawerVisible.value = false
}

const handleEditSku = (row: any) => {
  ElMessage.info(`打开 ${row.name} 的动态SKU管理面板`)
  // 实际项目中会弹出一个全屏的弹窗或者跳转到单独的SKU管理页面
}
</script>

<style scoped>
.spu-container {
  padding: 16px;
  background-color: transparent;
}
.box-card {
  border-radius: 8px;
  border: 1px solid #ebeef5;
}
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
  object-fit: cover;
  border-radius: 6px;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
  line-height: 120px;
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
}
.avatar-uploader-icon:hover {
  border-color: var(--el-color-primary);
}
</style>
