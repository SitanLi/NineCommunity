package com.mall.baselibrary.widget

import androidx.annotation.ArrayRes
import com.blankj.utilcode.util.Utils

object ResourceUtils {
    fun getMipmapIdArrays(@ArrayRes resId: Int): Array<Int> {
        val typedArray = Utils.getApp().resources.obtainTypedArray(resId)
        val map = (0 until typedArray.length()).map {
            typedArray.getResourceId(it, -1)
        }.toTypedArray()
        typedArray.recycle()
        return map
    }
}