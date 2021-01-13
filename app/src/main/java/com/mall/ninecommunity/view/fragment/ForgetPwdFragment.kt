package com.mall.ninecommunity.view.fragment

import android.view.View
import androidx.navigation.findNavController
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentForgetPwdBinding

class ForgetPwdFragment : BaseFragment<FragmentForgetPwdBinding>() {

    override fun initLayoutId(): Int = R.layout.fragment_forget_pwd
    override fun initView() {
        dataBinding.handler = ForgetPwdHandler()
    }

    override fun initData() {
    }

    inner class ForgetPwdHandler {
        fun forgetPassword(view: View) {
            view.findNavController().navigateUp()
        }
    }
}
