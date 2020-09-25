package com.mall.ninecommunity.ext

import android.view.View
import android.widget.TextView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mall.baselibrary.loadSir.ErrorCallback
import com.mall.baselibrary.loadSir.LoadingCallback
import com.mall.ninecommunity.R

/**
 *Time:2020/7/20
 *Author:HevenHolt
 *Description:LoadSir拓展
 */
fun View.registerLoadSir(callback: () -> Unit): LoadService<*> {
    val loadServer = LoadSir.getDefault().register(this) {
        callback.invoke()
    }
    post {
        loadServer.showCallback(LoadingCallback::class.java)
    }
    return loadServer
}

fun LoadService<*>.setErrorText(message: String) {
    this.setCallBack(ErrorCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.error_text).text = message
    }
}