// import store from '@/store'
import axios from 'axios'
// import { getToken } from '@/utils/auth'

// 创建 axios 实例
const service = axios.create({
  // url =base url+request url
  baseURL: 'http://localhost:8083/', // 请求地址前缀，将自动加在 url 前面
  // timeout: 6000, // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    // if (store.getters.token) {
    //   config.headers.Authorization = getToken()
    // }
    return config
  },
  (error) => {
    console.log(error)
    return Promise.reject(error)
  }
)
// 响应拦截器
service.interceptors.response.use(
  (response) => {
    console.log('响应拦截器拦截的结果：', response.data)
    return response.data
  },
  (error) => {
    console.log('err' + error) // for debug
  }
)

export default service
