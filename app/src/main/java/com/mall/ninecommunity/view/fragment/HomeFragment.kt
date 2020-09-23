package com.mall.ninecommunity.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.baselibrary.base.viewModel.fViewModels
import com.mall.ninecommunity.R
import com.mall.ninecommunity.adapter.main.PlantAdapter
import com.mall.ninecommunity.databinding.FragmentHomeBinding
import com.mall.ninecommunity.viewmodels.viewmodel.HomeViewModel

/**
 *
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    companion object {
        fun newInstance(category: String): HomeFragment {
            val args = Bundle()
            args.putString("category", category)
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val homeViewModel: HomeViewModel by fViewModels()

    override fun initLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        val mAdapter = PlantAdapter()
        dataBinding.plantList.adapter = mAdapter
        subscribeUi(mAdapter)

    }

    private fun subscribeUi(adapter: PlantAdapter) {
        homeViewModel.plantList.observe(this, Observer {
            adapter.submitList(it)
        })
    }


    override fun initData() {
        homeViewModel.loadDada()
    }

}