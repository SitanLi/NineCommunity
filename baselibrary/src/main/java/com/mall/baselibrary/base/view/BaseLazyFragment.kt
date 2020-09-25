package com.mall.baselibrary.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.mall.baselibrary.base.viewModel.BaseViewModel

/**
 *@Time :2019/11/6
 *@author: Lixiaoping
 *懒加载fragment
 */
abstract class BaseLazyFragment< DB : ViewDataBinding> : BaseFragment< DB>() {

    private var isFirstLoad = true
    private var isFirstTab: Boolean = false

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中
            lazyLoad()
            isFirstLoad = false
        } else {
            visibleToUser()
        }
    }

    /**
     * 每次在Fragment与用户可见状态且不是第一次对用户可见
     */
    abstract fun visibleToUser()

    /**
     * 懒加载，只有在Fragment第一次创建且第一次对用户可见
     */
    abstract fun lazyLoad()
}