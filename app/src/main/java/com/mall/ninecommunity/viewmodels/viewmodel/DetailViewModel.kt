package com.mall.ninecommunity.viewmodels.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mall.baselibrary.base.viewModel.BaseViewModel
import com.mall.ninecommunity.ext.request
import com.mall.ninecommunity.http.api.ApiService
import com.mall.ninecommunity.model.NewsBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *@createTime :2020/7/10  17:23
 *@Author:XiaopingLi
 *@Description :细节
 */
class DetailViewModel @ViewModelInject constructor(private var apiService: ApiService, app: Application) : BaseViewModel(app) {
    val newBeanLiveData = MutableLiveData<NewsBean>()

    fun toShow() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                request({ apiService.news() }, {
                    newBeanLiveData.value = it
                })
            }
        }
    }
}