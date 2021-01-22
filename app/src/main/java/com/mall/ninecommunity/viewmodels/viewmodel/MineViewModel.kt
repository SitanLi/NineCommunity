package com.mall.ninecommunity.viewmodels.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mall.baselibrary.base.viewModel.BaseViewModel
import com.mall.baselibrary.pic.PicBuilder
import com.mall.ninecommunity.ext.request
import com.mall.ninecommunity.http.api.ApiService
import com.mall.ninecommunity.model.NewsBean
import com.mall.ninecommunity.utils.PathUnifyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 *@createTime :2020/7/10  17:23
 *@Author:XiaopingLi
 *@Description :我的
 */
class MineViewModel @ViewModelInject constructor(app: Application) : BaseViewModel(app) {

    fun getBuilder(): PicBuilder {
        val cacheFile = File(PathUnifyUtils.getPictureDir(), "${System.currentTimeMillis()}.jpg")
        return PicBuilder(cacheFile, true, 200, 200).builder()
    }
}