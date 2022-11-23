import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/login'
import Home from '@/views/home'
import Administrator from '@/views/administrator'
import Change from '@/views/change'
import Staff from '@/views/staff'
// import Notice from '@/views/notice'
import Browse from '@/views/notice/browse.vue'
import Create from '@/views/notice/create.vue'
import userAttendence from '@/views/attendence/user.vue'
import allUserAttendence from '@/views/attendence/admin.vue'
import User from '@/views/user'
import userSalary from '@/views/salary/user.vue'
import allUserSalary from '@/views/salary/admin.vue'

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err)
}

Vue.use(VueRouter)

export const constantRoutes = [
  {
    path: '/userinfo',
    component: Home,
    children: [
      {
        path: '/userinfo',
        name: 'user',
        component: User,
        meta: {
          title: 'User',
        },
      },
    ],
    hidden: true,
  },
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    component: Login,
    hidden: true,
  },
  {
    path: '/home',
    // redirect: '/home/administrator',
    component: Home,
    name: 'Home',
    meta: { title: 'Home', affix: true },
    children: [
      {
        path: '/home/:path(.*)',
        component: () => import('@/views/redirect/index'),
      },
    ],
  },
  {
    path: '/messagelist',
    component: Home,
    children: [
      {
        path: '/messagelist',
        component: () => import('@/views/messagelist'),
        name: 'messageList',
      },
    ],
  },
]
export const asyncRoutes = [
  {
    path: '/administrator',
    component: Home,
    meta: { roles: ['admin'] },
    children: [
      {
        path: '/administrator',
        name: 'administrator',
        component: Administrator,
        meta: {
          title: 'Administrator',
          icon: 'administrator',
        },
      },
    ],
  },
  {
    path: '/attendence',
    component: Home,
    redirect: '/attendence/user',
    name: 'Attendence',
    meta: {
      title: 'Attendence',
      icon: 'attendence',
      roles: ['admin', 'editor'],
    },
    children: [
      {
        path: 'user',
        component: userAttendence,
        name: 'userAttendence',
        meta: {
          title: 'Attendence',
          icon: 'userattendence',
          roles: ['editor'],
        },
      },
      {
        path: 'alluser',
        component: allUserAttendence,
        name: 'allUserAttendence',
        meta: {
          title: 'allUserAttendence',
          icon: 'alluserattendence',
          roles: ['admin'],
        },
      },
    ],
  },
  {
    path: '/salary',
    component: Home,
    redirect: '/salary/user',
    meta: {
      title: 'Salary',
      icon: 'salary',
      roles: ['admin', 'editor'],
    },
    name: 'Salary',
    children: [
      {
        path: 'user',
        component: userSalary,
        name: 'userSalary',
        // hidden: true,
        meta: {
          title: 'Salary',
          icon: 'usersalary',
          roles: ['editor'],
        },
      },
      {
        path: 'alluser',
        component: allUserSalary,
        name: 'allUserSalary',
        // hidden: true,
        meta: {
          title: 'allUserSalary',
          icon: 'allusersalary',
          roles: ['admin'],
        },
      },
    ],
  },

  {
    path: '/change',
    component: Home,
    meta: { roles: ['admin'] },
    children: [
      {
        path: '/change',
        component: Change,
        name: 'change',
        // hidden: true,
        meta: {
          title: 'Change',
          icon: 'change',
        },
      },
    ],
  },
  {
    path: '/notice',
    // alwaysShow: true, // will always show the root menu
    component: Home,
    redirect: '/notice/browse',
    meta: {
      title: 'Notice',
      icon: 'notice',
      roles: ['admin', 'editor'], // you can set roles in root nav
    },
    name: 'Notice',
    children: [
      {
        path: 'create',
        component: Create,
        name: 'create',
        // hidden: true,
        meta: {
          title: 'Create',
          icon: 'create',
          roles: ['admin'], // or you can only set roles in sub nav
        },
      },
      {
        path: 'detail/:id(\\d+)',
        component: () => import('@/views/notice/detail.vue'),
        name: 'ArticleDetail',
        meta: { title: 'Article Detail', noCache: true, activeMenu: '/notice/browse' },
        hidden: true,
      },
      {
        path: 'browse',
        component: Browse,
        name: 'browse',
        // hidden: true,
        meta: {
          title: 'Browse',
          icon: 'browse',
          roles: ['editor'], // or you can only set roles in sub nav
        },
      },
    ],
  },
  {
    path: '/staff',
    // redirect: '/staff/formal',
    // alwaysShow: true, // will always show the root menu
    component: Home,
    name: 'Staff',
    meta: { roles: ['admin'] },
    children: [
      {
        path: '/staff',
        component: Staff,
        name: 'staff',
        meta: {
          title: 'Staff',
          icon: 'formal',
        },
      },
    ],
  },
]
const createRouter = () =>
  new VueRouter({
    // mode: 'history',
    routes: constantRoutes,
    scrollBehavior: () => ({ y: 0 }),
  })
const router = createRouter()
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
