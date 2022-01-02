package com.blend.base.common.ui.config

import com.blend.base.R


data class FragmentConfig(
    //是否添加状态栏View
    var addStateView: Boolean = false,

    //状态栏背景颜色
    var stateViewColor: Int = R.color.base_white,

    //BaseLoadFragment 初始化加载的文字提示
    var loadingMsg: String = "加载中",
)