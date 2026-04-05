# Lumina 数码商城前端设计风格指南

> 本文档定义了 Lumina 高端数码商城的完整视觉设计体系。以"光影科技美学"为核心驱动力，将自然光学的优雅与现代数码产品的精密感完美融合。每一处细节都经过精心雕琢，旨在创造如苹果官网般令人难忘的沉浸式浏览体验。

---

## 一、设计哲学与视觉核心

### 1.1 三大设计支柱

**光影叙事 (Light & Shadow Narrative)**
光线是设计的灵魂。通过模拟真实世界的光照效果——从柔和的环境光到锐利的高光反射——让界面拥有如同实体产品摄影般的质感。暗色区块用于聚焦产品本身，亮色区块展现生活场景，明暗交替创造呼吸般的视觉节奏。

**材质真实 (Material Authenticity)**
拒绝扁平化的廉价感。钛金属的冷冽光泽、磨砂玻璃的朦胧透射、拉丝铝的细腻纹理——这些真实的物理材质被数字化再现，赋予界面可触摸般的品质感。每一个圆角、每一条阴影都遵循现实世界的光学规律。

**克制中的张力 (Restrained Tension)**
极简不等于简单。在极致克制的视觉框架下，通过精准的间距控制、微妙的动效反馈和富有层次的Z轴空间构建，创造出"少即是多"的高级张力。用户会在不经意间感受到设计的力量，而非被设计所干扰。

### 1.2 绝对禁止事项

- 禁止使用紫色渐变（包括任何紫色调的高饱和度渐变）
- 禁止赛博朋克风格的霓虹发光、故障艺术（Glitch）元素
- 禁止过度堆砌装饰性图形、几何图案
- 禁止使用emoji作为UI元素或装饰
- 禁止高饱和度的糖果色系
- 禁止生硬的边框线（除非是极细的分隔线）

---

## 二、技术栈与工具链

| 技术 | 版本 | 用途说明 |
|------|------|----------|
| Vue 3 | ^3.5.31 | 前端框架（Composition API + `<script setup>`） |
| Vite | ^8.0.3 | 极速构建与开发服务器 |
| Element Plus | ^2.13.6 | UI基础组件库（需深度样式定制） |
| Tailwind CSS | ^4.2.2 | 原子化CSS（微调间距、毛玻璃、阴影） |
| VueUse | ^10.9.0 | 组合式API工具集（滚动视差、IntersectionObserver） |
| TypeScript | ~6.0.0 | 类型安全 |
| GSAP | ^3.12.5 | 高性能动画库（Hero区域、视差滚动） |

**关键配置要点：**
- 使用 `@tailwindcss/vite` 插件集成Tailwind CSS v4
- 路径别名：`@` → `src/`
- GSAP仅用于关键叙事性动画，日常交互使用CSS transitions

---

## 三、色彩体系：光学质感色谱

整体色调灵感来源于自然界的光学现象：冰川的冷冽蓝、钛金属的中性灰、深海的神秘黑。所有颜色均保持低饱和度、高明度的特质，确保长时间浏览不产生视觉疲劳。

### 3.1 核心色阶：Lumina Space Tones（空间灰阶）

定义在 `src/styles/index.css` 的 `@theme` 块中：

| 变量名 | 色值 | 视觉名称 | 用途场景 |
|--------|------|----------|----------|
| `lumina-50` | `#fafafa` | 极地白 | 最高亮度背景，用于纯白区块 |
| `lumina-100` | `#f5f5f7` | 晨雾灰 | 页面主背景色，卡片底色 |
| `lumina-200` | `#e8e8ed` | 云层灰 | 次级背景、分割线、弱边框 |
| `lumina-300` | `#d2d2d7` | 薄雾灰 | 输入框边框、禁用态文字 |
| `lumina-400` | `#86868b` | 远山灰 | 次要文字、辅助说明、占位符 |
| `lumina-500` | `#6e6e73` | 中景灰 | 次要按钮、标签文字 |
| `lumina-600` | `#424245` | 近景灰 | 强调文字、重要信息 |
| `lumina-800` | `#1d1d1f` | 深空灰 | **主文本色**，标题、正文核心 |
| `lumina-900` | `#000000` | 纯黑 | 沉浸式暗色区块背景、最高对比度文字 |
| `lumina-950` | `#0a0a0a` | 暗夜黑 | Hero区域的深邃背景（非纯黑，更柔和） |

### 3.2 点缀色系：金属与光学

| 变量名 | 色值 | 名称 | 使用规则 |
|--------|------|------|----------|
| `titanium` | `#86868b` | 钛金属色 | **主品牌色**，用于主要CTA按钮、链接悬停、选中状态图标 |
| `glacier-blue` | `#64b5f6` | 冰川蓝 | 极其克制的点缀，用于超链接默认态、成功提示、科技感微光阴影 |
| `solar-gold` | `#c9a96e` | 日耀金 | 限量版标签、VIP标识、价格高亮（极少使用） |
| `arctic-white` | `#f0f4f8` | 极地冰白 | 卡片悬浮背景、毛玻璃底层 |
| `alert-crimson` | `#dc3545` | 深红 | 删除操作、库存不足、错误提示（仅功能用途） |
| `success-emerald` | `#28a745` | 翡翠绿 | 操作成功、库存充足（低饱和度版本） |

### 3.3 渐变规范（严格限制使用）

**允许使用的渐变（仅限以下三种）：**

```css
/* 1. 微妙的光影渐变 - 用于Hero背景 */
--gradient-hero-dark: linear-gradient(180deg, #0a0a0a 0%, #1a1a1c 50%, #2a2a2e 100%);

/* 2. 极淡的表面渐变 - 用于卡片悬浮效果 */
--gradient-surface: linear-gradient(135deg, rgba(255,255,255,0.9) 0%, rgba(245,245,247,0.95) 100%);

/* 3. 按钮微妙渐变 - 仅主CTA按钮 */
--gradient-cta: linear-gradient(180deg, #424245 0%, #1d1d1f 100%);
```

**绝对禁止：**
- 任何包含紫色的渐变
- 多彩彩虹渐变
- 高饱和度荧光渐变
- 从黑到白的硬切渐变（必须使用柔和过渡）

### 3.4 Element Plus 主题深度覆盖

```css
:root {
  /* 主色系统 - 钛金属色 */
  --el-color-primary: #86868b;
  --el-color-primary-light-3: #a1a1a6;
  --el-color-primary-light-5: #b8b8bd;
  --el-color-primary-light-7: #d2d2d7;
  --el-color-primary-light-9: #f5f5f7;
  --el-color-primary-dark-2: #6e6e73;

  /* 功能色 - 低饱和度处理 */
  --el-color-success: #28a745;
  --el-color-warning: #c9a96e;
  --el-color-danger: #dc3545;
  --el-color-info: #86868b;

  /* 背景色 - 极简灰白 */
  --el-bg-color: #ffffff;
  --el-bg-color-page: #f5f5f7;
  --el-bg-color-overlay: rgba(255, 255, 255, 0.95);

  /* 文字色 - 层次分明 */
  --el-text-color-primary: #1d1d1f;
  --el-text-color-regular: #424245;
  --el-text-color-secondary: #86868b;
  --el-text-color-placeholder: #d2d2d7;

  /* 边框色 - 极弱化 */
  --el-border-color: #d2d2d7;
  --el-border-color-light: #e8e8ed;
  --el-border-color-lighter: #f5f5f7;
  --el-border-color-extra-light: #fafafa;

  /* 圆角统一收敛 */
  --el-border-radius-base: 12px;
  --el-border-radius-small: 8px;
  --el-border-radius-round: 9999px;
}
```

---

## 四、字体排印系统

### 4.1 字体族（Font Family）

```css
/* 主字体栈 - 优先使用系统级高质量无衬线字体 */
font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text",
             "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
             "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
```

**选择理由：**
- `-apple-system` 和 `SF Pro` 系列是苹果官方字体，在macOS/iOS上渲染质量极高
- `PingFang SC` 是苹果为中文优化的字体，笔画清晰、现代感强
- 回退到 `Helvetica Neue` 保持跨平台一致性

### 4.2 字号与字重层级

| 层级 | Tailwind类 | 字号 | 字重 | 行高 | 颜色 | 典型用途 |
|------|-----------|------|------|------|------|----------|
| Display Hero | `text-6xl md:text-8xl` | 60px/96px | 700 (bold) | 1.05 | white / lumina-800 | 首屏大标题（如"iPhone 15 Pro"） |
| H1 Section | `text-4xl md:text-5xl` | 36px/48px | 600 (semibold) | 1.1 | lumina-800 | 区块主标题 |
| H2 Page Title | `text-3xl` | 30px | 600 (semibold) | 1.2 | lumina-800 | 页面标题 |
| H3 Module | `text-2xl` | 24px | 600 (semibold) | 1.3 | lumina-800 | 子模块标题 |
| H4 Card Title | `text-xl` | 20px | 500 (medium) | 1.4 | lumina-800 | 卡片标题、商品名 |
| Body Large | `text-lg` | 18px | 400 (normal) | 1.6 | lumina-600 | 重要段落、描述文案 |
| Body Base | `text-base` | 16px | 400 (normal) | 1.6 | lumina-600 | 正文内容 |
| Body Small | `text-sm` | 14px | 400 (normal) | 1.5 | lumina-500 | 辅助说明、表单标签 |
| Caption | `text-xs` | 12px | 500 (medium) | 1.4 | lumina-400 | 标签、时间戳、版权信息 |

### 4.3 字距与特殊排版

**字距（Letter Spacing）：**
- 大标题（Display/H1）：`tracking-tight` (-0.025em)，紧凑有力
- 导航链接：`tracking-wide` (0.025em)，优雅舒展
- 正文：默认字距，确保可读性

**特殊排版效果：**

```css
/* 渐变文字 - 仅用于Hero区主标题 */
.text-gradient-hero {
  background: linear-gradient(180deg, #ffffff 0%, rgba(255,255,255,0.7) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* 半透明文字 - 用于副标题 */
.text-subtle {
  color: rgba(255, 255, 255, 0.7);
}
```

---

## 五、核心布局架构

### 5.1 全局容器规范

```html
<!-- 页面主容器 -->
<div class="min-h-screen bg-lumina-100">
  <!-- 最大宽度1280px居中，两侧留白 -->
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <!-- 内容区域 -->
  </div>
</div>
```

**响应式断点与容器内边距：**

| 断点 | 容器max-width | 左右padding | 典型布局 |
|------|--------------|-------------|----------|
| < 640px (mobile) | 100% | 16px (px-4) | 单列堆叠 |
| ≥ 640px (sm) | 640px | 24px (px-6) | 2列网格 |
| ≥ 768px (md) | 768px | 32px (px-8) | 侧边栏+内容区 |
| ≥ 1024px (lg) | 1024px | 32px (lg:px-8) | 3列Bento Box |
| ≥ 1280px (xl) | 1280px | 32px | 最大宽度限制 |

### 5.2 客户端全局布局 — ClientLayout

#### A. 顶部导航栏（Sticky Glassmorphism Header）

这是整个界面的"灵魂组件"。采用高强度毛玻璃效果，在用户滚动时透出下方的产品图像或背景色，营造悬浮于内容之上的高级感。

```
┌─────────────────────────────────────────────────────────────┐
│ ╔═══════════════════════════════════════════════════════╗   │
│ ║  Header (fixed, h-14, backdrop-blur-xl)               ║   │
│ ║  ┌────────┐  ┌─────┬─────┬─────┬─────┐  ┌────┬──────┐ ║   │
│ ║  │ Logo   │  │首页 │商品│关于│支持│  │🔍 │购物袋│ ║   │
│ ║  └────────┘  └─────┴─────┴─────┴─────┘  └────┴──────┘ ║   │
│ ╚═══════════════════════════════════════════════════════╝   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Main Content Area                                          │
│  ┌───────────────────────────────────────────────────────┐  │
│  │                                                       │  │
│  │              <RouterView />                           │  │
│  │                                                       │  │
│  └───────────────────────────────────────────────────────┘  │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│  Footer (bg-lumina-900, text-lumina-400, py-16)            │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  多列链接导航  |  版权信息  |  社交媒体图标            │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

**Header 完整实现代码：**

```vue
<template>
  <header
    class="fixed top-0 left-0 right-0 z-50 h-14 transition-all duration-500"
    :class="scrolled ? 'bg-lumina-100/80 backdrop-blur-xl shadow-sm' : 'bg-transparent'"
  >
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-full flex items-center justify-between">
      <!-- Logo 区域 -->
      <router-link to="/" class="flex items-center gap-2 group">
        <span class="text-xl font-semibold tracking-tight text-lumina-800 group-hover:text-lumina-900 transition-colors">
          Lumina.
        </span>
      </router-link>

      <!-- 主导航 - 桌面端显示 -->
      <nav class="hidden md:flex items-center gap-8">
        <router-link
          v-for="item in navItems"
          :key="item.path"
          :to="item.path"
          class="text-xs font-medium tracking-wide text-lumina-500 hover:text-lumina-800 transition-colors duration-300 relative py-1"
          :class="{ 'text-lumina-800': isActive(item.path) }"
        >
          {{ item.label }}
          <!-- 悬停下划线动画 -->
          <span
            class="absolute bottom-0 left-0 w-0 h-[1.5px] bg-titanium transition-all duration-300"
            :class="{ 'w-full': isActive(item.path) }"
          ></span>
        </router-link>
      </nav>

      <!-- 右侧操作区 -->
      <div class="flex items-center gap-4">
        <!-- 搜索图标 -->
        <button class="p-2 text-lumina-600 hover:text-lumina-800 transition-colors rounded-full hover:bg-lumina-200/50">
          <el-icon :size="18"><Search /></el-icon>
        </button>

        <!-- 购物袋图标 -->
        <button
          @click="toggleCart"
          class="relative p-2 text-lumina-600 hover:text-lumina-800 transition-colors rounded-full hover:bg-lumina-200/50"
        >
          <el-icon :size="18"><ShoppingBag /></el-icon>
          <!-- 数量徽章 -->
          <span
            v-if="cartCount > 0"
            class="absolute -top-0.5 -right-0.5 w-4 h-4 bg-alert-crimson text-white text-[10px] font-medium rounded-full flex items-center justify-center"
          >
            {{ cartCount > 99 ? '99+' : cartCount }}
          </span>
        </button>

        <!-- 用户头像 / 登录按钮 -->
        <template v-if="isLoggedIn">
          <button class="w-8 h-8 rounded-full bg-lumina-200 overflow-hidden hover:ring-2 ring-lumina-300 transition-all">
            <img :src="userAvatar" alt="User" class="w-full h-full object-cover" />
          </button>
        </template>
        <template v-else>
          <button
            @click="goToLogin"
            class="text-xs font-medium text-lumina-600 hover:text-lumina-800 px-4 py-1.5 rounded-full border border-lumina-300 hover:border-lumina-400 transition-all duration-300"
          >
            登录
          </button>
        </template>

        <!-- 移动端菜单按钮 -->
        <button class="md:hidden p-2 text-lumina-800" @click="toggleMobileMenu">
          <el-icon :size="20"><Menu /></el-icon>
        </button>
      </div>
    </div>
  </header>
</template>
```

**Header 关键样式解析：**

| 属性 | 值 | 说明 |
|------|-----|------|
| 定位 | `fixed top-0 z-50` | 固定顶部，始终可见 |
| 高度 | `h-14` (56px) | 紧凑高度，不占用过多垂直空间 |
| 背景（未滚动） | `bg-transparent` | 完全透明，透出下方内容 |
| 背景（已滚动） | `bg-lumina-100/80 backdrop-blur-xl` | 80%不透明度 + 20px模糊半径 |
| 过渡 | `duration-500` | 平滑的500ms过渡，避免突兀 |
| Logo字重 | `font-semibold tracking-tight` | 中粗体 + 紧凑字距 |
| 导航字号 | `text-xs` | 极小字号（12px），类似苹果官网 |
| 导航字距 | `tracking-wide` | 加宽字距，提升高级感 |
| 下划线 | `h-[1.5px] bg-titanium` | 1.5px高的钛金属色下划线 |
| 图标尺寸 | `:size="18"` | 小而精致，不抢夺焦点 |

#### B. 移动端导航菜单（Mobile Menu Drawer）

移动端采用右侧滑出的抽屉式菜单，而非汉堡菜单展开：

```vue
<teleport to="body">
  <transition name="drawer">
    <div v-if="mobileMenuOpen" class="fixed inset-0 z-[60]">
      <!-- 遮罩层 -->
      <div
        class="absolute inset-0 bg-lumina-950/40 backdrop-blur-sm"
        @click="toggleMobileMenu"
      ></div>

      <!-- 抽屉面板 -->
      <div class="absolute right-0 top-0 bottom-0 w-72 bg-white shadow-2xl transform transition-transform duration-300 ease-out">
        <div class="p-6 pt-20">
          <h3 class="text-sm font-semibold text-lumina-400 uppercase tracking-wider mb-6">菜单</h3>
          <nav class="space-y-1">
            <router-link
              v-for="item in navItems"
              :key="item.path"
              :to="item.path"
              class="block py-3 text-lg font-medium text-lumina-800 hover:text-titanium transition-colors border-b border-lumina-100"
              @click="toggleMobileMenu"
            >
              {{ item.label }}
            </router-link>
          </nav>

          <!-- 底部登录/注册 -->
          <div class="mt-8 pt-6 border-t border-lumina-200 space-y-3">
            <button v-if="!isLoggedIn" class="w-full py-2.5 text-sm font-medium text-lumina-800 border border-lumina-300 rounded-full hover:bg-lumina-50 transition-colors">
              登录
            </button>
            <button v-if="!isLoggedIn" class="w-full py-2.5 text-sm font-medium text-white bg-lumina-800 rounded-full hover:bg-lumina-900 transition-colors">
              注册
            </button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</teleport>
```

#### C. Footer（页脚）

Footer采用深色背景，与浅色页面主体形成强烈对比，同时提供充足的留白和清晰的层次结构。

```vue
<template>
  <footer class="bg-lumina-900 text-lumina-400 mt-auto">
    <!-- 主内容区 -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div class="grid grid-cols-2 md:grid-cols-4 gap-8 mb-12">
        <!-- 列1: 产品分类 -->
        <div>
          <h4 class="text-xs font-semibold text-lumina-200 uppercase tracking-wider mb-4">产品</h4>
          <ul class="space-y-2.5">
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">Mac</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">iPad</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">iPhone</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">Watch</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">AirPods</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">配件</a></li>
          </ul>
        </div>

        <!-- 列2: 服务 -->
        <div>
          <h4 class="text-xs font-semibold text-lumina-200 uppercase tracking-wider mb-4">服务</h4>
          <ul class="space-y-2.5">
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">Apple Music</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">iCloud+</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">Apple TV+</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">Apple Arcade</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">Apple One</a></li>
          </ul>
        </div>

        <!-- 列3: 关于我们 -->
        <div>
          <h4 class="text-xs font-semibold text-lumina-200 uppercase tracking-wider mb-4">关于</h4>
          <ul class="space-y-2.5">
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">品牌故事</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">新闻中心</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">工作机会</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">投资者关系</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">环境责任</a></li>
          </ul>
        </div>

        <!-- 列4: 支持 -->
        <div>
          <h4 class="text-xs font-semibold text-lumina-200 uppercase tracking-wider mb-4">支持</h4>
          <ul class="space-y-2.5">
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">常见问题</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">联系我们</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">售后服务</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">保修政策</a></li>
            <li><a href="#" class="text-sm hover:text-white transition-colors duration-200">门店查询</a></li>
          </ul>
        </div>
      </div>

      <!-- 分割线 -->
      <div class="border-t border-lumina-800 pt-8">
        <div class="flex flex-col md:flex-row justify-between items-center gap-4">
          <p class="text-xs text-lumina-500">
            Copyright &copy; {{ new Date().getFullYear() }} Lumina Digital. All rights reserved.
          </p>
          <div class="flex items-center gap-6">
            <a href="#" class="text-xs hover:text-white transition-colors">隐私政策</a>
            <a href="#" class="text-xs hover:text-white transition-colors">使用条款</a>
            <a href="#" class="text-xs hover:text-white transition-colors">法律声明</a>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>
```

**Footer 样式要点：**
- 背景：`bg-lumina-900`（深空灰），不是纯黑，更柔和
- 标题：`text-xs uppercase tracking-wider`，全大写 + 加宽字距
- 链接：`text-sm hover:text-white`，悬停变白色
- 分割线：`border-lumina-800`，比背景稍亮的灰色
- 版权文字：`text-xs text-lumina-500`，极小且低调

### 5.3 用户中心子布局 — UserLayout

用户中心采用左右分栏的无边框设计，左侧导航为纯文字悬浮式，右侧内容区使用极其柔和的弥散阴影卡片。

```
┌─────────────────────────────────────────────────────────────┐
│  (上方为ClientLayout的Header)                               │
├─────────────────────────────────────────────────────────────┤
│  ┌────────────────┐  ┌──────────────────────────────────┐  │
│  │  Sidebar       │  │  Content Area                    │  │
│  │  ────────────  │  │  ┌────────────────────────────┐  │  │
│  │                │  │  │                            │  │  │
│  │  个人中心       │  │  │     <RouterView />         │  │  │
│  │  · 订单记录     │  │  │                            │  │  │
│  │  · 收货地址     │  │  │                            │  │  │
│  │  · 我的收藏     │  │  └────────────────────────────┘  │  │
│  │  · 账户设置     │  │                                  │  │
│  │                │  │  min-h-[600px]                   │  │
│  └────────────────┘  └──────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**完整实现代码：**

```vue
<template>
  <div class="min-h-screen bg-lumina-100 pt-14">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="flex gap-12">
        <!-- 左侧导航 -->
        <aside class="w-48 shrink-0 hidden md:block">
          <h2 class="text-2xl font-semibold text-lumina-800 mb-8">账户</h2>
          <nav class="space-y-1">
            <router-link
              v-for="item in menuItems"
              :key="item.path"
              :to="item.path"
              class="block py-2.5 text-sm transition-colors duration-200"
              :class="isActive(item.path)
                ? 'text-lumina-800 font-semibold'
                : 'text-lumina-500 hover:text-lumina-800'"
            >
              {{ item.label }}
            </router-link>
          </nav>
        </aside>

        <!-- 右侧内容区 -->
        <main class="flex-1 min-w-0">
          <div class="bg-white rounded-2xl p-8 lg:p-10 shadow-[0_8px_30px_rgb(0,0,0,0.04)]">
            <router-view />
          </div>
        </main>
      </div>
    </div>
  </div>
</template>
```

**关键样式说明：**
- 侧边栏宽度：`w-48`（192px），紧凑且足够容纳文字
- 无边框设计：侧边栏没有背景色和边框，纯粹的文字导航
- 内容区圆角：`rounded-2xl`（16px），较大的圆角更显现代
- 弥散阴影：`shadow-[0_8px_30px_rgb(0,0,0,0.04)]`，极度柔和，几乎不可见但能提供层次感
- 内边距：`p-8 lg:p-10`，响应式调整

---

## 六、页面组件详细设计规范

### 6.1 首页（HomeView）— 沉浸式叙事体验

首页是整个商城的"门面"，需要像讲述一个故事一样引导用户探索产品。采用**全屏Hero + Bento Box网格 + 视差滚动**的三段式结构。

#### A. 第一段：沉浸式Hero区域（Viewport-height Banner）

占据首屏80%-90%的高度，背景为深邃的近黑色，中央放置旗舰产品的超大特写图，配合极简的大字号文案。

**视觉效果描述：**
- 背景：从 `#0a0a0a` 到 `#1a1a1c` 的垂直渐变（见第3.3节）
- 产品图：一台钛金属配色的智能手机，呈45度角倾斜放置，屏幕微微亮起，边缘有极其微弱的反光
- 文案：纯白色超大字号标题，下方是半透明的副标题
- CTA按钮：一个实心白色圆角按钮（主行动），一个透明描边按钮（次要行动）

**完整代码实现：**

```vue
<template>
  <section class="relative h-[85vh] min-h-[600px] w-full overflow-hidden bg-lumina-950">
    <!-- 背景渐变层 -->
    <div class="absolute inset-0 bg-gradient-to-b from-lumina-950 via-[#121214] to-[#1a1a1c]"></div>

    <!-- 可选：微妙的噪点纹理叠加（增加质感） -->
    <div class="absolute inset-0 opacity-[0.03]" style="background-image: url('data:image/svg+xml,...')"></div>

    <!-- 内容层 -->
    <div class="relative z-10 h-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col items-center justify-start pt-32 pb-20">
      <!-- 标题组 -->
      <div class="text-center mb-8 animate-fade-in-up">
        <h1 class="text-5xl sm:text-6xl md:text-7xl lg:text-8xl font-bold text-white tracking-tight leading-[1.05] mb-4">
          iPhone 15 Pro
        </h1>
        <p class="text-lg sm:text-xl md:text-2xl text-white/70 font-normal max-w-2xl mx-auto leading-relaxed">
          钛金属设计。A17 Pro芯片。<br class="hidden sm:block" />
          一部开创先机的iPhone。
        </p>
      </div>

      <!-- CTA 按钮组 -->
      <div class="flex flex-wrap items-center justify-center gap-4 mb-12 animate-fade-in-up animation-delay-200">
        <button class="group relative px-8 py-3 bg-white text-lumina-900 text-sm font-medium rounded-full hover:bg-lumina-100 active:scale-95 transition-all duration-200 shadow-[0_2px_10px_rgba(255,255,255,0.2)]">
          购买
          <span class="ml-1 opacity-0 group-hover:opacity-100 transition-opacity">&rarr;</span>
        </button>
        <button class="px-8 py-3 text-white text-sm font-medium rounded-full border border-white/30 hover:border-white/60 hover:bg-white/5 active:scale-95 transition-all duration-200">
          进一步了解
        </button>
      </div>

      <!-- 产品图片 -->
      <div class="flex-1 flex items-end justify-center w-full max-w-4xl animate-fade-in-up animation-delay-400 overflow-hidden">
        <img
          src="/images/hero-iphone-15-pro.png"
          alt="iPhone 15 Pro"
          class="w-[75%] max-w-2xl object-contain drop-shadow-[0_20px_60px_rgba(0,0,0,0.5)] hero-float"
        />
      </div>
    </div>

    <!-- 向下滚动指示器 -->
    <div class="absolute bottom-8 left-1/2 -translate-x-1/2 animate-bounce">
      <div class="w-6 h-10 border-2 border-white/30 rounded-full flex justify-center pt-2">
        <div class="w-1 h-2 bg-white/60 rounded-full"></div>
      </div>
    </div>
  </section>
</template>

<style scoped>
@keyframes fade-in-up {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in-up {
  animation: fade-in-up 0.8s cubic-bezier(0.16, 1, 0.3, 1) forwards;
  opacity: 0;
}

.animation-delay-200 {
  animation-delay: 0.2s;
}

.animation-delay-400 {
  animation-delay: 0.4s;
}

@keyframes hero-float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-10px);
  }
}

.hero-float {
  animation: hero-float 6s ease-in-out infinite;
}
</style>
```

**Hero区域关键参数：**

| 元素 | 规格 | 设计意图 |
|------|------|----------|
| 高度 | `h-[85vh] min-h-[600px]` | 占据大部分首屏，保证移动端最小高度 |
| 背景 | `bg-lumina-950` + 渐变叠加 | 深邃但不死黑，有层次感 |
| 标题字号 | `text-5xl → text-8xl` | 响应式递增，桌面端极具冲击力 |
| 标题字重 | `font-bold tracking-tight` | 粗体 + 紧凑字距，力量感强 |
| 行高 | `leading-[1.05]` | 极紧凑行高，适合大标题 |
| 副标题透明度 | `text-white/70` | 70%不透明度，不抢主标题焦点 |
| 主按钮 | 白底黑字 + 圆角 + 阴影 | 在深色背景上极为突出 |
| 次按钮 | 透明描边 + hover填充 | 不喧宾夺主 |
| 产品图阴影 | `drop-shadow-[0_20px_60px_rgba(0,0,0,0.5)]` | 让产品图"浮"在背景之上 |
| 动画曲线 | `cubic-bezier(0.16, 1, 0.3, 1)` | iOS风格弹性缓动 |
| 浮动动画 | 6秒周期 ±10px | 极缓慢的上下浮动，几乎不可察觉但增加生命力 |

#### B. 第二段：Bento Box便当盒网格展示区

摒弃传统的等大小网格，采用大小不一、错落有致的区块拼接（类似苹果官网的产品展示区）。大区块放置旗舰产品，小区块放置配件。

**布局结构示意：**

```
┌─────────────────────────────────────────────────────────┐
│  ┌─────────────────────┬──────────┬──────────┐         │
│  │                     │          │          │         │
│  │   MacBook Pro       │ AirPods  │ Watch    │         │
│  │   (col-span-2       │ Pro      │ Ultra    │         │
│  │    row-span-2)      │ (1x1)    │ (1x1)    │         │
│  │                     │          │          │         │
│  ├─────────────────────┼──────────┴──────────┤         │
│  │                     │    iPad Pro         │         │
│  │                     │   (col-span-2)      │         │
│  └─────────────────────┴─────────────────────┘         │
└─────────────────────────────────────────────────────────┘
```

**完整代码实现：**

```vue
<template>
  <section class="py-20 bg-lumina-100">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Bento Grid 容器 -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4 auto-rows-[280px]">

        <!-- 大区块：MacBook Pro (2x2) -->
        <div
          class="group relative md:col-span-2 md:row-span-2 bg-gradient-to-br from-lumina-200 to-lumina-100 rounded-3xl p-8 lg:p-10 overflow-hidden cursor-pointer hover:shadow-[0_20px_60px_rgb(0,0,0,0.08)] transition-shadow duration-500"
        >
          <!-- 背景纹理（可选） -->
          <div class="absolute inset-0 opacity-30">
            <div class="absolute top-0 right-0 w-96 h-96 bg-gradient-to-bl from-white/40 to-transparent rounded-full blur-3xl"></div>
          </div>

          <!-- 文案 -->
          <div class="relative z-10 h-full flex flex-col justify-between">
            <div>
              <h3 class="text-3xl lg:text-4xl font-semibold text-lumina-800 mb-2">MacBook Pro</h3>
              <p class="text-lumina-500 text-base lg:text-lg max-w-md">
                M3芯片登场。身怀绝技，大有可为。
              </p>
            </div>

            <!-- 产品图 -->
            <div class="self-end mt-4">
              <img
                src="/images/product-macbook-pro.png"
                alt="MacBook Pro"
                class="w-full max-w-md object-contain group-hover:scale-105 transition-transform duration-700 ease-out drop-shadow-2xl"
              />
            </div>
          </div>
        </div>

        <!-- 小区块1：AirPods Pro (1x1) -->
        <div
          class="group relative bg-white rounded-3xl p-8 flex flex-col items-center justify-between cursor-pointer hover:shadow-lg transition-shadow duration-300"
        >
          <div class="text-left w-full">
            <h4 class="text-xl font-semibold text-lumina-800 mb-1">AirPods Pro</h4>
            <p class="text-sm text-lumina-500">自适应音频。</p>
          </div>
          <img
            src="/images/product-airpods-pro.png"
            alt="AirPods Pro"
            class="w-28 object-contain group-hover:-translate-y-2 transition-transform duration-300"
          />
        </div>

        <!-- 小区块2：Watch Ultra (1x1) -->
        <div
          class="group relative bg-lumina-900 rounded-3xl p-8 flex flex-col justify-between cursor-pointer hover:shadow-[0_20px_40px_rgb(0,0,0,0.3)] transition-shadow duration-300 overflow-hidden"
        >
          <!-- 微光效果 -->
          <div class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-32 h-32 bg-glacier-blue/10 rounded-full blur-2xl"></div>

          <div class="relative z-10">
            <h4 class="text-xl font-semibold text-white mb-1">Watch Ultra 2</h4>
            <p class="text-sm text-lumina-400">终极探险伴侣。</p>
          </div>
          <img
            src="/images/product-watch-ultra.png"
            alt="Watch Ultra 2"
            class="self-end w-24 object-contain group-hover:scale-110 transition-transform duration-300"
          />
        </div>

        <!-- 横向区块：iPad Pro (跨2列) -->
        <div
          class="group relative md:col-span-2 bg-white rounded-3xl p-8 lg:p-10 flex items-center gap-8 cursor-pointer hover:shadow-lg transition-shadow duration-300 overflow-hidden"
        >
          <div class="flex-1 z-10">
            <h4 class="text-2xl lg:text-3xl font-semibold text-lumina-800 mb-2">iPad Pro</h4>
            <p class="text-lumina-500 text-base mb-4">M2芯片加持。不可思议的轻薄。</p>
            <span class="inline-block text-xs font-medium text-glacier-blue border-b border-glacier-blue/30 pb-0.5">
              了解更多 &rarr;
            </span>
          </div>
          <div class="flex-shrink-0">
            <img
              src="/images/product-ipad-pro.png"
              alt="iPad Pro"
              class="w-48 lg:w-64 object-contain group-hover:scale-105 transition-transform duration-500"
            />
          </div>
        </div>

      </div>
    </div>
  </section>
</template>
```

**Bento Grid 关键特性：**

| 特性 | 实现 | 效果 |
|------|------|------|
| 网格系统 | `grid-cols-4 auto-rows-[280px]` | 4列等高行（280px） |
| 大区块 | `md:col-span-2 md:row-span-2` | 占据2x2空间 |
| 圆角 | `rounded-3xl` (24px) | 大圆角，现代感强 |
| 悬停阴影 | `hover:shadow-[0_20px_60px_rgb(0,0,0,0.08)]` | 极柔和的提升感 |
| 图片缩放 | `group-hover:scale-105 duration-700` | 缓慢放大，不突兀 |
| 背景光晕 | `bg-gradient-to-bl from-white/40 blur-3xl` | 增加空气感和深度 |
| 暗色区块 | `bg-lumina-900` + 微光点缀 | 打破单调，增加节奏 |

#### C. 第三段：特色服务横幅（Feature Strip）

一个横向滚动的特色服务展示条，采用极简的图文结合方式。

```vue
<template>
  <section class="py-16 bg-white border-y border-lumina-200">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
        <!-- 服务项1：免费配送 -->
        <div class="flex flex-col items-center text-center group">
          <div class="w-14 h-14 rounded-full bg-lumina-100 flex items-center justify-center mb-4 group-hover:bg-lumina-200 transition-colors">
            <el-icon :size="24" class="text-lumina-600"><Van /></el-icon>
          </div>
          <h4 class="text-sm font-semibold text-lumina-800 mb-1">免费配送</h4>
          <p class="text-xs text-lumina-500 leading-relaxed">订单满¥999即享包邮</p>
        </div>

        <!-- 服务项2：正品保障 -->
        <div class="flex flex-col items-center text-center group">
          <div class="w-14 h-14 rounded-full bg-lumina-100 flex items-center justify-center mb-4 group-hover:bg-lumina-200 transition-colors">
            <el-icon :size="24" class="text-lumina-600"><Shield /></el-icon>
          </div>
          <h4 class="text-sm font-semibold text-lumina-800 mb-1">正品保障</h4>
          <p class="text-xs text-lumina-500 leading-relaxed">官方授权渠道</p>
        </div>

        <!-- 服务项3：售后无忧 -->
        <div class="flex flex-col items-center text-center group">
          <div class="w-14 h-14 rounded-full bg-lumina-100 flex items-center justify-center mb-4 group-hover:bg-lumina-200 transition-colors">
            <el-icon :size="24" class="text-lumina-600"><Service /></el-icon>
          </div>
          <h4 class="text-sm font-semibold text-lumina-800 mb-1">售后无忧</h4>
          <p class="text-xs text-lumina-500 leading-relaxed">7天无理由退换</p>
        </div>

        <!-- 服务项4：分期免息 -->
        <div class="flex flex-col items-center text-center group">
          <div class="w-14 h-14 rounded-full bg-lumina-100 flex items-center justify-center mb-4 group-hover:bg-lumina-200 transition-colors">
            <el-icon :size="24" class="text-lumina-600"><CreditCard /></el-icon>
          </div>
          <h4 class="text-sm font-semibold text-lumina-800 mb-1">分期免息</h4>
          <p class="text-xs text-lumina-500 leading-relaxed">3/6/12期免息可选</p>
        </div>
      </div>
    </div>
  </section>
</template>
```

### 6.2 商品列表页（ProductListView）— 精致筛选与瀑布流

#### A. 页面顶部分类横幅

```vue
<template>
  <div class="pt-14 bg-lumina-100 min-h-screen">
    <!-- 分类Hero -->
    <section class="bg-lumina-950 text-white py-16 md:py-24">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h1 class="text-4xl md:text-5xl font-bold tracking-tight mb-4">{{ categoryName }}</h1>
        <p class="text-lg text-white/60 max-w-2xl mx-auto">{{ categoryDescription }}</p>
      </div>
    </section>

    <!-- 工具栏：搜索 + 排序 + 筛选 -->
    <section class="sticky top-14 z-30 bg-lumina-100/95 backdrop-blur-md border-b border-lumina-200">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
        <div class="flex flex-col sm:flex-row items-stretch sm:items-center gap-4">
          <!-- 搜索框 -->
          <div class="flex-1 relative">
            <el-icon class="absolute left-3 top-1/2 -translate-y-1/2 text-lumina-400"><Search /></el-icon>
            <input
              type="text"
              v-model="keyword"
              placeholder="搜索商品..."
              class="w-full pl-10 pr-4 py-2.5 bg-white border border-lumina-200 rounded-full text-sm text-lumina-800 placeholder:text-lumina-400 focus:outline-none focus:border-lumina-400 focus:ring-1 focus:ring-lumina-300 transition-all"
            />
          </div>

          <!-- 排序按钮组 -->
          <div class="flex items-center gap-2 shrink-0">
            <span class="text-xs text-lumina-500 mr-1 hidden sm:inline">排序：</span>
            <button
              v-for="sort in sortOptions"
              :key="sort.value"
              @click="currentSort = sort.value"
              class="px-3 py-1.5 text-xs font-medium rounded-full transition-all duration-200"
              :class="currentSort === sort.value
                ? 'bg-lumina-800 text-white'
                : 'text-lumina-600 hover:bg-lumina-200'"
            >
              {{ sort.label }}
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- 商品网格 -->
    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        <!-- 商品卡片 x N -->
        <ProductCard
          v-for="product in products"
          :key="product.id"
          :product="product"
        />
      </div>

      <!-- 分页器 -->
      <div class="mt-12 flex justify-center">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="prev, pager, next"
          @current-change="handlePageChange"
        />
      </div>
    </section>
  </div>
</template>
```

#### B. 商品卡片组件（ProductCard）

这是最核心的可复用组件之一，需要在简洁中体现精致。

```vue
<template>
  <div
    class="group bg-white rounded-2xl overflow-hidden cursor-pointer transition-all duration-300 hover:shadow-[0_12px_40px_rgb(0,0,0,0.08)] hover:-translate-y-1"
    @click="goToDetail(product.id)"
  >
    <!-- 图片区域 -->
    <div class="aspect-square bg-lumina-100 relative overflow-hidden">
      <img
        :src="product.image"
        :alt="product.name"
        class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-700 ease-out"
        loading="lazy"
      />

      <!-- 标签徽章（可选） -->
      <span
        v-if="product.badge"
        class="absolute top-3 left-3 px-2.5 py-1 bg-lumina-900/80 backdrop-blur-sm text-white text-[11px] font-medium rounded-full"
      >
        {{ product.badge }}
      </span>

      <!-- 快捷操作按钮（悬停显示） -->
      <div class="absolute top-3 right-3 flex flex-col gap-2 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
        <button
          @click.stop="toggleFavorite(product.id)"
          class="w-8 h-8 rounded-full bg-white/90 backdrop-blur-sm flex items-center justify-center shadow-sm hover:bg-white transition-colors"
        >
          <el-icon :size="14" :class="isFavorite ? 'text-alert-crimson' : 'text-lumina-600'">
            <StarFilled v-if="isFavorite" /><Star v-else />
          </el-icon>
        </button>
      </div>
    </div>

    <!-- 信息区域 -->
    <div class="p-5">
      <!-- 分类标签 -->
      <span class="text-[11px] text-lumina-400 uppercase tracking-wider font-medium">
        {{ product.category }}
      </span>

      <!-- 商品名称 -->
      <h3 class="mt-1 text-base font-medium text-lumina-800 line-clamp-2 leading-snug group-hover:text-lumina-900 transition-colors">
        {{ product.name }}
      </h3>

      <!-- 价格行 -->
      <div class="mt-3 flex items-end justify-between">
        <div>
          <span class="text-lg font-semibold text-lumina-800">
            ¥{{ formatPrice(product.price) }}
          </span>
          <span v-if="product.originalPrice" class="ml-2 text-xs text-lumina-400 line-through">
            ¥{{ formatPrice(product.originalPrice) }}
          </span>
        </div>

        <!-- 加入购物车按钮 -->
        <button
          @click.stop="addToCart(product)"
          class="w-8 h-8 rounded-full bg-lumina-100 flex items-center justify-center text-lumina-600 hover:bg-lumina-800 hover:text-white transition-all duration-200 active:scale-90"
        >
          <el-icon :size="14"><Plus /></el-icon>
        </button>
      </div>
    </div>
  </div>
</template>
```

**ProductCard 样式规格：**

| 元素 | 样式 | 说明 |
|------|------|------|
| 卡片容器 | `bg-white rounded-2xl hover:shadow-[...] hover:-translate-y-1` | 圆角20px，悬停时轻微上浮+柔和阴影 |
| 图片比例 | `aspect-square` | 正方形，统一视觉节奏 |
| 图片动效 | `group-hover:scale-105 duration-700` | 缓慢放大（700ms），优雅不突兀 |
| 徽章 | `bg-lumina-900/80 backdrop-blur-sm rounded-full` | 半透明深色背景+毛玻璃 |
| 快捷按钮 | `opacity-0 group-hover:opacity-100` | 默认隐藏，悬停淡入 |
| 分类标签 | `text-[11px] uppercase tracking-wider` | 极小字号+全大写+宽字距 |
| 商品名 | `line-clamp-2 leading-snug` | 最多2行，紧凑行高 |
| 价格 | `text-lg font-semibold` | 稍大的价格，强调购买决策 |
| 加购按钮 | `圆形 + hover变色 + active:scale-90` | 点击时有下压反馈 |

### 6.3 商品详情页（ProductDetailView）— 明暗交替的沉浸式长页

这是最具挑战性的页面，需要通过**Dark/Light区块交替**来创造强烈的视觉节奏感，同时保持信息的清晰传达。

#### A. 吸顶式购买导航（Sticky Buy Bar）

当用户向下滚动超过首屏后，顶部出现一个极简的购买条，确保转化入口始终可达。

```vue
<template>
  <transition name="slide-down">
    <div
      v-if="showStickyBar"
      class="fixed top-14 left-0 right-0 z-40 bg-white/95 backdrop-blur-lg border-b border-lumina-200 shadow-sm"
    >
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-14 flex items-center justify-between">
        <!-- 产品名 -->
        <h3 class="text-sm font-medium text-lumina-800 truncate max-w-md">
          {{ product.name }}
        </h3>

        <!-- 价格 + 按钮 -->
        <div class="flex items-center gap-4">
          <span class="text-lg font-semibold text-lumina-800">
            ¥{{ formatPrice(product.price) }}
          </span>
          <button class="px-6 py-2 bg-lumina-800 text-white text-sm font-medium rounded-full hover:bg-lumina-900 active:scale-95 transition-all duration-200">
            购买
          </button>
        </div>
      </div>
    </div>
  </transition>
</template>

<style scoped>
.slide-down-enter-active,
.slide-down-leave-active {
  transition: all 0.3s cubic-bezier(0.16, 1, 0.3, 1);
}
.slide-down-enter-from {
  transform: translateY(-100%);
  opacity: 0;
}
.slide-down-leave-to {
  transform: translateY(-100%);
  opacity: 0;
}
</style>
```

**吸顶栏参数：**
- 定位：`fixed top-14`（紧贴在Header下方）
- 高度：`h-14`（56px），与Header同高
- 背景：`bg-white/95 backdrop-blur-lg`（95%不透明 + 强模糊）
- 出现时机：滚动超过首屏产品图底部
- 动画：从顶部滑入（slide-down），300ms弹性缓动

#### B. 页面主体结构（Dark/Light交替）

```vue
<template>
  <div class="pt-14 bg-lumina-100">
    <!-- ===== 区块1：产品外观展示（暗色背景）===== -->
    <section class="bg-lumina-950 text-white py-20 lg:py-32">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 lg:gap-20 items-center">
          <!-- 左侧：产品画廊 -->
          <div class="relative">
            <!-- 主图 -->
            <div class="aspect-square bg-lumina-900 rounded-3xl overflow-hidden flex items-center justify-center">
              <img
                :src="currentImage"
                :alt="product.name"
                class="max-w-full max-h-full object-contain drop-shadow-[0_30px_80px_rgba(0,0,0,0.5)]"
              />
            </div>

            <!-- 缩略图列表 -->
            <div class="flex gap-3 mt-4 justify-center">
              <button
                v-for="(img, idx) in product.images"
                :key="idx"
                @click="currentImage = img"
                class="w-16 h-16 rounded-xl overflow-hidden border-2 transition-all duration-200"
                :class="currentImage === img ? 'border-white' : 'border-transparent opacity-60 hover:opacity-100'"
              >
                <img :src="img" class="w-full h-full object-cover" />
              </button>
            </div>
          </div>

          <!-- 右侧：产品信息 + 购买选项 -->
          <div>
            <!-- 面包屑 -->
            <nav class="text-xs text-lumina-400 mb-4">
              <router-link to="/" class="hover:text-white transition-colors">首页</router-link>
              <span class="mx-2">/</span>
              <router-link to="/products" class="hover:text-white transition-colors">{{ product.category }}</router-link>
              <span class="mx-2">/</span>
              <span class="text-white">{{ product.name }}</span>
            </nav>

            <!-- 产品名 -->
            <h1 class="text-3xl lg:text-4xl font-semibold tracking-tight mb-3">
              {{ product.name }}
            </h1>

            <!-- 副标题 -->
            <p class="text-lg text-white/60 mb-6 leading-relaxed">
              {{ product.subtitle }}
            </p>

            <!-- 价格 -->
            <div class="mb-8">
              <span class="text-3xl font-bold">¥{{ formatPrice(product.price) }}</span>
              <span v-if="product.originalPrice" class="ml-3 text-lg text-white/40 line-through">
                ¥{{ formatPrice(product.originalPrice) }}
              </span>
            </div>

            <!-- SKU 选择器 -->
            <div class="space-y-6 mb-8">
              <div v-for="skuGroup in product.skuGroups" :key="skuGroup.name">
                <label class="block text-sm font-medium text-white/80 mb-3">
                  {{ skuGroup.name }}
                </label>
                <div class="flex flex-wrap gap-3">
                  <button
                    v-for="option in skuGroup.options"
                    :key="option.value"
                    @click="selectSku(skuGroup.name, option.value)"
                    class="px-5 py-2.5 text-sm rounded-full border transition-all duration-200"
                    :class="isSelected(skuGroup.name, option.value)
                      ? 'border-white bg-white text-lumina-950'
                      : 'border-white/20 text-white/80 hover:border-white/50 hover:bg-white/5'"
                  >
                    {{ option.label }}
                  </button>
                </div>
              </div>
            </div>

            <!-- 数量选择器 -->
            <div class="mb-8">
              <label class="block text-sm font-medium text-white/80 mb-3">数量</label>
              <div class="inline-flex items-center border border-white/20 rounded-full overflow-hidden">
                <button
                  @click="decreaseQty"
                  class="w-10 h-10 flex items-center justify-center text-white/60 hover:text-white hover:bg-white/10 transition-colors"
                  :disabled="quantity <= 1"
                >
                  <span class="text-lg">−</span>
                </button>
                <input
                  type="number"
                  v-model.number="quantity"
                  class="w-14 h-10 text-center text-white bg-transparent focus:outline-none [appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none"
                  min="1"
                  :max="product.stock"
                />
                <button
                  @click="increaseQty"
                  class="w-10 h-10 flex items-center justify-center text-white/60 hover:text-white hover:bg-white/10 transition-colors"
                  :disabled="quantity >= product.stock"
                >
                  <span class="text-lg">+</span>
                </button>
              </div>
            </div>

            <!-- CTA按钮组 -->
            <div class="flex gap-4">
              <button
                @click="addToCart"
                class="flex-1 py-3.5 bg-white text-lumina-950 text-sm font-semibold rounded-full hover:bg-lumina-100 active:scale-[0.98] transition-all duration-200 shadow-[0_4px_20px_rgba(255,255,255,0.15)]"
              >
                加入购物袋
              </button>
              <button
                @click="buyNow"
                class="flex-1 py-3.5 bg-lumina-800 text-white text-sm font-semibold rounded-full border border-white/20 hover:bg-lumina-700 hover:border-white/40 active:scale-[0.98] transition-all duration-200"
              >
                立即购买
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ===== 区块2：产品亮点介绍（亮色背景）===== -->
    <section class="bg-lumina-100 py-24">
      <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-3xl lg:text-4xl font-semibold text-lumina-800 text-center mb-16 tracking-tight">
          为什么选择 {{ product.shortName }}
        </h2>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div
            v-for="(feature, idx) in product.features"
            :key="idx"
            class="text-center p-6"
          >
            <div class="w-16 h-16 mx-auto mb-5 rounded-2xl bg-lumina-200 flex items-center justify-center">
              <el-icon :size="28" class="text-lumina-600">
                <component :is="feature.icon" />
              </el-icon>
            </div>
            <h3 class="text-lg font-semibold text-lumina-800 mb-2">{{ feature.title }}</h3>
            <p class="text-sm text-lumina-500 leading-relaxed">{{ feature.description }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ===== 区块3：技术参数（深灰背景）===== -->
    <section class="bg-lumina-900 text-white py-24">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-3xl font-semibold text-center mb-12 tracking-tight">技术规格</h2>

        <!-- 参数表格 -->
        <div class="space-y-0 divide-y divide-white/10">
          <div
            v-for="(spec, idx) in product.specifications"
            :key="idx"
            class="py-4 flex justify-between items-center"
          >
            <span class="text-sm text-white/60">{{ spec.label }}</span>
            <span class="text-sm font-medium text-right max-w-[60%]">{{ spec.value }}</span>
          </div>
        </div>
      </div>
    </section>

    <!-- ===== 区块4：用户评价（亮色背景）===== -->
    <section class="bg-white py-24">
      <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-3xl font-semibold text-lumina-800 text-center mb-12 tracking-tight">
          用户评价
        </h2>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div
            v-for="(review, idx) in reviews"
            :key="idx"
            class="p-6 rounded-2xl bg-lumina-50 border border-lumina-100"
          >
            <!-- 评分 -->
            <div class="flex items-center gap-1 mb-3">
              <el-icon v-for="star in 5" :key="star" :size="14" :class="star <= review.rating ? 'text-solar-gold' : 'text-lumina-300'">
                <StarFilled />
              </el-icon>
            </div>
            <p class="text-sm text-lumina-700 leading-relaxed mb-4">{{ review.content }}</p>
            <div class="flex items-center gap-3">
              <div class="w-8 h-8 rounded-full bg-lumina-200 flex items-center justify-center text-xs font-semibold text-lumina-600">
                {{ review.author[0] }}
              </div>
              <span class="text-xs font-medium text-lumina-800">{{ review.author }}</span>
              <span class="text-xs text-lumina-400">{{ review.date }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
```

**详情页区块交替逻辑：**

| 区块序号 | 背景色 | 内容 | 目的 |
|---------|--------|------|------|
| 1 | `bg-lumina-950`（近黑） | 产品外观图 + 购买选项 | 聚焦产品本身，高对比度突出卖点 |
| 2 | `bg-lumina-100`（浅灰） | 产品亮点介绍 | 放松视觉，便于阅读文字 |
| 3 | `bg-lumina-900`（深空灰） | 技术参数表格 | 再次收紧注意力，数据呈现更清晰 |
| 4 | `bg-white`（纯白） | 用户评价 | 明亮开放，增强信任感 |

### 6.4 购物车页面（CartView）— 右侧抽屉式

摒弃传统的独立购物车页面，采用**右侧滑出的Drawer（抽屉）**形式，用户可以在任何页面快速查看和管理购物车。

```vue
<template>
  <el-drawer
    v-model="cartVisible"
    direction="rtl"
    size="420px"
    :with-header="false"
    :modal-class="'backdrop-blur-sm bg-lumina-950/30'"
    class="cart-drawer"
  >
    <div class="h-full flex flex-col bg-white">
      <!-- 头部 -->
      <div class="flex items-center justify-between px-6 py-5 border-b border-lumina-100">
        <h2 class="text-xl font-semibold text-lumina-800">您的购物袋</h2>
        <button
          @click="cartVisible = false"
          class="w-8 h-8 rounded-full flex items-center justify-center text-lumina-400 hover:text-lumina-800 hover:bg-lumina-100 transition-colors"
        >
          <el-icon :size="18"><Close /></el-icon>
        </button>
      </div>

      <!-- 商品列表（可滚动） -->
      <div class="flex-1 overflow-y-auto px-6 py-4">
        <!-- 空状态 -->
        <div v-if="cartItems.length === 0" class="h-full flex flex-col items-center justify-center text-center">
          <div class="w-20 h-20 rounded-full bg-lumina-100 flex items-center justify-center mb-4">
            <el-icon :size="36" class="text-lumina-300"><ShoppingBag /></el-icon>
          </div>
          <p class="text-lumina-500 text-sm mb-1">购物袋是空的</p>
          <p class="text-lumina-400 text-xs">快去挑选心仪的产品吧</p>
        </div>

        <!-- 商品项列表 -->
        <div v-else class="space-y-6">
          <div
            v-for="item in cartItems"
            :key="item.id"
            class="flex gap-4 pb-6 border-b border-lumina-100 last:border-0"
          >
            <!-- 商品图片 -->
            <div class="w-20 h-20 shrink-0 rounded-xl bg-lumina-100 overflow-hidden">
              <img :src="item.image" :alt="item.name" class="w-full h-full object-cover" />
            </div>

            <!-- 商品信息 -->
            <div class="flex-1 min-w-0">
              <div class="flex justify-between items-start mb-1">
                <h4 class="text-sm font-medium text-lumina-800 line-clamp-1 pr-2">
                  {{ item.name }}
                </h4>
                <span class="text-sm font-semibold text-lumina-800 shrink-0">
                  ¥{{ formatPrice(item.price * item.quantity) }}
                </span>
              </div>

              <p class="text-xs text-lumina-400 mb-3 line-clamp-1">{{ item.skuText }}</p>

              <!-- 数量 + 删除 -->
              <div class="flex items-center justify-between">
                <!-- 数量调节 -->
                <div class="inline-flex items-center border border-lumina-200 rounded-full overflow-hidden">
                  <button
                    @click="updateQuantity(item.id, item.quantity - 1)"
                    class="w-7 h-7 flex items-center justify-center text-lumina-400 hover:text-lumina-800 transition-colors text-sm"
                  >
                    −
                  </button>
                  <span class="w-8 h-7 flex items-center justify-center text-xs text-lumina-800">
                    {{ item.quantity }}
                  </span>
                  <button
                    @click="updateQuantity(item.id, item.quantity + 1)"
                    class="w-7 h-7 flex items-center justify-center text-lumina-400 hover:text-lumina-800 transition-colors text-sm"
                  >
                    +
                  </button>
                </div>

                <!-- 删除按钮 -->
                <button
                  @click="removeItem(item.id)"
                  class="text-xs text-lumina-400 hover:text-alert-crimson transition-colors"
                >
                  移除
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 底部结算区（固定） -->
      <div v-if="cartItems.length > 0" class="border-t border-lumina-200 px-6 py-5 space-y-4">
        <!-- 小计 -->
        <div class="flex justify-between items-center">
          <span class="text-sm text-lumina-500">小计</span>
          <span class="text-xl font-semibold text-lumina-800">¥{{ formatPrice(totalPrice) }}</span>
        </div>

        <!-- 结账按钮 -->
        <button
          @click="goToCheckout"
          class="w-full py-3.5 bg-lumina-800 text-white text-sm font-semibold rounded-full hover:bg-lumina-900 active:scale-[0.98] transition-all duration-200"
        >
          结账
        </button>

        <!-- 继续购物 -->
        <button
          @click="cartVisible = false"
          class="w-full py-2 text-sm text-lumina-500 hover:text-lumina-800 transition-colors"
        >
          继续购物
        </button>
      </div>
    </div>
  </el-drawer>
</template>
```

**购物车Drawer关键特征：**

| 特征 | 实现 | 效果 |
|------|------|------|
| 方向 | `direction="rtl"` | 从右侧滑出 |
| 宽度 | `size="420px"` | 足够宽但不遮挡全部内容 |
| 遮罩 | `backdrop-blur-sm bg-lumina-950/30` | 模糊+30%黑色遮罩 |
| 无头部 | `:with-header="false"` | 自定义头部，更灵活 |
| 圆角数量器 | `rounded-full` | 圆形胶囊状，更精致 |
| 删除按钮 | `text-xs hover:text-alert-crimson` | 默认灰色，悬停变红 |
| 结账按钮 | `bg-lumina-800 rounded-full` | 深灰圆角按钮，稳重 |

### 6.5 结算页面（CheckoutView）— 步骤式流程

采用**单页多步骤**的设计，将地址选择、支付方式、订单确认整合在一个流畅的长页面中，避免频繁跳转打断心流。

```vue
<template>
  <div class="pt-14 bg-lumina-100 min-h-screen pb-20">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 页面标题 -->
      <h1 class="text-3xl font-semibold text-lumina-800 mb-8">结账</h1>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- 左侧：主要内容区（占2列） -->
        <div class="lg:col-span-2 space-y-8">

          <!-- 步骤1：收货地址 -->
          <section class="bg-white rounded-2xl p-6 lg:p-8 shadow-sm">
            <h2 class="text-lg font-semibold text-lumina-800 mb-5 flex items-center gap-2">
              <span class="w-6 h-6 rounded-full bg-lumina-800 text-white text-xs flex items-center justify-center">1</span>
              收货地址
            </h2>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <!-- 地址卡片 -->
              <div
                v-for="addr in addresses"
                :key="addr.id"
                @click="selectedAddressId = addr.id"
                class="border-2 rounded-xl p-4 cursor-pointer transition-all relative"
                :class="selectedAddressId === addr.id
                  ? 'border-lumina-800 bg-lumina-50'
                  : 'border-lumina-200 hover:border-lumina-300'"
              >
                <div class="flex items-start justify-between mb-2">
                  <span class="font-medium text-lumina-800">{{ addr.name }}</span>
                  <span class="text-sm text-lumina-500">{{ addr.phone }}</span>
                </div>
                <p class="text-sm text-lumina-600 leading-relaxed">
                  {{ addr.province }} {{ addr.city }} {{ addr.detail }}
                </p>
                <span v-if="addr.isDefault" class="absolute top-3 right-3 text-[10px] bg-lumina-200 text-lumina-700 px-2 py-0.5 rounded-full">
                  默认
                </span>
                <!-- 选中标记 -->
                <div
                  v-if="selectedAddressId === addr.id"
                  class="absolute -right-1.5 -bottom-1.5 w-6 h-6 bg-lumina-800 rounded-full flex items-center justify-center"
                >
                  <el-icon :size="12" class="text-white"><Check /></el-icon>
                </div>
              </div>

              <!-- 新增地址占位 -->
              <div
                @click="showAddAddress = true"
                class="border-2 border-dashed border-lumina-300 rounded-xl p-4 cursor-pointer hover:border-lumina-400 hover:bg-lumina-50/50 transition-all flex flex-col items-center justify-center min-h-[120px]"
              >
                <el-icon :size="24" class="text-lumina-400 mb-2"><Plus /></el-icon>
                <span class="text-sm text-lumina-500">添加新地址</span>
              </div>
            </div>
          </section>

          <!-- 步骤2：支付方式 -->
          <section class="bg-white rounded-2xl p-6 lg:p-8 shadow-sm">
            <h2 class="text-lg font-semibold text-lumina-800 mb-5 flex items-center gap-2">
              <span class="w-6 h-6 rounded-full bg-lumina-800 text-white text-xs flex items-center justify-center">2</span>
              支付方式
            </h2>

            <div class="space-y-3">
              <div
                v-for="method in paymentMethods"
                :key="method.id"
                @click="selectedPayment = method.id"
                class="border-2 rounded-xl p-4 cursor-pointer transition-all flex items-center gap-4"
                :class="selectedPayment === method.id
                  ? 'border-lumina-800 bg-lumina-50'
                  : 'border-lumina-200 hover:border-lumina-300'"
              >
                <!-- 支付图标 -->
                <div class="w-12 h-12 rounded-xl bg-lumina-100 flex items-center justify-center shrink-0">
                  <el-icon :size="24" class="text-lumina-600">
                    <component :is="method.icon" />
                  </el-icon>
                </div>
                <div class="flex-1">
                  <span class="font-medium text-lumina-800">{{ method.name }}</span>
                  <p class="text-xs text-lumina-500 mt-0.5">{{ method.description }}</p>
                </div>
                <!-- 选中圆点 -->
                <div
                  class="w-5 h-5 rounded-full border-2 flex items-center justify-center transition-colors"
                  :class="selectedPayment === method.id ? 'border-lumina-800' : 'border-lumina-300'"
                >
                  <div
                    v-if="selectedPayment === method.id"
                    class="w-2.5 h-2.5 rounded-full bg-lumina-800"
                  ></div>
                </div>
              </div>
            </div>
          </section>

          <!-- 步骤3：商品清单 -->
          <section class="bg-white rounded-2xl p-6 lg:p-8 shadow-sm">
            <h2 class="text-lg font-semibold text-lumina-800 mb-5 flex items-center gap-2">
              <span class="w-6 h-6 rounded-full bg-lumina-800 text-white text-xs flex items-center justify-center">3</span>
              订单商品
            </h2>

            <div class="divide-y divide-lumina-100">
              <div
                v-for="item in cartItems"
                :key="item.id"
                class="py-4 flex gap-4 items-center"
              >
                <div class="w-16 h-16 rounded-lg bg-lumina-100 overflow-hidden shrink-0">
                  <img :src="item.image" :alt="item.name" class="w-full h-full object-cover" />
                </div>
                <div class="flex-1 min-w-0">
                  <h4 class="text-sm font-medium text-lumina-800 line-clamp-1">{{ item.name }}</h4>
                  <p class="text-xs text-lumina-500 mt-0.5">{{ item.skuText }}</p>
                </div>
                <div class="text-right shrink-0">
                  <span class="text-sm font-medium text-lumina-800">x{{ item.quantity }}</span>
                  <p class="text-sm text-lumina-600 mt-0.5">¥{{ formatPrice(item.price * item.quantity) }}</p>
                </div>
              </div>
            </div>
          </section>
        </div>

        <!-- 右侧：订单摘要（粘性定位） -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-2xl p-6 shadow-sm sticky top-24">
            <h3 class="text-lg font-semibold text-lumina-800 mb-6">订单摘要</h3>

            <div class="space-y-3 mb-6">
              <div class="flex justify-between text-sm">
                <span class="text-lumina-500">商品总价</span>
                <span class="text-lumina-800">¥{{ formatPrice(subtotal) }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-lumina-500">运费</span>
                <span class="text-success-emerald font-medium">免运费</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-lumina-500">优惠</span>
                <span class="text-alert-crimson font-medium">-¥{{ formatPrice(discount) }}</span>
              </div>
            </div>

            <div class="border-t border-lumina-100 pt-4 mb-6">
              <div class="flex justify-between items-end">
                <span class="text-sm font-medium text-lumina-800">应付金额</span>
                <span class="text-2xl font-bold text-lumina-800">¥{{ formatPrice(total) }}</span>
              </div>
            </div>

            <button
              @click="submitOrder"
              :disabled="!canSubmit"
              class="w-full py-3.5 text-sm font-semibold rounded-full transition-all duration-200"
              :class="canSubmit
                ? 'bg-lumina-800 text-white hover:bg-lumina-900 active:scale-[0.98]'
                : 'bg-lumina-300 text-lumina-500 cursor-not-allowed'"
            >
              提交订单
            </button>

            <p class="text-[11px] text-lumina-400 text-center mt-3 leading-relaxed">
              提交订单即表示您同意我们的<br />服务条款和隐私政策
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
```

### 6.6 登录/注册页面（Login/Register）— 极简居中表单

```vue
<template>
  <div class="min-h-screen bg-lumina-100 pt-14 flex items-center justify-center px-4">
    <div class="w-full max-w-md">
      <!-- 表单卡片 -->
      <div class="bg-white rounded-2xl shadow-[0_8px_30px_rgb(0,0,0,0.06)] p-8 lg:p-10">
        <!-- 标题 -->
        <div class="text-center mb-8">
          <h1 class="text-2xl font-semibold text-lumina-800 tracking-tight mb-2">
            {{ isLogin ? '欢迎回来' : '创建账户' }}
          </h1>
          <p class="text-sm text-lumina-500">
            {{ isLogin ? '登录您的Lumina账户继续' : '加入Lumina，开启数字生活新体验' }}
          </p>
        </div>

        <!-- 表单 -->
        <form @submit.prevent="handleSubmit" class="space-y-5">
          <!-- 用户名/邮箱（仅在注册时显示用户名字段） -->
          <div v-if="!isLogin">
            <label class="block text-sm font-medium text-lumina-700 mb-1.5">用户名</label>
            <input
              type="text"
              v-model="form.username"
              placeholder="请输入用户名"
              class="w-full px-4 py-2.5 bg-lumina-50 border border-lumina-200 rounded-xl text-sm text-lumina-800 placeholder:text-lumina-400 focus:outline-none focus:border-lumina-400 focus:ring-1 focus:ring-lumina-300 transition-all"
            />
          </div>

          <!-- 邮箱 -->
          <div>
            <label class="block text-sm font-medium text-lumina-700 mb-1.5">邮箱地址</label>
            <input
              type="email"
              v-model="form.email"
              placeholder="your@email.com"
              class="w-full px-4 py-2.5 bg-lumina-50 border border-lumina-200 rounded-xl text-sm text-lumina-800 placeholder:text-lumina-400 focus:outline-none focus:border-lumina-400 focus:ring-1 focus:ring-lumina-300 transition-all"
            />
          </div>

          <!-- 密码 -->
          <div>
            <label class="block text-sm font-medium text-lumina-700 mb-1.5">密码</label>
            <div class="relative">
              <input
                :type="showPassword ? 'text' : 'password'"
                v-model="form.password"
                placeholder="请输入密码"
                class="w-full px-4 py-2.5 bg-lumina-50 border border-lumina-200 rounded-xl text-sm text-lumina-800 placeholder:text-lumina-400 focus:outline-none focus:border-lumina-400 focus:ring-1 focus:ring-lumina-300 transition-all pr-10"
              />
              <button
                type="button"
                @click="showPassword = !showPassword"
                class="absolute right-3 top-1/2 -translate-y-1/2 text-lumina-400 hover:text-lumina-600 transition-colors"
              >
                <el-icon :size="16"><View v-if="showPassword" /><Hide v-else /></el-icon>
              </button>
            </div>
          </div>

          <!-- 记住我 & 忘记密码（仅登录） -->
          <div v-if="isLogin" class="flex items-center justify-between">
            <label class="flex items-center gap-2 cursor-pointer">
              <input type="checkbox" v-model="form.rememberMe" class="w-4 h-4 rounded border-lumina-300 text-lumina-800 focus:ring-lumina-500" />
              <span class="text-sm text-lumina-600">记住我</span>
            </label>
            <a href="#" class="text-sm text-glacier-blue hover:underline">忘记密码？</a>
          </div>

          <!-- 提交按钮 -->
          <button
            type="submit"
            class="w-full py-3 bg-lumina-800 text-white text-sm font-semibold rounded-xl hover:bg-lumina-900 active:scale-[0.98] transition-all duration-200 shadow-[0_4px_12px_rgb(29,29,31,0.15)]"
          >
            {{ isLogin ? '登 录' : '注 册' }}
          </button>
        </form>

        <!-- 分割线 -->
        <div class="my-6 flex items-center gap-4">
          <div class="flex-1 h-px bg-lumina-200"></div>
          <span class="text-xs text-lumina-400">或</span>
          <div class="flex-1 h-px bg-lumina-200"></div>
        </div>

        <!-- 社交登录按钮 -->
        <div class="flex gap-3">
          <button class="flex-1 py-2.5 border border-lumina-200 rounded-xl text-sm font-medium text-lumina-700 hover:bg-lumina-50 transition-colors flex items-center justify-center gap-2">
            <!-- WeChat Icon -->
            <span>微信登录</span>
          </button>
          <button class="flex-1 py-2.5 border border-lumina-200 rounded-xl text-sm font-medium text-lumina-700 hover:bg-lumina-50 transition-colors flex items-center justify-center gap-2">
            <!-- Apple Icon -->
            <span>Apple登录</span>
          </button>
        </div>

        <!-- 切换登录/注册 -->
        <p class="text-center text-sm text-lumina-500 mt-6">
          {{ isLogin ? '还没有账户？' : '已有账户？' }}
          <button
            @click="isLogin = !isLogin"
            class="font-medium text-lumina-800 hover:text-lumina-900 transition-colors"
          >
            {{ isLogin ? '立即注册' : '立即登录' }}
          </button>
        </p>
      </div>
    </div>
  </div>
</template>
```

**登录页样式要点：**
- 卡片圆角：`rounded-2xl`（16px）
- 阴影：`shadow-[0_8px_30px_rgb(0,0,0,0.06)]`，极度弥散
- 输入框：`rounded-xl`（12px）+ `bg-lumina-50`（浅灰背景）
- 按钮：`rounded-xl` + 深色背景 + 微妙投影
- 分割线：`h-px bg-lumina-200`，极细水平线

---

## 七、动效与交互规范

### 7.1 全局缓动曲线（Easing Functions）

所有动画必须使用以下缓动曲线，确保视觉一致性：

```css
/* iOS风格弹性缓动 - 用于入场动画、模态框 */
--ease-out-expo: cubic-bezier(0.16, 1, 0.3, 1);

/* 平滑减速 - 用于悬停、展开 */
--ease-out-quart: cubic-bezier(0.25, 1, 0.5, 1);

/* 自然弹性 - 用于按钮点击反馈 */
--ease-spring: cubic-bezier(0.34, 1.56, 0.64, 1);

/* 线性匀速 - 仅用于loading旋转 */
--ease-linear: linear;
```

### 7.2 动画时长规范

| 类型 | 时长 | 使用场景 |
|------|------|----------|
| 微交互（颜色变化） | 150ms | 按钮hover、链接变色 |
| 轻交互（阴影、边框） | 200-300ms | 卡片悬停、输入框focus |
| 中交互（位移、缩放） | 300-500ms | 模态框出现、抽屉滑出 |
| 重交互（页面切换） | 500-800ms | Hero区域入场、视差滚动 |
| 叙事性动画 | 1000ms+ | 首屏产品浮动、序列帧播放 |

### 7.3 常用交互动效清单

**A. 卡片悬停提升：**
```css
.card-hover {
  transition: all 0.3s var(--ease-out-quart);
}
.card-hover:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 40px rgb(0, 0, 0, 0.08);
}
```

**B. 按钮按下反馈：**
```css
.btn-press {
  transition: transform 0.15s var(--ease-spring);
}
.btn-press:active {
  transform: scale(0.95);
}
```

**C. 图片悬停缩放：**
```css
.img-zoom {
  transition: transform 0.7s var(--ease-out-expo);
}
.img-zoom:hover {
  transform: scale(1.05);
}
```

**D. 淡入上移（通用入场）：**
```css
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(24px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in-up {
  animation: fadeInUp 0.6s var(--ease-out-expo) forwards;
}
```

**E. 模态框遮罩淡入：**
```css
.modal-overlay {
  animation: fadeIn 0.3s var(--ease-out-quart);
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
```

### 7.4 视差滚动（Parallax Scrolling）

用于Hero区和产品详情页的沉浸式体验：

```typescript
// 使用VueUse的useScroll组合函数
import { useScroll } from '@vueuse/core'

const { y } = useWindow()

// Hero区产品图的视差偏移
const heroParallax = computed(() => ({
  transform: `translateY(${y.value * 0.15}px)`
}))

// 背景层的慢速移动
const bgParallax = computed(() => ({
  transform: `translateY(${y.value * 0.05}px)`
}))
```

**应用示例：**
```vue
<img
  src="/hero-product.png"
  :style="heroParallax"
  class="hero-image will-change-transform"
/>
```

### 7.5 滚动触发动画（Scroll-triggered Reveals）

当元素进入视口时触发入场动画，使用IntersectionObserver实现：

```typescript
import { useIntersectionObserver } from '@vueuse/core'

// 可复用的滚动触发composable
function useScrollReveal(options = {}) {
  const target = ref(null)
  const isVisible = ref(false)

  const { stop } = useIntersectionObserver(
    target,
    ([{ isIntersecting }]) => {
      if (isIntersecting) {
        isVisible.value = true
        stop() // 只触发一次
      }
    },
    { threshold: 0.15, ...options }
  )

  return { target, isVisible }
}
```

**使用示例：**
```vue
<template>
  <div ref="target" class="reveal-wrapper">
    <div :class="{ 'animate-fade-in-up': isVisible }">
      内容...
    </div>
  </div>
</template>

<script setup>
const { target, isVisible } = useScrollReveal()
</script>
```

---

## 八、阴影与层级系统（Elevation System）

### 8.1 Z轴层级定义

| 层级 | Z-index | 阴影值 | 使用场景 |
|------|---------|--------|----------|
| Level 0 | 0 | none | 页面背景、静态内容 |
| Level 1 | 10 | `shadow-sm` | 默认卡片、普通容器 |
| Level 2 | 20 | `shadow-[0_4px_20px_rgb(0,0,0,0.04)]` | 悬停态卡片、轻度提升 |
| Level 3 | 30 | `shadow-[0_8px_30px_rgb(0,0,0,0.06)]` | 表单卡片、弹窗背景 |
| Level 4 | 40 | `shadow-[0_12px_40px_rgb(0,0,0,0.08)]` | 重要浮层、吸顶栏 |
| Level 5 | 50 | `shadow-2xl` | 固定导航栏（Header） |
| Overlay | 60 | N/A | 模态框遮罩层 |
| Modal | 70 | `shadow-[0_20px_60px_rgb(0,0,0,0.15)]` | 弹窗/抽屉 |
| Toast | 80 | `shadow-xl` | 消息提示 |
| Tooltip | 90 | `shadow-lg` | 工具提示 |

### 8.2 毛玻璃效果（Glassmorphism）层级

毛玻璃是本设计系统的核心视觉语言，不同场景使用不同的强度：

```css
/* Level 1: 轻度毛玻璃 - 卡片覆盖 */
.glass-light {
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
}

/* Level 2: 中度毛玻璃 - 导航栏 */
.glass-medium {
  background: rgba(245, 245, 247, 0.8);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
}

/* Level 3: 强度毛玻璃 - 固定顶栏 */
.glass-strong {
  background: rgba(250, 250, 250, 0.65);
  backdrop-filter: blur(24px);
  -webkit-backdrop-filter: blur(24px);
}

/* Level 4: 遮罩级毛玻璃 - 弹窗背景 */
.glass-overlay {
  background: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
}
```

---

## 九、响应式设计原则

### 9.1 断点策略

采用**移动优先（Mobile First）**的策略，从小屏幕逐步增强到大屏幕：

```css
/* 基础样式（< 640px）：移动端 */

/* sm: ≥ 640px - 大手机/小平板 */
@media (min-width: 640px) { ... }

/* md: ≥ 768px - 平板竖屏 */
@media (min-width: 768px) { ... }

/* lg: ≥ 1024px - 平板横屏/小型笔记本 */
@media (min-width: 1024px) { ... }

/* xl: ≥ 1280px - 桌面显示器 */
@media (min-width: 1280px) { ... }

/* 2xl: ≥ 1536px - 大屏显示器 */
@media (min-width: 1536px) { ... }
```

### 9.2 关键响应式调整点

| 组件 | Mobile (< 768px) | Desktop (≥ 1024px) |
|------|-------------------|---------------------|
| Header导航 | 隐藏，显示汉堡菜单 | 显示完整导航链接 |
| Hero标题 | `text-5xl` | `text-8xl` |
| Bento Grid | 单列堆叠 | 4列非对称网格 |
| 商品网格 | 2列 | 4列 |
| 商品详情 | 单列（图在上，信息在下） | 双列并排 |
| 购物车 | 全屏抽屉 | 右侧420px抽屉 |
| 用户中心 | 顶部Tab切换 | 左右分栏布局 |
| 结算页 | 单列步骤式 | 左2右1双栏布局 |

### 9.3 触摸目标尺寸

确保所有可点击元素在移动端有足够的触摸区域：

| 元素 | 最小尺寸 | 推荐尺寸 |
|------|---------|---------|
| 按钮 | 44×44px | 48×48px |
| 图标按钮 | 40×40px | 44×44px |
| 链接/导航项 | 高度44px | 高度48px |
| Tab切换 | 高度44px | 高度48px |
| Checkbox/Radio | 24×24px | 24×24px（含label区域44px） |

---

## 十、无障碍访问（Accessibility）

### 10.1 颜色对比度

所有文本必须满足WCAG 2.1 AA级别的对比度要求：

| 文字类型 | 最小对比度 | 典型组合 |
|---------|-----------|----------|
| 正常文本（< 18px） | 4.5:1 | `#1d1d1f` on `#ffffff` = 15.8:1 ✓ |
| 大文本（≥ 18px / 14px bold） | 3:1 | `#1d1d1f` on `#f5f5f7` = 12.1:1 ✓ |
| UI组件 | 3:1 | `#86868b` on `#ffffff` = 4.5:1 ✓ |

### 10.2 键盘导航

- 所有交互元素可通过Tab键到达
- 当前焦点元素应有明显的视觉指示（outline或ring）
- Escape键关闭模态框/抽屉
- Enter/Space激活按钮和链接

### 10.3 语义化HTML

- 使用正确的标签：`<header>`, `<nav>`, `<main>`, `<section>`, `<footer>`
- 所有图片必须有`alt`属性
- 表单元素必须关联`<label>`
- 使用ARIA属性增强动态内容的可访问性

---

## 十一、图片资源规范

### 11.1 图片格式优先级

| 格式 | 使用场景 | 优势 |
|------|---------|------|
| WebP | 所有产品图、Banner图 | 更小的文件体积，更好的压缩率 |
| AVIF | Hero大图（如果浏览器支持） | 比WebP更小的体积 |
| PNG | 透明背景的Logo、图标 | 无损压缩，支持透明通道 |
| SVG | 图标、装饰性图形 | 无限缩放不失真 |

### 11.2 图片尺寸建议

| 用途 | 推荐尺寸 | 说明 |
|------|---------|------|
| Hero产品图 | 1600×1200px 以上 | 高清大图，展示产品细节 |
| 商品列表图 | 800×800px（正方形） | 1:1比例，统一视觉 |
| 商品详情主图 | 1200×1200px | 支持放大查看 |
| 缩略图 | 200×200px | 快速加载 |
| 分类Banner | 1920×600px | 宽幅横幅 |
| 博客/文章配图 | 1200×675px (16:9) | 适配社交媒体分享 |

### 11.3 图片优化策略

- 使用`loading="lazy"`延迟加载非首屏图片
- 提供`srcset`和`sizes`实现响应式图片
- 使用CSS的`object-fit: cover`或`contain`统一裁剪方式
- 为重要图片提供低质量占位符（LQIP）或骨架屏

---

## 十二、完整CSS变量汇总（可直接复制使用）

```css
@import "tailwindcss";

@theme {
  /* ===== 核心色阶 ===== */
  --color-lumina-50: #fafafa;
  --color-lumina-100: #f5f5f7;
  --color-lumina-200: #e8e8ed;
  --color-lumina-300: #d2d2d7;
  --color-lumina-400: #86868b;
  --color-lumina-500: #6e6e73;
  --color-lumina-600: #424245;
  --color-lumina-800: #1d1d1f;
  --color-lumina-900: #000000;
  --color-lumina-950: #0a0a0a;

  /* ===== 点缀色 ===== */
  --color-titanium: #86868b;
  --color-glacier-blue: #64b5f6;
  --color-solar-gold: #c9a96e;
  --color-arctic-white: #f0f4f8;
  --color-alert-crimson: #dc3545;
  --color-success-emerald: #28a745;

  /* ===== 功能色映射 ===== */
  --color-primary: var(--el-color-primary);
  --color-success: var(--el-color-success);
  --color-warning: var(--el-color-warning);
  --color-danger: var(--el-color-danger);
  --color-info: var(--el-color-info);
}

:root {
  /* ===== Element Plus 主题覆盖 ===== */
  --el-color-primary: #86868b;
  --el-color-primary-light-3: #a1a1a6;
  --el-color-primary-light-5: #b8b8bd;
  --el-color-primary-light-7: #d2d2d7;
  --el-color-primary-light-9: #f5f5f7;
  --el-color-primary-dark-2: #6e6e73;

  --el-color-success: #28a745;
  --el-color-warning: #c9a96e;
  --el-color-danger: #dc3545;
  --el-color-info: #86868b;

  /* ===== 背景色 ===== */
  --el-bg-color: #ffffff;
  --el-bg-color-page: #f5f5f7;
  --el-bg-color-overlay: rgba(255, 255, 255, 0.95);

  /* ===== 文字色 ===== */
  --el-text-color-primary: #1d1d1f;
  --el-text-color-regular: #424245;
  --el-text-color-secondary: #86868b;
  --el-text-color-placeholder: #d2d2d7;

  /* ===== 边框色 ===== */
  --el-border-color: #d2d2d7;
  --el-border-color-light: #e8e8ed;
  --el-border-color-lighter: #f5f5f7;
  --el-border-color-extra-light: #fafafa;

  /* ===== 圆角统一 ===== */
  --el-border-radius-base: 12px;
  --el-border-radius-small: 8px;
  --el-border-radius-round: 9999px;
}

/* ===== 全局基础样式 ===== */
body {
  margin: 0;
  background-color: var(--el-bg-color-page);
  color: var(--el-text-color-primary);
  font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text",
               "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
               "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-rendering: optimizeLegibility;
}

/* ===== 自定义滚动条（Webkit内核）===== */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: var(--el-border-color);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: var(--el-border-color-light);
}

/* ===== 选中文字样式 ===== */
::selection {
  background-color: rgba(134, 134, 139, 0.2);
  color: inherit;
}

/* ===== Focus可见性（无障碍）===== */
:focus-visible {
  outline: 2px solid var(--el-color-primary);
  outline-offset: 2px;
}

/* ===== 全局动画工具类 ===== */
.will-change-transform {
  will-change: transform;
}

/* ===== 减少动效偏好（尊重用户设置）===== */
@media (prefers-reduced-motion: reduce) {
  *,
  *::before,
  *::after {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    transition-duration: 0.01ms !important;
  }
}
```

---

## 十三、页面路由结构图

```
/ (ClientLayout)
├── /                          → HomeView（Hero视差 + Bento Box网格 + 特色服务条）
├── /products                  → ProductListView（分类Hero + 筛选工具栏 + 商品网格 + 分页）
├── /products/:category        → ProductListView（按分类过滤）
├── /product/:id               → ProductDetailView（明暗交替区块 + 吸顶购买栏 + 画廊 + 参数 + 评价）
├── /cart                      → CartView（Drawer抽屉式购物车）
├── /checkout                  → CheckoutView（三步式结算 + 地址/支付/清单 + 订单摘要）
├── /login                     → LoginView（居中极简表单）
├── /register                  → RegisterView（复用Login组件，切换模式）
└── /user (UserLayout)
    ├── /user/profile          → ProfileView（资料编辑）
    ├── /user/address          → AddressView（地址CRUD）
    ├── /user/favorites        → FavoritesView（收藏商品网格）
    └── /user/orders           → OrderView（订单历史列表）

/admin/login                   → AdminLoginView（全屏背景 + 居中登录）
/admin (AdminLayout)
├── /admin/dashboard           → DashboardView（统计卡片 + 数据图表）
├── /admin/system/users        → UserView（CRUD表格）
├── /admin/system/roles        → RoleView
├── /admin/system/menus        → MenuView
├── /admin/product/category    → CategoryView
├── /admin/product/attribute   → AttributeView
└── /admin/product/spu-sku     → SpuSkuView
```

---

## 十四、设计检查清单（Design Checklist）

在开发每个页面之前，请逐项核对以下清单：

### 视觉一致性
- [ ] 是否使用了正确的色彩变量（lumina-* 或 titanium/glacier-blue）？
- [ ] 是否避免了紫色渐变和高饱和度颜色？
- [ ] 圆角是否符合规范（卡片rounded-2xl，按钮rounded-full/xl）？
- [ ] 阴影是否使用了弥散式（rgb透明度）而非硬阴影？
- [ ] 字号和字重是否遵循层级规范？

### 布局与间距
- [ ] 是否使用了正确的容器最大宽度（max-w-7xl）？
- [ ] 内外边距是否符合间距规范（gap-4/6/8，p-5/6/8/10）？
- [ ] 响应式断点是否正确处理了移动端和桌面端？
- [ ] Grid/Flex布局是否合理？

### 交互与动效
- [ ] 所有可点击元素是否有hover状态？
- [ ] 按钮是否有active:scale-95的下压反馈？
- [ ] 动画时长是否在规定范围内（150-800ms）？
- [ ] 是否使用了统一的缓动曲线（cubic-bezier(0.16, 1, 0.3, 1)）？
- [ ] 毛玻璃效果是否正确应用（backdrop-blur + 半透明背景）？

### 性能与可访问性
- [ ] 图片是否添加了loading="lazy"？
- [ ] 图片是否有合适的alt属性？
- [ ] 颜色对比度是否满足WCAG AA标准？
- [ ] 键盘导航是否可用？
- [ ] 是否尊重prefers-reduced-motion设置？

### 内容真实性
- [ ] 是否使用了真实的产品场景描述（而非泛泛的"精美图片"）？
- [ ] 文案是否简洁、专业、无emoji滥用？
- [ ] 价格、规格等信息是否合理可信？

---

## 附录：设计灵感来源与参考

### 主要参考对象
1. **Apple官网** (apple.com) - 极简布局、毛玻璃导航、明暗交替、Bento Box网格
2. **Nothing官网** (nothing.tech) - 字体排印、点状装饰、克制的高级感
3. **Linear官网** (linear.app) - 动效流畅度、微交互细节、暗色模式处理
4. **Stripe官网** (stripe.com) - 渐变运用、代码块呈现、视觉节奏

### 推荐阅读资源
- [Apple Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/)
- [Material Design 3 - Elevation](https://m3.material.io/styles/elevation/overview)
- [Tailwind CSS Documentation](https://tailwindcss.com/docs)

---

> **文档版本**: v1.0
> **最后更新**: 2026-01-05
> **适用项目**: Lumina 数码商城前端
> **设计理念**: 光影科技美学 - 自然优雅与数码精密的完美融合
