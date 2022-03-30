package com.blend.enlive.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blend.base.recyclerview.adapter.BaseRvListDBAdapter
import com.blend.base.recyclerview.holder.BaseRvDBViewHolder
import com.blend.base.recyclerview.holder.BaseRvViewHolder
import com.blend.base.recyclerview.simple.SimpleRvAdapter
import com.blend.enlive.R
import com.blend.enlive.databinding.FragmentHomeArticlesItemBinding
import com.blend.enlive.entity.ArticleEntity
import com.blend.enlive.entity.ArticleTagEntity


class ArticleListRvAdapter : BaseRvListDBAdapter<ArticleListRvAdapter.ViewHolder,
        FragmentHomeArticlesItemBinding,
        HomeArticleListItemViewModel,
        ArticleEntity>() {

    override val layoutResID: Int = R.layout.fragment_home_articles_item

    //进行数据绑定
    override fun convert(holder: BaseRvViewHolder<ArticleEntity>, entity: ArticleEntity) {
        super.convert(holder, entity)
        (holder as? ViewHolder)?.mBinding?.let {
            val adapter =
                SimpleRvAdapter<ArticleTagEntity>(R.layout.fragment_home_articles_item_tags)
            adapter.viewModel = viewModel
            adapter.mData.addAll(entity.tags.orEmpty())
            it.homeArticlesTagsRv.layoutManager =
                LinearLayoutManager(null, RecyclerView.HORIZONTAL, false)
            it.homeArticlesTagsRv.adapter = adapter
        }
    }


    class ViewHolder(binding: FragmentHomeArticlesItemBinding) :
        BaseRvDBViewHolder<FragmentHomeArticlesItemBinding, ArticleEntity>(binding)

}