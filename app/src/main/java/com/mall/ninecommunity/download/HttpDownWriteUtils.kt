package com.mall.ninecommunity.download

import com.mall.ninecommunity.model.DownloadInfo
import okhttp3.ResponseBody
import java.io.File

/**
 *Time:2020/3/25
 *Author:HevenHolt
 *Description:
 */
object HttpDownWriteUtils {
    fun writeFileFromResponse(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        val fileChain: AbstractOutputStreamChain = FileOutputStreamChain()
        val randomFileChain: AbstractOutputStreamChain = RandomFileOutputStreamChain()
        randomFileChain.next = fileChain
        randomFileChain.invoke(responseBody, file, info)
    }
}