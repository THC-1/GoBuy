import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/LoginView.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      component: () => import('@/layouts/ClientLayout.vue'),
      meta: { requiresAuth: false },
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('@/views/HomeView.vue')
        },
        {
          path: 'product/:id',
          name: 'ProductDetail',
          component: () => import('@/views/ProductDetailView.vue'),
          props: true
        }
      ]
    }
  ]
})

const WHITE_LIST = ['/login']

router.beforeEach((to, _from, next) => {
  const userStore = useUserStore()
  userStore.loadFromStorage()

  if (WHITE_LIST.includes(to.path)) {
    next()
    return
  }

  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({ path: '/login', query: { redirect: to.fullPath } })
    return
  }

  if (to.path === '/login' && userStore.isLoggedIn) {
    next({ path: '/' })
    return
  }

  next()
})

export default router
