package com.mall.ninecommunity.viewmodels.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mall.baselibrary.base.viewModel.BaseViewModel

/**
 *
 */
class PagerViewModel(app: Application):BaseViewModel(app){
    var currentItem = MutableLiveData<Int>()
}