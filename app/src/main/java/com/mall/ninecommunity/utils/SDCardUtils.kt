package com.mall.ninecommunity.utils

import android.os.StatFs
import com.blankj.utilcode.util.PathUtils

/**
 *
 */
object SDCardUtils {
    /**
     * 获得sd卡剩余容量，即可用大小
     */
    fun getSDAvailableSize(): Long {
        val externalStorageDirectory = PathUtils.getExternalStoragePath()
        val statFs = StatFs(externalStorageDirectory)
        val blockSize: Long
        val availableBlocks: Long
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.blockSizeLong
            availableBlocks = statFs.availableBlocksLong
        } else {
            blockSize = statFs.blockSize.toLong()
            availableBlocks = statFs.availableBlocks.toLong()
        }
        return blockSize * availableBlocks
    }
}