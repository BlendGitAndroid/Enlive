package com.blend.base.binding_adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView DataBinding 适配器
 */

/**
 * 设置是否使用自身滑动管理
 *
 * @param rv [RecyclerView] 对象
 * @param isNestedScrollingEnabled NestedScrollView 中是否使用自身滑动管理
 */
@BindingAdapter("android:bind_rv_isNestedScrollingEnabled")
fun setRecyclerViewIsNestedScrollingEnabled(rv: RecyclerView, isNestedScrollingEnabled: Boolean) {
    rv.isNestedScrollingEnabled = isNestedScrollingEnabled
}

/**
 * 设置滑动事件监听
 *
 * @param rv [RecyclerView] 对象
 * @param onScrollStateChanged 滑动状态变化监听
 * @param onScrolled 滑动监听
 */
@BindingAdapter("android:bind_rv_onScrollStateChanged",
    "android:bind_rv_onScrolled",
    requireAll = false)
fun setRecyclerViewOnScrollListener(
    rv: RecyclerView,
    onScrollStateChanged: ((RecyclerView, Int) -> Unit)?,
    onScrolled: ((RecyclerView, Int, Int) -> Unit)?,
) {
    rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            onScrollStateChanged?.invoke(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            onScrolled?.invoke(recyclerView, dx, dy)
        }
    })
}

/**
 * 设置布局管理器
 *
 * @param rv [RecyclerView] 对象
 * @param manager 布局管理器
 */
@BindingAdapter("android:bind_rv_layoutManager")
fun setRecyclerViewLayoutManager(rv: RecyclerView, manager: RecyclerView.LayoutManager?) {
    rv.layoutManager = manager
}

/**
 * 设置适配器
 *
 * @param rv [RecyclerView] 对象
 * @param adapter 适配器
 */
@BindingAdapter("android:bind_rv_adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    rv.adapter = adapter
}

/**
 * 设置适配器
 *
 * @param rv [RecyclerView] 对象
 * @param animator Item 动画
 */
@BindingAdapter("android:bind_rv_itemAnimator")
fun setRecyclerItemAnimator(rv: RecyclerView, animator: RecyclerView.ItemAnimator?) {
    rv.itemAnimator = animator
}