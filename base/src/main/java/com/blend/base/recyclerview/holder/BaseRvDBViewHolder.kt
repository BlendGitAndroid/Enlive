package com.blend.base.recyclerview.holder

import android.view.View
import androidx.databinding.ViewDataBinding
import com.blend.base.BR


open class BaseRvDBViewHolder<DB : ViewDataBinding, E> : BaseRvViewHolder<E> {

    lateinit var mBinding: DB

    constructor(view: View) : super(view)

    constructor(binding: DB) : this(binding.root) {
        mBinding = binding
    }

    override fun bindData(entity: E) {
        mBinding.setVariable(BR.item, entity)
        mBinding.executePendingBindings()
    }
}