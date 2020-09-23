package com.mall.baselibrary.acp

import java.security.InvalidParameterException
import kotlin.Boolean as Boolean1

/**
 *@Time :2019/11/4
 *@author: Lixiaoping
 *TODO :
 */
class ActBuilder(
        var perMissions: Array<String>? = arrayOf(),
        var dialogCancelable: Boolean1? = false,
        var dialogCanceledOnTouchOutside: Boolean1? = false,
        var rationalMessage: String? = "此功能需要您授权，否则将不能正常使用",
        var deniedMessage: String? = "您拒绝权限申请，此功能将不能正常使用，您可以去设置页面重新授权",
        var deniedCloseBtn: String? = "关闭",
        var deniedSettingBtn: String? = "设置权限",
        var rationalBtn: String? = "我知道了"
) {
    fun build(): ActBuilder {
        if (perMissions.isNullOrEmpty()) throw IllegalArgumentException("permissions no found...")
        return ActBuilder(perMissions,dialogCancelable,dialogCanceledOnTouchOutside,rationalMessage,deniedMessage,deniedCloseBtn,deniedSettingBtn,rationalBtn)
    }
}