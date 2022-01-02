package com.blend.enlive.main

import android.os.Bundle
import com.blend.base.common.ui.BaseActivity
import com.blend.enlive.R
import com.blend.enlive.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

}