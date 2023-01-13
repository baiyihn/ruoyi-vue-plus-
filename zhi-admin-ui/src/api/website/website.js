import request from '@/utils/request'

// 查询标签管理列表
export function getWebsiteConfig(query) {
  return request({
    url: '/website/admin/config',
    method: 'get',
    params: query
  })
}


export function updateWebsiteConfig(data) {
  return request({
    url: 'website/config',
    method: 'put',
    data: data
  })

}
