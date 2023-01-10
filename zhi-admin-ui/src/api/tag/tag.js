import request from '@/utils/request'

// 查询标签管理列表
export function listTag(query) {
  return request({
    url: '/tag/tag/list',
    method: 'get',
    params: query
  })
}

// 查询标签管理详细
export function getTag(id) {
  return request({
    url: '/tag/tag/' + id,
    method: 'get'
  })
}

// 新增标签管理
export function addTag(data) {
  return request({
    url: '/tag/tag',
    method: 'post',
    data: data
  })
}

// 修改标签管理
export function updateTag(data) {
  return request({
    url: '/tag/tag',
    method: 'put',
    data: data
  })
}

// 删除标签管理
export function delTag(id) {
  return request({
    url: '/tag/tag/' + id,
    method: 'delete'
  })
}
