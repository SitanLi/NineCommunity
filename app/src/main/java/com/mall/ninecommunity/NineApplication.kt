package com.mall.ninecommunity

import android.app.Application
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.mall.baselibrary.loadSir.ErrorCallback
import com.mall.baselibrary.loadSir.LoadingCallback
import com.mall.baselibrary.selector.XSelector
import com.mall.ninecommunity.utils.imageload.ImageLoadConfig
import dagger.hilt.android.HiltAndroidApp

/**
 *@Time :2019/10/30
 *@author: Lixiaoping
 *TODO : Application
 */
@HiltAndroidApp
class NineApplication : Application() {

    companion object {
        private var instance: NineApplication? = null
        fun getInstance(): NineApplication = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        //注册工具
        Utils.init(this)
        initLogUtils()
        initLoadSir()
        initCrashUtils()
        //初始化dex
        initMultiDex()
        //初始化颜色背景选择器
        XSelector.init(this)
        //初始化imageLoad图片默认资源
        ImageLoadConfig.instance().initImageResId(R.drawable.icon_user_default, R.drawable.icon_user_default, R.drawable.load_default_image, R.drawable.load_default_image)
    }

    private fun initLoadSir() {
        //LoadSir注册
        LoadSir.beginBuilder()
                .addCallback(LoadingCallback())//加载
                .addCallback(ErrorCallback())//错误
                .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
                .commit()
    }

    private fun initLogUtils() {
        LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG).saveDays = 5
    }

    private fun initCrashUtils() {
        CrashUtils.init { _, _ ->
            AppUtils.relaunchApp()
        }
    }


    private fun initMultiDex() {
        MultiDex.install(this)
    }

}