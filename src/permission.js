import router from '@/router'
import store from './store'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
NProgress.configure({ showSpinner: false }) // NProgress Configuration
const whiteList = ['/login'] // no redirect whitelist

// 路由全局前置守卫
router.beforeEach(async (to, from, next) => {
  NProgress.start()
  // eslint-disable-next-line prefer-regex-literals
  // const pathReg = new RegExp(/home/)
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/home' })
      NProgress.done()
    } else {
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      if (hasRoles) {
        console.log('router.routes', router)
        next()
      } else {
        try {
          // console.log('getInfo中的结果：', await store.dispatch('user/getInfo'))
          store.dispatch('user/getOptions')
          const { roles, authority } = await store.dispatch('user/getInfo')
          console.log('permission中的authority：', authority)
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)
          router.addRoutes(accessRoutes)
          next({ ...to, replace: true })
        } catch (error) {
          // console.log('刷新后出现的错误：', error)
          await store.dispatch('user/resetToken')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})
router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
