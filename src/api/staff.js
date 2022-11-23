import request from '@/utils/request'
export function deleteInfo(data) {
  return request({
    url: '/person/delete/' + data,
    method: 'delete',
  })
}
export function addStaff(data) {
  return request({
    url: '/person/add',
    method: 'post',
    data,
  })
}
export function updateStaff(data) {
  return request({
    url: '/person/update',
    method: 'post',
    data,
  })
}
export function findAll() {
  return request({
    url: '/person/findall',
    method: 'get',
  })
}
export function findByCondition(data) {
  return request({
    url: '/person/findbycondition',
    method: 'get',
    params: data,
  })
}
