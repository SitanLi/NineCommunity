package com.mall.ninecommunity.http.interceptor

import com.blankj.utilcode.util.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

class OkHttpOfflineCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtils.isAvailable()) {
            val offlineCacheTime = 60
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=${Int.MAX_VALUE}")
                .build()
        }
        return chain.proceed(request)
    }
}