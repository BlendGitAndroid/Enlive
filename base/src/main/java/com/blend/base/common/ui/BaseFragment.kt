package com.blend.base.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.blend.base.BR
import com.blend.base.app.MVVMBlend
import com.blend.base.common.BaseViewModel
import com.blend.base.common.ui.config.FragmentConfig
import com.blend.base.utils.ToastUtil
import com.blend.base.weiget.BaseProgressDialog
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(val config: FragmentConfig = FragmentConfig()) :
    Fragment() {

    protected lateinit var viewModel: VM

    protected lateinit var mBinding: DB

    private lateinit var mBaseView: View

    private var isFirst: Boolean = true

    private var dialog: BaseProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBaseView = initBinding(inflater, container)
        return mBaseView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        onCreateViewModel()
        mBinding.setVariable(BR.viewModel, viewModel)
        lifecycle.addObserver(viewModel)
        registerDefUIChange()
        initView(savedInstanceState)
        startObserve()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    abstract fun initView(savedInstanceState: Bundle?)

    open fun startObserve() {}

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val cls = type.actualTypeArguments[1] as Class<*>
            return if (ViewDataBinding::class.java.isAssignableFrom(cls) && cls != ViewDataBinding::class.java) {
                if (layoutId() == 0) throw IllegalArgumentException("Using DataBinding requires overriding method layoutId")
                mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
                (mBinding as ViewDataBinding).lifecycleOwner = this
                mBinding.root
            } else {
                if (layoutId() == 0) {
                    throw IllegalArgumentException("If you don't use ViewBinding, you need to override method layoutId")
                }
                inflater.inflate(layoutId(), container, false)
            }
        } else {
            throw IllegalArgumentException("BaseFragment user error!")
        }
    }

    open fun layoutId(): Int = 0

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    open fun lazyLoadData() {}

    private fun onCreateViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val argument = type.actualTypeArguments[0]
            val tClass = argument as? Class<VM> ?: BaseViewModel::class.java
            viewModel =
                ViewModelProvider(requireActivity(),
                    defaultViewModelProviderFactory).get(tClass) as VM
        }
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
        (dialog ?: BaseProgressDialog(activity).apply {
            setMessage("正在加载...")
        }).show()
    }


    private fun dismissLoading() {
        dialog?.run { if (isShowing) dismiss() }
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return MVVMBlend.getConfig().viewModelFactory
    }

}