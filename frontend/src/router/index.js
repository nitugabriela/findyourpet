import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import HomeView from '../views/HomeView.vue'

const routes = [
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/', name: 'Home', component: HomeView },
  {
    path: '/announcements',
    name: 'Announcements',
    component: () => import('../views/Announcements.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/browse',
    name: 'Browse',
    component: () => import('../views/Browse.vue')
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue')
   },
   {
     path: '/post',
     name: 'PostAnnouncement',
     component: () => import('../views/postAnnouncement.vue')
   }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
