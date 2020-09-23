package com.mall.ninecommunity.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentDetailBinding
import com.mall.ninecommunity.dialog.AppUpdateDialog
import com.mall.ninecommunity.utils.InjectorUtils
import com.mall.ninecommunity.viewmodels.viewmodel.DownloadViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val args: DetailFragmentArgs by navArgs()
    private val downloadViewModel: DownloadViewModel by viewModels { InjectorUtils.provideDownloadViewModelFactory() }

    override fun initLayoutId(): Int = R.layout.fragment_detail

    override fun initView() {
        if (args.plantName.isNotEmpty()) dataBinding.tvName.text = args.plantName
    }

    override fun initData() {
        downloadViewModel.initData()
        tv_name.setOnClickListener {
            AppUpdateDialog(requireContext(), R.style.public_Theme_dialog, downloadViewModel.versionBean).show()
        }
        tv_down.setOnClickListener {
        }
    }

}