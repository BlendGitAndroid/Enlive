package com.blend.base.kt

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.blend.base.R
import com.blend.base.utils.ApplicationUtil
import com.blend.base.utils.ToastUtil
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun Throwable.show() {
    errorMsg.ktToastShow()
}


fun String.ktToastShow() {
    if (isNotEmpty()) {
        ToastUtil.showToast(this)
    }
}

val Throwable.errorMsg: String
    get() {
        var errorMsg = handleNetworkException(this) //网络异常
        //TODO 异常处理
//        if (errorMsg == null) { //若不是网络原因
//            when (this) {
//                is HttpStatusCodeException -> {  //请求失败异常
//                    errorMsg = result
//                }
//                is JsonSyntaxException -> {   //JSON语法错误
//                    errorMsg = "数据解析失败，请稍后重试"
//                }
//                is ParseException -> {  //JSON解析错误
//                    errorMsg = message ?: errorCode
//                }
//            }
//        }
        return errorMsg ?: message ?: this.toString()
    }

//网络异常情况处理
private fun <T> handleNetworkException(throwable: T): String? {
    val id = if (throwable is UnknownHostException) {   //无网络连接
        if (isNetworkAvailable(ApplicationUtil.getApp())) R.string.network_unavailable else R.string.network_error
    } else if (throwable is SocketTimeoutException || throwable is TimeoutException) {
        R.string.network_timeout    //前者是Socket连接异常，后者是网络请求异常
    } else if (throwable is ConnectException) {
        R.string.network_weak  //连接异常
    } else {
        -1
    }
    return if (id == -1) null else ApplicationUtil.getApp().getString(id)
}

private fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            //Wifi
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            //蜂窝网
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}