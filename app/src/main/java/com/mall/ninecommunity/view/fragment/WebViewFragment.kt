package com.mall.ninecommunity.view.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.kingja.loadsir.core.LoadService
import com.mall.baselibrary.base.view.BaseActivity
import com.mall.baselibrary.base.view.BaseLazyFragment
import com.mall.baselibrary.common.Constants
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.WebViewLayoutBinding
import com.mall.ninecommunity.ext.registerLoadSir
import com.mall.ninecommunity.presenter.JavaScriptObject
import com.mall.ninecommunity.widget.MyWebViewClient
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [WebViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WebViewFragment : BaseLazyFragment<WebViewLayoutBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(h5Url: String) =
                WebViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(Constants.Action.H5_URL, h5Url)
                    }
                }
    }

    private var myWebViewClient: MyWebViewClient? = null
    private var loadServer: LoadService<*>? = null
    private var url: String? = null

    override fun initLayoutId(): Int = R.layout.web_view_layout

    override fun initView() {
        dataBinding.handle = WebHandle()
        initWebView()
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface")
    private fun initWebView() {
        url = arguments?.getString(Constants.Action.H5_URL)
        if (url.isNullOrEmpty()) return
        val titleParent = arguments?.getString(Constants.Action.TITLE)
        val webView = dataBinding.webView
        webView.requestFocusFromTouch()
        webView.settings.run {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            allowFileAccess = false//是否需要使用 file 协议
            allowFileAccessFromFileURLs = false
            allowUniversalAccessFromFileURLs = false
            savePassword = false
            setNeedInitialFocus(true)
            javaScriptCanOpenWindowsAutomatically = true
            loadsImagesAutomatically = true
            defaultTextEncodingName = "utf-8"
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        }
        webView.addJavascriptInterface(JavaScriptObject(webView, activity as BaseActivity<*>), "injectedObject")
        webView.removeJavascriptInterface("searchBoxJavaBridge_")
        webView.removeJavascriptInterface("accessibility")
        webView.removeJavascriptInterface("accessibilityTraversal")
        loadServer = webView.registerLoadSir {
            dataBinding.webView.loadUrl(url)
        }
        myWebViewClient = MyWebViewClient(loadServer)
        webView.webViewClient = myWebViewClient

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                dataBinding.handle?.progress?.value = newProgress
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                dataBinding.exitView.setTitleName(if (titleParent.isNullOrEmpty()) title else titleParent)
            }
        }
        webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack()
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun initData() {
    }

    override fun visibleToUser() {
    }

    override fun lazyLoad() {
        dataBinding.webView.loadUrl(url)
    }

    inner class WebHandle {
        var progress = MutableLiveData<Int>()
    }
}