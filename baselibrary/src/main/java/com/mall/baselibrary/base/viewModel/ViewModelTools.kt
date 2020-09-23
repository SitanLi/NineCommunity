package com.mall.baselibrary.base.viewModel

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.Utils

/**
 *@createTime :2020/9/22  18:07
 *@Author:XiaopingLi
 *@Description :Test
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.fViewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this }
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, {
    ViewModelProvider.AndroidViewModelFactory.getInstance(Utils.getApp())
})

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.aViewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        ViewModelProvider.AndroidViewModelFactory.getInstance(Utils.getApp())
    }
    return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
}
