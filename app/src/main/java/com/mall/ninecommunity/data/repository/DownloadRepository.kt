package com.mall.ninecommunity.data.repository

import com.mall.ninecommunity.data.dao.DownloadInfoDao
import com.mall.ninecommunity.di.di
import com.mall.ninecommunity.model.DownloadInfo
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

/**
 *@createTime :2020/9/21  11:12
 *@Author:XiaopingLi
 *@Description :下载
 */
class DownloadRepository private constructor():DIAware {
    override val di: DI  = di()
    private val downloadInfoDao:DownloadInfoDao by  instance()
    companion object {
        @Volatile
        private var instance: DownloadRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: DownloadRepository().also { instance = it }
                }

    }


    /**
     * 插入更新数据
     */
    suspend fun insertOrUpdate(downloadInfo: DownloadInfo) {
        downloadInfoDao.insert(downloadInfo)
    }

    /**
     * 查询数据
     */
    suspend fun queryDownloadInfoByPath(url: String) = downloadInfoDao.getDownloadInfo(url)

}