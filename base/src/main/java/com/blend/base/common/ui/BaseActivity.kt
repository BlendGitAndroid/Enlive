package com.blend.base.common.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blend.base.app.MVVMBlend
import com.blend.base.common.BaseViewModel
import com.blend.base.common.ui.config.ActivityConfig
import com.blend.base.kt.ktToastShow
import com.blend.base.utils.ApplicationUtil
import com.blend.base.utils.ToastUtil
import com.blend.base.weiget.BaseProgressDialog
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class BaseActivity<VM : BaseViewModel, DB : ViewBinding>(var config: ActivityConfig = ActivityConfig()) :
    AppCompatActivity() {

    protected lateinit var mBinding: ViewBinding
    protected lateinit var viewModel: VM
    private var dialog: BaseProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        lifecycle.addObserver(viewModel)
        //注册UI事件
        registerDefUIChange()
        initTitleBar()
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    open fun layoutId(): Int = 0

    fun initTitleBar() {

    }

    open fun enterFullScreen(activity: Activity) {
        var flags: Int = View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        flags =
            flags or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE // 保持View Layout不变，隐藏状态栏或者导航栏后，View不会拉伸。
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 让View全屏显示，Layout会被拉伸到StatusBar下面，不包含NavigationBar。
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 让View全屏显示，Layout会被拉伸到StatusBar和NavigationBar下面。
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // Activity全屏显示，且状态栏被隐藏覆盖掉。等同于（WindowManager.LayoutParams.FLAG_FULLSCREEN）。
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) // 隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
        // 这个flag只有当设置了SYSTEM_UI_FLAG_HIDE_NAVIGATION才起作用。如果没有设置这个flag，
        // 任意的View相互动作都退出SYSTEM_UI_FLAG_HIDE_NAVIGATION模式。如果设置就不会退出。
        flags = flags or (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        val decorView: View = activity.window.decorView
        if (decorView != null) {
            decorView.setSystemUiVisibility(flags)
        }
    }

    //布局解析
    private fun initViewDataBinding() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val cls = type.actualTypeArguments[1] as Class<*>
            if (ViewDataBinding::class.java.isAssignableFrom(cls) && cls != ViewDataBinding::class.java) {
                if (layoutId() == 0) throw IllegalArgumentException("Using DataBinding requires overriding method layoutId")
                mBinding = DataBindingUtil.setContentView(this, layoutId())
                (mBinding as ViewDataBinding).lifecycleOwner = this
            } else {
                if (layoutId() == 0) {
                    throw IllegalArgumentException("If you don't use ViewBinding, you need to override method layoutId")
                }
                setContentView(layoutId())
            }
            createViewModel(type.actualTypeArguments[0])
        } else {
            throw IllegalArgumentException("BaseActivity user error!")
        }
    }

    /**
     * 创建ViewModel
     */
    private fun createViewModel(type: Type) {
        val tClass = type as? Class<VM> ?: BaseViewModel::class.java
        viewModel =
            ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(tClass) as VM
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return MVVMBlend.getConfig().viewModelFactory
    }

    //注册UI事件
    private fun registerDefUIChange() {
        viewModel.defUI.showLoading.observe(this) {
            showLoading()
        }
        viewModel.defUI.dismissLoading.observe(this) {
            dialog?.run {
                if (isShowing) {
                    dismiss()
                }
            }
        }
        viewModel.defUI.toastEvent.observe(this) {
            ToastUtil.showToast(it)
        }
    }

    private fun showLoading() {
        (dialog ?: BaseProgressDialog(this).apply {
            setMessage("正在加载...")
        }).show()
    }

    private var mLastBackTime = 0L
    private val INTERVAL_TIME = 800

    open fun onBlockBackPressed(): Boolean = false
    open fun doOnBlockBackPressed() {}

    override fun onBackPressed() {
        if (onBlockBackPressed()) {
            doOnBlockBackPressed()
        } else if (config.isDoubleBack) {
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis - mLastBackTime < INTERVAL_TIME) {
                ApplicationUtil.exitApp()
            } else {
                mLastBackTime = currentTimeMillis
                "再按一次退出".ktToastShow()
            }
        } else {
            super.onBackPressed()
        }
    }

}