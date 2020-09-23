package com.mall.baselibrary.base.viewModel


import android.app.Application
import androidx.lifecycle.*
import com.blankj.utilcode.util.Utils
import com.mall.baselibrary.base.bean.DialogBean
import com.trello.rxlifecycle3.LifecycleProvider

/**
 * TODO:ViewModel基类，管理rxJava发出的请求，ViewModel销毁同时也取消请求
 */
abstract class BaseViewModel (app:Application): AndroidViewModel(app) {
    /**
     * 用来通知 Activity／Fragment 是否显示等待Dialog
     */
    protected var showDialog: DialogLiveData<DialogBean> = DialogLiveData()
    /**
     * 当ViewModel层出现错误需要通知到Activity／Fragment
     */
    protected var error: MutableLiveData<Any> = MutableLiveData()

    private var lifecycle: LifecycleProvider<*>? = null

    fun getShowDialog(owner: LifecycleOwner, observer: Observer<DialogBean>) {
        showDialog.observe(owner,observer)
    }

    fun getShowError(owner: LifecycleOwner, observer: Observer<Any>) {
        error.observe(owner, observer)
    }

    fun setLifeCycleProvide(lifecycleProvider: LifecycleProvider<*>) {
        this.lifecycle = lifecycleProvider
    }

    fun getLifeCycleProvide():LifecycleProvider<*> = lifecycle!!
}