import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/layouts/ClientLayout.vue'),
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('@/views/client/HomeView.vue'),
        },
        {
          path: 'product/:id',
          name: 'product-detail',
          component: () => import('@/views/client/ProductDetailView.vue'),
        },
        {
          path: 'cart',
          name: 'cart',
          component: () => import('@/views/client/CartView.vue'),
        },
        {
          path: 'checkout',
          name: 'checkout',
          component: () => import('@/views/client/CheckoutView.vue'),
        },
        {
          path: 'login',
          name: 'client-login',
          component: () => import('@/views/client/LoginView.vue'),
        },
        {
          path: 'register',
          name: 'client-register',
          component: () => import('@/views/client/RegisterView.vue'),
        },
        {
          path: 'user',
          component: () => import('@/views/client/user/UserLayout.vue'),
          redirect: '/user/profile',
          children: [
            {
              path: 'profile',
              name: 'user-profile',
              component: () => import('@/views/client/user/ProfileView.vue'),
            },
            {
              path: 'address',
              name: 'user-address',
              component: () => import('@/views/client/user/AddressView.vue'),
            },
            {
              path: 'favorites',
              name: 'user-favorites',
              component: () => import('@/views/client/user/FavoritesView.vue'),
            },
          ]
        },
      ],
    },
    {
      path: '/admin/login',
      name: 'admin-login',
      component: () => import('@/views/admin/LoginView.vue'),
    },
    {
      path: '/admin',
      component: () => import('@/layouts/AdminLayout.vue'),
      children: [
        {
          path: 'dashboard',
          name: 'admin-dashboard',
          component: () => import('@/views/admin/DashboardView.vue'),
        },
        {
          path: 'system/users',
          name: 'admin-system-users',
          component: () => import('@/views/admin/system/UserView.vue'),
        },
        {
          path: 'system/roles',
          name: 'admin-system-roles',
          component: () => import('@/views/admin/system/RoleView.vue'),
        },
        {
          path: 'system/menus',
          name: 'admin-system-menus',
          component: () => import('@/views/admin/system/MenuView.vue'),
        },
        {
          path: 'system/dicts',
          name: 'admin-system-dicts',
          component: () => import('@/views/admin/system/DictView.vue'),
        },
        {
          path: 'product/category',
          name: 'admin-product-category',
          component: () => import('@/views/admin/product/CategoryView.vue'),
        },
        {
          path: 'product/attribute',
          name: 'admin-product-attribute',
          component: () => import('@/views/admin/product/AttributeView.vue'),
        },
        {
          path: 'product/spu-sku',
          name: 'admin-product-spusku',
          component: () => import('@/views/admin/product/SpuSkuView.vue'),
        },
        {
          path: '',
          redirect: '/admin/dashboard',
        },
      ],
    },
  ],
})

export default router
