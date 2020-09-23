package com.mall.baselibrary.acp

/**
 *@Time :2019/11/4
 *@author: Lixiaoping
 *TODO :
 */
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