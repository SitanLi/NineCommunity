@file:Suppress("UNREACHABLE_CODE")

package com.mall.baselibrary.pic

import android.os.Parcel
import android.os.Parcelable
import java.io.File

/**
 *@Time :2019/11/5
 *@author: Lixiaoping
 *TODO :
 */
class PicBuilder(
        var cacheFile: File? = null,
        var isCrop: Boolean? = false,
        var cropWidth: Int? = 0,
        var cropHeight: Int? = 0,
        var providerAuthority: String? = ""
) {

    fun builder(): PicBuilder {
        if (cacheFile == null) throw IllegalArgumentException("cacheFile no found...")
        return PicBuilder(cacheFile, isCrop, cropWidth, cropHeight, providerAuthority)
    }

}