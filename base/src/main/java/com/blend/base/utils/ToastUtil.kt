package com.blend.base.utils

import android.widget.Toast


object ToastUtil {

    fun showToast(msg: String) {
        Toast.makeText(ApplicationUtil.getApp(), msg, Toast.LENGTH_LONG).show()
    }


}