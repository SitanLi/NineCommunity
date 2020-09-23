package com.mall.ninecommunity.adapter.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *@createTime :2020/7/8  17:52
 *@Author:XiaopingLi
 *@Description :fragment
 */
class FragmentAdapter(fragmentManager: FragmentManager, private val fragments: MutableList<Fragment>) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

}