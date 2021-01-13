package com.mall.ninecommunity.http.interceptor

import com.mall.ninecommunity.http.ApiPublicParams
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OkHttpHeadPublicParamsInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("channel", ApiPublicParams.getChannel())
                .addHeader("deviceId", ApiPublicParams.getDeviceId())
                .addHeader("clientMode", ApiPublicParams.getClientModel())
                .addHeader("signature", ApiPublicParams.getSignature())
                .addHeader("netWork", ApiPublicParams.getNetworkTypeName())
                .addHeader("osVersion", ApiPublicParams.getOSVerion())
                .addHeader("pk", ApiPublicParams.getPackageName())
                .addHeader("versionCode", ApiPublicParams.getAppVersionCode().toString())
                .addHeader("versionName", ApiPublicParams.getAppVersionName())
                .addHeader("clientType", ApiPublicParams.getClientType())

        val token = ApiPublicParams.getToken()
        val userId = ApiPublicParams.getUserId()
        token?.let { builder.addHeader("mall-auth", it) }
        userId?.let { builder.addHeader("userNo", it) }
        return chain.proceed(builder.build())
    }
}