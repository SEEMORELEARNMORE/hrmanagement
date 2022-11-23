import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
Vue.use(Vuex)

// export default new Vuex.Store({
//   state: {
//     department: [],
//     job: [],
//     edu_level: [],
//     total: [],
//     psw: '',
//     id: '',
//     language: 'zh',
//   },
//   getters: {},
//   mutations: {
//     setDepartment(state, val) {
//       state.department = val
//     },
//     setJob(state, val) {
//       state.job = val
//     },
//     setEdu_level(state, val) {
//       state.edu_level = val
//     },
//     setTotal(state, val) {
//       state.total = val
//     },
//     setPsw(state, val) {
//       state.psw = val
//     },
//     setId(state, val) {
//       state.id = val
//     },
//     setLanguage(state, val) {
//       state.language = val
//     },
//   },
//   actions: {},
//   modules: {},
// })

const modulesFiles = require.context('./modules', true, /\.js$/)

// you do not need `import app from './modules/app'`
// it will auto require all vuex module from modules file
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // set './app.js' => 'app'
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  modules[moduleName] = value.default
  return modules
}, {})

const store = new Vuex.Store({
  modules,
  getters,
})

export default store
