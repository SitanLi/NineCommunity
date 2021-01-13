package com.mall.baselibrary.pic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.mall.baselibrary.ext.scanFile
import java.io.File

class PicManager {
    companion object {
        const val TAG = "PicManager"
        const val REQUEST_CODE_CAMERA = 1009//拍照
        const val REQUEST_CODE_PICTURE = 1008//相册
        const val REQUEST_CODE_CUT_PIC = 1007//裁剪
    }

    private var builder: PicBuilder? = null
    private var listener: PicListener? = null

    fun setPicBuilder(builder: PicBuilder): PicManager {
        this.builder = builder
        return this
    }

    fun setListener(listener: PicListener): PicManager {
        this.listener = listener
        return this
    }

    fun takeCamera(context: Context) {
        behavior(context as Activity, REQUEST_CODE_CAMERA)
    }

    fun takePhotoAlbum(context: Context) {
        behavior(context as Activity, REQUEST_CODE_PICTURE)
    }

    private fun behavior(activity: Activity, type: Int) {
        if (type == REQUEST_CODE_CAMERA) {
            executeCamera(activity)
        } else if (type == REQUEST_CODE_PICTURE) {
            executePhotoAlbum(activity)
        }
    }

    private fun executeCamera(context: Activity) {
        val imageUri: Uri?
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = try {
                val authProvider = AppUtils.getAppPackageName() + ".FileProvider"
                builder?.cacheFile?.let { FileProvider.getUriForFile(context, authProvider, it) }
            } catch (e: Exception) {
                e.printStackTrace()
                Uri.fromFile(builder?.cacheFile)
            }

            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            imageUri = Uri.fromFile(builder?.cacheFile)
        }
        if (imageUri == null) {
            ToastUtils.showShort("获取uri为空")
            return
        }
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        context.startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    private fun executePhotoAlbum(context: Activity) {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        context.startActivityForResult(intent, REQUEST_CODE_PICTURE)
    }

    private fun crop(context: Activity, uri: Uri?) {

        LogUtils.iTag(TAG, "crop uri：%s", uri)

        val intent = Intent("com.android.camera.action.CROP")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.setDataAndType(uri, "image/*")
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true")

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", builder?.cropWidth)
        intent.putExtra("outputY", builder?.cropHeight)
        intent.putExtra("return-data", false)

        // intent.putExtra(MediaStore.EXTRA_OUTPUT,
        val cacheFile = builder?.cacheFile ?: return
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cacheFile))
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection

        context.startActivityForResult(intent, REQUEST_CODE_CUT_PIC)

    }

    fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, intent: Intent?) {
        if (listener == null || requestCode != REQUEST_CODE_CAMERA && requestCode != REQUEST_CODE_PICTURE && requestCode != REQUEST_CODE_CUT_PIC) {
            LogUtils.eTag(TAG, "onActivityResult Unknown requestCode or listener is null")
            return
        }
        if (resultCode != Activity.RESULT_OK) {
            LogUtils.eTag(TAG, "onActivityResult--cancel--resultCode", resultCode)
            return
        }
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                LogUtils.iTag(TAG, "onActivityResult camera")
                builder?.cacheFile?.let {
                    scanFile(activity, it, builder?.isCrop!!)
                }
            }
            REQUEST_CODE_PICTURE -> {

                if (intent == null || intent.data == null) {
                    LogUtils.eTag(TAG, "onActivityResult picture data is null ")
                    listener?.onTakePicFail()
                    return
                }
                LogUtils.iTag(TAG, "onActivityResult picture")
                if (builder?.isCrop!!) {
                    crop(activity, intent.data)
                } else {
                    LogUtils.iTag(TAG, "onActivityResult picture callback uri：%s", intent.data)
                    listener?.onTakePicSuccess(intent.data!!)
//                    onDestroy(activity)
                }
            }
            REQUEST_CODE_CUT_PIC -> {
                LogUtils.iTag(TAG, "onActivityResult crop")
                builder?.cacheFile?.let { scanFile(activity, it, false) }
            }
        }
    }

    private fun scanFile(activity: Activity, file: File, isCrop: Boolean) {
        file.scanFile(activity) { uri ->
            if (isCrop) {
                crop(activity, uri)
            } else {
                LogUtils.iTag(TAG, "scanFile camera or crop callback uri：%s", uri)
                listener?.onTakePicSuccess(uri)
            }
        }
    }
}
