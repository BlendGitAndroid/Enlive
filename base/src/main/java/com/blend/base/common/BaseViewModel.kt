package com.blend.base.common

import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.blend.base.app.BaseConstant
import com.blend.base.event.SingleLiveEvent
import com.blend.base.kt.ktToastShow
import com.blend.base.kt.show
import com.blend.base.net.BusinessException
import com.blend.base.net.TokenTimeOutException
import com.blend.base.utils.ApplicationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException


abstract class BaseViewModel : AndroidViewModel(ApplicationUtil.getApp()), LifecycleObserver {

    val defUI: UIChange by lazy { UIChange() }

    /**
     * 进行网络请求，界面销毁时自动取消
     *
     * block: 协程执行的block
     * errorBlock: 自定义处理异常
     * showToast: 是否在异常的时候弹出Toast
     * showLoading: 是否显示Loading
     */
    fun rxLaunchUI(
        block: suspend CoroutineScope.() -> Unit,
        errorBlock: ((Throwable) -> Unit)? = null,
        showToast: Boolean = false,
        showLoading: Boolean = false,
        finalBlock: (() -> Unit)? = null,
    ) {
        //使用viewModelScope自动管理生命周期，减少内存泄露
        viewModelScope.launch {
            try {
                if (showLoading) {  //展示loading
                    defUI.showLoading.call()
                }
                block()
            } catch (e: Throwable) {
                e.printStackTrace()
                if (isActive) {
                    if (errorBlock == null) {
                        parseThrowable(e, errorBlock, showToast)
                    } else {
                        errorBlock.invoke(e)
                    }
                }
            } finally {
                if (showLoading) {
                    defUI.dismissLoading.call()
                }
                finalBlock?.invoke()
            }
        }
    }

    /**
     * 统一处理异常方法
     */
    private fun parseThrowable(
        throwable: Throwable,
        errorBlock: ((Throwable) -> Unit)? = null,
        showToast: Boolean = true,
    ) {
        if (throwable is TokenTimeOutException) {
            blockLogoutEventAndDispatchEvent(throwable, errorBlock, showToast)
        } else {
            if (showToast) {
                when (throwable) {
                    is SocketTimeoutException, is UnknownHostException -> "连接超时".ktToastShow()

                    is SSLException -> "证书错误".ktToastShow()

                    is ConnectException -> "网络错误".ktToastShow()

                    is ParseException, is JSONException -> "解析错误".ktToastShow()

                    is BusinessException -> throwable.message.ktToastShow()

                    else -> {
                        throwable.show()
                    }
                }
            }
            errorBlock?.invoke(throwable)
        }
    }

    /**
     * 统一处理登出事件
     */
    private fun blockLogoutEventAndDispatchEvent(
        throwable: TokenTimeOutException,
        errorBlock: ((Throwable) -> Unit)? = null,
        showDialog: Boolean = true,
    ) {
        if (throwable.code == BaseConstant.LOGOUT_STATUS_CODE.toString()) {
            //TODO 事件总线
//            LiveEventBus.get(BaseConstant.EVENT_LOGOUT)
//                .post(LogoutTipsBean(showDialog, throwable.message ?: ""))
        } else {
            errorBlock?.invoke(throwable)
        }
    }

    inner class UIChange {
        val showLoading by lazy { SingleLiveEvent<String>() }
        val dismissLoading by lazy { SingleLiveEvent<Void>() }
        val toastEvent by lazy { SingleLiveEvent<String>() }
        val msgEvent by lazy { SingleLiveEvent<Message>() }
    }

}