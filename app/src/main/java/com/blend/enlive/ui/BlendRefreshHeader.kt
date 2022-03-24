package com.blend.enlive.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.airbnb.lottie.LottieAnimationView
import com.blend.base.tools.dip2px
import com.blend.enlive.R
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle


class BlendRefreshHeader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr), RefreshHeader {

    private val lottieAnimationView: LottieAnimationView by lazy {
        findViewById(R.id.blend_refresh_lottie)
    }

    init {
        val attribute = context.obtainStyledAttributes(attrs, R.styleable.BlendRefreshHeader)
        val paddingTop =
            attribute.getDimensionPixelSize(R.styleable.BlendRefreshHeader_header_paddingTop,
                16.dip2px(context).toInt())
        val paddingBottom =
            attribute.getDimensionPixelSize(R.styleable.BlendRefreshHeader_header_paddingTop,
                16.dip2px(context).toInt())
        attribute.recycle()
        inflate(context, R.layout.blend_refresh_header, this)
        val layoutParams = lottieAnimationView.layoutParams as MarginLayoutParams
        layoutParams.topMargin = paddingTop
        layoutParams.bottomMargin = paddingBottom
        lottieAnimationView.layoutParams = layoutParams
    }


    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState,
    ) {
        when (newState) {
            RefreshState.PullDownToRefresh, RefreshState.Refreshing, RefreshState.ReleaseToRefresh -> if (!lottieAnimationView.isAnimating) {
                lottieAnimationView.playAnimation()
            }
            else -> {
            }
        }
    }

    override fun getView(): View {
        return this
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun setPrimaryColors(vararg colors: Int) {

    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int,
    ) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        lottieAnimationView.cancelAnimation()
        lottieAnimationView.frame = 0
        return 0
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {

    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

}