import request from '@/utils/request'

// 查询说说管理列表
export function listTalk(query) {
  return request({
    url: '/talk/talk/list',
    method: 'get',
    params: query
  })
}

// 查询说说管理详细
export function getTalk(id) {
  return request({
    url: '/talk/talk/' + id,
    method: 'get'
  })
}

// 新增说说管理
export function addTalk(data) {
  return request({
    url: '/talk/talk',
    method: 'post',
    data: data
  })
}

// 修改说说管理
export function updateTalk(data) {
  return request({
    url: '/talk/talk',
    method: 'put',
    data: data
  })
}

// 删除说说管理
export function delTalk(id) {
  return request({
    url: '/talk/talk/' + id,
    method: 'delete'
  })
}
