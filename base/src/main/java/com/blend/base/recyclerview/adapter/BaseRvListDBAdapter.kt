package com.blend.base.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.blend.base.BR
import com.blend.base.recyclerview.defaultDiffCallback
import com.blend.base.recyclerview.holder.BaseRvDBViewHolder
import com.blend.base.recyclerview.holder.BaseRvViewHolder

/**
 * RecyclerView 适配器基类
 * - 若要使 头布局、脚布局 等必须在设置适配器之前设置布局管理器
 *
 * @param VH ViewHolder 类型，继承 [BaseRvDBViewHolder]
 * @param DB  DataBinding 类型，与 VH 一致 继承 [ViewDataBinding]
 * @param VM  事件处理类型 ViewModel
 * @param E  数据类型
 *
 * @author 王杰
 */
abstract class BaseRvListDBAdapter<out VH : BaseRvDBViewHolder<DB, E>, DB : ViewDataBinding, VM, E>
    : BaseRvListAdapter<BaseRvViewHolder<E>, E> {

    /**
     * 构造方法
     *
     * @param config Differ config
     */
    constructor(config: AsyncDifferConfig<E>) : super(config)

    /**
     * 构造方法
     *
     * @param diffCallback 数据是否相同回调
     */
    constructor(diffCallback: DiffUtil.ItemCallback<E> = defaultDiffCallback()) : super(diffCallback)

    /** 事件处理  */
    var viewModel: VM? = null

    override fun createSpecialViewHolder(view: View): BaseRvViewHolder<E> {
        @Suppress("UNCHECKED_CAST")
        val clazz = getViewHolderClass().superclass as Class<BaseRvViewHolder<E>>
        val constructor = clazz.getConstructor(View::class.java)
        return constructor.newInstance(view)
    }

    override fun createCustomViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder<E> {
        // 普通布局
        // 加载布局，初始化 DataBinding
        val binding = DataBindingUtil.inflate<DB>(
            LayoutInflater.from(parent.context),
            layoutResID, parent, false
        )
        // 绑定事件处理
        binding.setVariable(BR.viewModel, viewModel)
        // 创建 ViewHolder
        return createViewHolder(binding)
    }

    /**
     * 创建ViewHolder
     *
     * @param binding DataBinding对象
     *
     * @return ViewHolder 对象
     */
    protected open fun createViewHolder(binding: DB): BaseRvDBViewHolder<DB, E> {
        @Suppress("UNCHECKED_CAST")
        val holderConstructor =
            (getViewHolderClass() as Class<BaseRvDBViewHolder<DB, E>>).getConstructor(
                getDataBindingClass())
        return holderConstructor.newInstance(binding)
    }

    /**
     * 获取 DataBinding 的类
     *
     * @return DataBinding 的实际类型
     */
    protected open fun getDataBindingClass(): Class<DB> {
        @Suppress("UNCHECKED_CAST")
        return getActualTypeList()[1] as Class<DB>
    }

    /** 布局 id */
    abstract val layoutResID: Int
}