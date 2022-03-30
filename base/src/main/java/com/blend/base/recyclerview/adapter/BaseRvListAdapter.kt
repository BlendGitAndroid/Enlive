@file:Suppress("unused", "LeakingThis")

package com.blend.base.recyclerview.adapter

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.blend.base.recyclerview.defaultDiffCallback
import com.blend.base.recyclerview.holder.BaseRvViewHolder

/**
 * RecyclerView 适配器基类
 * - 若要使 头布局、脚布局 等必须在设置适配器之前设置布局管理器
 *
 * @param VH ViewHolder 类型，继承 [BaseRvViewHolder]
 * @param E  数据类型
 */
abstract class BaseRvListAdapter<VH : BaseRvViewHolder<E>, E>
    : AbstractAdapter<BaseRvViewHolder<E>, E> {

    /** 数据集合 */
    val mDiffer: AsyncListDiffer<E>

    private val mListener = AsyncListDiffer.ListListener<E> { previousList, currentList ->
        this.onCurrentListChanged(previousList, currentList)
    }

    /**
     * 构造方法
     *
     * @param config Differ config
     */
    constructor(config: AsyncDifferConfig<E>) {
        val listUpdateCallback = AdapterListUpdateCallback(this)
        mDiffer = AsyncListDiffer(listUpdateCallback, config)
        mDiffer.addListListener(mListener)
    }

    /**
     * 构造方法
     *
     * @param diffCallback 数据是否相同回调
     */
    constructor(diffCallback: DiffUtil.ItemCallback<E> = defaultDiffCallback())
            : this(AsyncDifferConfig.Builder(diffCallback).build())

    override fun getDataCount(): Int {
        return mDiffer.currentList.size
    }

    override fun getItem(position: Int): E {
        return mDiffer.currentList[position]
    }

    override fun getViewHolderClass(): Class<BaseRvViewHolder<E>> {
        @Suppress("UNCHECKED_CAST")
        return getActualTypeList()[0] as Class<BaseRvViewHolder<E>>
    }

    /**
     * 列表变化回调
     */
    open fun onCurrentListChanged(previousList: List<E>, currentList: List<E>) {}

    fun submitList(list: List<E>?) {
        mDiffer.submitList(list)
    }

    fun submitList(list: List<E>?, commitCallback: Runnable?) {
        mDiffer.submitList(list, commitCallback)
    }
}