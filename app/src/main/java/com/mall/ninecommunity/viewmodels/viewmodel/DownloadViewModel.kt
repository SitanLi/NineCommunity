package com.mall.ninecommunity.viewmodels.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.Utils
import com.mall.ninecommunity.data.repository.DownloadRepository
import com.mall.ninecommunity.model.DownloadInfo
import com.mall.ninecommunity.model.VersionBean

/**
 *@createTime :2020/9/21  11:29
 *@Author:XiaopingLi
 *@Description :
 */
class DownloadViewModel @ViewModelInject constructor(private val repository: DownloadRepository) : AndroidViewModel(Utils.getApp()) {
    var versionBean = MutableLiveData<VersionBean>()
    suspend fun getDownInfo(url: String): DownloadInfo? = repository.queryDownloadInfoByPath(url)
    suspend fun addDownInfo(downloadInfo: DownloadInfo) = repository.insertOrUpdate(downloadInfo)

    fun initData() {
        val url = "https://df8d2df77364d304bf0087c727a977c0.dlied1.cdntips.net/imtt.dd.qq.com/16891/apk/3355024D2C10BB75A2150E22231B20A0.apk"
        versionBean.value = VersionBean("1.0.0", url, 0, "com.tencent.qq", "第一个APP", 0, "当前进度")
    }
}