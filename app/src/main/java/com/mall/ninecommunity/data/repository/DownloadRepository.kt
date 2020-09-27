package com.mall.ninecommunity.data.repository

import com.mall.ninecommunity.data.dao.DownloadInfoDao
import com.mall.ninecommunity.model.DownloadInfo
import javax.inject.Inject

/**
 *@createTime :2020/9/21  11:12
 *@Author:XiaopingLi
 *@Description :下载
 */
class DownloadRepository @Inject constructor() {
    @Inject
    lateinit var downloadInfoDao: DownloadInfoDao

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