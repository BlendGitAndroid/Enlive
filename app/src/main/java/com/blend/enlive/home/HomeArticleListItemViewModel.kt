package com.blend.enlive.home

import com.blend.base.event.SingleLiveEvent
import com.blend.enlive.entity.ArticleEntity
import com.blend.enlive.web.WebViewActivity


class HomeArticleListItemViewModel(
    private val jumpToWebViewData: SingleLiveEvent<WebViewActivity.ActionModel>,
) {

    val onArticleItemClick: (ArticleEntity) -> Unit = {
        jumpToWebViewData.value =
            WebViewActivity.ActionModel(it.id.orEmpty(), it.title.orEmpty(), it.link.orEmpty())
    }

}