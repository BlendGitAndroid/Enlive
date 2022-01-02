package com.blend.base.net


class BusinessException(val code: String, override val message: String) : Exception()