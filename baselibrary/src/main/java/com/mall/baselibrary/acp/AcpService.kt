package com.mall.baselibrary.acp

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.blankj.utilcode.util.LogUtils

object AcpService {
    /**
     * 检查权限授权状态
     *
     * @param context    上下文
     * @param permission 权限
     * @return
     */
    fun checkSelfPermission(context: Context, permission: String): Int {
        try {
            val info = context.packageManager.getPackageInfo(
                    context.packageName, 0)
            val targetSdkVersion = info.applicationInfo.targetSdkVersion
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    ContextCompat.checkSelfPermission(context, permission)
                } else {
                    PermissionChecker.checkSelfPermission(context, permission)
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ContextCompat.checkSelfPermission(context, permission)
    }

    /**
     * 申请授权
     *
     * @param activity    activity
     * @param permissions 取消
     * @param requestCode 请求码
     */
    fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun shouldShowRequestPermissionRationale(activity: Activity, permission: String): Boolean {
        val shouldShowRational = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        LogUtils.i("shouldShowRational = $shouldShowRational")
        return shouldShowRational
    }
}