package com.blend.enlive.net

import com.blend.enlive.entity.BannerEntity
import retrofit2.http.GET

/**
 * 网络请求接口
 */
interface ServiceApi {

    @GET("/banner/json")
    suspend fun getHomeBannerList() : NetResponseResult<ArrayList<BannerEntity>>

}