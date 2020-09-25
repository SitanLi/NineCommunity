package com.mall.baselibrary.utils

import android.net.Uri

object URLUtils {
    /**
     * 获取url中的参数
     */
    fun getQueryParameterByUrl(url: String, key: String): String? {
        val uri = Uri.parse(url)
        return uri.getQueryParameter(key)
    }

    /**
     * 拼接参数
     */
    fun appendQueryParameter(url: String, key: String, value: String): String? {
        val buildUpon = Uri.parse(url).buildUpon()
        buildUpon.appendQueryParameter(key, value)
        return buildUpon.toString()
    }
}