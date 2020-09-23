package com.mall.baselibrary.base.dialog

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.mall.baselibrary.base.viewModel.BaseViewModel

/**
 *@Time :2019/11/5
 *@author: Lixiaoping
 *TODO :
 */
abstract class BaseDialog<VM : BaseViewModel, DB : ViewDataBinding>(context: Context, themResId: Int) : BaseNoModelDialog<DB>(context, themResId) {
    protected lateinit var viewModel: VM

    override fun initBinding() {
        super.initBinding()
        binding.setVariable(initBRId(),this)
        viewModel = initViewModel()
    }

    abstract fun initBRId(): Int

    abstract fun initViewModel(): VM
}