package com.mall.ninecommunity.http


class ApiException(code: Int, message: String) : Exception(message, Throwable(code.toString()))
class ApiResultDataNullException : Exception()