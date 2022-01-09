package com.blend.enlive.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.blend.base.common.ui.BaseActivity
import com.blend.enlive.R
import com.blend.enlive.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {


    private lateinit var controller: NavController

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        controller = findNavController(R.id.main_fragment)
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navigator = FragmentNavigator(this, supportFragmentManager, fragment.id)
        controller.navigatorProvider.addNavigator(navigator)
//        controller.setGraph(R.)
    }

}