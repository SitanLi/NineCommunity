package com.mall.ninecommunity.download

import android.os.Handler
import com.mall.ninecommunity.data.dao.DownloadInfoDao
import com.mall.ninecommunity.download.listener.DownloadProgressListener
import com.mall.ninecommunity.download.listener.HttpDownOnNextListener
import com.mall.ninecommunity.model.DownloadInfo
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.SoftReference


class ProgressDownSubscriber<T>(private val downloadController: DownloadController, private val downloadInfoDao: DownloadInfoDao, private var downInfo: DownloadInfo, private val handler: Handler) : Observer<T>, DownloadProgressListener {
    //弱引用结果回调
    private var mSubscriberOnNextListener: SoftReference<HttpDownOnNextListener<T>>

    init {
        this.mSubscriberOnNextListener = SoftReference(downInfo.getHttpDownOnNextListener())
    }

    fun setDownInfol(downInfo: DownloadInfo) {
        this.mSubscriberOnNextListener = SoftReference(downInfo.getHttpDownOnNextListener())
        this.downInfo = downInfo
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    override fun onSubscribe(d: Disposable) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get()!!.onStart()
        }
        downInfo.setState(DownState.START)
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    override fun onComplete() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get()!!.onComplete(downInfo)
        }
        downloadController.removeDownload(downInfo)
        downInfo.setState(DownState.FINISH)
        downInfo.readLength = downInfo.countLength
        GlobalScope.launch {
            downloadInfoDao.insert(downInfo)
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     */
    override fun onError(e: Throwable) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get()!!.onError(e)
        }
        downloadController.removeDownload(downInfo)
        downInfo.setState(DownState.ERROR)
        GlobalScope.launch {
            downloadInfoDao.insert(downInfo)
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * @param t 创建Subscriber时的泛型类型
     */
    override fun onNext(t: T) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get()!!.onNext(t)
        }
    }

    override fun update(read: Long, count: Long, done: Boolean) {
        var readNew = read
        if (downInfo.countLength > count) {
            readNew = downInfo.countLength - count + read
        } else {
            downInfo.countLength = count
        }
        downInfo.readLength = readNew

        if (mSubscriberOnNextListener.get() == null || !downInfo.updateProgress) return
        handler.post(Runnable {
            /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
            if (downInfo.getState() === DownState.PAUSE || downInfo.getState() === DownState.STOP) return@Runnable
            downInfo.setState(DownState.DOWN)
            mSubscriberOnNextListener.get()!!.updateProgress(downInfo.readLength, downInfo.countLength)
        })
    }
}