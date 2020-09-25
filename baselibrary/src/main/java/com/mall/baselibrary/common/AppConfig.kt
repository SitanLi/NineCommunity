package com.mall.baselibrary.common

import com.blankj.utilcode.util.CacheMemoryStaticUtils

/**
 *@createTime :2020/9/25  14:47
 *@Author:XiaopingLi
 *@Description :
 */
object AppConfig {

    const val INTERCEPT_TAO_BAO_DETAIL_URL = "INTERCEPT_TAO_BAO_DETAIL_URL"
    const val DEFAULT_INTERCEPT_TAO_BAO_DETAIL_URL = "https://h5.m.taobao.com/awp/core/detail.htm"
    const val DEFAULT_INTERCEPT_TMALL_DETAIL_URL = "https://detail.m.tmall.com/item.htm"
    const val DEFAULT_INTERCEPT_TMALL_INTERNATIONAL_DETAIL_URL = "https://detail.tmall.com/item.htm"
    const val DEFAULT_INTERCEPT_TMALL_INTERNATIONAL_DETAIL_URL_1 = "https://detail.tmall.hk/hk/item.htm"

    @Synchronized
    fun setUrlConfig(map: Map<String, String>) {
        val urlConfig = getUrlConfig()
        urlConfig.putAll(map)
        CacheMemoryStaticUtils.put(Constants.MemoryKey.CONFIG_URL, urlConfig)
    }

    fun getUrlConfig(): MutableMap<String, String> = CacheMemoryStaticUtils.get(Constants.MemoryKey.CONFIG_URL)
            ?: mutableMapOf()
}