package com.mall.ninecommunity

import com.mall.baselibrary.BuildConfig

/**
 * Administrator
 * created at 2018/7/31 9:55
 * TODO:域名配置
 */
object RequestDomainConfig {
    private val isTestService = BuildConfig.DEBUG

    //测试服
    private var WX_WEB_PAY_REFERER = "https://testh5.wowolive99.com"//微信H5支付域名
    private var URL_DOMAIN = "http://testservice.wowolive99.com"//链接地址
//    private var URL_REQUEST_DEFAULT = "http://testservice.wowolive99.com/service/"//接口地址
    private var URL_REQUEST_DEFAULT = "http://testservice.wowolive99.com/cmd/"//接口地址

    //正式服
    //    public static final String WX_WEB_PAY_REFERER = "https://h5.wowolive99.com";//微信H5支付域名
    //    public static final String URL_DOMAIN = "https://www.wowolive99.com";//链接地址
    //    public static final String URL_REQUEST_DEFAULT = "http://service.wowolive99.com/servic/";//接口地址

    fun getDoMainUrl(): String {
        if (isTestService) {
            URL_DOMAIN = "http://testservice.wowolive99.com"
        } else {
            URL_DOMAIN = "https://www.wowolive99.com"
        }
        return URL_DOMAIN
    }

    fun getWxWebPayReferer(): String {
        if (isTestService) {
            WX_WEB_PAY_REFERER = "https://testh5.wowolive99.com"
        } else {
            WX_WEB_PAY_REFERER = "https://h5.wowolive99.com"
        }
        return WX_WEB_PAY_REFERER
    }

    fun getRequestDefault(): String {
        if (isTestService) {
            URL_REQUEST_DEFAULT = "https://news-at.zhihu.com/api/4/"
        } else {
            URL_REQUEST_DEFAULT = "https://news-at.zhihu.com/api/4/"
        }
        return URL_REQUEST_DEFAULT
    }
}
