package com.mall.ninecommunity.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mall.ninecommunity.model.DownloadInfo

/**
 *Time:2020/8/31
 *Author:HevenHolt
 *Description:下载类Dao
 */
@Dao
interface DownloadInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(downloadInfo: DownloadInfo)

    @Query("SELECT * FROM DownloadInfo WHERE url  = :url")
    suspend fun getDownloadInfo(url: String): DownloadInfo?
}