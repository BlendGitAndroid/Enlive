package com.blend.enlive.home

import com.blend.enlive.entity.ArticleEntity
import com.blend.enlive.entity.ArticleListEntity
import com.blend.enlive.entity.BannerEntity
import com.blend.enlive.net.NetResponseResult
import com.blend.enlive.net.RetrofitFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class HomeRepository {

    //第一页数据
    private val NET_PAGE_START = 0

    suspend fun getHomeBannerList(): NetResponseResult<ArrayList<BannerEntity>> =
        RetrofitFactory.instance.service.getHomeBannerList()


    suspend fun getHomeArticleList(pageNum: Int): NetResponseResult<ArticleListEntity> {
        val ls = arrayListOf<ArticleEntity>()
        if (pageNum == NET_PAGE_START) {
            //TODO async的意思
            val tops = GlobalScope.async {
                RetrofitFactory.instance.service.getHomepageTopArticleList().data.orEmpty()
            }
            tops.await().forEach {
                ls.add(it.copy(top = "1"))
            }
        }
        // 获取文章列表
        val resultAsync = GlobalScope.async {
            RetrofitFactory.instance.service.getHomepageArticleList(pageNum)
        }
        val result = resultAsync.await()
        // 添加文章列表到 ls
        ls.addAll(result.data?.datas.orEmpty())
        // 处理收藏状态
        ls.forEach {
            it.collected.set(it.collect?.toBoolean() == false)
        }
        //TODO copy的使用
        return result.copy(data = result.data?.copy(datas = ls))
    }

}