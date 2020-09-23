package com.mall.ninecommunity.http

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.mall.ninecommunity.model.BaseModel
import com.mall.ninecommunity.model.SingleBaseModel
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxHelper {


    private val gson = Gson()

    fun <T> handleResult(): ObservableTransformer<BaseModel<T>, T> =
            ObservableTransformer { upstream ->
                val flatMap = upstream.flatMap { result ->
                    if (result.isSuccess) { // code: 200
                        if (result.data == null) Observable.error(ApiResultDataNullException()) else {
                           LogUtils.i(gson.toJson(result))
                            createData(result)
                        }
                    } else {
                        Observable.error(ApiException(result.code, result.msg))
                    }
                }
                flatMap.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }

    private fun <T> createData(data: BaseModel<T>): ObservableSource<out T>? {
        return Observable.create { e ->
            try {
                data.data?.let { e.onNext(it) }
                e.onComplete()
            } catch (ex: Exception) {
                e.onError(ex)
            }
        }
    }

    private fun <T> createData(data: T): ObservableSource<out T>? {
        return Observable.create { e ->
            try {
                e.onNext(data)
                e.onComplete()
            } catch (ex: Exception) {
                e.onError(ex)
            }
        }
    }

    fun <T> handleSingleResult(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        val flatMap = upstream.flatMap<T> { result ->
//            result as SingleBaseModel
//            if (result.isSuccess) {
//                LogUtils.i(gson.toJson(result))
                createData(result)
//            } else {
//                Observable.error<T>(ApiException(result.code, result.msg))
//            }
        }
        flatMap.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}