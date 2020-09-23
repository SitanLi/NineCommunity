package com.mall.baselibrary.acp

import java.util.*
import kotlin.collections.HashSet

/**
 *@Time :2019/11/4
 *@author: Lixiaoping
 *TODO :
 */
object AcpConstant {
//manifest注册的权限集合
    var manifestPermissionSet = HashSet<String>()
    //拒绝授权集合
    var deniedPermissionList = LinkedList<String>()

}