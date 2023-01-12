import request from '@/utils/request'

// 查询分类管理列表
export function listCategory(query) {
  return request({
    url: '/category/category/list',
    method: 'get',
    params: query
  })
}

// 查询分类管理详细
export function getCategory(id) {
  return request({
    url: '/category/category/' + id,
    method: 'get'
  })
}

// 新增分类管理
export function addCategory(data) {
  return request({
    url: '/category/category',
    method: 'post',
    data: data
  })
}

// 修改分类管理
export function updateCategory(data) {
  return request({
    url: '/category/category',
    method: 'put',
    data: data
  })
}

// 删除分类管理
export function delCategory(id) {
  return request({
    url: '/category/category/' + id,
    method: 'delete'
  })
}
