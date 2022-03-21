package com.blend.base.net

import com.blend.base.tools.jsonDefault
import com.blend.base.utils.ApplicationUtil
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


abstract class BaseRetrofitFactory {

    private val okhttpClient = HttpManager.init(ApplicationUtil.getApp())

    fun <T> getService(serviceClass: Class<T>, hostUrl: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(hostUrl)
            .addConverterFactory(jsonDefault.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
            .client(okhttpClient)
            .build()
        return retrofit.create(serviceClass)
    }

}