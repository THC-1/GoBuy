# Lumina 数码商城一期工程 — Spec

## Why

搭建Lumina高端数码商城的C端MVP基础框架，建立用户认证体系与商品浏览体验的核心链路。当前项目仅有空壳骨架（Spring Boot + Vue 3脚手架），需要从零实现用户模块、首页、商品详情页三大核心功能。

## What Changes

### 后端新增
- 添加 MyBatis-Plus、MySQL、JWT、Spring Security、Redis、Lombok 依赖
- 创建 user 数据表及实体类
- 实现注册/登录/刷新Token/获取用户信息/退出登录 5个RESTful API
- 实现 JWT 双Token认证机制（Access Token 2h + Refresh Token 7d）
- 统一响应包装 Result<T>、全局异常处理、业务异常体系
- CORS跨域配置、Security配置（放行公开接口）

### 前端新增
- 安装 Element Plus、Tailwind CSS v4、Axios、VueUse 依赖
- 搭建全局样式体系（Lumina色彩变量 + Element Plus主题覆盖 + 字体排印）
- 实现 ClientLayout 全局布局（Glassmorphism Header + Footer）
- 实现登录/注册页面（AuthForm组件，支持模式切换）
- 实现 Pinia 用户状态管理（Token持久化 + 自动续期）
- 实现 Axios 封装（请求/响应拦截器 + 401自动刷新）
- 实现路由守卫（未登录重定向）
- 实现首页（Hero沉浸式Banner + Bento Box网格 + 特色服务横幅）
- 实现商品详情页（画廊 + SKU选择器 + 明暗交替区块 + 吸顶购买栏）
- 所有前端页面严格遵循 Lumina 设计风格指南

### Impact
- Affected specs: 无（全新项目首次规格）
- Affected code: backend/pom.xml, frontend/package.json, 全部新增代码

---

## ADDED Requirements

### Requirement: SQL脚本文件管理规范
The system SHALL store all database SQL scripts in a dedicated `sql/` directory at project root level, with execution-order-prefixed naming convention.

#### Scene: SQL文件存放规则
- **WHEN** 需要创建或修改数据库结构/数据
- **THEN** 所有SQL语句必须独立存放在项目根目录的 `sql/` 文件夹下，文件名格式为 `序号_描述.sql`（如 `01_create_user_table.sql`、`02_insert_init_data.sql`），序号代表执行顺序，由用户自行执行

#### Scene: SQL文件内容要求
- **WHEN** 创建SQL脚本文件
- **THEN** 每个SQL文件仅包含一个逻辑单元的DDL/DML语句，文件头部包含注释说明用途和执行前提条件

### Requirement: 后端用户认证系统
The system SHALL provide a secure JWT-based authentication API with registration, login, token refresh, and logout capabilities.

#### Scenario: 用户成功注册
- **WHEN** 用户提交合法的注册信息（username/email/password符合校验规则）且用户名和邮箱未被占用
- **THEN** 系统创建用户记录（密码BCrypt加密），返回201状态码及包含 accessToken、refreshToken、userInfo 的响应体

#### Scenario: 用户名或邮箱已存在
- **WHEN** 用户提交注册请求但 username 或 email 已存在于数据库
- **THEN** 系统返回409 Conflict状态码及明确的错误提示信息

#### Scenario: 用户成功登录
- **WHEN** 用户提交正确的 account（邮箱或用户名）和 password，且账号状态为正常
- **THEN** 系统验证密码后生成双Token对，更新 last_login_time，返回200及登录响应

#### Scene: 账号密码错误
- **WHEN** 用户提交的 password 与数据库中 BCrypt 哈希不匹配
- **THEN** 系统返回401 Unauthorized

#### Scene: 账号被禁用
- **WHEN** 用户账号 status = 0（禁用）
- **THEN** 系统返回403 Forbidden

#### Scene: Token刷新
- **WHEN** 前端携带有效的 Refresh Token 请求 /api/v1/auth/refresh-token
- **THEN** 系统验证Refresh Token合法性，返回新的 accessToken + refreshToken 对

#### Scene: 获取当前用户信息
- **WHEN** 已登录用户携带有效 AccessToken 请求 GET /api/v1/users/me
- **THEN** 系统解析Token中的userId，查询并返回 UserVO（不含password等敏感字段）

#### Scene: 退出登录
- **WHEN** 已登录用户请求 POST /api/v1/auth/logout
- **THEN** 系统清除服务端Token缓存（Redis），返回200

### Requirement: 前端登录注册页面
The system SHALL provide a unified login/register page following Lumina design specifications.

#### Scene: 登录模式渲染
- **WHEN** 用户访问 /login 路由且 isLogin=true
- **THEN** 页面展示 account 输入框、password 输入框（带可见性切换）、记住我复选框、忘记密码链接、登录按钮

#### Scene: 注册模式渲染
- **WHEN** 用户切换到注册模式（isLogin=false）
- **THEN** 页面额外展示 username 输入框和 confirmPassword 输入框，按钮文字变为"注册"

#### Scene: 表单实时校验
- **WHEN** 用户在输入框中输入内容并触发 blur 或 change 事件
- **THEN** 系统按规则实时校验：username(2-20字符字母数字下划线)、email(合法格式)、password(6-20字符含字母数字)、confirmPassword(一致性)，显示对应错误提示

#### Scene: 注册成功后的状态管理
- **WHEN** 用户提交注册表单且后端返回201
- **THEN** Pinia Store 存储 token/refreshToken/userInfo，设置 isLoggedIn=true，localStorage备份，跳转首页

### Requirement: 前端全局布局与导航
The system SHALL provide a ClientLayout with Glassmorphism Header and dark Footer per Lumina design specs.

#### Scene: Header毛玻璃效果
- **WHEN** 页面处于顶部（scrollY=0）
- **THEN** Header背景完全透明（bg-transparent）；当 scrollY > 0 时变为 bg-lumina-100/80 backdrop-blur-xl，过渡时长500ms

#### Scene: 登录态Header显示
- **WHEN** 用户已登录（isLoggedIn=true）
- **THEN** 右侧操作区显示圆形用户头像（w-8 h-8 rounded-full），隐藏"登录"按钮

#### Scene: 未登录态Header显示
- **WHEN** 用户未登录
- **THEN** 右侧操作区显示"登录"按钮（text-xs font-medium rounded-full border），隐藏头像

#### Scene: 移动端菜单
- **WHEN** 屏幕宽度 < 768px 且用户点击汉堡图标
- **THEN** 右侧滑出 MobileMenuDrawer（w-72），带半透明遮罩层（bg-lumina-950/40 backdrop-blur-sm）

#### Scene: Footer渲染
- **WHEN** 用户滚动至页面底部
- **THEN** Footer以深色背景（bg-lumina-900）展示4列链接区域（产品/服务/关于/支持）+ 版权声明

### Requirement: 首页Hero Banner区
The system SHALL provide an immersive Hero section with product showcase per Lumina design §6.1.A.

#### Scene: Hero区完整渲染
- **WHEN** 用户访问首页首屏
- **THEN** 渲染85vh高度暗色渐变背景（#0a0a0a → #1a1a1c），居中显示产品大标题（text-5xl~8xl font-bold tracking-tight text-white）、70%透明度副标题、白底主CTA按钮+描边次按钮、底部产品浮动图（±10px 6s周期循环动画）、滚动弹跳指示器

#### Scene: Hero入场动画
- **WHEN** Hero区进入视口
- **THEN** 标题/副标题/按钮依次淡入上移（staggered animation，延迟0ms/200ms/400ms），曲线为 cubic-bezier(0.16, 1, 0.3, 1)

### Requirement: 首页Bento Box商品网格
The system SHALL provide an asymmetric Bento Grid product display per Lumina design §6.1.B.

#### Scene: 桌面端Bento Grid布局
- **WHEN** 屏幕宽度 ≥ 1024px
- **THEN** 渲染4列等高行（auto-rows-[280px]）网格：旗舰产品占2x2（col-span-2 row-span-2），两个1x1小区块，一个跨2列横向区块；圆角rounded-3xl

#### Scene: 移动端Bento Grid降级
- **WHEN** 屏幕宽度 < 768px
- **THEN** 所有区块单列堆叠（grid-cols-1），保持圆角和内边距

#### Scene: 商品卡片交互
- **WHEN** 用户鼠标悬停在商品卡片上
- **THEN** 卡片上移4px（translate-y-1）+ 柔和阴影放大（shadow-[0_12px_40px_rgb(0,0,0,0.08)]）+ 图片缓慢缩放（scale-105 duration-700）

#### Scene: 商品卡片点击跳转
- **WHEN** 用户点击商品卡片
- **THEN** 路由跳转至 /product/:id（商品详情页）

### Requirement: 商品详情页
The system SHALL provide an immersive product detail page with Dark/Light alternating sections per Lumina design §6.3.

#### Scene: 产品外观展示区（暗色背景）
- **WHEN** 用户进入商品详情页首屏
- **THEN** 渲染bg-lumina-950背景的双列布局：左侧产品画廊（正方形主图+64x64缩略图列表），右侧面包屑导航+产品名称（text-3xl~4xl font-semibold）+副标题+价格+SKU选择器（胶囊形按钮rounded-full）+数量选择器+CTA按钮组

#### Scene: SKU选择交互
- **WHEN** 用户点击某个SKU选项
- **THEN** 该选项反色高亮（border-white bg-white text-lumina-950），同组其他选项恢复默认态；若该SKU有imageUrl则同步切换产品主图

#### Scene: 吸顶购买栏
- **WHEN** 用户向下滚动超过首屏产品图底部
- **THEN** 从顶部滑入固定购买栏（fixed top-14 z-40 bg-white/95 backdrop-blur-lg），显示产品名（truncate）+ 价格 + 购买按钮，动画300ms cubic-bezier(0.16, 1, 0.3, 1)

#### Scene: 产品亮点介绍区（亮色背景）
- **WHEN** 用户继续滚动至第二区块
- **THEN** 渲染bg-lumina-100背景的3列等宽网格，每项含64x64图标容器（rounded-2xl bg-lumina-200）+ 标题（text-lg font-semibold lumina-800）+ 描述（text-sm lumina-500）

#### Scene: 技术参数表（深灰背景）
- **WHEN** 用户继续滚动至第三区块
- **THEN** 渲染bg-lumina-900背景的Key-Value参数列表，分割线divide-white/10，左标签text-white/60右值text-sm font-medium text-right

### Requirement: HTTP请求拦截器与Token自动续期
The system SHALL provide Axios interceptors with automatic token refresh mechanism.

#### Scene: 请求拦截注入Token
- **WHEN** 前端发起API请求
- **THEN** 请求头自动注入 Authorization: Bearer <accessToken> 和 Content-Type: application/json

#### Scene: 401自动刷新Token
- **WHEN** 后端返回401状态码
- **THEN** 拦截器使用 refreshToken 尝试获取新Token对；成功则重试原请求；失败则清除登录态，跳转/login?redirect=<currentPath>

### Requirement: 路由守卫
The system SHALL provide route guards for authentication protection.

#### Scene: 未登录访问受保护路由
- **WHEN** 未登录用户访问 requiresAuth: true 的路由
- **THEN** 自动重定向至 /login?redirect=<targetPath>

#### Scene: 已登录用户访问登录页
- **WHEN** 已登录用户访问 /login
- **THEN** 自动重定向至 /

---

## MODIFIED Requirements

（无 — 本项目为新项目，无已有需求修改）

## REMOVED Requirements

（无 — 本项目为新阶段，无需求移除）
