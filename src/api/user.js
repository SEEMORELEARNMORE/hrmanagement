import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login/match',
    method: 'post',
    data,
  })
}
export function getInfo(data) {
  return request({
    url: '/person/findbyid/' + data,
    method: 'get',
  })
}
export function updatePsw(data) {
  return request({
    url: '/person/updatepsw',
    method: 'post',
    data,
  })
}
export function getOptions() {
  return request({
    url: '/codeOptions',
    method: 'get',
  })
}
export function addData() {}
export function uploadAvatar(data) {
  return request({
    url: '/person/uploadavatar',
    method: 'post',
    data,
  })
}
