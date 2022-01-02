package com.blend.base.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*
import kotlin.system.exitProcess


object ApplicationUtil {

    private lateinit var sApp: Application
    private var mActivityStack: Stack<Activity> = Stack()
    private var mForegroundActivityCount = 0

    fun initApp(app: Application) {
        sApp = app;
        sApp.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                mActivityStack.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                mForegroundActivityCount++
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                mForegroundActivityCount--
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                mActivityStack.remove(activity)
            }

        })
    }

    /**
     * 获取全局Application
     */
    fun getApp(): Application {
        return sApp
    }

    /**
     * 前台Activity的个数
     * @return > 0，处于前台；= 0，退到后台。
     */
    fun getForegroundActivityCount(): Int {
        return mForegroundActivityCount
    }

    /**
     * 是否在后台
     * @return
     */
    fun isInBackground(): Boolean {
        return mForegroundActivityCount <= 0
    }

    /**
     * 当前Activity
     */
    fun getCurrActivity(): Activity? {
        return mActivityStack.lastElement()
    }

    /**
     * 退出应用
     */
    fun exitApp() {
        finishAllActivities()
        killProcess()
    }

    /**
     * 关闭所有Activity
     */
    private fun finishAllActivities() {
        for (activity in mActivityStack) {
            activity.finish()
        }
    }

    private fun killProcess() {
        //方式1：android.os.Process.killProcess（）
        android.os.Process.killProcess(android.os.Process.myPid())

        // 方式2：System.exit()
        // System.exit() = Java中结束进程的方法：关闭当前JVM虚拟机
        exitProcess(0)

        // System.exit(0)和System.exit(1)的区别
        // 1. System.exit(0)：正常退出；
        // 2. System.exit(1)：非正常退出，通常这种退出方式应该放在catch块中。
    }
}