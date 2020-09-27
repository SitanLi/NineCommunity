package com.mall.ninecommunity.model

/**
 *@createTime :2020/9/27  10:14
 *@Author:XiaopingLi
 *@Description :请求参数
 */
class HttpParams : HashMap<String, Any>() {
    fun obtain(): HttpParams = HttpParams()
    fun obtain(key: String, value: Any): HttpParams {
        val httpParam = HttpParams()
        httpParam[key] = value
        return httpParam
    }
}