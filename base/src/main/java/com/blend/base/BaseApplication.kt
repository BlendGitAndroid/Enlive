package com.blend.base

import android.app.Application
import android.content.Context
import com.blend.base.app.GlobalConfig
import com.blend.base.app.MVVMBlend
import com.blend.base.utils.ApplicationUtil


open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MVVMBlend.install(GlobalConfig())
        ApplicationUtil.initApp(this)
    }

}