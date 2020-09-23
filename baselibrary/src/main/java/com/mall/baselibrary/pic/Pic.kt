package com.mall.baselibrary.pic

/**
 * @Author Administrator
 * @Date 2019/10/17-17:26
 * @TODO
 */
object Pic {
    private val picManager = PicManager()
    @Synchronized
    fun getPicManager() = picManager

}
