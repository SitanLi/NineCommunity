package com.mall.ninecommunity.view.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.adapter.main.HomeAdapter
import com.mall.ninecommunity.databinding.FragmentHomeBinding
import com.mall.ninecommunity.viewmodels.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var adapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        fun newInstance(category: String): HomeFragment {
            val args = Bundle()
            args.putString("category", category)
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun initLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        dataBinding.recyclerView.adapter = adapter
        subscribeUi()

    }

    private fun subscribeUi() {
        homeViewModel.storiesBeanList.observe(this, Observer {
            adapter.submitList(it)
        })
    }


    override fun initData() {
        homeViewModel.loadDada()
    }

}