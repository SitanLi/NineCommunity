package com.mall.ninecommunity.utils

import com.mall.ninecommunity.data.repository.DownloadRepository
import com.mall.ninecommunity.viewmodels.factory.DownloadViewModelFactory

/**
 *@createTime :2020/9/21  11:43
 *@Author:XiaopingLi
 *@Description :
 */
object InjectorUtils {
    private fun getDownloadRepository(): DownloadRepository = DownloadRepository.getInstance()
    fun provideDownloadViewModelFactory() = DownloadViewModelFactory(getDownloadRepository())
}