package com.mall.ninecommunity.view.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentMineBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MineFragment : BaseFragment<FragmentMineBinding>() {

    companion object {
        fun newInstance(): MineFragment = MineFragment()
    }


    override fun initLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        dataBinding.handlers = MineHandlers()
    }


    override fun initData() {
    }

    inner class MineHandlers {
        fun toLogin(view: View) {
            val directions = PagerFragmentDirections.actionPagerFragmentToNavLogin()
            view.findNavController().navigate(directions)
        }
    }

}