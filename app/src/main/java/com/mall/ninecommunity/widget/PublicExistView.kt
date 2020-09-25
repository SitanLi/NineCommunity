package com.mall.ninecommunity.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ColorUtils
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.PublicLayoutExistBinding
import com.mall.ninecommunity.helper.OnClickBindingHelper

/**
 *@createTime :2020/8/31  10:32
 *@Author:XiaopingLi
 *@Description :头部退出自定义view
 */
class PublicExistView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private var existBackground: Int? = null
    private var isShowBack: Boolean = true
    private var isNavigation: Boolean = false
    private var backView: Drawable? = null
    private var titleName: String? = null
    private var titleNameColor: Int? = null
    private var rightView: Drawable? = null
    private var rightName: String? = null
    private var rightNameColor: Int? = null
    var rightViewCallback: (() -> Unit)? = null
    var rightNameCallback: (() -> Unit)? = null
    private val binding by lazy {
        DataBindingUtil.inflate<PublicLayoutExistBinding>(LayoutInflater.from(context), R.layout.public_layout_exist, null, false)
    }

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.PublicExistView, 0, 0)
        existBackground = a.getColor(R.styleable.PublicExistView_exist_background, ColorUtils.getColor(R.color.translucent))
        backView = a.getDrawable(R.styleable.PublicExistView_back_view)
        isShowBack = a.getBoolean(R.styleable.PublicExistView_is_show_back, true)
        isNavigation = a.getBoolean(R.styleable.PublicExistView_is_navigation, false)
        titleName = a.getString(R.styleable.PublicExistView_title_name)
        rightView = a.getDrawable(R.styleable.PublicExistView_right_view)
        rightName = a.getString(R.styleable.PublicExistView_right_name)
        titleNameColor = a.getColor(R.styleable.PublicExistView_title_name_color, ColorUtils.getColor(R.color.white))
        rightNameColor = a.getColor(R.styleable.PublicExistView_right_name_color, ColorUtils.getColor(R.color.white))
        a.recycle()
        setLayout()
    }

    private fun setLayout() {
        existBackground?.let { binding.rLayout.setBackgroundColor(it) }
        binding.mBack.visibility = if (isShowBack) View.VISIBLE else View.GONE
        binding.mBack.setOnClickListener { OnClickBindingHelper.onBackPress(this, isNavigation) }
        backView?.let { binding.mBack.setImageDrawable(it) }
        titleName?.let { binding.mTitle.text = it }
        titleNameColor?.let { binding.mTitle.setTextColor(it) }
        rightView?.let { binding.rightView.setImageDrawable(it) }
        rightName?.let { binding.rightName.text = it }
        rightNameColor?.let { binding.rightName.setTextColor(it) }
        binding.rightView.setOnClickListener { rightViewCallback?.invoke() }
        binding.rightName.setOnClickListener { rightNameCallback?.invoke() }
        addView(binding.root)
    }

    fun setTitleName(name: String?) {
        if (name.isNullOrEmpty()) return
        binding.mTitle.text = name
        invalidate()
    }

    fun setIsShowBack(isShow: Boolean = true) {
        binding.mBack.visibility = if (isShow) View.VISIBLE else View.GONE
        invalidate()
    }
}