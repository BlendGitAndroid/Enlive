package com.blend.enlive.net

import com.blend.base.net.BaseRetrofitFactory
import com.blend.enlive.constants.BASE_URL


class RetrofitFactory private constructor(hostUrl: String) : BaseRetrofitFactory() {

    val service by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        getService(ServiceApi::class.java, hostUrl)
    }

    companion object {
        val instance: RetrofitFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitFactory(BASE_URL)
        }
    }

}