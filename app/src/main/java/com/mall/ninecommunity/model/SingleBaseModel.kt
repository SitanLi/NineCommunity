package com.mall.ninecommunity.model

open class SingleBaseModel(
        val code: Int = -1,
        val msg: String = "error"
){
    val isSuccess
        get() = code in 200..299 || code == 0
}