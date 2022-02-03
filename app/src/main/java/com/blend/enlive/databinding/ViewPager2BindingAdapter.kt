package com.blend.enlive.databinding

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2


/** 根据是否平滑滚动 [smoothScroll] 将 [vp] 切换到指定页 [currentItem] */
@BindingAdapter("android:bind_vp2_currentItem", "android:bind_vp2_smoothScroll", requireAll = false)
fun setViewPagerCurrent(vp: ViewPager2, currentItem: Int?, smoothScroll: Boolean?) {

    if (currentItem == null) {
        return
    }
    vp.setCurrentItem(currentItem, smoothScroll == true)

}

/** 将 [vp] 预加载页数设置为 [offscreenPageLimit] */
@BindingAdapter("android:bind_vp2_offscreenPageLimit")
fun setViewPagerOffscreenPageLimit(vp: ViewPager2, offscreenPageLimit: Int?) {
    if (null == offscreenPageLimit) {
        return
    }
    val setLimit = if (offscreenPageLimit < 1) {
        ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
    } else {
        offscreenPageLimit
    }
    vp.offscreenPageLimit = setLimit
}

/** 设置 [vp] 是否支持用户输入 [isUserInputEnabled] */
@BindingAdapter("android:bind_vp2_userInputEnabled")
fun setViewPagerScrollable(vp: ViewPager2, isUserInputEnabled: Boolean?) {
    if (null == isUserInputEnabled) {
        return
    }
    vp.isUserInputEnabled = isUserInputEnabled
}