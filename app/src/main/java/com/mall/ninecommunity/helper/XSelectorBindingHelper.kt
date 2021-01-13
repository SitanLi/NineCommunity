package com.mall.ninecommunity.helper

import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.mall.baselibrary.selector.XSelector

/**
 *Time:2019/12/30
 *Author:HevenHolt
 *Description:布局设置XSelector
 */
object XSelectorBindingHelper {
    /**
     * 渐变色设置
     */
    @BindingAdapter(value = ["xRadius", "xGradientStart", "xGradientEnd", "xGradientOrientation"], requireAll = false)
    @JvmStatic
    fun xSelectorGradient(view: View, xRadius: Float?, @ColorInt xGradientStart: Int, @ColorInt xGradientEnd: Int, xGradientHorizontal: Boolean?) {
        if (xGradientHorizontal == true)
            XSelector.shapeSelector().radius(xRadius
                    ?: 0f).gradientLinear(xGradientStart, xGradientEnd).into(view)
        else
            XSelector.shapeSelector().radius(xRadius
                    ?: 0f).gradient(xGradientStart, xGradientEnd).into(view)
    }

    /**
     * 颜色选择
     */
    @BindingAdapter(value = ["xRadius", "xDefaultBgColor", "disabledBgColor"], requireAll = false)
    @JvmStatic
    fun xSelectorRadius(view: View, xRadius: Float?, @ColorInt xDefaultBgColor: Int, @ColorInt disabledBgColor: Int) {
        XSelector.shapeSelector().defaultBgColor(xDefaultBgColor).disabledBgColor(disabledBgColor).radius(xRadius
                ?: 0f).into(view)
    }

    /**
     * 背景圆角设置(右边圆角)
     */
    @BindingAdapter(value = ["trRadius", "brRadius", "tlRadius", "blRadius", "xDefaultBgColor"], requireAll = false)
    @JvmStatic
    fun xSelectorRadius(view: View, trRadius: Float?, brRadius: Float?, tlRadius: Float?, blRadius: Float?, @ColorInt xDefaultBgColor: Int) {
        XSelector.shapeSelector().defaultBgColor(xDefaultBgColor).trRadius(trRadius
                ?: 0f).brRadius(brRadius ?: 0f)
                .tlRadius(tlRadius ?: 0f).blRadius(blRadius ?: 0f).into(view)
    }

    /**
     * 背景线条绘制
     */
    @BindingAdapter(value = ["xRadius", "xStrokeColor", "xStrokeWidth"], requireAll = false)
    @JvmStatic
    fun xSelectorStroke(view: View, xRadius: Float?, @ColorInt xStrokeColor: Int, xStrokeWidth: Int?) {
        XSelector.shapeSelector().defaultStrokeColor(xStrokeColor).strokeWidth(xStrokeWidth
                ?: 0).radius(xRadius
                ?: 0f).into(view)
    }
}