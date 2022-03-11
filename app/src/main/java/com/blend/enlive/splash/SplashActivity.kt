package com.blend.enlive.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blend.base.common.ui.BaseActivity
import com.blend.base.kt.ktStartActivityAndFinish
import com.blend.enlive.R
import com.blend.enlive.constants.SPLASH_DELAY_MS
import com.blend.enlive.databinding.ActivitySplashBinding
import com.blend.enlive.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (!this.isTaskRoot) {
            val mainIntent = intent
            val action = mainIntent.action
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }
        lifecycleScope.launch {
            ktStartActivityAndFinish(MainActivity::class)
            delay(SPLASH_DELAY_MS)
        }
    }

}