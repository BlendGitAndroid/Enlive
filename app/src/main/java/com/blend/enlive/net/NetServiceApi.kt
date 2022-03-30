package com.blend.enlive.net

import com.blend.enlive.entity.ArticleEntity
import com.blend.enlive.entity.ArticleListEntity
import com.blend.enlive.entity.BannerEntity
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 网络请求接口
 */
interface ServiceApi {

    @GET("/banner/json")
    suspend fun getHomeBannerList(): NetResponseResult<ArrayList<BannerEntity>>


    /** 获取并返回首页置顶文章列表 */
    @GET("/article/top/json")
    suspend fun getHomepageTopArticleList(): NetResponseResult<ArrayList<ArticleEntity>>

    @GET("/article/list/{pageNum}/json")
    suspend fun getHomepageArticleList(@Path("pageNum") pageNum: Int): NetResponseResult<ArticleListEntity>

}