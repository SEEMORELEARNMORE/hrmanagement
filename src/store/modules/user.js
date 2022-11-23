import { login, getInfo, getOptions } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
const state = {
  token: getToken(),
  name: '',
  avatar: '',
  authority: '',
  roles: [],
  department: [],
  job: [],
  edu_level: [],
  total: [],
  psw: '',
  id: '',
  job_options: [],
  department_options: [],
  edu_level_options: [],
  unread: [],
}
const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_AUTHORITY: (state, authority) => {
    state.authority = authority
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  setDepartment(state, val) {
    state.department = val
  },
  setJob(state, val) {
    state.job = val
  },
  setEdu_level(state, val) {
    state.edu_level = val
  },
  setTotal(state, val) {
    state.total = val
  },
  setPsw(state, val) {
    state.psw = val
  },
  setId(state, val) {
    state.id = val
  },
  setUnread(state, val) {
    state.unread = val
  },
  setJob_options(state, val) {
    state.job_options = val
  },
  setDepartment_options(state, val) {
    state.department_options = val
  },
  setEdu_level_options(state, val) {
    state.edu_level_options = val
  },
}
const actions = {
  login({ commit }, userInfo) {
    const { id, passwd } = userInfo
    const _this = this
    return new Promise((resolve, reject) => {
      login({ id: id.trim(), passwd: passwd })
        .then((response) => {
          // console.log(response)
          const { authority } = response
          const tokens = ['editor-', 'admin-']
          const token = tokens[authority] + id
          // console.log('登录之后的authority:', authority)
          commit('SET_TOKEN', token)
          _this.dispatch('user/getInfo')
          setToken(token)
          resolve()
        })
        .catch((error) => {
          console.log('login请求发生错误' + error)
          reject(error)
        })
    })
  },
  logout({ commit, state, dispatch }) {
    commit('SET_TOKEN', '')
    commit('SET_ROLES', [])
    removeToken()
    resetRouter()

    dispatch('tagsView/delAllViews', null, { root: true })
  },
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      const id = state.token.substr(-7)
      getInfo(id)
        .then((response) => {
          console.log('getInfo中的值：', response)
          const { avatar, name, authority } = response
          const roles = authority === 0 ? ['editor'] : ['admin']
          commit('SET_ROLES', roles)
          commit('SET_NAME', name)
          commit('SET_AUTHORITY', authority)
          commit('SET_AVATAR', avatar)
          resolve({ roles, name, avatar })
        })
        .catch((error) => {
          console.log('getInfo发生错误：' + error)
          reject(error)
        })
    })
  },
  resetToken({ commit }) {
    return new Promise((resolve) => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },
  getOptions({ commit }) {
    return new Promise((resolve, reject) => {
      getOptions().then((response) => {
        // console.log('getOptions中的数据:', response)
        commit('setJob_options', response.job)
        commit('setDepartment_options', response.dep)
        commit('setEdu_level_options', response.edu)
      })
    })
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
}
