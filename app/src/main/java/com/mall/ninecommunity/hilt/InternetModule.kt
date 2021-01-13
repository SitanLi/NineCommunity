package com.mall.ninecommunity.hilt

import com.blankj.utilcode.util.PathUtils
import com.mall.ninecommunity.BuildConfig
import com.mall.ninecommunity.http.api.ApiService
import com.mall.ninecommunity.http.api.ApiUserService
import com.mall.ninecommunity.http.interceptor.OkHttpClientLogInterceptor
import com.mall.ninecommunity.http.interceptor.OkHttpHeadPublicParamsInterceptor
import com.mall.ninecommunity.http.interceptor.OkHttpNetCacheInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *@createTime :2020/9/24  13:57
 *@Author:XiaopingLi
 *@Description :使用hilt注入网络请求依赖
 */
@Module
@InstallIn(ApplicationComponent::class)
object InternetModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(okHttpNetCacheInterceptor: OkHttpNetCacheInterceptor,
                            okHttpHeadPublicParamsInterceptor: OkHttpHeadPublicParamsInterceptor,
                            okHttpClientLogInterceptor: OkHttpClientLogInterceptor): OkHttpClient {
        val httpClientBuild = OkHttpClient.Builder()
        val cacheSize = 10 * 1024 * 1024L//10MB
        val cache = Cache(File(PathUtils.getInternalAppCachePath()), cacheSize)
        httpClientBuild.addNetworkInterceptor(okHttpNetCacheInterceptor)
                .addInterceptor(okHttpHeadPublicParamsInterceptor)
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG)
            httpClientBuild.addInterceptor(okHttpClientLogInterceptor)
        return httpClientBuild.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                //支持返回Call<String>
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持直接格式化json返回Bean对象
                .addConverterFactory(GsonConverterFactory.create())
                //支持RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(if (BuildConfig.DEBUG) ApiService.BASE_URL_DEBUG else ApiService.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideUserService(retrofit: Retrofit):ApiUserService  = retrofit.create(ApiUserService::class.java)
}
