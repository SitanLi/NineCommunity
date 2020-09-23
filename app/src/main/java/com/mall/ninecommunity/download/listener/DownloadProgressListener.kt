package com.mall.ninecommunity.download.listener

interface DownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    fun update(read: Long, count: Long, done: Boolean)
}