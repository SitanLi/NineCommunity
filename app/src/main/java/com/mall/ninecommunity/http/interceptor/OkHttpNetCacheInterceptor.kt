package com.mall.ninecommunity.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class OkHttpNetCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val onlineCacheTime = 30
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=$onlineCacheTime")
                .removeHeader("Pragma")
                .build()
    }
}