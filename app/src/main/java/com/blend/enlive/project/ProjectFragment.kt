package com.blend.enlive.project

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.blend.base.common.ui.BaseFragment
import com.blend.enlive.R
import com.blend.enlive.databinding.FragmentProjectBinding
import com.blend.enlive.im.MessengerService


class ProjectFragment : BaseFragment<ProjectViewModel, FragmentProjectBinding>() {

    private lateinit var mService: Messenger

    override fun layoutId(): Int {
        return R.layout.fragment_project
    }

    override fun initView(savedInstanceState: Bundle?) {

        val intent = Intent(activity, MessengerService::class.java)
        activity?.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        mBinding.imBtn.setOnClickListener {
            val msg = Message.obtain(null, 1)
            val bundle = Bundle()
            bundle.putString("msg", "hello, this is client")
            msg.data = bundle
            msg.replyTo = replyMessenger
            mService.send(msg)
        }

    }

    private val replyMessenger = Messenger(MessengerHandler())

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 2) {
                Log.e("Messenger", "Client: " + msg.data.getString("reply"))
            } else {
                super.handleMessage(msg)
            }
        }
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    override fun onDestroy() {
        activity?.unbindService(mConnection)
        super.onDestroy()
    }


}