package com.mall.ninecommunity.hilt

import com.mall.ninecommunity.controller.iml.DownloadControllerIml
import com.mall.ninecommunity.controller.iml.UserInfoControllerIml
import com.mall.ninecommunity.controller.inter.DownloadController
import com.mall.ninecommunity.controller.inter.UserInfoController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 *@createTime :2020/10/15  13:50
 *@Author:XiaopingLi
 *@Description :控制类
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class ControllerModule {
    @Binds
    abstract fun getDownloadController(downloadControllerIml: DownloadControllerIml): DownloadController

    @Binds
    abstract fun getUserInfoController(userInfoControllerIml: UserInfoControllerIml): UserInfoController
}