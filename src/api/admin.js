import request from '@/utils/request'

// 管理员相关请求

// 人事变动相关请求
export function getChangeData() {
  return request({
    url: '/change/findall',
    method: 'get',
  })
}
export function findChangeData(data) {
  return request({
    url: '/change/findbycondition',
    method: 'get',
    params: data,
  })
}

// 薪水相关请求
export function getSalaryList() {
  return request({
    url: '/salary/findall',
    method: 'get',
  })
}
export function deleteSalary(data) {
  return request({
    url: '/salary/delete',
    method: 'delete',
    params: data,
  })
}
export function addSalary(data) {
  return request({
    url: '/salary/add',
    method: 'post',
    data,
  })
}
export function updateSalary(data) {
  return request({
    url: '/salary/update',
    method: 'post',
    data,
  })
}
export function findSalaryData(data) {
  return request({
    url: '/salary/findbycondition',
    method: 'post',
    data,
  })
}

// 考勤相关请求
export function getAttendanceList() {
  return request({
    url: '/attendance/findall',
    method: 'get',
  })
}
export function addAttendance(data) {
  return request({
    url: '/attendance/add',
    method: 'post',
    data,
  })
}
export function updateAttendance(data) {
  return request({
    url: '/attendance/update',
    method: 'get',
    params: data
  })
}
export function findAttendanceData(data) {
  return request({
    url: '/attendance/findbycondition',
    method: 'post',
    data,
  })
}
