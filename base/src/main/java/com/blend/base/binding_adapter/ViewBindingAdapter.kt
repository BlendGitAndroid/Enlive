package com.blend.base.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.blend.base.kt.setOnThrottleClickListener
import com.blend.base.constants.DEFAULT_CLICK_THROTTLE_MS


/** 给 [v] 设置点击事件 [click]，传递数据 [item]，并设置重复点击拦截间隔时间 [throttle]，[throttle] 默认 [DEFAULT_CLICK_THROTTLE_MS] */
@BindingAdapter("android:bind_onClick", "android:bind_onClick_item", "android:bind_onClick_throttle", requireAll = false)
fun <T> setViewOnClick(v: View, click: ViewItemClickListener<T>?, item: T, throttle: Long?) {
    if (null == click) {
        v.setOnClickListener(null)
        return
    }
    val interval = throttle ?: DEFAULT_CLICK_THROTTLE_MS
    v.setOnThrottleClickListener({ click.onItemClick(item) }, interval)
}


/** 给 [v] 设置点击事件 [listener] 并设置重复点击拦截间隔时间 [throttle]，[throttle] 默认 [DEFAULT_CLICK_THROTTLE_MS] */
@BindingAdapter("android:bind_onClick", "android:bind_onClick_throttle", requireAll = false)
fun setViewOnClick(v: View, listener: ViewClickListener?, throttle: Long?) {
    if (null == listener) {
        v.setOnClickListener(null)
        return
    }
    val interval = throttle ?: DEFAULT_CLICK_THROTTLE_MS
    v.setOnThrottleClickListener({ listener.onClick() }, interval)
}

/** 给 [v] 设置点击事件 [click] 并设置重复点击拦截间隔时间 [throttle]，[throttle] 默认 [DEFAULT_CLICK_THROTTLE_MS] */
@BindingAdapter("android:bind_onClick", "android:bind_onClick_throttle", requireAll = false)
fun setViewOnClick(v: View, click: ((View) -> Unit)?, throttle: Long?) {
    if (null == click) {
        v.setOnClickListener(null)
        return
    }
    val interval = throttle ?: DEFAULT_CLICK_THROTTLE_MS
    v.setOnThrottleClickListener({ click.invoke(v) }, interval)
}

/** 给 [v] 设置点击事件 [click]，传递数据 [item]，并设置重复点击拦截间隔时间 [throttle]，[throttle] 默认 [DEFAULT_CLICK_THROTTLE_MS] */
@BindingAdapter("android:bind_onClick", "android:bind_onClick_item", "android:bind_onClick_throttle", requireAll = false)
fun <T> setViewOnClick(v: View, click: ((T) -> Unit)?, item: T, throttle: Long?) {
    if (null == click) {
        v.setOnClickListener(null)
        return
    }
    val interval = throttle ?: DEFAULT_CLICK_THROTTLE_MS
    v.setOnThrottleClickListener({ click.invoke(item) }, interval)
}

/** View 点击事件 */
interface ViewClickListener {

    /** 点击回调 */
    fun onClick()
}


/** View 点击事件，传递参数 [T] */
interface ViewItemClickListener<T> {

    /** 点击回调，传递参数 [item] */
    fun onItemClick(item: T)
}

/** 根据是否显示 [show]，是否移除 [gone] 设置 [v] 的显示状态 */
@BindingAdapter("android:bind_visibility", "android:bind_visibility_gone", requireAll = false)
fun setViewVisibility(v: View, show: Boolean?, gone: Boolean? = true) {
    val visibility = if (show == true) View.VISIBLE else if (gone != false) View.GONE else View.INVISIBLE
    if (v.visibility == visibility) {
        return
    }
    v.visibility = visibility
}