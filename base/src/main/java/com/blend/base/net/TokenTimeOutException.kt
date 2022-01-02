package com.blend.base.net

//TODO为什么这里要这样写
class TokenTimeOutException(val code: String, override val message: String?) : Exception()