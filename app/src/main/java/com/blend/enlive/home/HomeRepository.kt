package com.blend.enlive.home

import com.blend.enlive.entity.BannerEntity
import com.blend.enlive.net.NetResponseResult
import com.blend.enlive.net.RetrofitFactory


class HomeRepository {

    suspend fun getHomeBannerList(): NetResponseResult<ArrayList<BannerEntity>> =
        RetrofitFactory.instance.service.getHomeBannerList()

}