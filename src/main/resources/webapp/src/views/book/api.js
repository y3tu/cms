import {service} from '@/plugin/axios'

/**
 * 获取小说列表
 */
export function page(params) {
    return service({
        url: 'book/page',
        method: 'post',
        data: params
    })
}
