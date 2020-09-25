package com.mall.ninecommunity.http.api

import com.mall.ninecommunity.model.NewsBean
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val BASE_URL_DEBUG = "https://news-at.zhihu.com/api/4/"
        const val BASE_URL:String ="http://timeline-merger-ms.juejin.im/v1/"

    }

    /**
     * 测试接口
     */
    @GET("news/latest")
    fun news(): Call<NewsBean>
}