package com.blend.base.kt

import android.app.Activity
import android.content.Intent
import kotlin.reflect.KClass

private var triggerLastTime = 0L

fun <T : Activity> Activity.ktStartActivity(clazz: KClass<T>, block: (Intent.() -> Unit)? = null) {
    ktSeriesClick {
        startActivity(Intent(this, clazz.java).apply {
            block?.invoke(this)
        })
    }
}

fun <T : Activity> Activity.ktStartActivityAndFinish(
    clazz: KClass<T>,
    block: (Intent.() -> Unit)? = null,
) {
    ktStartActivity(clazz, block)
    finish()
}

fun ktSeriesClick(time: Long = 600, block: () -> Unit?) {
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= time) {
        block.invoke()
    }
    triggerLastTime = currentClickTime
}