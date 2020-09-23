package com.mall.ninecommunity.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.mall.baselibrary.selector.XSelector
import com.mall.baselibrary.selector.selector.CompoundDrawableSelector

/**

 * @Author Administrator
 * @Date 2019/10/21-13:53
 * @TODO 阴影、图片按压背景、文字按压背景、文字按压背景等设置工具
 */
fun View.setShadow(@ColorRes bgColor: Int, @ColorRes shadowColor: Int) {
    XSelector.shadowHelper()
            .setBgColor(bgColor)
            .setShapeRadius(5f)
            .setShadowRadius(5f)
            .setShadowColor(shadowColor)
            .into(this)
}

fun View.setBgPressColor(@ColorRes bgColor: Int, @ColorRes pressedColor: Int) {
    XSelector.shapeSelector()
            .defaultBgColor(bgColor)
            .pressedBgColor(pressedColor)
            .selectorColor("#000000", "#ffffff")
            .radius(20f)
            .into(this)
}


fun TextView.setTextPressColor(@ColorRes defColor: Int, @ColorRes pressedColor: Int) {
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