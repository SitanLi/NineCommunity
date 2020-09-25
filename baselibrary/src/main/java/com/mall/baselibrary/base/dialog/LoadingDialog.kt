package com.mall.baselibrary.base.dialog

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import com.mall.baselibrary.R
import com.mall.baselibrary.databinding.DialogLoadingBinding

/**
 * LoadingDialog
 */
class LoadingDialog(context: Context, themeResId: Int) : BaseDialog<DialogLoadingBinding>(context, themeResId) {
    override fun initBindingResId(): Int = R.layout.dialog_loading

    init {
        initGapWidthContentView(Gravity.CENTER)
    }

    /**
     * 设置等待提示信息
     */
    fun setLoadingMsg(msg: String) {
        if (TextUtils.isEmpty(msg)) return
        binding.tvMsg.text = msg
    }

}