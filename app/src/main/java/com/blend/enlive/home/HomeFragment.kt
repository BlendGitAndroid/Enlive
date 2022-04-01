package com.blend.enlive.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blend.base.binding_adapter.SmartRefreshState
import com.blend.base.common.ui.BaseFragment
import com.blend.base.recyclerview.simple.SimpleRvAdapter
import com.blend.base.utils.BarUtils
import com.blend.enlive.R
import com.blend.enlive.databinding.FragmentHomeBinding
import com.blend.enlive.entity.BannerEntity
import com.blend.enlive.web.WebViewActivity


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    //Banner适配器
    private val mBannerAdapter = SimpleRvAdapter<BannerEntity>(R.layout.app_home_banner)

    //文章列表适配器
    private val mArticlesAdapter = ArticleListRvAdapter()

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        //设置状态栏高度
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.homeTopTv)
        mBinding.homeVpBanner.adapter = mBannerAdapter.also { adapter ->
            adapter.viewModel = viewModel
        }

        viewModel.getHomeBannerList()

        viewModel.refreshing.value = SmartRefreshState(true)

        viewModel.getHomeArticleList(0)

        viewModel.run {
            bannerData.observe(this@HomeFragment, {
                // 更新 Banner 列表
                mBannerAdapter.refresh(it)
                // 设置 Banner 数量并开启轮播
                bannerCount = it.size
            })

            articleListData.observe(this@HomeFragment, {
                mArticlesAdapter.submitList(it)
            })

            jumpWebViewData.observe(this@HomeFragment, {
                WebViewActivity.actionStart(requireContext(), it)
            })
        }

        mBinding.homeRvArticles.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = mArticlesAdapter.also { adapter ->
                adapter.viewModel = viewModel.articleListItemViewModel
//                it.setEmptyView(R.layout.app_home_articles_empty)
            }
        }

    }


}