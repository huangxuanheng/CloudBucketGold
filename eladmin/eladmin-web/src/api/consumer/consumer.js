import request from '@/utils/request'
import qs from 'qs'

export function getConsumers(params) {
  return request({
    url: 'api/consumer/list',
    method: 'get',
    params
  })
}

export function getConsumer(id) {
  if(id===null){
    id=0
  }
    const params = {
        id: id
      }
    return request({
      url: 'api/consumer?'+qs.stringify(params, { indices: false }),
      method: 'get'
    })
  }


export function add(data) {
  // debugger
  return request({
    url: 'api/consumer',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/consumer',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/consumer',
    method: 'put',
    data
  })
}

export default { add, edit, del, getConsumers, getConsumer }
