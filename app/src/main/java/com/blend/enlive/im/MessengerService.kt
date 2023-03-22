package com.blend.enlive.im

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log


class MessengerService : Service() {

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                Log.e("Messenger", "Service: " + msg.data.getString("msg"))
                val client = msg.replyTo
                val reply = Message.obtain(null, 2)
                val bundle = Bundle()
                bundle.putString("reply", "copy! this is service")
                reply.data = bundle
                client.send(reply)
            } else {
                super.handleMessage(msg)
            }
        }
    }

    private val mMessenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return mMessenger.binder
    }
}