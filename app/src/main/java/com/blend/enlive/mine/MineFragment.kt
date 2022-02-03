package com.blend.enlive.mine

import android.os.Bundle
import com.blend.base.common.ui.BaseFragment
import com.blend.enlive.R
import com.blend.enlive.databinding.FragmentMineBinding


class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {

    override fun layoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}