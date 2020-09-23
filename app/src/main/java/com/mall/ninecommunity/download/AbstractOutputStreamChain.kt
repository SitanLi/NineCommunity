package com.mall.ninecommunity.download

import com.mall.ninecommunity.model.DownloadInfo
import okhttp3.ResponseBody
import java.io.File

/**
 *Time:2020/3/25
 *Author:HevenHolt
 *Description:
 */
abstract class AbstractOutputStreamChain(private val type: Int) {
    companion object {
        const val OUTPUT_STREAM_FILE = 1
        const val OUTPUT_STREAM_RANDOM_FILE = 2
    }

    var next: AbstractOutputStreamChain? = null

    fun invoke(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        val allLength = if (0L == info.countLength)
            responseBody.contentLength()
        else
            info.readLength + responseBody
                    .contentLength()
        val level = if (allLength > 0) OUTPUT_STREAM_RANDOM_FILE else OUTPUT_STREAM_FILE
        if (level == type) {
            execute(responseBody, file, info)
        } else {
            next?.invoke(responseBody, file, info)
        }
    }

    abstract fun execute(responseBody: ResponseBody, file: File, info: DownloadInfo)
}