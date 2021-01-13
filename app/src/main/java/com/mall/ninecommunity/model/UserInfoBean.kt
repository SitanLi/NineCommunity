package com.mall.ninecommunity.model

import android.os.Parcel
import android.os.Parcelable

/**
 *@createTime :2020/9/28  14:21
 *@Author:XiaopingLi
 *@Description :用户信息接口
 */
data class UserInfoBean(
        var userNo: String?,
        var accessToken: String?,
        var nickName: String?,//用户昵称
        var avatar: String?,//用户头像
        var expiresIn: Int?,//过期时间,秒
        var refreshToken: String?,//刷新token
        var tokenType: String?,//令牌类型
        var relationId: String?,//rid
        var phoneNumber: String?,//手机号
        var sex: Int?,//性别 0-女 1-男
        var mobileAuthenticationStatus: String?,//手机验证状态,Null 未认证,电话号码 已认证
        var nameAuthenticationStatus: Int?,//实名认证状态,0未认证,1认证中,2已认证,3认证失败
        var alipayOpenId: String?,//支付宝账户
        var taobaoNickName: String?,//淘宝昵称
        var wechartNickName: String?,//微信昵称
        var isRemoveWeChart: Boolean = false,//微信是否可以解绑
        var cash: Int?,//现金豆
        var wallet: Double?,//余额
        var accumulated: Double?,//累计收益
        var userCode: Int?,//新用户code,用于区分新用户跳转绑定手机号与设置密码界面;501:跳转设置密码,502:跳转绑定手机号
        var isNewUser: Boolean = true,
        var userId: String?,
        var loginTrackId: Int?,//注册轨迹id：非注册流程为NULL
        var invitationCode: String?,//邀请码
        var roleType:Int?//1:合伙人 2：运营商
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userNo)
        parcel.writeString(accessToken)
        parcel.writeString(nickName)
        parcel.writeString(avatar)
        parcel.writeValue(expiresIn)
        parcel.writeString(refreshToken)
        parcel.writeString(tokenType)
        parcel.writeString(relationId)
        parcel.writeString(phoneNumber)
        parcel.writeValue(sex)
        parcel.writeString(mobileAuthenticationStatus)
        parcel.writeValue(nameAuthenticationStatus)
        parcel.writeString(alipayOpenId)
        parcel.writeString(taobaoNickName)
        parcel.writeString(wechartNickName)
        parcel.writeByte(if (isRemoveWeChart) 1 else 0)
        parcel.writeValue(cash)
        parcel.writeValue(wallet)
        parcel.writeValue(accumulated)
        parcel.writeValue(userCode)
        parcel.writeByte(if (isNewUser) 1 else 0)
        parcel.writeString(userId)
        parcel.writeValue(loginTrackId)
        parcel.writeString(invitationCode)
        parcel.writeValue(roleType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoBean> {
        override fun createFromParcel(parcel: Parcel): UserInfoBean {
            return UserInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoBean?> {
            return arrayOfNulls(size)
        }
    }
}