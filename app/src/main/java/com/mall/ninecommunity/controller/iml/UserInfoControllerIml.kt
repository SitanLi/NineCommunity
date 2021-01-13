package com.mall.ninecommunity.controller.iml

import com.blankj.utilcode.util.CacheDoubleStaticUtils
import com.mall.baselibrary.common.Constants
import com.mall.baselibrary.utils.ObjectUtils
import com.mall.ninecommunity.controller.inter.UserInfoController
import com.mall.ninecommunity.http.InternetCallback
import com.mall.ninecommunity.http.api.ApiUserService
import com.mall.ninecommunity.model.UserInfoBean
import javax.inject.Inject

/**
 *@createTime :2020/9/28  14:32
 *@Author:XiaopingLi
 *@Description :用户信息
 */
class UserInfoControllerIml @Inject constructor(private val apiUserService: ApiUserService) : UserInfoController {
    private var userInfo: UserInfoBean? = null

    init {
        userInfo = CacheDoubleStaticUtils.getParcelable(Constants.CacheKey.USER_INFO, UserInfoBean)
    }

    override fun requestUserInfo() {
        val userInfo = getUserInfo() ?: return
        apiUserService.getUserInfo().enqueue(InternetCallback(doNext = {
            ObjectUtils.copyProperties(it, userInfo)
            saveUserInfo(it)
        }))
    }

    override fun hasUserInfo(): Boolean = userInfo != null

    override fun getUserInfo(): UserInfoBean? = userInfo

    override fun saveUserInfo(userInfoBean: UserInfoBean) {
        this.userInfo = userInfoBean
        CacheDoubleStaticUtils.put(Constants.CacheKey.USER_INFO, userInfoBean)
    }

    override fun clear() {
        this.userInfo = null
        CacheDoubleStaticUtils.remove(Constants.CacheKey.USER_INFO)
    }

    override fun getRelationId(): String? = userInfo?.relationId

    override fun saveRelationId(relationId: String) {
        userInfo?.relationId = relationId
        userInfo?.let { saveUserInfo(it) }
    }
}