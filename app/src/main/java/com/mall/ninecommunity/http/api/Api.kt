package com.mall.ninecommunity.http.api

import com.blankj.utilcode.util.PathUtils
import com.mall.baselibrary.base.http.BaseApi
import com.mall.ninecommunity.BuildConfig
import com.mall.ninecommunity.http.interceptor.OkHttpClientLogInterceptor
import com.mall.ninecommunity.http.interceptor.OkHttpNetCacheInterceptor
import com.mall.ninecommunity.http.interceptor.OkHttpOfflineCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

class Api : BaseApi() {

    /**
     * 静态内部类单例
     */
    object ApiHolder {
        const val BASE_URL = "https://service.sqwvvip.com"
        private val api = Api()
        val JueJinApiService: ApiService = api.initRetrofit(ApiService.JUE_JIN_BASE_URL)
                .create(ApiService::class.java)
    }

    /**
     * 做自己需要的操作
     */
    override fun setClient(): OkHttpClient? {
        return initOkHttpClient()
    }

    private fun initOkHttpClient(): OkHttpClient? {
        val httpClientBuild = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
            httpClientBuild.addInterceptor(OkHttpClientLogInterceptor())
        val cacheSize = 10 * 1024 * 1024L//10MB
        val cache = Cache(File(PathUtils.getInternalAppCachePath()), cacheSize)
        httpClientBuild.addNetworkInterceptor(OkHttpNetCacheInterceptor())
                .addInterceptor(OkHttpOfflineCacheInterceptor())
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
        return httpClientBuild.build()
    }

    companion object {
        val jueJinInstance: ApiService
            get() = ApiHolder.JueJinApiService
    }

}
