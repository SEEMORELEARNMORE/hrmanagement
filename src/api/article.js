import request from '@/utils/request'

export function getImgUrl(data) {
  return request({
    url: '/upload/uploadImg',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data,
  })
}

export function getData() {
  return request({
    url: '/article/getlist',
    method: 'get',
  })
}

export function getSearchData(data) {
  return request({
    url: '/article/findbycondition',
    method: 'post',
    data,
  })
}

export function getById(id) {
  return request({
    url: '/article/getbyid/' + id,
    method: 'get',
  })
}

export function postArticle(data) {
  return request({
    url: '/article/add',
    method: 'post',
    data,
  })
}

export function getUnRead(id) {
  return request({
    url: '/unread/isunread/' + id,
    method: 'get',
  })
}

export function messageTotal(id) {
  return request({
    url: '/unread/total/' + id,
    method: 'get',
  })
}

export function getListWithState(id) {
  return request({
    url: '/article/getlistwithstate/' + id,
    method: 'get',
  })
}

export function readArticle(data) {
  return request({
    url: '/unread/read',
    method: 'post',
    data,
  })
}
