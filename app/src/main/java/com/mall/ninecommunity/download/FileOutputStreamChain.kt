package com.mall.ninecommunity.download

import com.blankj.utilcode.util.CloseUtils
import com.mall.ninecommunity.model.DownloadInfo
import okhttp3.ResponseBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 *Time:2020/3/25
 *Author:HevenHolt
 *Description:
 */
class FileOutputStreamChain : AbstractOutputStreamChain(OUTPUT_STREAM_FILE) {
    override fun execute(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        if (!file.parentFile.exists())
            file.parentFile.mkdirs()

        var inputStream: InputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            inputStream = responseBody.byteStream()
            bos = BufferedOutputStream(FileOutputStream(file))
            val buffer = ByteArray(1024 * 1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                bos.write(buffer, 0, len)
            }
            bos.flush()
        } catch (e: Exception) {
            throw HttpTimeException(e.message)
        } finally {
            CloseUtils.closeIO(inputStream)
            CloseUtils.closeIO(bos)
        }
    }
}