package com.mall.ninecommunity.download

import com.mall.ninecommunity.model.DownloadInfo
import okhttp3.ResponseBody
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile
import java.nio.channels.FileChannel

/**
 *Time:2020/3/25
 *Author:HevenHolt
 *Description:
 */
class RandomFileOutputStreamChain : AbstractOutputStreamChain(OUTPUT_STREAM_RANDOM_FILE) {
    override fun execute(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        var randomAccessFile: RandomAccessFile? = null
        var channelOut: FileChannel? = null
        var inputStream: InputStream? = null
        try {
            if (!file.parentFile.exists())
                file.parentFile.mkdirs()
            val allLength = if (0L == info.countLength)
                responseBody.contentLength()
            else
                info.readLength + responseBody
                        .contentLength()
            inputStream = responseBody.byteStream()

            randomAccessFile = RandomAccessFile(file, "rwd")
            channelOut = randomAccessFile.channel
            val mappedBuffer = channelOut!!.map(FileChannel.MapMode.READ_WRITE,
                    info.readLength, allLength - info.readLength)
            val buffer = ByteArray(1024 * 1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                mappedBuffer.put(buffer, 0, len)
            }
        } catch (e: Exception) {
            throw HttpTimeException(e.message)
        } finally {
            inputStream?.close()
            channelOut?.close()
            randomAccessFile?.close()
        }
    }
}