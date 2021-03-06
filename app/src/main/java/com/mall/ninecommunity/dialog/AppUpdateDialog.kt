package com.mall.ninecommunity.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.mall.baselibrary.base.dialog.BaseDialog
import com.mall.ninecommunity.R
import com.mall.ninecommunity.controller.inter.DownloadController
import com.mall.ninecommunity.databinding.DialogAppUpdateBinding
import com.mall.ninecommunity.download.listener.HttpDownOnNextListener
import com.mall.ninecommunity.hilt.ControllerEntryPoint
import com.mall.ninecommunity.model.DownloadInfo
import com.mall.ninecommunity.model.VersionBean
import com.mall.ninecommunity.utils.PathUnifyUtils
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

/**
 *@Time :2019/11/1
 *@author: Lixiaoping
 * 下载更新dialog
 */
class AppUpdateDialog(context: Context, private var versionVo: VersionBean) : BaseDialog<DialogAppUpdateBinding>(context, R.style.public_Theme_dialog) {
    private var downloadController: DownloadController? = null

    init {
        initWidthAndHeightView(Gravity.CENTER, ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(397f))
        initUI()
        initData()
    }

    override fun initBindingResId(): Int = R.layout.dialog_app_update

    private fun initData() {
        val entryPoint = EntryPointAccessors.fromApplication(Utils.getApp(), ControllerEntryPoint::class.java)
        downloadController = entryPoint.downloadController()
    }

    private fun initUI() {
        binding.update = versionVo
        binding.noForceUpdateLayout.handle = UpdateHandle()
        if (versionVo.musted == 1) toDownLoad()
    }

    private fun toDownLoad() {
        val listener: HttpDownOnNextListener<DownloadInfo> = object : HttpDownOnNextListener<DownloadInfo>() {
            override fun onNext(t: DownloadInfo) {

            }

            override fun onStart() {

            }

            override fun onComplete(downInfo: DownloadInfo) {
                val fileName = versionVo.url?.substring(versionVo.url!!.lastIndexOf("/"))
                val file = File(PathUnifyUtils.getPictureDir() + fileName)
                AppUtils.installApp(file)
                dismiss()
            }

            override fun updateProgress(readLength: Long, countLength: Long) {
                binding.progressView.progress = ((readLength.toFloat() / countLength.toFloat()) * 100).toInt()
                binding.update?.progressTips = String.format(context.getString(R.string.app_update_tips), ((readLength.toFloat() / countLength.toFloat()) * 100).toInt())
                LogUtils.e("errrrr", readLength, countLength)
            }
        }
        GlobalScope.launch {
            downloadController?.startDownload(versionVo.url ?: "", listener)
        }
    }

    override fun onBackPressed() {
        if (versionVo.musted != 1)
            super.onBackPressed()
    }

    inner class UpdateHandle {
        fun confirm() {
            toDownLoad()
        }

        fun cancel(view: View) {
            dismiss()
        }
    }

}