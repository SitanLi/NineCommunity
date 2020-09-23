package com.mall.ninecommunity.model

data class VersionBean(
        var version: String?,//	版本号
        var url: String?,//下载地址
        var musted: Int?,//	是否强制更新，0非强制 1强制
        var packager: String?,//	包名
        var remark: String?,//	更新描述
        var progress: Int?,
        var progressTips: String?
) : SingleBaseModel(){
    constructor():this(null,null,null,null,null,null,null)
}