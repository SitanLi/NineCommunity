package com.mall.baselibrary.base.viewModel

import androidx.lifecycle.MutableLiveData
import com.mall.baselibrary.base.bean.DialogBean

class DialogLiveData<T> : MutableLiveData<T>() {
    companion object {
        private var dialogBean = DialogBean()
    }

    fun setValue(isShow: Boolean) {
        dialogBean.isShow = isShow
        dialogBean.msg = ""
        value = dialogBean as T
    }

    fun setValue(isShow: Boolean, msg: String) {
        dialogBean.isShow = isShow
        dialogBean.msg = msg
        value = dialogBean as T
    }
}