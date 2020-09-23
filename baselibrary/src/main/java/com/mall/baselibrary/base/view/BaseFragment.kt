package com.mall.baselibrary.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mall.baselibrary.R
import com.mall.baselibrary.base.dialog.LoadingDialog
import com.trello.rxlifecycle3.components.support.RxFragment

/**
 *@Time :2019/10/30
 *@author: Lixiaoping
 *TODO :Fragment基类：无ViewModel
 */
abstract class BaseFragment<DB : ViewDataBinding> : RxFragment() {
    protected var startFragmentTime: Long = 0L
    protected lateinit var dataBinding: DB
    private val loadingDialog by lazy {
        LoadingDialog(requireContext(), R.style.LoadingDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFragmentTime = System.currentTimeMillis()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = initDataBinding(inflater, initLayoutId(), container)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    /**
     * 初始化要加载的布局资源ID
     */
    abstract fun initLayoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化DataBinding
     */
    protected open fun initDataBinding(inflater: LayoutInflater, @LayoutRes layoutId: Int, container: ViewGroup?): DB {
        return DataBindingUtil.inflate(inflater, layoutId, container, false)
    }

    /**
     * 显示用户等待框
     *
     * @param msg 提示信息
     */
    fun showDialog(msg: String) {
        if (loadingDialog.isShowing) {
            loadingDialog.setLoadingMsg(msg)
        } else {
            loadingDialog.setLoadingMsg(msg)
            loadingDialog.show()
        }
    }

    /**
     * 隐藏等待框
     */
    fun dismissDialog() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.unbind()
    }
}
