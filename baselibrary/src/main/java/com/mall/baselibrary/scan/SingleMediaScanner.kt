package com.mall.baselibrary.scan

import android.content.Context
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import com.blankj.utilcode.util.ViewUtils
import java.io.File

/**
 * Time:2020/7/30
 * Author:HevenHolt
 * Description:文件扫描类
 */
class SingleMediaScanner(context: Context?, private val mFile: File, private val listener: (String, Uri) -> Unit) : MediaScannerConnectionClient {
    private val mMs: MediaScannerConnection = MediaScannerConnection(context, this)

    init {
        mMs.connect()
    }

    override fun onMediaScannerConnected() {
        mMs.scanFile(mFile.absolutePath, "image/jpeg")
    }

    override fun onScanCompleted(path: String, uri: Uri) {
        mMs.disconnect()
        ViewUtils.runOnUiThread { listener.invoke(path, uri) }
    }
}