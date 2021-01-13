package com.mall.ninecommunity.http

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import com.blankj.utilcode.util.*
import com.mall.ninecommunity.BuildConfig
import com.mall.ninecommunity.controller.inter.UserInfoController
import com.mall.ninecommunity.hilt.ControllerEntryPoint
import dagger.hilt.android.EntryPointAccessors
import org.json.JSONObject
import java.util.*

object ApiPublicParams {
    private var userInfoController: UserInfoController? = null
    private var channelName: String = ""

    init {
        val entryPoint = EntryPointAccessors.fromApplication(Utils.getApp(), ControllerEntryPoint::class.java)
        userInfoController = entryPoint.userInfoController()
    }

    /**
     * 获取友盟渠道名
     */
    fun getChannel(): String {
        if (channelName.isNotEmpty()) return channelName
        channelName = "official"
        try {
            val packageManager = Utils.getApp().packageManager
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                val applicationInfo = packageManager.getApplicationInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_META_DATA)
                if (applicationInfo != null) {
                    val metaData = applicationInfo.metaData
                    if (metaData != null) {
                        //此处这样写的目的是为了在debug模式下也能获取到渠道号，如果用getString的话只能在Release下获取到。
                        val channel = metaData.get("UMENG_CHANNEL")
                        if (channel != null)
                            channelName = channel.toString()
                    }
                }

            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return channelName
    }

    private var deviceId = "00000000"

    /**
     * 获取设备号，当无法获取时，返回00000000
     */
    @SuppressLint("MissingPermission")
    fun getDeviceId(): String {
//        LogUtils.e("umCutomId : ${UMConfigure.getUMIDString(Utils.getApp())}")
        if ("00000000" == deviceId && PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            deviceId = EncryptUtils.encryptMD5ToString(PhoneUtils.getDeviceId()).toLowerCase(Locale.getDefault())
        }
        return deviceId
    }

    /**
     * 获取手机设备型号
     */
    fun getClientModel(): String = Build.MODEL ?: "unknow"

    private var signature = ""

    /**
     * 获取签名
     */
    fun getSignature(): String {
        if (signature.isNotEmpty()) return signature
        signature = AppUtils.getAppSignatureMD5()
        return signature
    }

    /**
     * 获取网络类型
     */
    fun getNetworkTypeName(): String {
        var netTypeName = "unknow"
        if (PermissionUtils.isGranted(Manifest.permission.ACCESS_NETWORK_STATE))
            netTypeName = NetworkUtils.getNetworkType().name
        return netTypeName
    }

    /**
     * 获取系统OS版本
     */
    fun getOSVerion() = Build.VERSION.RELEASE ?: "unknow"

    /**
     * 获取包名
     */
    fun getPackageName() = AppUtils.getAppPackageName() ?: "unknow"

    /**
     *获取token
     */
    fun getToken(): String? {
        val userInfoBean = userInfoController?.getUserInfo()
        return userInfoBean?.tokenType + " " + userInfoBean?.accessToken
    }

    /**
     * 获取用户id
     */
    fun getUserId(): String? {
        val userInfoBean = userInfoController?.getUserInfo()
        return userInfoBean?.userNo
    }

    /**
     * 获取App版本号
     */
    fun getAppVersionCode() = AppUtils.getAppVersionCode()

    /**
     * 获取App版本名
     */
    fun getAppVersionName() = AppUtils.getAppVersionName() ?: "unknow"

    /**
     *获取客户端类型
     */
    fun getClientType() = "0" //0 安卓，1 iOS

    /**
     * h5专用
     */
    fun toJSONString(token: String? = null): String? {
        val json = JSONObject()
        json.put("token", token)
        json.put("clientType", getClientType())
        json.put("versionCode", getAppVersionCode())
        json.put("versionName", getAppVersionName())
        json.put("pk", getPackageName())
        return json.toString()
    }
}