package com.mall.baselibrary.base.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import com.mall.baselibrary.R
import com.mall.baselibrary.base.dialog.LoadingDialog
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

/**
 *  Activity基类
 */
abstract class BaseActivity<DB : ViewDataBinding> : RxAppCompatActivity() {
    protected var startActivityTime: Long = 0L
    protected lateinit var dataBinding: DB
    private val loadingDialog by lazy { LoadingDialog(this, R.style.LoadingDialog) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = setContentView(this, initLayoutId())
        dataBinding.lifecycleOwner = this
        startActivityTime = System.currentTimeMillis()
        initView()
        initData()
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

    abstract fun initLayoutId(): Int

    abstract fun initView()

    abstract fun initData()
}