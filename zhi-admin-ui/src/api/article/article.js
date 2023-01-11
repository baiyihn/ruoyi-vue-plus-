import request from '@/utils/request'

// 查询文章列表列表
export function listArticle(query) {
  return request({
    url: '/article/article/list',
    method: 'get',
    params: query
  })
}

// 查询文章列表详细
export function getArticle(id) {
  return request({
    url: '/article/article/' + id,
    method: 'get'
  })
}

// 新增文章列表
export function addArticle(data) {
  return request({
    url: '/article/article',
    method: 'post',
    data: data
  })
}

// 修改文章列表
export function updateArticle(data) {
  return request({
    url: '/article/article',
    method: 'put',
    data: data
  })
}

// 删除文章列表
export function delArticle(id) {
  return request({
    url: '/article/article/' + id,
    method: 'delete'
  })
}
