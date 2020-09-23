package com.mall.baselibrary.base.dialog

import com.mall.baselibrary.base.viewModel.BaseViewModel


/**
 *@Time :2019/11/5
 *@author: Lixiaoping
 *TODO :
 */
interface DialogModelCallback<VM: BaseViewModel> {
    fun getViewModel():VM
}