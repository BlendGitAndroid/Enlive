package com.blend.base.recyclerview.simple

import android.view.View
import androidx.databinding.ViewDataBinding
import com.blend.base.recyclerview.adapter.BaseRvDBAdapter
import com.blend.base.recyclerview.holder.BaseRvDBViewHolder
import com.blend.base.recyclerview.holder.BaseRvViewHolder
import java.lang.reflect.ParameterizedType


class SimpleRvAdapter<E>(override val layoutResID: Int) : BaseRvDBAdapter<
        SimpleRvAdapter.ViewHolder<E>,
        ViewDataBinding,
        Any,
        E>() {

    override fun getViewHolderClass(): Class<BaseRvViewHolder<E>> {
        @Suppress("UNCHECKED_CAST")
        return ((getActualTypeList()[0] as ParameterizedType).rawType) as Class<BaseRvViewHolder<E>>
    }


    class ViewHolder<E> : BaseRvDBViewHolder<ViewDataBinding, E> {
        constructor(view: View) : super(view)

        constructor(binding: ViewDataBinding) : super(binding)
    }


}