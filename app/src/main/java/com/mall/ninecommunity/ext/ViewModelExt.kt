package com.mall.ninecommunity.ext

import com.blankj.utilcode.util.LogUtils
import com.mall.baselibrary.base.viewModel.BaseViewModel
import com.mall.ninecommunity.http.InternetCallback
import retrofit2.Call

/**
 *Time:2020/7/16
 *Author:HevenHolt
 *Description:ViewModel拓展类
 */
fun <T> BaseViewModel.request(
        block: () -> Call<T>,
        success: (T?) -> Unit,
        error: (Int, String) -> Unit = { code, message -> LogUtils.e("request error->code:$code,message:$message") }

) {
    val call = block.invoke()
    call.enqueue(InternetCallback<T>(
            doNext = {
                success.invoke(it)
            },
            doNull = {
                success.invoke(null)
            },
            doError = { e, message ->
                error.invoke(e.hashCode(), message)
            }
    ))
}
