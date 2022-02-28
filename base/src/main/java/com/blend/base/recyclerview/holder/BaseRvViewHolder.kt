package com.blend.base.recyclerview.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder基类
 */
abstract class BaseRvViewHolder<E>(view: View) : RecyclerView.ViewHolder(view) {

    /**
     * 绑定数据
     */
    abstract fun bindData(entity: E)

}