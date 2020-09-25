package com.mall.ninecommunity.helper

import android.view.View
import androidx.navigation.findNavController
import com.blankj.utilcode.util.ActivityUtils

/**
 *通用onClick
 */
object OnClickBindingHelper {
    @JvmStatic
    fun onBackPress(view: View, isNavigation: Boolean) {
        if (isNavigation)
            view.findNavController().navigateUp()
        else{
            val topActivity = ActivityUtils.getTopActivity()
            topActivity?.finish()
        }
    }
}