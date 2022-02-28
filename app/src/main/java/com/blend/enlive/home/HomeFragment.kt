package com.blend.enlive.home

import android.os.Bundle
import com.blend.base.common.ui.BaseFragment
import com.blend.base.recyclerview.simple.SimpleRvAdapter
import com.blend.enlive.R
import com.blend.enlive.databinding.FragmentHomeBinding
import com.blend.enlive.entity.BannerEntity


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mBannerAdapter = SimpleRvAdapter<BannerEntity>(R.layout.app_home_banner)

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }


    override fun initView(savedInstanceState: Bundle?) {
        mBinding.vpHomeBanner.adapter = mBannerAdapter.also { adapter ->
            adapter.viewModel = viewModel
        }

        viewModel.getHomeBannerList();

    }


}