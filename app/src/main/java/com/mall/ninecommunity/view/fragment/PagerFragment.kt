package com.mall.ninecommunity.view.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.baselibrary.base.viewModel.fViewModels
import com.mall.baselibrary.widget.ResourceUtils
import com.mall.baselibrary.widget.tablayout.listener.CustomTabEntity
import com.mall.baselibrary.widget.tablayout.listener.OnTabSelectListener
import com.mall.ninecommunity.R
import com.mall.ninecommunity.adapter.main.FragmentAdapter
import com.mall.ninecommunity.databinding.FragmentPagerBinding
import com.mall.ninecommunity.model.main.TabEntity
import com.mall.ninecommunity.viewmodels.viewmodel.PagerViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PagerFragment : BaseFragment<FragmentPagerBinding>(), OnTabSelectListener, ViewPager.OnPageChangeListener {
    private val pagerViewModel: PagerViewModel by fViewModels()

    override fun initLayoutId(): Int = R.layout.fragment_pager

    override fun initView() {
        val titles = resources.getStringArray(R.array.main_tab)
        val tabSelectIcons = ResourceUtils.getMipmapIdArrays(R.array.main_tab_selected)
        val tabUnSelectIcons = ResourceUtils.getMipmapIdArrays(R.array.main_tab_unselected)
        val listTab: (Int, String) -> CustomTabEntity = { index, s -> TabEntity(s, tabSelectIcons[index], tabUnSelectIcons[index]) }
        val tabList = titles.mapIndexed(listTab)
        dataBinding.tabLayout.setTabData(tabList)
        dataBinding.tabLayout.setOnTabSelectListener(this)
        val fragments = mutableListOf<Fragment>(HomeFragment.newInstance("test1"), HomeFragment.newInstance("test2"),
                HomeFragment.newInstance("test3"), HomeFragment.newInstance("test4"), HomeFragment.newInstance("test5"))
        dataBinding.viewPager.adapter = FragmentAdapter(childFragmentManager, fragments)
        dataBinding.viewPager.addOnPageChangeListener(this)
    }

    override fun initData() {
        dataBinding.model = pagerViewModel
    }

    override fun onTabSelect(position: Int) {
        pagerViewModel.currentItem.value = position
    }

    override fun onTabReselect(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        dataBinding.tabLayout.currentTab = position
    }
}