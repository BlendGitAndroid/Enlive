package com.blend.enlive.home

import android.util.Log
import android.view.MotionEvent
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blend.base.common.BaseViewModel
import com.blend.base.event.SingleLiveEvent
import com.blend.enlive.entity.BannerEntity
import com.blend.enlive.web.WebViewActivity
import kotlinx.coroutines.*


class HomeViewModel : BaseViewModel() {

    val mRepository = HomeRepository()

    //banner轮播
    private var carouselJob: Job? = null

    //banner下标
    val bannerIndex: ObservableInt = ObservableInt()

    //banner预加载数量
    val bannerLimit: ObservableInt = ObservableInt()

    val bannerData: MutableLiveData<ArrayList<BannerEntity>> = MutableLiveData()


    /** 跳转网页数据 */
    val jumpWebViewData = SingleLiveEvent<WebViewActivity.ActionModel>()

    var bannerCount: Int = 0
        set(count) {
            field = count
            bannerLimit.set(field * 2)
            startCarousel()
        }

    //banner触摸事件
    val onBannerTouch: (MotionEvent) -> Boolean = {
        when (it.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                // 按下、移动，取消轮播
                stopCarousel()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 抬起、取消，开启轮播
                startCarousel()
            }
        }
        false
    }

    fun startCarousel() {
        // 关闭轮播
        stopCarousel()
        if (bannerCount < 2) {
            // Banner 小于2，不轮播
            return
        }
        // 新建协程
        carouselJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                // 延时
                delay(3_000L)
                // 切换
                val current = bannerIndex.get()
                bannerIndex.set((current + 1) % bannerCount)
            }
        }
    }

    fun stopCarousel() {
        if (carouselJob != null) {
            if (carouselJob?.isActive == true) {
                carouselJob?.cancel()
            }
            carouselJob = null
        }
    }

    //获取banner的列表
    fun getHomeBannerList() {
        rxLaunchUI({
            mRepository.getHomeBannerList().data.also {
                Log.e("getHomeBannerList: ", it.toString())
                TODO("postValue，为什么")
                bannerData.postValue(it)
            }

        })

    }

    /** Banner 列表条目点击 */
    val onBannerItemClick: (BannerEntity) -> Unit = { item ->
        jumpWebViewData.value =
            WebViewActivity.ActionModel(item.id.orEmpty(), item.title.orEmpty(), item.url.orEmpty())
    }

}