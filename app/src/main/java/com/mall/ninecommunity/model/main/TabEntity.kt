package com.mall.ninecommunity.model.main

import com.mall.baselibrary.widget.tablayout.listener.CustomTabEntity

class TabEntity(
        private val title: String,
        private val selectedIcon: Int,
        private val unSelectedIcon: Int
) : CustomTabEntity {
    override fun getTabTitle(): String = title
    override fun getTabSelectedIcon(): Int = selectedIcon
    override fun getTabUnselectedIcon(): Int = unSelectedIcon
}