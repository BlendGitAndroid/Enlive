package com.blend.enlive.web

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.blend.base.common.ui.BaseActivity
import com.blend.base.kt.startTargetActivity
import com.blend.base.utils.StatusBarUtils
import com.blend.enlive.R
import com.blend.enlive.databinding.AppActivityWebviewBinding
import kotlinx.parcelize.Parcelize


class WebViewActivity : BaseActivity<WebViewViewModel, AppActivityWebviewBinding>() {


    override fun layoutId(): Int = R.layout.app_activity_webview

    /** WebView Fragment 对象 */
    private val webViewFragment: WebViewFragment by lazy {
        WebViewFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary))
        // 获取网页数据
        intent.getParcelableExtra<ActionModel>(WEBVIEW_ACTION_PARCELABLE)?.let { data ->
            viewModel.webData.value = data
        }

        // 加载 Fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.webViewFcv, webViewFragment)
        }

        viewModel.defClose.observe(this, {
            finish()
        })
    }

    override fun onBackPressed() {
        if (!webViewFragment.canGoBack()) {
            super.onBackPressed()
        }
    }


    /**
     * 界面跳转数据 Model
     *
     * @param id 文章 id
     * @param title 标题
     * @param url 打开链接
     */
    @Parcelize
    data class ActionModel(
        val id: String?,
        val title: String?,
        val url: String?,
    ) : Parcelable

    companion object {

        const val WEBVIEW_ACTION_PARCELABLE = "WEBVIEW_ACTION_PARCELABLE"

        /** 使用 [context] 打开 [WebViewActivity] 界面，传递参数网页数据[webData] */
        fun actionStart(context: Context, webData: ActionModel?) {
            context.startTargetActivity<WebViewActivity>(bundleOf(
                WEBVIEW_ACTION_PARCELABLE to webData
            ))
        }
    }

}