package com.blend.base.weiget

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.blend.base.R


class BaseProgressDialog(context: Context?) :
    Dialog(context!!, R.style.blend_dialog_default_style) {

    private var basePbProgress: ProgressBar? = null

    private var basePbMessage: TextView? = null

    init {
        init()
    }

    private var handler: Handler? = null
        private get() {
            if (field == null) {
                field = Handler(Looper.getMainLooper())
            }
            return field
        }

    private fun init() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.blend_progress_dialog, null)
        basePbProgress = view.findViewById(R.id.basePbProgress)
        basePbMessage = view.findViewById(R.id.basePbMessage)
        setContentView(view)
        setCancelable(false)
    }

    fun showProgress(show: Boolean) {
        basePbProgress!!.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setMessage(message: String?) {
        basePbMessage!!.text = message
    }

    fun show(time: Long) {
        if (!isShowing) {
            show()
        }
        handler!!.postDelayed({
            if (isShowing) {
                cancel()
            }
        }, time)
    }

}