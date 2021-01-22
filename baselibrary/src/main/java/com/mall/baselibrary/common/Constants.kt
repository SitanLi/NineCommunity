package com.mall.baselibrary.common

/**
 *@Time :2019/11/1
 *@author: Lixiaoping
 *TODO :
 */
object Constants {
    //图片工具类
    var REQUEST_CODE_CUT_PIC = 1007
    var REQUEST_CODE_PICTURE = 1008
    var REQUEST_CODE_CAMERA = 1009
    object Action{
         const val H5_URL = "h5Url"
        const val TITLE = "title"
    }
    object MemoryKey {
        const val CONFIG_URL = "urlConfig"//用于保存URL的MAP
    }

    object CacheKey {
        const val USER_INFO = "userInfo"
    }

    object SPKey {
        const val HEAD_IMG = "headImg"//头像sp
    }
}