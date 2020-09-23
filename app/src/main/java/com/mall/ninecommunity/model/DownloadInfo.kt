package com.mall.ninecommunity.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mall.ninecommunity.download.DownState
import com.mall.ninecommunity.download.HttpDownService
import com.mall.ninecommunity.download.listener.HttpDownOnNextListener

/**
 *Time:2020/8/31
 *Author:HevenHolt
 *Description:下载类Entity
 */
@Entity
class DownloadInfo(
        var savePath: String?,//存储位置
        var countLength: Long,//文件总长度
        var readLength: Long,//文件已下载长度
        @Ignore var service: HttpDownService?,//下载唯一的HttpService
        @Ignore var listener: HttpDownOnNextListener<*>? = null,////回调监听
        var connectionTime: Int = 15,
        var stateInter: Int = 0,
        @PrimaryKey var url: String,
        var updateProgress: Boolean
) {
    constructor(url: String) : this(null, 0, 0, null, null, 15, 0, url, false)

    fun setState(state: DownState) {
        stateInter = state.state
    }

    fun getState(): DownState {
        return when (stateInter) {
            0 -> DownState.START
            1 -> DownState.DOWN
            2 -> DownState.PAUSE
            3 -> DownState.STOP
            4 -> DownState.ERROR
            5 -> DownState.FINISH
            else -> DownState.FINISH
        }
    }

    fun <T> getHttpDownOnNextListener(): HttpDownOnNextListener<T> = listener as HttpDownOnNextListener<T>
}