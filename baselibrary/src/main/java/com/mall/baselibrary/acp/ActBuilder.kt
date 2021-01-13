package com.mall.baselibrary.acp

import kotlin.Boolean as Boolean1

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
        require(!perMissions.isNullOrEmpty()) { "permissions no found..." }
        dialogCancelable = false
        dialogCanceledOnTouchOutside = false
        rationalMessage = "此功能需要您授权，否则将不能正常使用"
        deniedMessage = "您拒绝权限申请，此功能将不能正常使用，您可以去设置页面重新授权"
        deniedCloseBtn = "关闭"
        deniedSettingBtn = "设置权限"
        rationalBtn = "我知道了"
        return this
    }
}