package com.mall.ninecommunity.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentLoginBindingPhoneBinding

/**
绑定手机
 */
class LoginBindingPhoneFragment : BaseFragment<FragmentLoginBindingPhoneBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = LoginBindingPhoneFragment()
    }

    override fun initLayoutId(): Int=R.layout.fragment_login_binding_phone

    override fun initData() {
    }

    override fun initView() {
    }
}