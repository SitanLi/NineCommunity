package com.mall.ninecommunity.widget

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ViewUtils
import com.kingja.loadsir.core.LoadService
import com.mall.baselibrary.base.view.BaseActivity
import com.mall.baselibrary.common.AppConfig
import com.mall.baselibrary.loadSir.ErrorCallback
import com.mall.baselibrary.utils.URLUtils
import java.lang.ref.WeakReference


class MyWebViewClient  (loadServer: LoadService<*>?) : WebViewClient() {
    private var originalUrl: String? = null
    private val loadServerWeak = WeakReference<LoadService<*>>(loadServer)
    private var isError = false

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        isError = true
        view?.post {
            loadServerWeak.get()?.showCallback(ErrorCallback::class.java)
        }
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        isError = false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (isError) return
        view?.post {
            loadServerWeak.get()?.showSuccess()
        }
    }


    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        try {
            if (url == originalUrl) {
                view?.clearHistory()
            }

            originalUrl = url

            val isCommodityItem = url.startsWith(AppConfig.getUrlConfig()[AppConfig.INTERCEPT_TAO_BAO_DETAIL_URL]
                    ?: AppConfig.DEFAULT_INTERCEPT_TAO_BAO_DETAIL_URL) ||
                    url.startsWith(AppConfig.DEFAULT_INTERCEPT_TMALL_DETAIL_URL) ||
                    url.startsWith(AppConfig.DEFAULT_INTERCEPT_TMALL_INTERNATIONAL_DETAIL_URL) ||
                    url.startsWith(AppConfig.DEFAULT_INTERCEPT_TMALL_INTERNATIONAL_DETAIL_URL_1)
            if (url.startsWith("weixin://") //微信
                    || url.startsWith("alipays://") //支付宝
                    || url.startsWith("mailto://") //邮件
                    || url.startsWith("tmall://")//淘宝
                    || url.startsWith("tbopen://")//淘宝
                    || url.startsWith("tel://")//电话
                    || isCommodityItem//电话
                    || url.startsWith("dianping://")//大众点评
            ) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                Utils.getApp().startActivity(intent)
                if (isCommodityItem) {
                    intentDetailCommodityActivity(url, view)
                }
                return true
            }
        } catch (e: Exception) {
        }
        view?.loadUrl(url)
        return true
    }

    private fun intentDetailCommodityActivity(url: String, view: WebView?) {
        LogUtils.d("intercept request:${url},${Thread.currentThread().name}")
        val itemId = URLUtils.getQueryParameterByUrl(url, "id")
        if (!itemId.isNullOrEmpty()) {
            ViewUtils.runOnUiThread {
                val topActivity = ActivityUtils.getTopActivity()
                if (topActivity !is BaseActivity<*>) return@runOnUiThread
                topActivity.showDialog("Loading")
            }
        }
    }
}