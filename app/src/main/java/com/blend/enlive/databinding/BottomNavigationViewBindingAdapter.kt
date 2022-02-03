package com.blend.enlive.databinding

import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * BottomNavigationView DataBinding 适配器
 */

/**
 * 为 [bnv] 设置条目选中回调[itemSelected]
 */
@BindingAdapter("android:bind_bnv_onItemSelected", "android:bind_bnv_selectedIdAttrChanged", requireAll = false)
fun setOnNavigationItemSelectedListener(
    btn: BottomNavigationView,
    itemSelected: ((Int) -> Boolean)?,
    listener: InverseBindingListener?,
) {

    btn.setOnNavigationItemSelectedListener {
        if (btn.selectedItemId != it.itemId) {
            listener?.onChange()
        }
        itemSelected?.invoke(it.itemId) ?: true
    }

}

/**
 * 设置 [bnv] 选中指定 [selectedId] 的条目
 */
@BindingAdapter("android:bind_bnv_selectedId")
fun setNavigationSelectedId(bnv: BottomNavigationView, @IdRes selectedId: Int) {
    bnv.selectedItemId = selectedId
}

/**
 * 获取并返回 [bnv] 当前选中 item 的 id，[Int] 类型
 */
@InverseBindingAdapter(attribute = "android:bind_bnv_selectedId")
fun getNavigationSelectedId(bnv: BottomNavigationView): Int {
    return bnv.selectedItemId
}