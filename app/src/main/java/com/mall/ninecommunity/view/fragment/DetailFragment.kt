package com.mall.ninecommunity.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentDetailBinding
import com.mall.ninecommunity.viewmodels.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun initLayoutId(): Int = R.layout.fragment_detail

    override fun initView() {
        if (args.plantName.isNotEmpty()) dataBinding.tvName.text = args.plantName
    }


    override fun initData() {
        tv_name.setOnClickListener {
            detailViewModel.toShow()
        }
        tv_down.setOnClickListener {
        }

        detailViewModel.newBeanLiveData.observe(this, Observer {
            println(it)
        })
    }

}