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
 *TODO : 懒加载fragment
 */
abstract class BaseLazyFragment< DB : ViewDataBinding> : BaseFragment< DB>() {

    private var isCreateView: Boolean = false
    private var isActivityCreated: Boolean = false
    private var lazy: Boolean = false
    // 这个Fragment是不是tab页面的第一个页面
    private var isFirstTab: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        isCreateView = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isActivityCreated = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isCreateView && isActivityCreated && !lazy) {
            //不是第一个Tab的Fragment 进行懒加载请求数据
            lazy = true
            lazyLoad()
        } else if (isVisibleToUser && !isCreateView && !isActivityCreated && !lazy) {
            //这个Fragment是多个Tab中的第一个
            isFirstTab = true
        } else {
            //对用户可见时，是否需要重新刷新数据
            if (isVisibleToUser) visibleToUser()
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