package com.mall.ninecommunity.view.fragment

import android.view.View
import androidx.navigation.findNavController
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentLoginByPwdBinding


class LoginByPwdFragment : BaseFragment<FragmentLoginByPwdBinding>() {
    companion object {

    }

    override fun initLayoutId(): Int = R.layout.fragment_login_by_pwd

    override fun initData() {
    }

    override fun initView() {
        dataBinding.handler = LoginByPwdHandler()
    }

    inner class LoginByPwdHandler {
        fun showPwdVisible(view: View) {

        }

        fun loginByPwd(view: View) {
            val directions = LoginByPwdFragmentDirections.actionLoginByPwdFragmentToLoginBindingPhoneFragment()
            view.findNavController().navigate(directions)
        }

        fun intentForgetPwd(view: View) {
            val directions = LoginByPwdFragmentDirections.actionLoginByPwdFragmentToForgetPwdFragment()
            view.findNavController().navigate(directions)
        }

        fun intentLoginByCode(view: View) {
            view.findNavController().navigateUp()
        }

        fun changeLoginTipStates(view: View) {}
    }
}