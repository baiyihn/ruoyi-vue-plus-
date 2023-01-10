import request from '@/utils/request'

// 查询评论管理列表
export function listComment(query) {
  return request({
    url: '/comment/comment/list',
    method: 'get',
    params: query
  })
}

// 查询评论管理详细
export function getComment(id) {
  return request({
    url: '/comment/comment/' + id,
    method: 'get'
  })
}

// 新增评论管理
export function addComment(data) {
  return request({
    url: '/comment/comment',
    method: 'post',
    data: data
  })
}

// 修改评论管理
export function updateComment(data) {
  return request({
    url: '/comment/comment',
    method: 'put',
    data: data
  })
}

// 删除评论管理
export function delComment(id) {
  return request({
    url: '/comment/comment/' + id,
    method: 'delete'
  })
}
