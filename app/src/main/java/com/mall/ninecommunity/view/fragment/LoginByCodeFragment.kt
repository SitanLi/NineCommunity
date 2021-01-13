package com.mall.ninecommunity.view.fragment

import android.view.View
import androidx.navigation.findNavController
import com.blankj.utilcode.util.ColorUtils
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentLoginByCodeBinding

/**
 * 验证码登录
 */
class LoginByCodeFragment : BaseFragment<FragmentLoginByCodeBinding>() {
    override fun initLayoutId(): Int = R.layout.fragment_login_by_code

    override fun initView() {
        dataBinding.lifecycleOwner = this
        dataBinding.handlers = LoginByCodeHandle()
    }

    override fun initData() {
        dataBinding.searchEdit.setOnTextChangeListener {
            dataBinding.verificationCode.setTextColor(ColorUtils.getColor(if (it.length > 10) R.color.c_ff0030 else R.color.c_bfbfbf))
        }
    }

    inner class LoginByCodeHandle {

        fun requestCode() {

        }

        fun loginByCode(view: View) {
            val directions = LoginByCodeFragmentDirections.actionLoginByCodeFragmentToLoginBindingPhoneFragment()
            view.findNavController().navigate(directions)
        }

        fun intentLoginByPwd(view: View) {
            val directions = LoginByCodeFragmentDirections.actionLoginByCodeFragmentToLoginByPwdFragment()
            view.findNavController().navigate(directions)

        }

        fun changeLoginTipStates(view: View) {

        }
    }
}