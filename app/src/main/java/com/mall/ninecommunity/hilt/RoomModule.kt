package com.mall.ninecommunity.hilt

import android.content.Context
import com.mall.ninecommunity.controller.iml.DownloadControllerIml
import com.mall.ninecommunity.controller.iml.UserInfoControllerIml
import com.mall.ninecommunity.controller.inter.DownloadController
import com.mall.ninecommunity.controller.inter.UserInfoController
import com.mall.ninecommunity.data.AppDataBase
import com.mall.ninecommunity.data.dao.DownloadInfoDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 *@createTime :2020/9/24  13:57
 *@Author:XiaopingLi
 *@Description :使用hilt注入网络请求依赖
 */
@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase = AppDataBase.getInstance(context)

    @Provides
    @Singleton
    fun provideDownloadInfoDao(appDataBase: AppDataBase): DownloadInfoDao = appDataBase.getDownloadInfoDao()

}

