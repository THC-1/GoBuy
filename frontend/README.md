# GoBuy 商城前端开发与对接说明文档

本文档旨在为后续的**前端维护人员**以及**后端开发人员**提供本项目（GoBuy商城）前端部分的架构说明、路由结构、美学规范以及接口对接指南。

---

## 1. 技术栈与基础架构

- **核心框架**：Vue 3 (Composition API, `<script setup>`)
- **路由管理**：Vue Router 4
- **状态管理**：Pinia (目前预留，尚未重度使用，后续购物车/用户信息可迁入)
- **UI 组件库**：Element Plus (按需自动引入)
- **CSS 引擎**：Tailwind CSS v4 (原子化 CSS)
- **构建工具**：Vite

### 1.1 目录结构简析
```text
frontend/
├── src/
│   ├── assets/        # 静态资源（图片、全局图标等）
│   ├── layouts/       # 基础布局层
│   │   ├── AdminLayout.vue   # B端后台管理布局（带侧边栏与顶部导航）
│   │   └── ClientLayout.vue  # C端商城前台布局（带前台Header和Footer）
│   ├── router/        # 路由配置目录 (index.ts 包含所有页面映射)
│   ├── styles/        # 全局样式
│   │   └── index.css  # Tailwind 引入、CSS变量定义与 Element Plus 主题重写
│   ├── views/         # 页面视图组件
│   │   ├── admin/     # B端后台所有页面（登录、系统设置、商品中心）
│   │   └── client/    # C端商城所有页面（首页、详情、购物车、结算、个人中心）
│   ├── App.vue        # 根组件
│   └── main.ts        # 全局入口文件
├── package.json       # 项目依赖
└── vite.config.ts     # Vite 与按需引入插件配置
```

---

## 2. 美学与样式规范 (⚠️ 核心约束)

本项目严格遵循**“自然、优雅、清新”**的设计基调。

- **主色调**：大地色系（`earth`）、莫兰迪绿（`morandi-green`）。
- **设计禁忌**：**系统内严禁出现任何紫色（Purple）及紫色渐变**。
- **主题实现**：
  - 在 `src/styles/index.css` 中重写了 Element Plus 的全局 CSS 变量（如 `--el-color-primary` 被替换为大地棕/莫兰迪绿）。
  - Tailwind CSS 也配置了对应的自定义颜色值（`bg-earth-500` 等）。
  - 维护人员新增页面时，请直接使用 Tailwind 提供的原子类（如阴影 `shadow-md`、圆角 `rounded-lg`、色彩 `text-earth-800`），避免写死冗余的 Hex 颜色代码。

---

## 3. 路由设计与页面划分

系统分为两大部分，路由入口在 `src/router/index.ts`：

### 3.1 B端后台管理系统 (`/admin/*`)
采用 `AdminLayout.vue` 作为嵌套容器（登录页除外）。
- `/admin/login`：管理员登录页（含验证码）。
- `/admin` (Dashboard)：后台数据仪表盘。
- `/admin/system/user`：用户管理（列表、新增/编辑、状态启停）。
- `/admin/system/role`：角色管理（RBAC核心）。
- `/admin/system/menu`：菜单/权限管理（树形结构表格）。
- `/admin/system/dict`：数据字典管理。
- `/admin/product/category`：商品分类管理（三级树形组件）。
- `/admin/product/attribute`：属性/规格模板管理。
- `/admin/product/spusku`：商品SPU与动态SKU管理（复杂表单与表格联动）。

### 3.2 C端商城系统 (`/*`)
采用 `ClientLayout.vue` 作为嵌套容器（登录注册页除外）。
- `/login` & `/register`：C端用户登录/注册页。
- `/` (Home)：商城首页（Banner、商品推荐网格）。
- `/product/:id`：商品详情页（图文展示、动态SKU多维选择、评价展示）。
- `/cart`：购物车页（商品勾选、数量增减、实时总价计算）。
- `/checkout`：订单结算确认页（收货地址选择、商品清单、模拟支付状态流转）。
- `/user` (UserLayout)：个人中心父路由。
  - `/user/profile`：个人资料修改。
  - `/user/address`：收货地址管理。
  - `/user/favorites`：我的收藏夹。

---

## 4. 后端对接指南 (API Integration Guide)

目前前端页面中的所有数据均为**前端 Mock（模拟数据）**，通过 Vue 的 `ref` 或 `reactive` 硬编码在各个 `.vue` 视图组件的 `<script setup>` 中。后端开发人员需要进行以下替换工作：

### 4.1 网络请求封装
建议前端维护人员在 `src/utils/request.ts` 中引入 `axios`，并配置全局请求拦截器（携带 JWT Token）与响应拦截器（统一处理 401、403、500 错误及格式化后端统一返回体 `Result<T>`）。

### 4.2 重点对接模块说明

#### A. 认证与鉴权模块 (Auth)
- **接口**：后台登录 (`/api/admin/login`)、前台登录/注册 (`/api/user/login`, `/api/user/register`)。
- **机制**：需返回 JWT Token。前端获取 Token 后需存入 `localStorage` 或 Pinia，并在后续请求的 Header 中携带 `Authorization: Bearer <token>`。

#### B. 动态 SKU 模块 (复杂难点)
- **前端现状**：在 `/admin/product/spusku` 中，使用表格展开行的方式模拟了 SPU 下挂载 SKU 的逻辑。
- **后端需提供**：
  1. 获取属性模板及其规格值的接口。
  2. 提交 SPU 基本信息、详情富文本、主图的接口。
  3. **SKU笛卡尔积生成**：后端需支持接收由多个规格组合生成的 SKU 列表（包含各 SKU 的价格、库存、图片），或者前端在提交时将完整的 SKU 组合 JSON 数组一并提交给后端保存。
  4. 商品详情页 (`/product/:id`) 中，后端需要返回该 SPU 下所有的规格列表（用于渲染按钮）以及所有的 SKU 列表（用于前端匹配用户点击的规格组合并带出对应的价格和库存）。

#### C. 购物车与订单流转 (Cart & Order)
- **购物车**：需支持按 `sku_id` 将商品加入购物车。购物车列表接口需返回商品的最新价格、库存状态（是否下架/缺货）。
- **订单提交 (防超卖/并发)**：
  - 前端在 `/checkout` 页面点击“提交订单”时，会将选中的收货地址 ID 和购物车/直接购买的 `sku_id` + `数量` 传给后端。
  - 后端需要处理并发逻辑（乐观锁/Redis扣减等），生成全局唯一的订单号，并返回给前端。
- **模拟支付**：在前端点击支付后，需调用后端的支付回调模拟接口（如 `/api/order/mock-pay`），后端接收后修改订单状态为“已支付/待发货”。

### 4.3 数据对接步骤示例
1. 找到对应的 `.vue` 页面（例如 `src/views/admin/system/UserView.vue`）。
2. 将硬编码的 `const tableData = ref([...])` 清空为 `ref([])`。
3. 引入对应的 API 请求函数（如 `import { getUserPage } from '@/api/system'`）。
4. 在 `onMounted` 钩子中发起请求，并将返回的数据赋值给 `tableData.value`。
5. 联调分页（`currentPage`, `pageSize`）与搜索表单的查询条件传递。

---

## 5. 常用命令

```bash
# 安装依赖
npm install

# 启动本地开发服务器 (默认 http://localhost:5173)
npm run dev

# 执行 TypeScript 类型检查与生产环境打包
npm run build

# 运行代码格式化与 Lint 校验
npm run lint
```