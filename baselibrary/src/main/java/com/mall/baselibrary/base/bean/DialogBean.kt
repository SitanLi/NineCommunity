package com.mall.baselibrary.base.bean

/**
 * 对话框实体
 */
class DialogBean(
    var isShow: Boolean?,
    var msg: String
){
    constructor() : this(false,"")
}