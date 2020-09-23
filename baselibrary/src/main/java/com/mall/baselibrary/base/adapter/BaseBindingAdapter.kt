package com.mall.baselibrary.base.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *@Time :2019/10/30
 *@author: Lixiaoping
 *TODO :
 */
abstract class BaseQuickDataBindingAdapter<T>(layoutResId: Int, data: MutableList<T>) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data) {
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: T?) {
        if (item == null) return
        val binding = helper.getBinding<ViewDataBinding>()
        binding?.setVariable(bindVariable(), item)
        binding?.executePendingBindings()
    }

    abstract fun bindVariable(): Int
}