package com.blend.enlive.splash

import com.blend.base.common.BaseViewModel


class SplashViewModel : BaseViewModel() {

    val splashRepository = SplashRepository()

    fun queryUserInfo() {
        rxLaunchUI(
            {

            }
        )
    }

}