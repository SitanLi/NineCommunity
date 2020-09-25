package com.mall.ninecommunity.hilt

import com.mall.ninecommunity.controller.inter.DownloadController
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 *@createTime :2020/9/25  18:45
 *@Author:XiaopingLi
 *@Description :
 */
@EntryPoint
@InstallIn(ApplicationComponent::class)
interface ControllerEntryPoint {
    fun downloadController(): DownloadController
}