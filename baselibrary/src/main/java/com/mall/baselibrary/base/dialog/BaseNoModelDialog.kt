package com.mall.baselibrary.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * Administrator
 * created at 2018/11/7 15:14
 * TODO:Dialog基类
 */
abstract class BaseNoModelDialog<VM : ViewDataBinding>(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    protected lateinit var binding: VM

    protected open fun initBinding() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), initBindingResId(), null, false)
    }

    protected fun initMatchWidthContentView(gravity: Int) {
        initBinding()
        initCustomWidthContentView(binding.root, gravity, ScreenUtils.getScreenWidth())
    }

    protected fun initGapWidthContentView(gravity: Int) {
        initBinding()
        initCustomWidthContentView(binding.root, gravity,
                ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(40f)
        )
    }

    protected fun initCustomWidthAndYContentView(gravity: Int, width: Int, y: Int) {
        initBinding()
        setContentView(binding.root)
        window!!.setGravity(gravity)
        val lp = window!!.attributes
        lp.width = width
        lp.y = y
        window!!.attributes = lp
        this.setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    protected fun initWidthAndHeightView(gravity: Int, width: Int, height: Int) {
        initBinding()
        setContentView(binding.root)
        window!!.setGravity(gravity)
        val lp = window!!.attributes
        lp.width = width
        lp.height = height
        window!!.attributes = lp
        this.setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    protected fun initDefaultContentView(gravity: Int) {
        initBinding()
        setContentView(binding.root)
        window!!.setGravity(gravity)
        val lp = window!!.attributes
        window!!.attributes = lp
        this.setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    protected fun initCustomWidthContentView(layout: View, gravity: Int, width: Int) {
        setContentView(layout)
        window!!.setGravity(gravity)
        val lp = window!!.attributes
        lp.width = width
        window!!.attributes = lp
        this.setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    protected fun initFullScreenContentView(layout: Int) {
        setContentView(layout)
        val lp = window!!.attributes
        lp.gravity = Gravity.BOTTOM
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        window!!.attributes = lp
    }

    abstract fun initBindingResId(): Int

    /**
     * toast显示
     *
     * @param resId string资源id
     */
    protected fun showMsgToast(resId: Int) {
        ToastUtils.showShort(context.resources.getString(resId))
    }

    /**
     * toast显示
     *
     * @param msg
     */
    protected fun showMsgToast(msg: String) {
        ToastUtils.showShort(msg)
    }

}
