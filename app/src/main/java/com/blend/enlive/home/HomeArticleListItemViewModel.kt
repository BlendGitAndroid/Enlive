package com.blend.enlive.home

import com.blend.base.common.BaseViewModel
import com.blend.base.event.SingleLiveEvent
import com.blend.enlive.web.WebViewActivity


class HomeArticleListItemViewModel(
    private val viewModel: BaseViewModel,
    private val jumpToWebViewData: SingleLiveEvent<WebViewActivity.ActionModel>,
) {



}