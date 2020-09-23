package com.mall.baselibrary.acp

/**
 *@Time :2019/11/4
 *@author: Lixiaoping
 *TODO :
 */
object Acp {
    private val acpManager = AcpManager()

    @Synchronized
    fun getAcpManager() = acpManager
}