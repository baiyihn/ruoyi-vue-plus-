import request from '@/utils/request'

// 查询友链管理列表
export function listFriendLink(query) {
  return request({
    url: '/friendLink/friendLink/list',
    method: 'get',
    params: query
  })
}

// 查询友链管理详细
export function getFriendLink(id) {
  return request({
    url: '/friendLink/friendLink/' + id,
    method: 'get'
  })
}

// 新增友链管理
export function addFriendLink(data) {
  return request({
    url: '/friendLink/friendLink',
    method: 'post',
    data: data
  })
}

// 修改友链管理
export function updateFriendLink(data) {
  return request({
    url: '/friendLink/friendLink',
    method: 'put',
    data: data
  })
}

// 删除友链管理
export function delFriendLink(id) {
  return request({
    url: '/friendLink/friendLink/' + id,
    method: 'delete'
  })
}
