package com.mall.ninecommunity.http.interceptor

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.Charset
import javax.inject.Inject

class OkHttpClientLogInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headers = request.headers()
        val headMap = HashMap<String,String>()
        repeat(headers.size()){
            headMap[headers.name(it)] = headers.value(it)
        }
        LogUtils.json("OkHttpClientRequest=${request.url()}",headMap)
        val time = System.currentTimeMillis()
        val response = chain.proceed(request)
        val charset = response.body()?.contentType()?.charset()
        val source = response.body()?.source()
        source?.request(Long.MAX_VALUE)
        LogUtils.json(
            "OkHttpClientResponse=请求时长=${System.currentTimeMillis() - time}=(${request.url()})",
            source?.buffer()?.clone()?.readString(
                charset
                    ?: Charset.forName("utf-8")
            )
        )
        return response
    }
}