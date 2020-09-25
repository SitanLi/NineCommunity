package com.mall.baselibrary.loadSir

import com.kingja.loadsir.callback.Callback
import com.mall.baselibrary.R

/**
 *Time:2020/7/20
 *Author:HevenHolt
 *Description:
 */
class LoadingCallback : Callback() {
    override fun onCreateView(): Int = R.layout.load_sir_loading
}