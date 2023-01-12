import request from '@/utils/request'

// 查询页面管理列表
export function listPage(query) {
  return request({
    url: '/page/page/list',
    method: 'get',
    params: query
  })
}

// 查询页面管理详细
export function getPage(id) {
  return request({
    url: '/page/page/' + id,
    method: 'get'
  })
}

// 新增页面管理
export function addPage(data) {
  return request({
    url: '/page/page',
    method: 'post',
    data: data
  })
}

// 修改页面管理
export function updatePage(data) {
  return request({
    url: '/page/page',
    method: 'put',
    data: data
  })
}

// 删除页面管理
export function delPage(id) {
  return request({
    url: '/page/page/' + id,
    method: 'delete'
  })
}
