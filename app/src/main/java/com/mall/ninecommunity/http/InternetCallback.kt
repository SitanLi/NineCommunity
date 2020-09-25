package com.mall.ninecommunity.http

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *@createTime :2020/9/25  9:26
 *@Author:XiaopingLi
 *@Description :
 */
class InternetCallback<T> : Callback<T> {
    private var doError: ((e: Throwable, message: String) -> Unit)? = null
    private var doNull: (() -> Unit)? = null
    private var doNext: ((t: T) -> Unit)? = null

    constructor(doNext: ((t: T) -> Unit)?, doError: ((e: Throwable, message: String) -> Unit)?, doNull: (() -> Unit)?) {
        this.doNext = doNext
        this.doNull = doNull
        this.doError = doError
    }


    constructor(doNext: ((t: T) -> Unit)?) {
        this.doNext = doNext
    }

    var showToast = true

    override fun onFailure(call: Call<T>, e: Throwable) {
        e.printStackTrace()
        if (e is UnknownHostException) {// 未知主机
            doErrorSwitchHost()
        } else if (e is retrofit2.HttpException) {
            if ("HTTP 404 Not Found" == e.message || "HTTP 502 Fiddler - DNS Lookup Failed" == e.message) { // 404 not found
                doErrorSwitchHost()
            } else {
                e.message?.let { doError(it) }
            }
        } else if (e is SocketTimeoutException) {
            doError("请求超时，请稍后再试...")
        } else if (e is ConnectException) {
            doError("网络连接有误，请稍后再试...")
        } else if (e is ApiException) {// 非 200 错误
            e.message?.let { doError(it) }
//            if ("登录超时" == e.message) {
//            }
        } else if (e is ApiResultDataNullException) {
            doNull?.invoke()
        } else {
            doError("请求失败，请稍后再试...")
        }
        doError?.invoke(e, e.message.toString())
        LogUtils.i("Http error --> ${e.message} || ${e.stackTrace}")
    }

    @Suppress("UNCHECKED_CAST")
    override fun onResponse(call: Call<T>, response: Response<T>) {
        val t = response.body() as T
        doNext?.invoke(t)
    }

    private fun doErrorSwitchHost() {
        if (showToast)
            ToastUtils.showShort("连接不上服务器...")
    }

    private fun doError(message: String) {
        if (showToast)
            ToastUtils.showShort(message)
    }
}