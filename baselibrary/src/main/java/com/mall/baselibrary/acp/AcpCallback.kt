package com.mall.baselibrary.acp

interface AcpCallback {
    /**
     * 同意
     */
    fun onGranted()

    /**
     * 拒绝
     */
    fun onDenied(permissions: List<String>)
}