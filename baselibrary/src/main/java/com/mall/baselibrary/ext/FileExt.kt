package com.mall.baselibrary.ext

import android.content.Context
import android.net.Uri
import com.mall.baselibrary.scan.SingleMediaScanner
import java.io.File

/**
 *Time:2020/7/30
 *Author:HevenHolt
 *Description:文件扫描拓展
 */

fun File.scanFile(context: Context, callback: (Uri) -> Unit) {
    SingleMediaScanner(context, this) { _, uri ->
        callback.invoke(uri)
    }
}

