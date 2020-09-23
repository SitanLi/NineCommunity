package com.mall.ninecommunity.utils

import android.Manifest
import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.mall.baselibrary.acp.Acp
import com.mall.baselibrary.acp.AcpCallback
import com.mall.baselibrary.acp.ActBuilder
import com.mall.baselibrary.pic.Pic
import com.mall.baselibrary.pic.PicBuilder
import com.mall.baselibrary.pic.PicListener

/**
 *@Time :2019/11/4
 *@author: Lixiaoping
 *TODO :
 */
object PicUtils {
    const val TAKE_CAMERA = 1//拍照
    const val TAKE_PICTURE = 2//相册

    fun takePic(context: Context, type: Int, builder: PicBuilder, listener: PicListener) {
        Acp.getAcpManager().setShowRational(false)
                .setActBuilder(ActBuilder(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)).build())
                .setAcPermissionListener(object : AcpCallback {
                    override fun onGranted() {
                        LogUtils.i("权限申请--同意")
                        if (type == TAKE_CAMERA) {
                            Pic.getPicManager().setPicBuilder(builder).setListener(listener).takeCamera(context)

                        } else if (type == TAKE_PICTURE) {
                            Pic.getPicManager().setPicBuilder(builder).setListener(listener).takePhotoAlbum(context)
                        }
                    }

                    override fun onDenied(permissions: List<String>) {
                        LogUtils.e("权限申请--拒绝", permissions.toString())
                        ToastUtils.showShort("权限申请--拒绝$permissions")
                    }
                }).request(context)

    }
}
