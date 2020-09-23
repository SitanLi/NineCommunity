package com.mall.ninecommunity.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mall.ninecommunity.data.repository.DownloadRepository
import com.mall.ninecommunity.viewmodels.viewmodel.DownloadViewModel

/**
 *@createTime :2020/9/21  11:26
 *@Author:XiaopingLi
 *@Description :
 */
class DownloadViewModelFactory(private val repository: DownloadRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DownloadViewModel(repository) as T
    }
}