package com.mall.ninecommunity.viewmodels.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mall.baselibrary.base.viewModel.BaseViewModel
import com.mall.ninecommunity.ext.request
import com.mall.ninecommunity.http.api.ApiService
import com.mall.ninecommunity.model.TopStoriesBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 */
class HomeViewModel @ViewModelInject constructor(private var apiService: ApiService, app: Application) : BaseViewModel(app) {
    var storiesBeanList = MutableLiveData<MutableList<TopStoriesBean>>()

    fun loadDada() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                request({ apiService.news() }, {
                    storiesBeanList.value = it?.top_stories?.toMutableList() ?: mutableListOf()
                })
            }
        }
    }
}