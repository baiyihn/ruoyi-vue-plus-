import request from '@/utils/request'

// 查询留言管理列表
export function listMessage(query) {
  return request({
    url: '/message/message/list',
    method: 'get',
    params: query
  })
}

// 查询留言管理详细
export function getMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'get'
  })
}

// 新增留言管理
export function addMessage(data) {
  return request({
    url: '/message/message',
    method: 'post',
    data: data
  })
}

// 修改留言管理
export function updateMessage(data) {
  return request({
    url: '/message/message',
    method: 'put',
    data: data
  })
}

// 删除留言管理
export function delMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'delete'
  })
}

// 审核留言管理
export function auditMessage(query){
  return request({
    url:'/message/message/audit',
    method: 'get',
    params: query
  })
}
