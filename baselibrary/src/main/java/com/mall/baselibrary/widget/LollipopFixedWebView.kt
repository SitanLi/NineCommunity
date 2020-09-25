package com.mall.baselibrary.widget

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

/**
 *@createTime :2020/9/25  13:43
 *@Author:XiaopingLi
 *@Description :webView
 */
class LollipopFixedWebView : WebView {
    constructor(context: Context) : super(getFixedContext(context)) {}
    constructor(context: Context, attrs: AttributeSet) : super(getFixedContext(context), attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(getFixedContext(context), attrs, defStyleAttr)

    companion object {
        private fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22)) {
                context.createConfigurationContext(Configuration())
            } else context
        }
    }
}