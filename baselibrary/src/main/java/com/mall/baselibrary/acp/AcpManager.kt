package com.mall.baselibrary.acp

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.blankj.utilcode.util.LogUtils
import java.util.*

class AcpManager {

    companion object {
        const val TAG = "AcpManager"
        const val REQUEST_CODE_PERMISSION = 0x38
        const val REQUEST_CODE_SETTING = 0x39
    }

    private var callback: AcpCallback? = null//授权回调
    private var builder: ActBuilder? = null//授权配置信息
    private var isShowRational = true

    fun setAcPermissionListener(callback: AcpCallback): AcpManager {
        this.callback = callback
        return this
    }

    fun setActBuilder(builder: ActBuilder): AcpManager {
        this.builder = builder
        return this
    }

    fun setShowRational(showRational: Boolean): AcpManager {
        isShowRational = showRational
        return this
    }

    fun request(context: Context) {
        if (AcpConstant.manifestPermissionSet.isEmpty()) {
            getManifestPermissions(context)
        }
        checkSelfPermission(context, null)
    }


    private fun getManifestPermissions(context: Context) {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(
                    context.packageName, PackageManager.GET_PERMISSIONS)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        if (packageInfo != null) {
            val permissions = packageInfo.requestedPermissions
            if (permissions != null) {
                Collections.addAll(AcpConstant.manifestPermissionSet, *permissions)
            }
        }
    }

    /**
     * 检查权限
     */
    private fun checkSelfPermission(context: Context, acpActivity: Activity?) {
        if (builder == null) return
        AcpConstant.deniedPermissionList.clear()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            LogUtils.i(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.M")
            if (callback != null) {
                callback?.onGranted()
            }
            onDestroy(acpActivity)
            return
        }
        val permissions = builder?.perMissions
        permissions?.forEach {
            //检查申请的权限是否在 AndroidManifest.xml 中
            if (AcpConstant.manifestPermissionSet.contains(it)) {
                val checkSelfPermission = AcpService.checkSelfPermission(context, it)
                LogUtils.i(TAG, "checkSelfPermission", checkSelfPermission)
                //如果是拒绝状态则装入拒绝集合中
                if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                    AcpConstant.deniedPermissionList.add(it)
                }
            }
        }
        //检查如果没有一个拒绝响应 onGranted 回调
        if (AcpConstant.deniedPermissionList.isEmpty()) {
            LogUtils.i(TAG, "deniedPermissionList.isEmpty()")
            if (callback != null) {
                callback?.onGranted()
            }
            onDestroy(acpActivity)
            return
        }
        startAcpActivity(context)
    }

    /**
     * 检查权限是否存在拒绝不再提示
     *
     * @param activity
     */
    fun checkRequestPermissionRationale(activity: Activity) {
        var rationale = false
        //如果有拒绝则提示申请理由提示框，否则直接向系统请求权限
        for (permission in AcpConstant.deniedPermissionList) {
            rationale = rationale || AcpService.shouldShowRequestPermissionRationale(activity, permission)
        }
        LogUtils.i(TAG, "rationale", rationale)
        val permissions = AcpConstant.deniedPermissionList.toArray(arrayOf<String>())
        if (rationale && isShowRational) {
            showRationalDialog(activity, permissions)
        } else {
            requestPermissions(activity, permissions)
        }
    }


    /**
     * 响应向系统请求权限结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                val grantedPermissions = LinkedList<String>()
                val deniedPermissions = LinkedList<String>()
                for (i in permissions.indices) {
                    val permission = permissions[i]
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        grantedPermissions.add(permission)
                    } else {
                        deniedPermissions.add(permission)
                    }
                }
                //全部允许才回调 onGranted 否则只要有一个拒绝都回调 onDenied
                if (!grantedPermissions.isEmpty() && deniedPermissions.isEmpty()) {
                    if (callback != null) {
                        callback?.onGranted()
                    }
                    onDestroy(activity)
                } else if (!deniedPermissions.isEmpty()) {
                    showDeniedDialog(activity, deniedPermissions)
                }
            }
        }
    }

    /**
     * 响应设置权限返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(activity: Activity, requestCode: Int) {
        if (callback == null || builder == null || requestCode != REQUEST_CODE_SETTING) {
            onDestroy(activity)
            return
        }
        checkSelfPermission(activity, activity)
    }


    /**
     * 申请理由对话框
     *
     * @param permissions
     */
    private fun showRationalDialog(activity: Activity, permissions: Array<String>) {
        if (builder == null) return
        val alertDialog = AlertDialog.Builder(activity)
                .setMessage(builder?.rationalMessage)
                .setPositiveButton(builder?.rationalBtn) { _, _ -> requestPermissions(activity, permissions) }.create()
        alertDialog.setCancelable(builder?.dialogCancelable!!)
        alertDialog.setCanceledOnTouchOutside(builder?.dialogCanceledOnTouchOutside!!)
        alertDialog.show()
    }


    /**
     * 拒绝权限提示框
     *
     * @param permissions
     */
    private fun showDeniedDialog(activity: Activity, permissions: List<String>) {
        if (builder == null) return
        val alertDialog = AlertDialog.Builder(activity)
                .setMessage(builder?.deniedMessage)
                .setNegativeButton(builder?.deniedCloseBtn) { _, _ ->
                    if (callback != null) {
                        callback?.onDenied(permissions)
                    }
                    onDestroy(activity)
                }
                .setPositiveButton(builder?.deniedSettingBtn) { _, _ -> startSetting(activity) }.create()
        alertDialog.setCancelable(builder?.dialogCancelable!!)
        alertDialog.setCanceledOnTouchOutside(builder?.dialogCanceledOnTouchOutside!!)
        alertDialog.show()
    }


    /**
     * 向系统请求权限
     *
     * @param permissions
     */
    private fun requestPermissions(activity: Activity, permissions: Array<String>) {
        AcpService.requestPermissions(activity, permissions, REQUEST_CODE_PERMISSION)
    }


    /**
     * 启动处理权限过程的 Activity
     */
    private fun startAcpActivity(context: Context) {
        val intent = Intent(context, AcpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * 跳转到设置界面
     */
    private fun startSetting(activity: Activity) {
        if (MiuiOs.isMIUI()) {
            val intent = MiuiOs.getSettingIntent(activity)
            if (MiuiOs.isIntentAvailable(activity, intent)) {
                activity.startActivityForResult(intent, REQUEST_CODE_SETTING)
                return
            }
        }
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + activity.packageName)
            activity.startActivityForResult(intent, REQUEST_CODE_SETTING)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                activity.startActivityForResult(intent, REQUEST_CODE_SETTING)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

        }

    }

    /**
     * 摧毁本库的 AcpActivity
     */
    private fun onDestroy(activity: Activity?) {
        callback = null
        activity?.finish()
    }
}