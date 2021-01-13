package com.mall.baselibrary.acp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import com.blankj.utilcode.util.SPStaticUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object MiuiOs {

    private const val UNKNOWN = "UNKNOWN"
    private const val VERSION_CODE_KEY = "xm_sys_version_code"

    /**
     * 获取应用权限设置 Intent <br></br>
     * http://blog.csdn.net/dawanganban/article/details/41749911
     *
     * @param context
     */
    fun getSettingIntent(context: Context): Intent? {
        // 之兼容miui v5/v6/v7  的应用权限设置页面
        var vCode = SPStaticUtils.getInt(VERSION_CODE_KEY)
        if (vCode == 0) {
            vCode = getSystemVersionCode()
            SPStaticUtils.put(VERSION_CODE_KEY, vCode)
        }
        if (vCode >= 6) {
            val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
            intent.putExtra("extra_pkgname", context.packageName)
            return intent
        }
        return null
    }

    /**
     * 获取 V5/V6 后面的数字
     *
     * @return
     */
    fun getSystemVersionCode(): Int {
        val systemProperty = getSystemProperty()
        if (!TextUtils.isEmpty(systemProperty) && systemProperty != UNKNOWN
                && systemProperty.length == 2 && systemProperty.toUpperCase().startsWith("V")) {
            var code: Int? = 0
            try {
                code = Integer.valueOf(systemProperty.substring(1))
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

            return code!!
        }
        return 0
    }

    /**
     * 判断V5/V6
     *
     * @return V5 、V6 、V7 字符
     */
    fun getSystemProperty(): String {
        var line = UNKNOWN
        var reader: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name")
            reader = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = reader.readLine()
            return line
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null)
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

        }
        return line
    }

    /**
     * 判断 activity 是否存在
     *
     * @param context
     * @param intent
     * @return
     */
    fun isIntentAvailable(context: Context, intent: Intent?): Boolean {
        return if (intent == null) false else context.packageManager.queryIntentActivities(intent, 0).size > 0
    }

    /**
     * 检查手机是否是miui
     *
     * @return
     * @ref http://dev.xiaomi.com/doc/p=254/index.html
     */
    fun isMIUI(): Boolean {
        val device = Build.MANUFACTURER
        return device == "Xiaomi"
    }
}