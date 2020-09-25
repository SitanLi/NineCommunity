package com.mall.ninecommunity.controller.inter

import com.mall.ninecommunity.download.listener.HttpDownOnNextListener
import com.mall.ninecommunity.model.DownloadInfo

/**
 *Time:2020/8/31
 *Author:HevenHolt
 *Description:下载器
 */
interface DownloadController {
    /**
     * 开始下载
     */
    suspend fun startDownload(url: String, httpDownOnNextListener: HttpDownOnNextListener<*>)

    suspend fun startDownload(url: String, savePath: String, httpDownOnNextListener: HttpDownOnNextListener<*>)

    /**
     * 开始下载
     */
    fun startDownload(downloadInfo: DownloadInfo)

    /**
     * 多文件下载
     */
    suspend fun startDownload(urls: List<String>, httpDownOnNextListener: HttpDownOnNextListener<*>)

    /**
     * 多文件下载
     */
    fun startDownload(mSetDownInfo: MutableSet<DownloadInfo>)

    /**
     * 移除下载
     */
    fun removeDownload(downloadInfo: DownloadInfo)

    /**
     * 生成常规的DownloadInfo
     */
   suspend fun generalDownloadInfo(url: String): DownloadInfo
}