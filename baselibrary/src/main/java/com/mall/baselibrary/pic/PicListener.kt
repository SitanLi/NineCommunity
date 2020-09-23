package com.mall.baselibrary.pic

import android.net.Uri

/**
 * @Author Administrator
 * @Date 2019/10/18-9:59
 * @TODO
 */
interface PicListener {

    fun onTakePicSuccess(uri: Uri)

    fun onTakePicFail()
}
