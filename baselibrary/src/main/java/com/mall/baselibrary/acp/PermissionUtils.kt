package com.mall.baselibrary.acp

import android.annotation.SuppressLint
import com.blankj.utilcode.util.PermissionUtils


object PermissionUtils {
    @SuppressLint("WrongConstant")
    fun permission(vararg permission: String, isForce: Boolean, callback: (Boolean) -> Unit) {
        PermissionUtils.permission(*permission)
                .callback { isAllGranted, _, _, _ ->
                    if (isForce) {
                        if (isAllGranted) {
                            callback.invoke(true)
                        } else {
                            callback.invoke(false)
                        }
                    } else {
                        callback.invoke(true)
                    }
                }.request()
    }
}