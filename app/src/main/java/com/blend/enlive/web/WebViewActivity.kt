package com.blend.enlive.web

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.blend.base.common.ui.BaseActivity
import com.blend.enlive.R
import com.blend.enlive.databinding.AppActivityWebviewBinding


class WebViewActivity : BaseActivity<WebViewViewModel, AppActivityWebviewBinding>() {


    override fun layoutId(): Int = R.layout.app_activity_webview

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }




    /**
     * 界面跳转数据 Model
     *
     * @param id 文章 id
     * @param title 标题
     * @param url 打开链接
     */

    data class ActionModel(
        val id: String?,
        val title: String?,
        val url: String?,
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(title)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ActionModel> {
            override fun createFromParcel(parcel: Parcel): ActionModel {
                return ActionModel(parcel)
            }

            override fun newArray(size: Int): Array<ActionModel?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {

//        /** 使用 [context] 打开 [WebViewActivity] 界面，传递参数网页数据[webData] */
//        fun actionStart(context: Context, webData: ActionModel?) {
//            context.startTargetActivity<WebViewActivity>(bundleOf(
//                ACTION_PARCELABLE to webData
//            ))
//        }
    }

}