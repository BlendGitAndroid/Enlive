package com.blend.enlive.main

import android.os.Bundle
import com.blend.base.common.ui.BaseActivity
import com.blend.base.utils.BarUtils
import com.blend.enlive.R
import com.blend.enlive.account.AccountFragment
import com.blend.enlive.adapter.setFragmentAdapter
import com.blend.enlive.databinding.ActivityMainBinding
import com.blend.enlive.home.HomeFragment
import com.blend.enlive.main.tab.MAIN_TAB_ACCOUNT
import com.blend.enlive.main.tab.MAIN_TAB_HOME
import com.blend.enlive.main.tab.MAIN_TAB_MINE
import com.blend.enlive.main.tab.MAIN_TAB_PROJECT
import com.blend.enlive.mine.MineFragment
import com.blend.enlive.project.ProjectFragment

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    init {
        config.isDoubleBack = true
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarLightMode(this, false)
        isFullScreen()
        mBinding.vpMain.setFragmentAdapter(this) {
            count(4)
            createFragment { position ->
                when (position) {
                    MAIN_TAB_HOME -> HomeFragment()
                    MAIN_TAB_ACCOUNT -> AccountFragment()
                    MAIN_TAB_PROJECT -> ProjectFragment()
                    MAIN_TAB_MINE -> MineFragment()
                    else -> HomeFragment()
                }
            }
        }
    }

}