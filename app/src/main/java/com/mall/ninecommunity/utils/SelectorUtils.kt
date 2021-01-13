package com.mall.ninecommunity.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import android.view.View
import android.widget.TextView
import com.mall.baselibrary.selector.XSelector
import com.mall.baselibrary.selector.selector.CompoundDrawableSelector

/**

 * 阴影、图片按压背景、文字按压背景、文字按压背景等设置工具
 */
fun View.setShadow(@ColorRes bgColor: Int, @ColorRes shadowColor: Int) {
    XSelector.shadowHelper()
            .setBgColor(bgColor)
            .setShapeRadius(5f)
            .setShadowRadius(5f)
            .setShadowColor(shadowColor)
            .into(this)
}

fun View.setBgPressColor(@ColorInt bgColor: Int, @ColorInt pressedColor: Int) {
    XSelector.shapeSelector()
            .defaultBgColor(bgColor)
            .pressedBgColor(pressedColor)
            .selectorColor( Color.parseColor("#000000"), Color.parseColor("#fffffff"))
            .radius(20f)
            .into(this)
}

fun View.setDefaultColorRadius(@ColorInt bgColor: Int, radius: Float) {
    XSelector.shapeSelector().radius(radius).defaultBgColor(bgColor).into(this)
}


fun TextView.setTextPressColor(@ColorInt defColor: Int, @ColorRes pressedColor: Int) {
    XSelector.colorSelector()
            .defaultColor(defColor)
            .pressedColor(pressedColor)
            .into(this)
}

fun View.setBgPressDrawable(@DrawableRes defDrawableRes: Int, @DrawableRes pressedDrawableRes: Int) {
    XSelector.drawableSelector()
            .defaultDrawable(defDrawableRes)
            .pressedDrawable(pressedDrawableRes)
            .selectorColor("#000000", "#ffffff")
            .into(this)
}

fun TextView.setCompoundPressDrawable(@DrawableRes defDrawableRes: Int, @DrawableRes pressedDrawableRes: Int, @CompoundDrawableSelector.DrawableOrientation drawableOrientation: String) {
    XSelector.compoundDrawableSelector()
            .setDrawablePadding(5f)
            .setDrawableOrientation(drawableOrientation)
            .defaultDrawable(defDrawableRes)
            .pressedDrawable(pressedDrawableRes)
            .selectorColor("#ff0000", "#000000")
            .into(this)
}