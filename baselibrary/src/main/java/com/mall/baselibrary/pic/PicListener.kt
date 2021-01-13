package com.mall.baselibrary.pic

import android.net.Uri

interface PicListener {

    fun onTakePicSuccess(uri: Uri?)

    fun onTakePicFail()
}
