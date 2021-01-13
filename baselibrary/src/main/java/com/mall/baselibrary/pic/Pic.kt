package com.mall.baselibrary.pic

object Pic {
    private val picManager = PicManager()
    @Synchronized
    fun getPicManager() = picManager

}
