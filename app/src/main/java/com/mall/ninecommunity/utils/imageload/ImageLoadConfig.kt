package com.mall.ninecommunity.utils.imageload

/**
 *@Time :2019/11/6
 *@author: Lixiaoping
 *TODO : 资源配置
 */
class ImageLoadConfig {
    companion object {
        private val config = ImageLoadConfig()
        fun instance(): ImageLoadConfig {
            return config
        }
    }

    var circleLoadResId = 0
    var circleErrorResId = 0
    var rectangleLoadResId = 0
    var rectangleErrorResId = 0

    fun initImageResId(circleLoadResId: Int, circleErrorResId: Int, rectangleLoadResId: Int, rectangleErrorResId: Int) {
        this.circleLoadResId = circleLoadResId
        this.circleErrorResId = circleErrorResId
        this.rectangleLoadResId = rectangleLoadResId
        this.rectangleErrorResId = rectangleErrorResId
    }
}