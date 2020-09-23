package com.mall.ninecommunity

import com.blankj.utilcode.util.LogUtils
import com.mall.baselibrary.base.view.BaseActivity
import com.mall.ninecommunity.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private var TAG = MainActivity::class.java.simpleName
    }

    override fun initLayoutId(): Int = R.layout.activity_main


    override fun initView() {
        LogUtils.e(TAG, "初始化时间", System.currentTimeMillis() - startActivityTime)
    }

    override fun initData() {

    }
}
