package com.mall.ninecommunity.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mall.ninecommunity.data.dao.DownloadInfoDao
import com.mall.ninecommunity.model.DownloadInfo

/**
 *@createTime :2020/9/21  13:34
 *@Author:XiaopingLi
 *@Description :
 */

const val DATABASE_NAME = "nine_community_db"

@Database(entities = [DownloadInfo::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDownloadInfoDao(): DownloadInfoDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase =
                instance ?: synchronized(this) {
                    instance ?: buildDataBase(context)
                }

        private fun buildDataBase(context: Context): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME).build()
    }
}