package com.mall.ninecommunity.download

import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.mall.ninecommunity.data.dao.DownloadInfoDao
import com.mall.ninecommunity.download.listener.DownloadInterceptor
import com.mall.ninecommunity.download.listener.HttpDownOnNextListener
import com.mall.ninecommunity.http.api.Api
import com.mall.ninecommunity.model.DownloadInfo
import com.mall.ninecommunity.utils.PathUnifyUtils
import com.mall.ninecommunity.utils.SDCardUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *Time:2020/8/31
 *Author:HevenHolt
 *Description:
 */
class DownloadControllerIml(private val downloadInfoDao: DownloadInfoDao) : DownloadController {
    /*记录下载数据*/
    private val downInfos: MutableSet<DownloadInfo>

    /*回调sub队列*/
    private val subMap: HashMap<String, ProgressDownSubscriber<*>>

    /*下载进度回掉主线程*/
    private val handler: Handler

    /*可用内存*/
    private var sdcardAvailableSize = 0L

    init {
        downInfos = HashSet()
        subMap = HashMap()
        handler = Handler(Looper.getMainLooper())
    }

    override suspend fun startDownload(url: String, httpDownOnNextListener: HttpDownOnNextListener<*>) {
        startDownload(url, PathUnifyUtils.getPictureDir(), httpDownOnNextListener)
    }

    override suspend fun startDownload(url: String, savePath: String, httpDownOnNextListener: HttpDownOnNextListener<*>) {
        val downInfo = generalDownloadInfo(url, savePath)
        downInfo.apply {
            updateProgress = true
        }
        downInfo.listener = httpDownOnNextListener
        startDownload(downInfo)
    }

    override fun startDownload(downloadInfo: DownloadInfo) {
        /*正在下载不处理*/
        if (subMap[downloadInfo.url] != null) {
            subMap[downloadInfo.url]?.setDownInfol(downloadInfo)
            return
        }
        /*添加回调处理类*/
        val subscriber = ProgressDownSubscriber<DownloadInfo>(this, downloadInfoDao, downloadInfo, handler)
        /*记录回调sub*/
        subMap[downloadInfo.url] = subscriber
        /*下载完成检测文件是否存在*/
        if (checkHasDownload(downloadInfo)) {
            LogUtils.d("文件已经存在，不需要二次下载")
            return
        }
        /*检测内存大小*/
        if (sdcardAvailableSize == 0L) {
            sdcardAvailableSize = SDCardUtils.getSDAvailableSize()
        }
        if (sdcardAvailableSize <= 10 * 1024 * 1024) {
            ToastUtils.showShort("内存空间不足，无法下载...")
            return
        }

        /*获取service，多次请求公用一个sercie*/
        val httpService: HttpDownService
        if (downInfos.contains(downloadInfo)) {
            httpService = downloadInfo.service!!
        } else {
            val interceptor = DownloadInterceptor(subscriber)
            val builder = OkHttpClient.Builder()
            //手动创建一个OkHttpClient并设置超时时间
            builder.connectTimeout(downloadInfo.connectionTime.toLong(), TimeUnit.SECONDS)
            builder.addInterceptor(interceptor)

            val retrofit = Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Api.ApiHolder.BASE_URL)
                    .build()
            httpService = retrofit.create(HttpDownService::class.java)
            downloadInfo.service = httpService
            downInfos.add(downloadInfo)
        }

        /*得到rx对象-上一次下載的位置開始下載*/
        httpService.download("bytes=" + downloadInfo.readLength + "-", downloadInfo.url)
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*失败后的retry配置*/
                .retryWhen(RetryWhenNetworkException())
                /*读取下载写入文件*/
                .map {
                    val fileName = downloadInfo.url.substring(downloadInfo.url.lastIndexOf("/"))
                    HttpDownWriteUtils.writeFileFromResponse(it, File(downloadInfo.savePath + "/$fileName"), downloadInfo)
                    downloadInfo
                }
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber)
    }

    override suspend fun startDownload(urls: List<String>, httpDownOnNextListener: HttpDownOnNextListener<*>) {
        val downInfoList: List<DownloadInfo?> = urls.map {
            val downInfo = generalDownloadInfo(it)
            downInfo.listener = httpDownOnNextListener
            downInfo
        }
        val toMutableSet = downInfoList.filterNotNull().toMutableSet()
        startDownload(toMutableSet)
    }

    override fun startDownload(mSetDownInfo: MutableSet<DownloadInfo>) {
        if (mSetDownInfo.isEmpty()) return
        /*检测内存大小*/
        if (sdcardAvailableSize == 0L) {
            sdcardAvailableSize = SDCardUtils.getSDAvailableSize()
        }
        if (sdcardAvailableSize <= 100 * 1024 * 1024) {
            LogUtils.e("内存空间不足，无法下载...")
            return
        }
        mSetDownInfo.forEach { info ->
            /*正在下载不处理*/
            if (subMap[info.url] != null) {
                subMap[info.url]?.setDownInfol(info)
                return@forEach
            }
            /*添加回调处理类*/
            val subscriber = ProgressDownSubscriber<DownloadInfo>(this, downloadInfoDao, info, handler)
            /*记录回调sub*/
            subMap[info.url] = subscriber
            /*下载完成检测文件是否存在*/
            if (checkHasDownload(info)) {
                LogUtils.d("文件已经存在，不需要二次下载")
                return@forEach
            }
            val httpService: HttpDownService
            if (downInfos.contains(info)) {
                httpService = info.service!!
            } else {
                val interceptor = DownloadInterceptor(subscriber)
                val builder = OkHttpClient.Builder()
                //手动创建一个OkHttpClient并设置超时时间
                builder.connectTimeout(info.connectionTime.toLong(), TimeUnit.SECONDS)
                builder.addInterceptor(interceptor)

                val retrofit = Retrofit.Builder()
                        .client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(Api.ApiHolder.BASE_URL)
                        .build()
                httpService = retrofit.create(HttpDownService::class.java)
                info.service = httpService
                downInfos.add(info)
            }
            /*得到rx对象-上一次下載的位置開始下載*/
            httpService.download("bytes=" + info.readLength + "-", info.url)
                    /*指定线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*失败后的retry配置*/
                    .retryWhen(RetryWhenNetworkException())
                    /*读取下载写入文件*/
                    .map {
                        val fileName = info.url.substring(info.url.lastIndexOf("/"))
                        HttpDownWriteUtils.writeFileFromResponse(it, File(info.savePath + "/$fileName"), info)
                        info
                    }
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread())
                    /*数据回调*/
                    .subscribe(subscriber)
        }
    }

    override fun removeDownload(downloadInfo: DownloadInfo) {
        subMap.remove(downloadInfo.url)
        downInfos.remove(downloadInfo)
    }

    override suspend fun generalDownloadInfo(url: String): DownloadInfo = generalDownloadInfo(url, PathUnifyUtils.getPictureDir())

    private suspend fun generalDownloadInfo(url: String, savePath: String): DownloadInfo {
        var downloadInfo = downloadInfoDao.getDownloadInfo(url)
        if (null == downloadInfo) {
            downloadInfo = DownloadInfo(url).apply {
                this.savePath = savePath
            }
            downloadInfoDao.insert(downloadInfo)
        }
        return downloadInfo
    }

    private fun checkHasDownload(downInfo: DownloadInfo): Boolean {
        if (downInfo.getState() == DownState.FINISH || downInfo.getState() == DownState.ERROR) {
            val fileName = downInfo.url.substring(downInfo.url.lastIndexOf("/"))
            val file = File(downInfo.savePath + fileName)
            val isDownload = file.exists() && file.length() == downInfo.countLength
            if (!isDownload) {
                file.deleteOnExit()
                downInfo.readLength = 0
            } else {
                subMap[downInfo.url]?.onComplete()
                removeDownload(downInfo)
                downInfo.setState(DownState.FINISH)
            }
            GlobalScope.launch {
                downloadInfoDao.insert(downInfo)
            }
            return isDownload
        }
        return false
    }
}