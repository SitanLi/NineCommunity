package com.mall.ninecommunity.controller.inter

import com.mall.ninecommunity.model.UserInfoBean

/**
 *@createTime :2020/9/28  14:30
 *@Author:XiaopingLi
 *@Description :用户信息
 */
interface UserInfoController {
    /**
     * 请求用户信息
     */
    fun requestUserInfo()

    /**
     * 是否存在用户
     */
    fun hasUserInfo(): Boolean

    /**
     * 获取用户信息
     */
    fun getUserInfo(): UserInfoBean?

    /**
     * 保存用户信息
     */
    fun saveUserInfo(userInfoBean: UserInfoBean)

    /**
     * 清空用户信息
     */
    fun clear()

    /**
     * 获取relationId
     */
    fun getRelationId(): String?

    /**
     * 保存relationId
     */
    fun saveRelationId(relationId: String)
}