package com.blend.enlive.net

import kotlinx.serialization.Serializable


@Serializable
data class NetResponseResult<T>(
    val errorCode: Int? = -1,
    val errorMsg: String? = "",
    val data: T? = null,
)