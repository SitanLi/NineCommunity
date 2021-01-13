package com.mall.ninecommunity.http.api

import com.mall.ninecommunity.model.UserInfoBean
import retrofit2.Call
import retrofit2.http.POST

/**
 *@createTime :2020/10/12  11:53
 *@Author:XiaopingLi
 *@Description :用户接口
 */
interface ApiUserService {
    /**
     * 获取用户信息
     */
    @POST("/user-center/platformuser/tplatformuser/queryAppUserInfos")
    fun getUserInfo(): Call<UserInfoBean>
}