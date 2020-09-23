package com.mall.baselibrary.selector

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.ConvertUtils

object XSelectorHelper {
    fun getColorRes(@ColorRes colorRes: Int) = XSelector.getContext().resources.getColor(colorRes)

    fun getDrawableRes(@DrawableRes drawableRes: Int): Drawable = XSelector.getContext().resources.getDrawable(drawableRes)

    fun dip2px(dipValue: Float): Int = ConvertUtils.dp2px(dipValue)
}