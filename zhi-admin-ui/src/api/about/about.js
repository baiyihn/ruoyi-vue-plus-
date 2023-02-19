import request from '@/utils/request'

// 查询关于我的信息
export function getAbout(query) {
  return request({
    url: '/blogInfo/about',
    method: 'get',
    params: query
  })
}

// 修改关于我
export function updateAbout(data) {
  return request({
    url: '/blogInfo/admin/about',
    method: 'put',
    data: data
  })
}




