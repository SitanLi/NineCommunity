package com.mall.ninecommunity.di

import com.blankj.utilcode.util.Utils
import com.mall.ninecommunity.NineApplication
import com.mall.ninecommunity.data.AppDataBase
import com.mall.ninecommunity.data.dao.DownloadInfoDao
import com.mall.ninecommunity.download.DownloadController
import com.mall.ninecommunity.download.DownloadControllerIml
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

/**
 *@createTime :2020/9/22  13:54
 *@Author:XiaopingLi
 *@Description :
 */
fun di() = NineApplication.getInstance().di

val appConfigModule = DI.Module("appConfigModule") {
    bind<AppDataBase>() with singleton {
        AppDataBase.getInstance(Utils.getApp())
    }
    bind<DownloadInfoDao>() with singleton {
        instance<AppDataBase>().getDownloadInfoDao()
    }
    bind<DownloadController>() with singleton { DownloadControllerIml(instance()) }
}