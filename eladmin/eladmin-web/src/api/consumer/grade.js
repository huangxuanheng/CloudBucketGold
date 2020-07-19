import request from '@/utils/request'
import qs from 'qs'

export function getGrades(params) {
  return request({
    url: 'api/grade/list',
    method: 'get',
    params
  })
}

export function getGrade(id) {
  const params = {
    id: id
  }
  return request({
    url: 'api/grade?' + qs.stringify(params, { indices: false }),
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/grade',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/grade',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/grade',
    method: 'put',
    data
  })
}

export default { add, edit, del, getGrades, getGrade }
