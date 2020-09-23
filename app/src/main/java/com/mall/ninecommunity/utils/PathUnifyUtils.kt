package com.mall.ninecommunity.utils

import com.blankj.utilcode.util.PathUtils
import java.io.File

/**
 *Time:2020/7/31
 *Author:HevenHolt
 *Description:
 */
object PathUnifyUtils {
    fun getPictureDir(): String {
        val path = PathUtils.getExternalPicturesPath() + File.separator + "sqw"
        val file = File(path)
        if (!file.exists()) file.mkdirs()
        return path
    }
}