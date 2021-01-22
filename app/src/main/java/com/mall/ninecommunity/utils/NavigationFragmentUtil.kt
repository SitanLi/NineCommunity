package com.mall.ninecommunity.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

object NavigationFragmentUtil {
    fun <F : Fragment> getFragment(activity: AppCompatActivity, fragmentClass: Class<F>): F? {
        val navHostFragment = activity.supportFragmentManager.fragments.first() as NavHostFragment
        navHostFragment.childFragmentManager.fragments.forEach {
            if (fragmentClass.isAssignableFrom(it.javaClass)) {
                return it as F
            }
        }
        return null
    }
}