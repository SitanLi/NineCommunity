package com.mall.ninecommunity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.mall.baselibrary.base.view.BaseActivity
import com.mall.ninecommunity.databinding.ActivityMainBinding
import com.mall.ninecommunity.dialog.AppUpdateDialog
import com.mall.ninecommunity.utils.InjectorUtils
import com.mall.ninecommunity.viewmodels.viewmodel.DownloadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val downloadViewModel: DownloadViewModel by viewModels { InjectorUtils.provideDownloadViewModelFactory() }

    companion object {
        private var TAG = MainActivity::class.java.simpleName
    }

    override fun initLayoutId(): Int = R.layout.activity_main


    override fun initView() {
        LogUtils.e(TAG, "初始化时间", System.currentTimeMillis() - startActivityTime)
    }

    override fun initData() {
        downloadViewModel.initData()
        downloadViewModel.versionBean.observe(this, Observer {
//            if (BuildConfig.DEBUG)return@Observer
            AppUpdateDialog(this, it).show()
        })
    }
}
