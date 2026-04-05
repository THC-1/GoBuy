# Checklist

## 后端验证
- [ ] 项目根目录存在 `sql/` 文件夹
- [ ] `sql/01_create_user_table.sql` 文件存在且命名符合 `序号_描述.sql` 规范
- [ ] SQL文件头部包含注释说明用途和执行前提条件
- [ ] SQL语句可在MySQL 8.0+中正确执行（CREATE TABLE语法无误）
- [ ] pom.xml 包含所有必需依赖（MyBatis-Plus、MySQL、jjwt、Security、Lombok、Redis）
- [ ] application.yml 配置完整（datasource/redis/mybatis-plus/jwt四组配置）
- [ ] Result<T> 统一响应包装类可正常序列化为 {code, message, data} 结构
- [ ] GlobalExceptionHandler 可捕获 MethodArgumentNotValidException 并返回400 + 字段错误信息
- [ ] GlobalExceptionHandler 可捕获 BusinessException 并返回自定义错误码
- [ ] GlobalExceptionHandler 所有 catch 块均有 log.error 记录，无空 catch
- [ ] JwtUtil.generateAccessToken() 和 generateRefreshToken() 可正常生成不同过期时间的Token
- [ ] JwtUtil.parseToken() 可正确解析Token中的userId等claims
- [ ] SecurityConfig 正确放行 /api/v1/auth/** 路径（permitAll）
- [ ] POST /api/v1/auth/register 接口可用：合法请求返回201+Token对，重复用户名返回409，参数非法返回400
- [ ] POST /api/v1/auth/login 接口可用：正确凭据返回200+Token对，错误密码返回401，禁用账号返回403
- [ ] POST /api/v1/auth/refresh-token 接口可用：有效RefreshToken返回新Token对，无效/过期返回401
- [ ] GET /api/v1/users/me 接口可用：携带有效AccessToken返回UserVO，无Token返回401
- [ ] POST /api/v1/auth/logout 接口可用：清除Token缓存返回200
- [ ] User 实体类使用 @TableLogic 注解标记逻辑删除字段
- [ ] 密码以 BCrypt 方式加密存储（非明文）
- [ ] AuthServiceImpl 中无循环内数据库查询（无N+1问题）
- [ ] 涉及多表写操作的方法标注 @Transactional 且范围精准

## 前端基础架构验证
- [ ] npm install 成功无报错（element-plus/tailwindcss/axios/vueuse 均安装）
- [ ] Vite dev server 可正常启动（npm run dev 无编译错误）
- [ ] src/styles/index.css 包含完整的 @theme 色彩体系（lumina-50~950 + titanium/glacier-blue等点缀色）
- [ ] Element Plus 主题覆盖生效（--el-color-primary: #86868b 等变量在浏览器中可见）
- [ ] 全局字体栈设置为 -apple-system / SF Pro / PingFang SC / Microsoft YaHei
- [ ] Axios 实例 baseURL 指向后端地址（默认 http://localhost:8080/api/v1）
- [ ] 请求拦截器自动注入 Authorization: Bearer <token>
- [ ] 401 响应触发 refreshToken 流程（控制台可观测到刷新请求）
- [ ] Token 刷新失败后清除状态并跳转 /login
- [ ] 非401 错误显示 ElMessage 错误提示

## 登录注册页面验证
- [ ] /login 路由正常渲染 LoginView 页面
- [ ] 登录模式显示 account + password + rememberMe 字段
- [ ] 注册模式额外显示 username + confirmPassword 字段
- [ ] password 输入框右侧有可见性切换图标（眼睛图标）
- [ ] username 失去焦点时：少于2字符或含非法字符显示错误提示
- [ ] email 失去焦点时：格式不合法显示错误提示
- [ ] password 失去焦点时：少于6字符或不含字母数字组合显示错误提示
- [ ] confirmPassword 与 password 不一致时显示错误提示
- [ ] 提交按钮点击后有 loading 状态（按钮禁用+loading spinner）
- [ ] 注册成功后跳转首页且 Header 显示用户头像
- [ ] 登录成功后 Pinia Store 中 isLoggedIn === true
- [ ] localStorage 中存储了 token 和 userInfo
- [ ] 页面刷新后登录态保持（从localStorage恢复）

## 全局布局验证
- [ ] ClientLayout 正确组装 Header + RouterView + Footer
- [ ] Header 固定在页面顶部（fixed top-0 z-50）
- [ ] Header 高度为 h-14 (56px)
- [ ] 页面未滚动时 Header 背景透明
- [ ] 页面滚动后 Header 变为毛玻璃效果（backdrop-blur-xl 可见背景模糊）
- [ ] 过渡动画平滑（约500ms无明显闪烁）
- [ ] Logo "Lumina." 点击可跳转首页
- [ ] 导航链接当前页有钛金属色下划线
- [ ] 导航链接 hover 有下划线展开动画（duration-300）
- [ ] 未登录时显示"登录"按钮（rounded-full border 样式）
- [ ] 已登录时显示圆形头像（w-8 h-8 rounded-full）
- [ ] 移动端宽度(<768px)隐藏导航链接，显示汉堡菜单图标
- [ ] 点击汉堡菜单弹出右侧抽屉（w-72）
- [ ] 抽屉遮罩层半透明 + backdrop-blur 效果
- [ ] 点击遮罩或链接可关闭抽屉
- [ ] Footer 背景色为 bg-lumina-900（深灰非纯黑）
- [ ] Footer 显示4列链接区域（产品/服务/关于/支持）
- [ ] Footer 底部有版权声明和法律条款链接

## 首页验证
- [ ] Hero区高度为 85vh（移动端最小600px）
- [ ] Hero区背景为暗色渐变（#0a0a0a → #1a1a1c）
- [ ] 产品标题字号响应式（移动端text-5xl桌面端text-8xl）
- [ ] 副标题颜色为 text-white/70（70%白色透明度）
- [ ] 主CTA按钮为白底黑字 rounded-full
- [ ] 次CTA按钮为描边 rounded-full border-white/30
- [ ] 产品图有浮动动画（缓慢上下±10px，约6秒周期）
- [ ] 标题/副标题/按钮有依次入场动画（staggered fade-in-up）
- [ ] 底部有滚动弹跳指示器
- [ ] Bento Grid 在桌面端(≥1024px)呈4列不规则网格
- [ ] 旗舰产品区块占 2x2（col-span-2 row-span-2）
- [ ] 商品卡片悬停有上浮4px效果
- [ ] 商品卡片悬停图片有 scale(1.05) 缩放效果（duration 约700ms）
- [ ] 点击商品卡片跳转至 /product/:id
- [ ] 特色服务横幅四宫格正确展示（免费配送/正品保障/售后无忧/分期免息）
- [ ] 服务图标容器为圆形（w-14 h-14 rounded-full）且 hover 变色
- [ ] Footer 多列链接布局完整
- [ ] 移动端 Bento Grid 单列堆叠
- [ ] 所有图片有 loading="lazy"

## 商品详情页验证
- [ ] 面包屑导航显示 "首页 / 分类名 / 商品名称"
- [ ] 产品画廊主图为正方形（aspect-square）
- [ ] 缩略图列表横向排列（64x64px 圆角）
- [ ] 点击缩�略图可切换主图
- [ ] 当前选中缩略图有白色边框高亮
- [ ] 产品名称为 text-3xl~4xl font-semibold tracking-tight
- [ ] 价格为 text-3xl font-bold ¥格式化
- [ ] 原价有删除线样式（如有折扣）
- [ ] SKU选择器每组只可选一个选项
- [ ] SKU选中项反色高亮（白底深色文字）
- [ ] SKU禁用项 opacity 降低且不可点击
- [ ] 数量选择器最小值为1，最大值不超过库存
- [ ] 减号按钮在 quantity=1 时禁用
- [ ] 滚动超过首屏后吸顶购买栏出现
- [ ] 吸顶购买栏显示正确的商品名和价格
- [ ] 吸顶购买栏有 slide-down 从顶部滑入动画
- [ ] 产品亮点区为3列网格（桌面端）
- [ ] 技术参数表 Key-Value 对齐美观
- [ ] 区块按 Dark(950) → Light(100) → Dark(900) 交替排列
- [ ] 移动端详情页单列布局（图在上信息在下）
- [ ] 页面滚动流畅无抖动

## 设计规范遵循验证
- [ ] 未使用紫色渐变或高饱和度糖果色
- [ ] 未使用 emoji 作为 UI 元素
- [ ] 卡片圆角使用 rounded-2xl 或 rounded-3xl（非默认4px）
- [ ] 按钮圆角使用 rounded-full 或 rounded-xl
- [ ] 阴影使用弥散式 rgb(0,0,0,透明度) 格式
- [ ] 字号和字重符合设计规范层级
- [ ] 动效时长在 150ms ~ 800ms 范围内
- [ ] 使用统一缓动曲线 cubic-bezier(0.16, 1, 0.3, 1)
- [ ] 图片 alt 属性不为空
- [ ] 表单元素关联了 label
- [ ] 尊重 prefers-re reduced-motion 设置
