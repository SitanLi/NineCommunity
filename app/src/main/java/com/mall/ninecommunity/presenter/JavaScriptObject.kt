package com.mall.ninecommunity.presenter

import android.webkit.WebView
import com.mall.baselibrary.base.view.BaseActivity
import java.lang.ref.WeakReference

/**
 *@createTime :2020/9/25  15:01
 *@Author:XiaopingLi
 *@Description :
 */
class JavaScriptObject(private val webView: WebView? = null, weakActivity: BaseActivity<*>)  {
    private var activity: WeakReference<BaseActivity <*>> = WeakReference(weakActivity)
}