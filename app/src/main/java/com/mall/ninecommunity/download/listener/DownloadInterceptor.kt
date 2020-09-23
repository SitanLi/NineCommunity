package com.mall.ninecommunity.download.listener

import okhttp3.Interceptor
import okhttp3.Response

class DownloadInterceptor(val listener: DownloadProgressListener?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Accept-Encoding", "identity")
        val originalResponse = chain.proceed(request.build())
        return originalResponse.newBuilder()
                .body(DownloadResponseBody(originalResponse.body(), listener))
                .build()
    }
}